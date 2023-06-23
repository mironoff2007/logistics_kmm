package ru.mironov.logistics.ui

import me.tatarka.inject.annotations.Inject
import ru.mironov.domain.viewmodel.ViewModel
import ru.mironov.common.Logger
import ru.mironov.logistics.repo.ParcelsSynchronizer

@Inject
class SyncViewModel(
    private val parcelsSync: ParcelsSynchronizer,
    private val logger: Logger
) : ViewModel() {

    init {
        parcelsSync.start()
    }

}