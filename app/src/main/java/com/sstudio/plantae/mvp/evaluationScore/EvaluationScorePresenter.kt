package com.sstudio.plantae.mvp.evaluationScore

interface EvaluationScorePresenter {
    fun getAllEvaluationScore()
    fun getEvaluationScore(name: String)
    fun postScore(name: String, score: Double)
    fun deleteEvaluationScore(id: Int)
}