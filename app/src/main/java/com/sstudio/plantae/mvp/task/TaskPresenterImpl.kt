package com.sstudio.plantae.mvp.task

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.sstudio.plantae.BuildConfig.BASE_URL
import com.sstudio.plantae.BuildConfig.TASK_URL
import com.sstudio.plantae.model.Meta
import com.sstudio.plantae.model.Task
import com.sstudio.plantae.remote.IUploadAPI
import com.sstudio.plantae.remote.RetrofitClient
import com.sstudio.plantae.utils.ProgressRequestBody
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class TaskPresenterImpl(private val context: Context, private val taskView: TaskView) :
    TaskPresenter {

    private val apiServices = RetrofitClient.getClient().create(IUploadAPI::class.java)

    override fun getAllTask() {
        taskView.showDialog()
        val call = apiServices.getData()
        call.enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                response.body()?.let { taskView.showTaskList(it.data) }
            }

            override fun onFailure(call: Call<Task>, t: Throwable?) {
                t?.message?.let {
                    taskView.toast(it)
                    Log.d("myTagPresenterAll", it)
                }
                taskView.dismissDialog()
            }
        })
    }

    override fun getTask(name: String) {
        taskView.showDialog()
        Thread(Runnable {
            apiServices.getDataByName(name)
//            apiServices.insertData("insert", "namaa", "judul", "desk", "videoUrl", "aa")
//            apiServices.insertComment("52", "sh", "ssss")
                .enqueue(object : Callback<Task> {
                    override fun onResponse(
                        call: Call<Task>,
                        response: Response<Task>
                    ) {
                        val mResponse = response.body()
                        mResponse?.meta?.code?.let {
                            if (it == 200.toLong()) {
                                taskView.dismissDialog()
                                taskView.callbackCheckTaskExist(response.body()?.data)
                            }
                        }
                    }

                    override fun onFailure(call: Call<Task>, t: Throwable) {
                        taskView.dismissDialog()
                        taskView.toast(t.message + "onFailure.insertData")
                    }
                })
        }).start()
    }

    override fun uploadTask(name: String, selectedFileUri: Uri) {
        taskView.showDialog()

//        val path: String = FileUtils(this, selectedFileUri)
//        val file = File(path)
        val file = File(selectedFileUri.path)
        val requestFile = ProgressRequestBody(file, taskView)
        val body = MultipartBody.Part.createFormData(
            "file",
            "Tugas - $name.${file.extension}",
            requestFile
        )
//        val namePart = MultipartBody.Part.createFormData("file", name)
        val fileBody: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), file /* file name*/)
        val filePart = MultipartBody.Part.createFormData("file", file.name, fileBody)

        val requestBodyBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)

        requestBodyBuilder.addFormDataPart(
            "name",
            null,
            RequestBody.create(MediaType.parse("text/plain"), name)
        )
        requestBodyBuilder.addFormDataPart(
            "doc_image_file", file.name, RequestBody.create(
                MediaType.parse("multipart/form-data"), file
            )
        )
        val multipartBody: MultipartBody = requestBodyBuilder.build()

        val namePart = RequestBody.create(MediaType.parse("text/plain"), name)
        Thread(Runnable {
            apiServices.uploadFile(filePart, namePart)
//            apiServices.insertData("insert", "namaa", "judul", "desk", "videoUrl", "aa")
//            apiServices.insertComment("52", "sh", "ssss")
                .enqueue(object : Callback<Meta> {
                    override fun onResponse(
                        call: Call<Meta>,
                        response: Response<Meta>
                    ) {
                        if (response.body()?.meta?.code == 200.toLong()) {
                            taskView.dismissDialog()
                            taskView.taskUploaded()
                            taskView.toast(response.body()?.meta?.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<Meta>, t: Throwable) {
                        taskView.dismissDialog()
                        taskView.toast(t.message + "onFailure.insertData")
                    }
                })
        }).start()
    }

    override fun deleteTAsk(id: Int) {
        taskView.showDialog()
        Thread(Runnable {
            apiServices.delData(id)
//            apiServices.insertData("insert", "namaa", "judul", "desk", "videoUrl", "aa")
//            apiServices.insertComment("52", "sh", "ssss")
                .enqueue(object : Callback<Task.Meta> {
                    override fun onResponse(
                        call: Call<Task.Meta>,
                        response: Response<Task.Meta>
                    ) {
                        taskView.dismissDialog()
                        val mResponse = response.body()
                        Log.d("myTag taskPresenter", mResponse?.code.toString())
                        taskView.taskDeleted()
                    }

                    override fun onFailure(call: Call<Task.Meta>, t: Throwable) {
                        taskView.dismissDialog()
                        taskView.toast(t.message + "onFailure.delete")
                    }
                })
        }).start()
    }

    override fun taskDownloader(nameFile: String) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("${BASE_URL}task/download/$nameFile")
            )
        )
    }
}