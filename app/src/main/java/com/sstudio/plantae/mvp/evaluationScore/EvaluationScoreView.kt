package com.sstudio.plantae.mvp.evaluationScore

import com.sstudio.plantae.model.EvaluationScore

interface EvaluationScoreView {
    fun showDialog()
    fun dismissDialog()
    fun toast(text: String)
    fun showEvaluationScoreList(score: List<EvaluationScore.Data>)
    fun callbackCheckEvaluationScoreExist(score: List<EvaluationScore.Data>?)
    fun scoreDeleted()
    fun scorePosted()
}