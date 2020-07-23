package com.discom.firemultiplepdf.ok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.discom.firemultiplepdf.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import java.io.File;


public class open_pdf extends AppCompatActivity {
    private final String TAG = PDFOpener.class.getSimpleName();
    private InterstitialAd interstitialAd;
    private PDFView pdfView;
    private String getText;
    private String Items;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_pdf);

        getText = getIntent().getStringExtra("pdfFileName");
        Items = getIntent().getStringExtra("pdfFileNames");
        pdfView = findViewById(R.id.pdfView);
        try {
            getSupportActionBar().setTitle(Items);
            File file = getFileStreamPath(getText);
            Log.e("file", "file: " + file.getAbsolutePath());
            pdfView.fromFile(file)

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




    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.main, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_refresh:
                    pdfView = findViewById(R.id.pdfView);

                    try {
                        File file = getFileStreamPath(getText);
                        Log.e("file", "file: " + file.getAbsolutePath());
                        pdfView.fromFile(file)

                                .nightMode(true)
                                .enableAntialiasing(true)
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .scrollHandle(new DefaultScrollHandle(this))
                                .spacing(0)
                                .load();
                        Toast.makeText(open_pdf.this, "Dark mode ON", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    break;


                case R.id.light:
                    pdfView = findViewById(R.id.pdfView);

                    try {
                        File file = getFileStreamPath(getText);
                        Log.e("file", "file: " + file.getAbsolutePath());
                        pdfView.fromFile(file)

                                .enableAntialiasing(true)
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .scrollHandle(new DefaultScrollHandle(this))
                                .spacing(0)
                                .load();
                        Toast.makeText(open_pdf.this, "Dark mode ON", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    break;

                case R.id.delete:
                    pdfView = findViewById(R.id.pdfView);
                    File file = new File(getFilesDir(),(getText));
                    if (file.exists()){
                        deleteFile(getText);
                        Toast.makeText(getApplicationContext(), "PDF Is Deleted", Toast.LENGTH_SHORT).show();
                        super.onBackPressed();

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "PDF not Found", Toast.LENGTH_SHORT).show();
                    }

            }
            return super.onOptionsItemSelected(item);
        }


    }


