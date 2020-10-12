package com.sstudio.plantae

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import kotlinx.android.synthetic.main.activity_lks.*
import kotlinx.android.synthetic.main.activity_lks.pdfView
import kotlinx.android.synthetic.main.activity_theory.*

class LksActivity : AppCompatActivity(), OnPageChangeListener, OnLoadCompleteListener,
    OnPageErrorListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lks)

//        pdfView.fromUri(Uri.parse("android.resource://${packageName}/" + R.raw.materi))
        pdfView.fromAsset("lks.pdf")
            .defaultPage(0)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .scrollHandle(DefaultScrollHandle(this))
            .spacing(10) // in dp
            .onPageError(this)
            .load();
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