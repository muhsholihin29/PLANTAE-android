package com.sstudio.plantae.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sstudio.plantae.R
import com.sstudio.plantae.model.EvaluationScore
import com.sstudio.plantae.mvp.evaluationScore.EvaluationScorePresenterImpl
import kotlinx.android.synthetic.main.item_list_task.view.tv_number
import kotlinx.android.synthetic.main.item_score.view.*


class ScoreAdapter(
    private val context: Context,
    private val scorePresenterImpl: EvaluationScorePresenterImpl
):
    RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {

    lateinit var listScore: List<EvaluationScore.Data>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_score, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listScore.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val number = position + 1

        holder.textNo.text = number.toString()
        holder.textName.text = listScore[position].name
        holder.textScore.text = listScore[position].score.toString()
        holder.cvScore.setOnLongClickListener {
            val dialogClickListener =
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            scorePresenterImpl.deleteEvaluationScore(listScore[position].id)
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                        }
                    }
                }
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage("Apakah anda yakin ingin menghapus?").setPositiveButton("Ya", dialogClickListener)
                .setNegativeButton("Tidak", dialogClickListener).show()
           true
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textNo = view.tv_number
        val textName = view.tv_name
        val textScore = view.tv_score
        val cvScore = view.cv_score
    }
}