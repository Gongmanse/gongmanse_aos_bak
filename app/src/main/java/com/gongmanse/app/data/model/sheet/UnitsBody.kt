package com.gongmanse.app.data.model.sheet

import com.gongmanse.app.utils.Constants

data class UnitsBody(
    val id: Int?,
    val units: String?,
    var isCurrent: Boolean = Constants.Init.INIT_BOOLEAN
)