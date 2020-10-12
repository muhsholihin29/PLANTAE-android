package com.sstudio.plantae

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOCUMENTS
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import kotlinx.android.synthetic.main.activity_theory.*


class TheoryActivity : AppCompatActivity(), OnPageErrorListener, OnLoadCompleteListener,
    OnPageChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theory)
        openPdf()
    }

    private fun openPdf() {
        val destination = "${Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS)}/plantae/"
        val fileName = "lks.pdf"
        val filePath = destination+fileName
//        val file = getFileStreamPath(filePath)
//        val fileBrochure = File(Environment.getExternalStorageDirectory().toString() + "/plantae/" + "lks.pdf")
//        if (File(destination, fileName).exists()) {
//            pdfView.fromFile(File(filePath))
//        }else {
//            downloadFile(filePath)
//        }

//        pdfView.fromUri(Uri.parse(filePath))
        pdfView.fromAsset("materi.pdf")
            .defaultPage(0)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .scrollHandle(DefaultScrollHandle(this))
            .spacing(15) // in dp
            .onPageError(this)
            .load();
    }

    private fun downloadFile(filePath: String){
        val uri: Uri = Uri.parse("file://$filePath")
        val url: String = "https://sstudio-project.xyz/plantae/api/v2/task/download/lks.pdf" //paste url here
        val request = DownloadManager.Request(Uri.parse(url))
        request.setDescription("Downloading....")
//        request.setTitle("lks")
        request.setDestinationUri(uri)

        val manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = manager.enqueue(request)

        object : BroadcastReceiver() {
            override fun onReceive(ctxt: Context, intent: Intent) {
                Log.d("Update status", "Download completed $filePath")
                openPdf()
                unregisterReceiver(this)
            }
        }
//        registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onPageError(page: Int, t: Throwable?) {
        Log.e("myTag", "Cannot load page $page")
    }

    override fun loadComplete(nbPages: Int) {

    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        val pageNumber = page
        title = String.format("%s %s / %s", "lks.pdf", page + 1, pageCount)
    }
}