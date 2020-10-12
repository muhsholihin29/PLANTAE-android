package com.sstudio.plantae

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sstudio.plantae.model.Task
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_finish_eval.*
import kotlinx.android.synthetic.main.dialog_finish_eval.btn_dialEval_yes
import kotlinx.android.synthetic.main.dialog_login.*


class HomeActivity : AppCompatActivity() {

    private val REQUEST_PERMISSION = 112
    private lateinit var taskPreference: TaskPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        permissions()
        taskPreference = TaskPreference(this)
        if (taskPreference.getTask().name == "") {
            dialogLogin()
        }
        if (taskPreference.getTask().name == "guru") {
            home_menu_btn_list_task.visibility = View.VISIBLE
            home_menu_btn_eval_result.visibility = View.VISIBLE
        }

        home_menu_materi.setOnClickListener {
            startActivity(Intent(this, TheoryActivity::class.java))
        }

        home_menu_kdipk.setOnClickListener {
            startActivity(Intent(this, KDnIPKActivity::class.java))
        }

        home_menu_lks.setOnClickListener {
            startActivity(Intent(this, LksActivity::class.java))
        }

        home_menu_eval.setOnClickListener {
            startActivity(Intent(this, EvaluationActivity::class.java))
        }

        home_menu_angket.setOnClickListener {
            startActivity(Intent(this, QuestionnaireActivity::class.java))
        }

        home_menu_upload.setOnClickListener {
            startActivity(Intent(this, UploadTaskActivity::class.java))
        }

        btn_home_about.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        home_menu_btn_list_task.setOnClickListener {
            startActivity(Intent(this, ListTaskActivity::class.java))
        }

        home_menu_btn_eval_result.setOnClickListener {
            startActivity(Intent(this, ResultEvaluationActivity::class.java))
        }

        btn_home_logout.setOnClickListener {
            taskPreference.setTask(Task.Data())
            dialogLogin()
        }
    }

    private fun permissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,),
                REQUEST_PERMISSION
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val hasPermission = ContextCompat.checkSelfPermission(
                baseContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET
                    ),
                    REQUEST_PERMISSION
                )
            }
        }
    }

    private fun dialogLogin(){
        btn_home_logout.visibility = View.GONE
        val dialogLogin = Dialog(this)
        dialogLogin.setContentView(R.layout.dialog_login)
        dialogLogin.setCancelable(false)
        dialogLogin.btn_login.setOnClickListener {
            if (dialogLogin.et_login_name.text.toString() != "") {
                if (dialogLogin.et_login_name.text.toString() == "guru") {
                    if (dialogLogin.et_login_password.text.toString() != "guruguru")
                        Toast.makeText(this, "$ _Error", Toast.LENGTH_SHORT).show()
                    else{
                        home_menu_btn_list_task.visibility = View.VISIBLE
                        home_menu_btn_eval_result.visibility = View.VISIBLE
                        taskPreference.setTask(Task.Data(0, dialogLogin.et_login_name.text.toString(), "", "", ""))
                        dialogLogin.dismiss()
                        btn_home_logout.visibility = View.VISIBLE
                        }
                }else if (dialogLogin.et_login_password.text.toString() == "siswa"){
                    taskPreference.setTask(Task.Data(0, dialogLogin.et_login_name.text.toString(), "", "", ""))
                    dialogLogin.dismiss()
                    home_menu_btn_list_task.visibility = View.GONE
                    home_menu_btn_eval_result.visibility = View.GONE
                    btn_home_logout.visibility = View.VISIBLE
                }else{
                    Toast.makeText(this, "Password salah", Toast.LENGTH_SHORT).show()
                }

            }
        }
        dialogLogin.show()
    }
}