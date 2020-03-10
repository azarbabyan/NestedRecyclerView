package com.artzarbo.nestedRecyclerView.model

/**
 * Created by Artur Zarbabyan on 3/10/2020.
 */
data class Item(
    var lineId: Int,
    var suggestionId: Int?,
    var fare: Double?,
    var duration: Int?,
    var distance: Int?,
    var currency: String?,
    var summary: String?,
    var description: String?,
    var startLocation: String?,
    var endLocation: String?
){
    constructor():
            this(0,null,0.0,250,
                2000,"$","summary",
                "description","startLocation","endLocation")
}