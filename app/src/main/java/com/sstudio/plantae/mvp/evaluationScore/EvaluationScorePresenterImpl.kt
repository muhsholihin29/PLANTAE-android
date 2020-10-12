package com.sstudio.plantae.mvp.evaluationScore

import android.content.Context
import android.util.Log
import com.sstudio.plantae.model.Meta
import com.sstudio.plantae.model.EvaluationScore
import com.sstudio.plantae.remote.RetrofitClient
import com.sstudio.plantae.remote.ScoreAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EvaluationScorePresenterImpl(val context: Context, val evaluationView: EvaluationScoreView): EvaluationScorePresenter {

    private val apiServices = RetrofitClient.getClient().create(ScoreAPI::class.java)

    override fun getAllEvaluationScore() {
        evaluationView.showDialog()
        val call = apiServices.getData()
        call.enqueue(object : Callback<EvaluationScore> {
            override fun onResponse(call: Call<EvaluationScore>, response: Response<EvaluationScore>) {
//                scoreList = response.body()
//                scoreList?.let {  }

                val mResponse = response.body()

                mResponse?.meta?.message?.let {
                    Log.d("myTagEvalScore", mResponse.meta.message)
                    if (it == "Success") {
                        evaluationView.dismissDialog()
                        evaluationView.showEvaluationScoreList(mResponse.data)
                    }
                }
            }

            override fun onFailure(call: Call<EvaluationScore>, t: Throwable?) {
                t?.message?.let {
                    Log.d("myTagEvalScore", it)
                    evaluationView.toast(it)
                }
                evaluationView.dismissDialog()
            }
        })
    }

    override fun getEvaluationScore(name: String) {
        evaluationView.showDialog()
        Thread(Runnable {
            apiServices.getDataByName(name)
//            apiServices.insertData("insert", "namaa", "judul", "desk", "videoUrl", "aa")
//            apiServices.insertComment("52", "sh", "ssss")
                .enqueue(object : Callback<EvaluationScore> {
                    override fun onResponse(
                        call: Call<EvaluationScore>,
                        response: Response<EvaluationScore>
                    ) {
                        val mResponse = response.body()
                        mResponse?.meta?.message?.let {
                            if (it == "Success") {
                                evaluationView.dismissDialog()
                                evaluationView.callbackCheckEvaluationScoreExist(response.body()?.data)
                            }
                        }
                    }

                    override fun onFailure(call: Call<EvaluationScore>, t: Throwable) {
                        evaluationView.dismissDialog()
                        evaluationView.toast(t.message + "onFailure.insertData")
                    }
                })
        }).start()
    }

    override fun postScore(name: String, score: Double) {
        evaluationView.showDialog()
        Thread(Runnable {
            apiServices.postScore(name, score)
//            apiServices.insertData("insert", "namaa", "judul", "desk", "videoUrl", "aa")
//            apiServices.insertComment("52", "sh", "ssss")
                .enqueue(object : Callback<EvaluationScore.Meta> {
                    override fun onResponse(call: Call<EvaluationScore.Meta>, response: Response<EvaluationScore.Meta>) {
                        if (response.body()?.message == "Success") {
                            evaluationView.dismissDialog()
                            evaluationView.scorePosted()
                            evaluationView.toast(response.body()?.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<EvaluationScore.Meta>, t: Throwable) {
                        evaluationView.dismissDialog()
                        evaluationView.toast(t.message + "onFailure.insertData")
                    }
                })
        }).start()
    }

    override fun deleteEvaluationScore(id: Int) {
        evaluationView.showDialog()
        Thread(Runnable {
            apiServices.delData(id)
//            apiServices.insertData("insert", "namaa", "judul", "desk", "videoUrl", "aa")
//            apiServices.insertComment("52", "sh", "ssss")
                .enqueue(object : Callback<EvaluationScore.Meta> {
                    override fun onResponse(call: Call<EvaluationScore.Meta>, response: Response<EvaluationScore.Meta>) {
                        evaluationView.dismissDialog()
//                        Log.d("myTagDelete", "deleteResponse ${response.body()?.message}")
//                        if (response.body()?.message == "Success") {
//                            Log.d("myTagDelete", "deleteSuccess")
                            evaluationView.scoreDeleted()
//                        }
                    }

                    override fun onFailure(call: Call<EvaluationScore.Meta>, t: Throwable) {
                        evaluationView.dismissDialog()
                        evaluationView.toast(t.message + "onFailure.delete")
                    }
                })
        }).start()
    }
}