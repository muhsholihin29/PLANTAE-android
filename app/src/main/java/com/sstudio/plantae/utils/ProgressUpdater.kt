package com.sstudio.plantae.utils

import com.sstudio.plantae.mvp.task.TaskView


class ProgressUpdater(private var uploaded: Int, private var fileLength: Long, private var listener: TaskView) : Runnable {
    override fun run() {
        listener.onProgressUpdate((100*uploaded/fileLength).toInt())
    }
}
