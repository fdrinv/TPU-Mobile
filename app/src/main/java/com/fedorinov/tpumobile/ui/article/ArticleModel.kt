package com.fedorinov.tpumobile.ui.article

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class ArticleModel(
    val id: UUID
): Parcelable
