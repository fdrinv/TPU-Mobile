package com.fedorinov.tpumobile.logic.sync

/**
 * Информационный элемент синхронизации. Содержит в себе информацию текущем объекте синхронизации.
 */
data class SyncInfoItem(
    val id: Int,
    val name: String,
    val state: SyncInfoItemState = SyncInfoItemState.Waiting,
    val estimatedSize: Int
)