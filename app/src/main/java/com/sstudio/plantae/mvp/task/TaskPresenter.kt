package com.sstudio.plantae.mvp.task

import android.net.Uri

interface TaskPresenter {
    fun getAllTask()
    fun getTask(name: String)
    fun uploadTask(name: String, selectedFileUri: Uri)
    fun deleteTAsk(id: Int)
    fun taskDownloader(nameFile: String)
}