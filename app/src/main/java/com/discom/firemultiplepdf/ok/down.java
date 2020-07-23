package com.discom.firemultiplepdf.ok;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.discom.firemultiplepdf.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class down extends AppCompatActivity {
    private final String TAG = PDFOpener.class.getSimpleName();
    private PDFView pdfView;
    private String pdf_link5;
    private String MY_PDF5;
    private String getItem5;
    private SeekBar seekBar;
    private TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);

        pdf_link5 = getIntent().getStringExtra("3pdf_link");
        MY_PDF5 = getIntent().getStringExtra("3MY_PDF");
        getItem5 = getIntent().getStringExtra("3getItem");
        txtView = findViewById(R.id.textView);
        pdfView = findViewById(R.id.pdfView);

        initSeekBar();
        downloadPdf(MY_PDF5);


    }



    private void initSeekBar() {

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int val = (progress * (seekBar.getWidth() - 3
                        * seekBar.getThumbOffset())) / seekBar.getMax();
                txtView.setText("" + progress);
                txtView.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);

                getSupportActionBar().setTitle("Downloading.. PDF 👍");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void downloadPdf(final String fileName) {

        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                return downloadPdf();
            }


            @Nullable
            private Boolean downloadPdf() {
                try {
                    File file = getFileStreamPath(fileName);
                    if (file.exists())
                        return true;

                    try {
                        FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                        URL u = new URL(pdf_link5);
                        URLConnection conn = u.openConnection();
                        int contentLength = conn.getContentLength();
                        InputStream input = new BufferedInputStream(u.openStream());
                        byte data[] = new byte[contentLength];
                        long total = 0;
                        int count;
                        while ((count = input.read(data)) != -1) {
                            total += count;
                            publishProgress((int) ((total * 100) / contentLength));
                            fileOutputStream.write(data, 0, count);
                        }

                        fileOutputStream.flush();
                        fileOutputStream.close();
                        input.close();
                        return true;
                    } catch (final Exception e) {
                        e.printStackTrace();
                        return false; // swallow a 404

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;

            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                seekBar.setProgress(values[0]);

            }


            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                Toast.makeText(down.this, "PDF Downloaded", Toast.LENGTH_SHORT).show();
                if (aBoolean)  {
                    seekBar.setVisibility(View.GONE);
                    txtView.setVisibility(View.GONE);
                    openPdf(fileName);
                } else {
                    Toast.makeText(down.this, "  unable to download pdf", Toast.LENGTH_SHORT).show();

                }
            }
        }
                .execute();

    }
    private void openPdf(String fileName) {
        try {
            getSupportActionBar().setTitle("Showing PDF 👍");
            File file = getFileStreamPath(fileName);
            Log.e("file", "file: " + file.getAbsolutePath());
            seekBar.setVisibility(View.GONE);
            pdfView.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(getItem5);
            pdfView.fromFile(file)
                    .nightMode(true)
                    .enableAntialiasing(true)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .spacing(0)
                    .load();

        } catch (Exception e) {
            e.printStackTrace();

        }



    }

    }
