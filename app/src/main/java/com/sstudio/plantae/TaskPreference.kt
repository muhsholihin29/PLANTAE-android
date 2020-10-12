package com.sstudio.plantae

import android.content.Context
import com.sstudio.plantae.model.Task

internal class TaskPreference(context: Context) {
    companion object {
        private const val PREFS_TASK = "task_pref"
        private const val NAME = "name"
        private const val TASK = "task"
        private const val TASK_ID = "task_id"

    }

    private val preferences = context.getSharedPreferences(PREFS_TASK, Context.MODE_PRIVATE)
    fun setTask(task: Task.Data) {
        val editor = preferences.edit()
        editor.putString(NAME, task.name)
        editor.putString(TASK, task.task)
        editor.putInt(TASK_ID, task.id)
        editor.apply()
    }

    fun getTask(): Task.Data {
        val model = Task.Data()
        model.name = preferences.getString(NAME, "") as String
        model.task = preferences.getString(TASK, "") as String
        model.id = preferences.getInt(TASK_ID, 0)
        return model
    }
}