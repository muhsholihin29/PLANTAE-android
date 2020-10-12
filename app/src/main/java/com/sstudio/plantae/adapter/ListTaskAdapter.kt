package com.sstudio.plantae.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sstudio.plantae.GlobalVar
import com.sstudio.plantae.R
import com.sstudio.plantae.model.Evaluation
import com.sstudio.plantae.model.Task
import com.sstudio.plantae.mvp.task.TaskPresenterImpl
import kotlinx.android.synthetic.main.item_list_task.view.*

class ListTaskAdapter(private val context: Context, private val taskPresenterImpl: TaskPresenterImpl):
    RecyclerView.Adapter<ListTaskAdapter.ViewHolder>() {

    lateinit var listTask: List<Task.Data>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_task, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val number = position + 1

        holder.textNo.text = number.toString()
        holder.textTask.text = listTask[position].task
        holder.btnDownload.setOnClickListener {
            taskPresenterImpl.taskDownloader(listTask[position].task)
            Log.d("myTagPresenter Url", listTask[position].task)
        }
        holder.cvTask.setOnLongClickListener {
            val dialogClickListener =
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            taskPresenterImpl.deleteTAsk(listTask[position].id)
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
        val textTask = view.tv_list_task
        val btnDownload = view.btn_list_task_download
        val cvTask = view.cv_task
    }
}