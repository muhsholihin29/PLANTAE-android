package com.sstudio.plantae.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Evaluation(
    var question: String,
    var a: String,
    var b: String,
    var c: String,
    var d: String,
    var e: String
): Parcelable