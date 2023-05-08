package com.fedorinov.tpumobile.logic.sync

/**
 * Состояние работы с сущностями одного типа.
 * [Waiting] - ожидание.
 * [Fetching] - получение.
 * [Processing] - обработка.
 * [Done] - завершена работа.
 */
sealed class SyncInfoItemState {
    object Waiting : SyncInfoItemState()
    object Fetching : SyncInfoItemState()
    object Processing : SyncInfoItemState()
    object Done : SyncInfoItemState()
}