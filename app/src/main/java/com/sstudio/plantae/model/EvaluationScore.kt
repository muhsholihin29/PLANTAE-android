package com.sstudio.plantae.model

data class EvaluationScore(
    val data: List<Data>,
    val meta: Meta
) {
    data class Data(
        val id: Int,
        val name: String,
        val score: Double
    )

    data class Meta(
        val code: Int,
        val message: String
    )
}