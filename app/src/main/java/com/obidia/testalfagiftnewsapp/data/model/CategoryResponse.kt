package com.obidia.testalfagiftnewsapp.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Keep
@Parcelize
data class CategoryResponse(
    val categoryName: String
) : Parcelable
