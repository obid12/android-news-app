package com.obidia.testalfagiftnewsapp.data.model.response


import com.google.gson.annotations.SerializedName
import com.obidia.testalfagiftnewsapp.domain.entity.SourcesEntity

data class SourcesResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("sources")
    val sources: MutableList<Sources>?
)

data class Sources(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
) {
    fun toSourceEntity() = SourcesEntity(
        id ?: "",
        name ?: ""
    )
}