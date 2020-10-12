package com.sstudio.plantae

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.plantae.adapter.EvalAdapter
import com.sstudio.plantae.adapter.ListTaskAdapter
import com.sstudio.plantae.model.Task
import com.sstudio.plantae.mvp.task.TaskPresenterImpl
import com.sstudio.plantae.mvp.task.TaskView
import kotlinx.android.synthetic.main.activity_list_task.*

class ListTaskActivity : AppCompatActivity(), TaskView {
    
    private lateinit var taskPresenterImpl: TaskPresenterImpl
    private lateinit var inAnimation: AlphaAnimation
    private lateinit var outAnimation: AlphaAnimation

    private lateinit var listTaskAdapter: ListTaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_task)

        setSupportActionBar(toolbar_list_task)
        toolbar_list_task.title = "Daftar tugas siswa"

        taskPresenterImpl = TaskPresenterImpl(this, this)
        taskPresenterImpl.getAllTask()

        swipe_list_task.setOnRefreshListener {
            taskPresenterImpl.getAllTask()
            swipe_list_task.isRefreshing = false
        }
    }

    override fun onProgressUpdate(percenage: Int) {
        
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
        
    }

    override fun showTaskList(task: List<Task.Data>) {
        listTaskAdapter = ListTaskAdapter(this, taskPresenterImpl)
        listTaskAdapter.listTask = task
        rv_task.isNestedScrollingEnabled = false
        rv_task.layoutManager = LinearLayoutManager(this)
        rv_task.adapter = listTaskAdapter
        listTaskAdapter.notifyDataSetChanged()
        dismissDialog()
    }

    override fun callbackCheckTaskExist(task: List<Task.Data>?) {
        
    }

    override fun taskDeleted() {
        Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show()
        taskPresenterImpl.getAllTask()
        listTaskAdapter.notifyDataSetChanged()
    }

    override fun taskUploaded() {
        
    }
}