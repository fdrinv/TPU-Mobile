package com.fedorinov.tpumobile.ui.model

import java.util.Date
import java.util.UUID

data class Article(
    val id: UUID? = null,
    val name: String? = "",
    val text: String? = "",
    val topic: String? = "",
    val createDate: Date? = null
)
