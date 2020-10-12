package com.sstudio.plantae.model

data class Meta(
    val meta: Meta
) {
    data class Meta(
        val code: Long,
        val message: String
    )
}