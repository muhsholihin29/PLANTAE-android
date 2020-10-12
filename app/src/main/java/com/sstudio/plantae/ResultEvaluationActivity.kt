package com.sstudio.plantae

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.plantae.adapter.EvalAdapter
import com.sstudio.plantae.adapter.ListTaskAdapter
import com.sstudio.plantae.adapter.ScoreAdapter
import com.sstudio.plantae.model.EvaluationScore
import com.sstudio.plantae.model.Task
import com.sstudio.plantae.mvp.evaluationScore.EvaluationScorePresenterImpl
import com.sstudio.plantae.mvp.evaluationScore.EvaluationScoreView
import kotlinx.android.synthetic.main.activity_list_task.*
import kotlinx.android.synthetic.main.activity_result_evaluation.*
import kotlinx.android.synthetic.main.activity_result_evaluation.progressBarHolder

class ResultEvaluationActivity : AppCompatActivity(), EvaluationScoreView {

    private lateinit var scorePresenterImpl: EvaluationScorePresenterImpl
    private lateinit var inAnimation: AlphaAnimation
    private lateinit var outAnimation: AlphaAnimation
    private lateinit var scoreAdapter: ScoreAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_evaluation)

        setSupportActionBar(toolbar_list_score)
        toolbar_list_score.title = "Hasil evaluasi siswa"

        scorePresenterImpl = EvaluationScorePresenterImpl(this, this)
        scorePresenterImpl.getAllEvaluationScore()

        swipe_list_score.setOnRefreshListener {
            scorePresenterImpl.getAllEvaluationScore()
            swipe_list_score.isRefreshing = false
        }
    }
    override fun showDialog() {
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation.duration = 200
        progressBarHolder.animation = inAnimation
        progressBarHolder.visibility = View.VISIBLE
    }

    override fun dismissDialog() {
        outAnimation = AlphaAnimation(1f, 0f)
        outAnimation.duration = 200
        progressBarHolder.animation = outAnimation
        progressBarHolder.visibility = View.GONE
    }

    override fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun showEvaluationScoreList(score: List<EvaluationScore.Data>) {
        scoreAdapter = ScoreAdapter(this, scorePresenterImpl)
        scoreAdapter.listScore = score
        rv_score.isNestedScrollingEnabled = false
        rv_score.layoutManager = LinearLayoutManager(this)
        rv_score.adapter = scoreAdapter
        scoreAdapter.notifyDataSetChanged()
        dismissDialog()
    }

    override fun callbackCheckEvaluationScoreExist(score: List<EvaluationScore.Data>?) {
        
    }

    override fun scoreDeleted() {
        scorePresenterImpl.getAllEvaluationScore()
        scoreAdapter.notifyDataSetChanged()
    }

    override fun scorePosted() {
        
    }
}