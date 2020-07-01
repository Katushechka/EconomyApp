package com.example.p1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowSelectedIncome extends AppCompatActivity {

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


    private TextView title;
    private TextView sum;
    private TextView data;
    private TextView category;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_incomes);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        title = (TextView)findViewById(R.id.text_view_title);
        sum = (TextView)findViewById(R.id.text_view_sum);
        data = (TextView)findViewById(R.id.text_view_data);
        category = (TextView)findViewById(R.id.image_view_category);
        setText();
    }

    public void setText(){
        Intent intent = getIntent();

        String titleToSet = intent.getStringExtra(EXTRA_TITLE);
        title.setText("Titel:  \n" +titleToSet);

       int sumToSet = intent.getIntExtra(EXTRA_SUM, 0);
       String summa = Integer.toString(sumToSet);
       sum.setText("Summa: \n "+summa + " kr");


        String dataToSet = intent.getStringExtra(EXTRA_DATE);
        data.setText("Datum: " +dataToSet);

        String categoryToSet = intent.getStringExtra(EXTRA_CATEGORY);
        category.setText("Kategori: " +categoryToSet);

    }
}
