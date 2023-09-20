package ru.mironov.logistics.repo

import com.mironov.database.parcel.ParcelsDbSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.common.ktor.source.ParcelsWebSource

@Inject
class ParcelsSynchronizer(
    private val parcelsDbSource: ParcelsDbSource,
    private val parcelsWebSource: ParcelsWebSource,
    private val logger: Logger,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val supervisor = SupervisorJob()
    private val scope = CoroutineScope(dispatcher  + supervisor)

    private var job: Job? = null
    private var timerJob: Job? = null
    private var period = 10L //seconds

    fun start() {
        stop()
        timerJob = scope.launch {
            logger.logD(LOG_TAG, "start sync")
            while (this.isActive) {
                delay(period * 1000)
                logger.logD(LOG_TAG, "sync with period $period s")
                job?.join()
                job = scope.launch {
                    try {
                        val parcels = parcelsDbSource.fetchNotSynced().map { it.toServerParcel() }
                        if (parcels.isNotEmpty()) {
                            logger.logD(LOG_TAG, "sync size ${parcels.size}")
                            val resp = parcelsWebSource.registerParcels(parcels)
                            if (resp) {
                                logger.logD(LOG_TAG, "sync ok")
                                parcelsDbSource.replaceAllTransaction(parcels.map { it.toSyncedParcel() })

                                clearSynced()
                            }
                            else {
                                logger.logD(LOG_TAG, "sync fail")
                            }
                        }
                        else {
                            logger.logD(LOG_TAG, "all synced")
                            clearSynced()
                        }
                    }
                    catch (e: Exception) {
                        logger.logE(LOG_TAG, e.stackTraceToString())
                    }
                }
            }
        }
    }

    private suspend fun clearSynced() {
        val syncedParcels = parcelsDbSource.fetchSynced()
        val notDeleted = parcelsDbSource.fetchSynced().isNotEmpty()
        if (notDeleted) {
            parcelsDbSource.delete(syncedParcels)
            val deleted = parcelsDbSource.fetchSynced().isEmpty()
            logger.logD(LOG_TAG, "synced ${syncedParcels.size} parcels are deleted $deleted")
        }
    }

    fun stop() {
        try {
            if (timerJob?.isActive == true) {
                logger.logD(LOG_TAG, "stop sync")
                timerJob?.cancel()
            }
        }
        catch (e: Exception) {
            logger.logE(LOG_TAG, e.stackTraceToString())
        }
    }

    companion object {
        private const val LOG_TAG = "ParcelsSynchronizer"
    }
}
