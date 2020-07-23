package com.discom.firemultiplepdf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.discom.firemultiplepdf.ok.down;
import com.discom.firemultiplepdf.ok.on;

public class online_download extends AppCompatActivity
        implements View.OnClickListener {

    private String pdf_link3;
    private String MY_PDF3;
    private String getItem3;
    private CardView downcard,oncard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_download);


        pdf_link3 = getIntent().getStringExtra("2pdf_link");
        MY_PDF3 = getIntent().getStringExtra("2MY_PDF");
        getItem3 = getIntent().getStringExtra("2getItem");



        downcard = (CardView) findViewById(R.id.down_card);
        oncard = (CardView) findViewById(R.id.on_card);


        downcard.setOnClickListener(this);
        oncard.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        Intent i;
        switch (v.getId()) {
            case R.id.down_card:
                i = new Intent(this, down.class);
                String pdf_link4 = pdf_link3;
                String MY_PDF4 = MY_PDF3;
                String getItem4 = getItem3;
                i.putExtra("3pdf_link",pdf_link4);
                i.putExtra("3MY_PDF",MY_PDF4);
                i.putExtra("3getItem",getItem4);
                startActivity(i);
                break;
            case R.id.on_card:
                i = new Intent(this, on.class);
                startActivity(i);
                break;
        }
    }
}