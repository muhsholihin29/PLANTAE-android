package com.sstudio.plantae

import android.R.attr.button
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codekidlabs.storagechooser.StorageChooser
import com.sstudio.plantae.model.Task
import com.sstudio.plantae.mvp.task.TaskPresenterImpl
import com.sstudio.plantae.mvp.task.TaskView
import kotlinx.android.synthetic.main.activity_upload_task.*
import kotlinx.android.synthetic.main.dialog_login.*


class UploadTaskActivity : AppCompatActivity(), TaskView {

    private var pathSelected = ""
    private lateinit var dialog: ProgressDialog
    private lateinit var taskPresenterImpl: TaskPresenterImpl
    private lateinit var taskPreference: TaskPreference
    private lateinit var inAnimation: AlphaAnimation
    private lateinit var outAnimation: AlphaAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_task)

        taskPresenterImpl = TaskPresenterImpl(this, this)
        taskPreference = TaskPreference(this)
        dialog = ProgressDialog(this)
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        dialog.setMessage("Uploading...")
        dialog.isIndeterminate = false
        dialog.max = 100
        dialog.setCancelable(false)
        checkTaskExist()

        btn_choose.setOnClickListener {
            chooserFile()
        }
        btn_upload.setOnClickListener {
            if (pathSelected == ""){
                toast("Harap pilih file")
            }else{
                taskPresenterImpl.uploadTask(taskPreference.getTask().name, Uri.parse(pathSelected))
            }
        }
        btn_delete.setOnClickListener {
            taskPresenterImpl.deleteTAsk(taskPreference.getTask().id)
            Log.d("myTag", taskPreference.getTask().id.toString())
            showDialog()
        }
    }

    private fun checkTaskExist() {
        showDialog()
        taskPresenterImpl.getTask(taskPreference.getTask().name)
    }

    private fun chooserFile() {

        val formats: ArrayList<String> = ArrayList()
        formats.add("pdf")

        // Initialize Builder
        val chooser = StorageChooser.Builder()
            .withActivity(this)
            .withFragmentManager(fragmentManager)
            .withMemoryBar(true)
            .allowCustomPath(true)
            .customFilter(formats)
            .setType(StorageChooser.FILE_PICKER)
            .build()

        // Show dialog whenever you want by
        chooser.show()

        // get path that the user has chosen
        chooser.setOnSelectListener { path ->
            Log.e("SELECTED_PATH", path)
            tv_upload_message.text = path.substring(path.lastIndexOf("/") + 1)
            pathSelected = path
        }
    }

    override fun onProgressUpdate(percenage: Int) {
        dialog.progress = percenage
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
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }


    override fun showTaskList(task: List<Task.Data>) {

    }

    override fun callbackCheckTaskExist(taskList: List<Task.Data>?) {
        dismissDialog()
        if (taskList != emptyList<Task.Data>() && taskList != null) {
            val task = taskList.first()
            taskPreference.setTask(Task.Data(task.id, task.name, task.task, "", ""))

            //change button to delete & info file
            btn_choose.visibility = View.GONE
            btn_delete.visibility = View.VISIBLE
            btn_upload.isEnabled = false
            tv_upload_message.text = task.task

            //setPreference
            taskPreference.setTask(Task.Data(task.id, task.name, task.task, "", ""))
        }
    }

    override fun taskDeleted() {
        dismissDialog()

        //change button & info file
        btn_choose.visibility = View.VISIBLE
        btn_delete.visibility = View.GONE
        tv_upload_message.text = "Tidak ada file"
        btn_upload.isEnabled = true
        checkTaskExist()
    }

    override fun taskUploaded() {
        checkTaskExist()
    }
}