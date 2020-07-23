package com.discom.firemultiplepdf.ok;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.discom.firemultiplepdf.R;
import com.discom.firemultiplepdf.online_download;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class PDFOpener extends AppCompatActivity  {

    private InterstitialAd interstitialAd;
    private AdView adView;
    private final String TAG = PDFOpener.class.getSimpleName();
    private SeekBar seekBar;
    private PDFView pdfView;
    private TextView txtView;
    private String Pdf_link ;
    private String MY_PDF;
    private int STORAGE_PERMISSION_CODE = 1;
    private String getItem;



    public static final String SAMPLE_FILE = "Acids, Bases and Salts.pdf";
    private String pdfFileName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfopener);


        if (ContextCompat.checkSelfPermission(PDFOpener.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestStoragePermission();
        }




        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "331085661108287_331432567740263", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.fb_banner);
        adContainer.addView(adView);
        // adView.loadAd();

        interstitialAd = new InterstitialAd(PDFOpener.this, "331085661108287_331449584405228");
       // interstitialAd.loadAd();

        final WebView webView = findViewById(R.id.webView);


        //S chand class 10 Started here



         getItem = getIntent().getStringExtra("pdfFileNam");


        if (getItem.equals("Acids, Bases and Salts")) {

            pdfView= (PDFView)findViewById(R.id.pdfView);
            displayFromAsset(SAMPLE_FILE);
        }



        if (getItem.equals("2: Micro-Organisms: Friends and Foe")) {
            Pdf_link = "https://firebasestorage.googleapis.com/v0/b/cbse-10-ncert-solution.appspot.com/o/cl8%20ch%20(2).pdf?alt=media&token=3e8c3100-8570-439d-a402-2427e67e2f12";
            pdfView = findViewById(R.id.pdfView);
            txtView = findViewById(R.id.textView);
            MY_PDF = "my_fb10.pdf";
            initSeekBar();
            downloadPdf(MY_PDF);
            getSupportActionBar().setTitle(getItem);
        }


        if (getItem.equals("3: Synthetic Fibers and Plastics")) {
            String url = "https://firebasestorage.googleapis.com/v0/b/cbse-10-ncert-solution.appspot.com/o/cl8%20ch%20(3).pdf?alt=media&token=e9a326ee-5f63-4e84-9d8e-b4b604789abe";
            try {
                url = URLEncoder.encode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String finalURL = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + url;
            ShowContent();

            webView.loadUrl(finalURL);

        }

        if (getItem.equals("Chapter 1: Introduction to Physics")) {

            Pdf_link = "https://firebasestorage.googleapis.com/v0/b/cbse-10-ncert-solution.appspot.com/o/Exampler%2Fclass%206th%2Fscience%2Fch_1.pdf?alt=media&token=d7d176be-f55e-4d11-a3ea-5cb3487f5f13";
            MY_PDF = "my_fb9.pdf";
            Intent start = new Intent(PDFOpener.this, online_download.class);
            String pdf_link2 = Pdf_link;
            String MY_PDF2 = MY_PDF;
            String getItem2 = getItem;
            start.putExtra("2pdf_link",pdf_link2);
            start.putExtra("2MY_PDF",MY_PDF2);
            start.putExtra("2getItem",getItem2);
            startActivity(start);

        }
    }


    private void displayFromAsset(String assetFileName) {
        getItem = assetFileName;

        pdfView.fromAsset(SAMPLE_FILE)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }


    private void ShowContent() {

        final WebView webView = findViewById(R.id.webView);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, final String url) {

            }

        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webView.loadUrl("javascript:(function(){"+"document.querySelector('[role=\"toolbar\"]').remove();})()");
                getSupportActionBar().setTitle("Loading.. PDF 👍");

                if (newProgress == 100) {

                    progressBar.setVisibility(View.GONE);
                    getSupportActionBar().setTitle("S Chand Class 8th");
                }
            }


        });

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
                    interstitialAd.loadAd();
                    File file = getFileStreamPath(fileName);
                    if (file.exists())
                        return true;

                    try {

                        if (!isConnected(PDFOpener.this)) buildDialog(PDFOpener.this).show();
                        requestStoragePermission();

                        interstitialAd.loadAd();
                        FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                        URL u = new URL(Pdf_link);
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

            String getItem = getIntent().getStringExtra("pdfFileNam");

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                Toast.makeText(PDFOpener.this, "PDF Downloaded", Toast.LENGTH_SHORT).show();
                if (aBoolean)  {
                    seekBar.setVisibility(View.GONE);
                    txtView.setVisibility(View.GONE);
                     CardView cardView = findViewById(R.id.card);
                     cardView.setVisibility(View.VISIBLE);{
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (interstitialAd.isAdLoaded()) {
                                   // interstitialAd.show();


                                    // Step 1: Display the interstitial
                                    interstitialAd.setAdListener(new InterstitialAdListener() {
                                        @Override
                                        public void onInterstitialDisplayed(Ad ad) {

                                        }

                                        @Override
                                        public void onError(Ad ad, AdError adError) {

                                            // Ad error callback
                                            Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                                        }

                                        @Override
                                        public void onAdLoaded(Ad ad) {
                                            //interstitialAd.show();
                                        }


                                        @Override
                                        public void onAdClicked(Ad ad) {
                                            // Ad clicked callback
                                            Log.d(TAG, "Interstitial ad clicked!");
                                        }

                                        @Override
                                        public void onLoggingImpression(Ad ad) {
                                            // Ad impression logged callback
                                            Log.d(TAG, "Interstitial ad impression logged!");
                                        }

                                        @Override
                                        public void onInterstitialDismissed(Ad ad) {
                                            String item = MY_PDF;
                                            String item2 = getItem;
                                            Intent start = new Intent(PDFOpener.this, open_pdf.class);
                                            start.putExtra("pdfFileName",item);
                                            start.putExtra("pdfFileNames",item2);
                                            startActivity(start);
                                            finish();
                                        }
                                    });

                                }

                                  else {
                                    String item = MY_PDF;
                                    String item2 = getItem;
                                    Intent start = new Intent(PDFOpener.this, open_pdf.class);
                                    start.putExtra("pdfFileName",item);
                                    start.putExtra("pdfFileNames",item2);
                                    startActivity(start);
                                    finish();
                                    }


                            }
                        });

                    }
                } else {
                    Toast.makeText(PDFOpener.this, "  unable to download pdf", Toast.LENGTH_SHORT).show();
                    if (!isConnected(PDFOpener.this)) buildDialog(PDFOpener.this).show();
                }
            }
        }
                .execute();

    }





    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))return true;
            else return false;

        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or Wifi to Download this. " + "Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();

            }
        });

        return builder;
    }



    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Storage Permission needed")
                    .setMessage("Storage permission is needed for Storing files please grant it")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(PDFOpener.this,
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadPdf(MY_PDF);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }
    }



    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();


    }




}


