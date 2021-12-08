package com.example.p1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowSelectedExpense extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.p1.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.example.p1.EXTRA_TITLE";

    public static final String EXTRA_SUM =
            "com.example.p1.EXTRA_SUM";

    public static final String EXTRA_CATEGORY =
            "com.example.p1.EXTRA_CATEGORY";

    public static final String EXTRA_DATE =
            "com.example.p1.EXTRA_DATE";

    public static final String EXTRA_BARCODE =
            "com.example.p1.EXTRA_BARCODE";


    private TextView title;
    private TextView sum;
    private TextView data;
    private TextView category;
    private ImageView image_view_category;
    private TextView text_view_barcode;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_expenses);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        title = (TextView)findViewById(R.id.text_view_title);
        sum = (TextView)findViewById(R.id.text_view_sum);
        data = (TextView)findViewById(R.id.text_view_data);
        image_view_category = (ImageView)findViewById(R.id.image_view_category);
        text_view_barcode = (TextView)findViewById(R.id.text_view_barcode);
        setText();
    }

    public void setText(){
        Intent intent = getIntent();
        String livsmedel = "livsmedel";
        String fritid = "fritid";
        String boende = "boende";
        String resor ="resor";
        String övrigt = "övrigt";

        String titleToSet = intent.getStringExtra(EXTRA_TITLE);
        title.setText("Titel:  \n" +titleToSet);

        int sumToSet = intent.getIntExtra(EXTRA_SUM, 0);
        String summa = Integer.toString(sumToSet);
        sum.setText("Summa: " + summa + " kr");


        String dataToSet = intent.getStringExtra(EXTRA_DATE);
        data.setText("Datum: " + dataToSet);

        String categoryToSet = intent.getStringExtra(EXTRA_CATEGORY);

        if (categoryToSet.equals(livsmedel)){
            image_view_category.setImageResource(R.drawable.livsmedel);
        }
        if (categoryToSet.equals(fritid)){
            image_view_category.setImageResource(R.drawable.fritid);
        }
        if (categoryToSet.equals(boende)){
            image_view_category.setImageResource(R.drawable.boende);
        }
        if (categoryToSet.equals(resor)){
            image_view_category.setImageResource(R.drawable.resor);
        }
        if (categoryToSet.equals(övrigt)){
            image_view_category.setImageResource(R.drawable.other);
        }

        String setBarCode = intent.getStringExtra(EXTRA_BARCODE);
        if (setBarCode != null){
            text_view_barcode.setText(setBarCode);
        } else {
            text_view_barcode.setText("Ingen streckkod");
        }

    }
}
