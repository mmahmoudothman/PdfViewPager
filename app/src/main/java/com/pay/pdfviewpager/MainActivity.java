package com.pay.pdfviewpager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class MainActivity extends AppCompatActivity implements DownloadFile.Listener {

    private static final String TAG = "MainActivity";
    private RemotePDFViewPager remotePDFViewPager;

    private PDFPagerAdapter pdfPagerAdapter;

    private String url;

    private ProgressBar progressBar;

    private LinearLayout pdfLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set the Visibility of the progressbar to visible
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //initialize the pdfLayout
        pdfLayout = findViewById(R.id.pdf_layout);

        //initialize the url variable
        url = "https://uwaterloo.ca/onbase/sites/ca.onbase/files/uploads/files/samplesecured_256bitaes_pdf.pdf";

        //Create a RemotePDFViewPager object
        remotePDFViewPager = new RemotePDFViewPager(this, url, this);
        }


    @Override
    public void onSuccess(String url, String destinationPath) {

        // That's the positive case. PDF Download went fine
        pdfPagerAdapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(pdfPagerAdapter);
        updateLayout();
        progressBar.setVisibility(View.GONE);
    }

    private void updateLayout() {

        pdfLayout.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onFailure(Exception e) {
        // This will be called if download fails
        Log.e(TAG, "onFailure: " + e.getMessage());
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        // You will get download progress here
        // Always on UI Thread so feel free to update your views here
        Log.e(TAG, "onProgressUpdate: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (pdfPagerAdapter != null) {
            pdfPagerAdapter.close();
        }
    }


}