package com.sstudio.plantae.mvp.task

import android.graphics.Bitmap
import com.sstudio.plantae.model.Task

interface TaskView {
    fun onProgressUpdate(percenage: Int)
    fun showDialog()
    fun dismissDialog()
    fun toast(text: String)
    fun showTaskList(task: List<Task.Data>)
    fun callbackCheckTaskExist(task: List<Task.Data>?)
    fun taskDeleted()
    fun taskUploaded()
}