package com.sstudio.plantae.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sstudio.plantae.GlobalVar.quizAnswer
import com.sstudio.plantae.R
import com.sstudio.plantae.model.Evaluation
import kotlinx.android.synthetic.main.item_quiz.view.*


class EvalAdapter(private var context: Context) :
    RecyclerView.Adapter<EvalAdapter.ViewHolder>() {

    var quiz: List<Evaluation> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quiz.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quizTrue =
            intArrayOf(2, 0, 1, 1, 4, 1, 1, 0, 1, 0, 2, 4, 3, 1, 2) //0=A 1=B 2=C 3=D 4=E
        val number = position + 1

        holder.textNo.text = number.toString()
        holder.textQuiz.text = quiz[position].question
        holder.rbA.text = quiz[position].a
        holder.rbB.text = quiz[position].b
        holder.rbC.text = quiz[position].c
        holder.rbD.text = quiz[position].d
        holder.rbE.text = quiz[position].e

        if (number == 15){
            holder.imgQuiz.visibility = View.VISIBLE
            holder.imgQuiz.setImageResource(R.drawable.quiz15)
        }

        holder.rbA.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                holder.rbB.isChecked = false
                holder.rbC.isChecked = false
                holder.rbD.isChecked = false
                holder.rbE.isChecked = false
                if (quizTrue[position] == 0) { //quiz positive
                    quizAnswer[position] = 6.66
                    Log.d("myTag", "$position ${quizAnswer[position]}")
                } else {
//                        locusInput[position] = 1
                    Log.d("myTag", "$position ${quizAnswer[position]}")
                }
            }
        }
        holder.rbB.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                holder.rbA.isChecked = false
                holder.rbC.isChecked = false
                holder.rbD.isChecked = false
                holder.rbE.isChecked = false
                if (quizTrue[position] == 1) { //quiz positive
                    quizAnswer[position] = 6.66
                    Log.d("myTag", "$position ${quizAnswer[position]}")
                } else {
//                        locusInput[position] = 1
                    Log.d("myTag", "$position ${quizAnswer[position]}")
                }
            }
        }
        holder.rbC.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                holder.rbB.isChecked = false
                holder.rbA.isChecked = false
                holder.rbD.isChecked = false
                holder.rbE.isChecked = false
                if (quizTrue[position] == 2) { //quiz positive
                    quizAnswer[position] = 6.66
                    Log.d("myTag", "$position ${quizAnswer[position]}")
                } else {
//                        locusInput[position] = 1
                    Log.d("myTag", "$position ${quizAnswer[position]}")
                }
            }
        }
        holder.rbD.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                holder.rbB.isChecked = false
                holder.rbC.isChecked = false
                holder.rbA.isChecked = false
                holder.rbE.isChecked = false
                if (quizTrue[position] == 3) { //quiz positive
                    quizAnswer[position] = 6.66
                    Log.d("myTag", "$position ${quizAnswer[position]}")
                } else {
//                        locusInput[position] = 1
                    Log.d("myTag", "$position ${quizAnswer[position]}")
                }
            }
        }
        holder.rbE.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                holder.rbB.isChecked = false
                holder.rbC.isChecked = false
                holder.rbD.isChecked = false
                holder.rbA.isChecked = false
                if (quizTrue[position] == 4) { //quiz positive
                    quizAnswer[position] = 6.66
                    Log.d("myTag", "$position ${quizAnswer[position]}")
                } else {
//                        locusInput[position] = 1
                    Log.d("myTag", "$position ${quizAnswer[position]}")
                }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textNo = view.tv_number
        val cardView = view.cv_quiz
        val rbA = view.rb_a
        val rbB = view.rb_b
        val rbC = view.rb_c
        val rbD = view.rb_d
        val rbE = view.rb_e
        val textQuiz = view.tv_quiz
        val imgQuiz = view.img_quest
    }
}