package com.fedorinov.tpumobile.data.database.entity

import java.util.UUID

abstract class BaseServerEntity(
    open val externalId: UUID? = null
)