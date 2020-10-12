package com.sstudio.plantae

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sstudio.plantae.adapter.EvalAdapter
import com.sstudio.plantae.model.Evaluation
import com.sstudio.plantae.model.EvaluationScore
import com.sstudio.plantae.mvp.evaluationScore.EvaluationScorePresenterImpl
import com.sstudio.plantae.mvp.evaluationScore.EvaluationScoreView
import kotlinx.android.synthetic.main.activity_evaluation.*
import kotlinx.android.synthetic.main.dialog_finish_eval.*
import kotlinx.android.synthetic.main.dialog_skor_quiz.*

class EvaluationActivity : AppCompatActivity(), EvaluationScoreView {

    lateinit var evalAdapter: EvalAdapter
    private lateinit var evaluationScorePresenterImpl: EvaluationScorePresenterImpl
    private lateinit var taskPreference: TaskPreference
    private lateinit var inAnimation: AlphaAnimation
    private lateinit var outAnimation: AlphaAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluation)


        taskPreference = TaskPreference(this)
        evaluationScorePresenterImpl = EvaluationScorePresenterImpl(this, this)
        checkEvaluationScoreExist()
        evalAdapter = EvalAdapter(this)
        rv_locus.isNestedScrollingEnabled = false
        rv_locus.layoutManager = LinearLayoutManager(this)
        rv_locus.adapter = evalAdapter

        btn_eval_finish.setOnClickListener {
            dialogFinishEval()
        }
    }

    private fun checkEvaluationScoreExist() {
        btn_eval_finish.visibility = View.GONE
        evaluationScorePresenterImpl.getEvaluationScore(taskPreference.getTask().name)
    }

    private fun parseJsonEval(): List<Evaluation> {
        val gson = Gson()
        val dataEval = resources.getStringArray(R.array.eval)
        var eval: List<Evaluation> = ArrayList()
        for (i in dataEval){
            eval = eval + gson.fromJson(i, Evaluation::class.java)
        }
        return eval
    }

    private fun dialogScore(score: Double?){
        val dialogAdd = Dialog(this)
            dialogAdd.setContentView(R.layout.dialog_skor_quiz)
        if (score != null){
            dialogAdd.tv_score.text = score.toString()
        }else {
            dialogAdd.tv_score.text = GlobalVar.quizAnswer.sum().toString()
        }

        dialogAdd.btn_dialScorel_yes.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            dialogAdd.dismiss()
            finish()
        }
        dialogAdd.setCancelable(false)
        dialogAdd.show()
    }

    private fun dialogFinishEval(){
        val dialogAdd = Dialog(this)
        dialogAdd.setContentView(R.layout.dialog_finish_eval)

        dialogAdd.btn_dialEval_yes.setOnClickListener {
            dialogAdd.dismiss()
            dialogScore(null)
            evaluationScorePresenterImpl.postScore(taskPreference.getTask().name, GlobalVar.quizAnswer.sum())
        }
        dialogAdd.btn_dialEval_no.setOnClickListener {
            dialogAdd.dismiss()
        }
        dialogAdd.show()
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
        
    }

    override fun callbackCheckEvaluationScoreExist(score: List<EvaluationScore.Data>?) {
        if (score != null && score.isNotEmpty()){
            val mScore = score.first()
            dialogScore(mScore.score)
        }else{
            evalAdapter.quiz = parseJsonEval()
            evalAdapter.notifyDataSetChanged()
            btn_eval_finish.visibility = View.VISIBLE
        }
    }

    override fun scoreDeleted() {
        
    }

    override fun scorePosted() {
        toast("Evaluasi selesai")
    }
}