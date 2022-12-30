package com.obidia.testalfagiftnewsapp.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep

@Keep
@kotlinx.parcelize.Parcelize
data class SourcesEntity(
    val idSource: String,
    val name: String,
) : Parcelable
