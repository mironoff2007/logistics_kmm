package ru.mironov.logistics.repo

import com.mironov.database.parcel.ParcelsTable
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
import ru.mironov.logistics.parcel.ParcelsApi

@Inject
class ParcelsSynchronizer(
    private val parcelsTable: ParcelsTable,
    private val parcelsApi: ParcelsApi,
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
                        val parcels = parcelsTable.fetchNotSynced().map{ it.toServerParcel() }
                        if (parcels.isNotEmpty()) {
                            logger.logD(LOG_TAG, "sync size ${parcels.size}")
                            val resp = parcelsApi.registerParcels(parcels)
                            if (resp) {
                                logger.logD(LOG_TAG, "sync ok")
                                parcelsTable.replaceAllTransaction(parcels.map { it.toSyncedParcel() })
                            }
                            else {
                                logger.logD(LOG_TAG, "sync fail")
                            }
                        }
                        else {
                            logger.logD(LOG_TAG, "all synced")
                        }
                    }
                    catch (e: Exception) {
                        logger.logE(LOG_TAG, e.stackTraceToString())
                    }
                }
            }
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
