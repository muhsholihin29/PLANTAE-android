package com.sstudio.plantae.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val data: List<Data> = ArrayList(),
    val meta: Meta? = null
): Parcelable {
    @Parcelize
    data class Data(
        var id: Int = 0,
        var name: String = "",
        var task: String = "",
        val created_at: String = "",
        val updated_at: String = ""
    ): Parcelable

    @Parcelize
    data class Meta(
        @SerializedName("code")
        val code: Long,
        val message: String
    ): Parcelable
}