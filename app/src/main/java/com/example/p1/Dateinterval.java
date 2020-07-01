package com.example.p1;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Dateinterval extends AppCompatActivity {


    private List<Date> listDate;
    private CalendarPickerView datePicker;
    private static String simpleStartDate, simpleEndDate;
    private static final String STARTDATE = "startDate";
    private static final String STOPDATE = "stopDate";
    private ShowIncomesWithinDateinterval showIncomesWithinDateinterval;
    static final String CHOICE = "choice" ;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date);

        if (savedInstanceState != null) {
            simpleStartDate = savedInstanceState.getString(STARTDATE);
            simpleEndDate = savedInstanceState.getString(STOPDATE);
        }

        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.YEAR, 2);

        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.DAY_OF_MONTH, -20);
     //   minDate.add(Calendar.MONTH, -1);

        datePicker = findViewById(R.id.datePicker);

        datePicker.init(minDate.getTime(), maxDate.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE);


     /*   datePicker.init(today, calendar.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);*/



        final Button btnNext = findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setTextColor(ContextCompat.getColor(this, R.color.colorInputField));





        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);
                btnNext.setEnabled(false);
                btnNext.setTextColor(ContextCompat.getColor(Dateinterval.this,
                        R.color.colorInputField));
                datePicker.getSelectedDates();
                listDate = datePicker.getSelectedDates();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

                for (int i = 0; i < listDate.size(); i++) {

                    Date startDate = listDate.get(0);
                    Date stopDate = listDate.get(listDate.size() - 1);
                    simpleStartDate = sdf.format(startDate);
                    simpleEndDate = sdf.format(stopDate);
                }

                if (!(simpleStartDate.equals(simpleEndDate))) {
                    btnNext.setEnabled(true);
                    btnNext.setTextColor(ContextCompat.getColor(Dateinterval.this, R.color.colorYellow));
                }
            }

            @Override
            public void onDateUnselected(Date date) {
            }
        });


          Intent intent = getIntent();
            String res = intent.getStringExtra(CHOICE);
            final int choice = Integer.parseInt((res));
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choice == 1) {
                    Intent intent = new Intent(Dateinterval.this, ShowIncomesWithinDateinterval.class);
                    intent.putExtra(STARTDATE, simpleStartDate);
                    intent.putExtra(STOPDATE, simpleEndDate);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Dateinterval.this, ShowExpensesWithinDateinterval.class);
                    intent.putExtra(STARTDATE, simpleStartDate);
                    intent.putExtra(STOPDATE, simpleEndDate);
                    startActivity(intent);
                }



            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STARTDATE, simpleStartDate);
    }

    public static String getStartDate() {
        return simpleStartDate;
    }

    public static String getEndDate() {
        return simpleEndDate;
    }

    public void setShowIncomesWithinDateinterval(ShowIncomesWithinDateinterval showIncomesWithinDateinterval){
        this.showIncomesWithinDateinterval = showIncomesWithinDateinterval;

    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
    }
}











/*

package com.example.p1;
        import android.content.Intent;
        import android.content.res.Resources;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.content.ContextCompat;
        import com.squareup.timessquare.CalendarPickerView;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
public class Dateinterval extends AppCompatActivity {
    private List<Date> listDate;
    private CalendarPickerView datePicker;
    private static String simpleStartDate, simpleEndDate;
    private static final String STARTDATE = "startDate";
    private static final String STOPDATE = "stopDate";
    private ShowIncomesWithinDateinterval showIncomesWithinDateinterval;
    static final String CHOICE = "choice" ;
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date);
        if (savedInstanceState != null) {
            simpleStartDate = savedInstanceState.getString(STARTDATE);
            simpleEndDate = savedInstanceState.getString(STOPDATE);
        }
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 2);
        final Button btnNext = findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setTextColor(ContextCompat.getColor(this, R.color.colorInputField));
        datePicker = findViewById(R.id.datePicker);
        datePicker.init(today, calendar.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);
        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);
                btnNext.setEnabled(false);
                btnNext.setTextColor(ContextCompat.getColor(Dateinterval.this,
                        R.color.colorInputField));
                datePicker.getSelectedDates();
                listDate = datePicker.getSelectedDates();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
                for (int i = 0; i < listDate.size(); i++) {
                    Date startDate = listDate.get(0);
                    Date stopDate = listDate.get(listDate.size() - 1);
                    simpleStartDate = sdf.format(startDate);
                    simpleEndDate = sdf.format(stopDate);
                }
                if (!(simpleStartDate.equals(simpleEndDate))) {
                    btnNext.setEnabled(true);
                    btnNext.setTextColor(ContextCompat.getColor(Dateinterval.this, R.color.colorYellow));
                }
            }
            @Override
            public void onDateUnselected(Date date) {
            }
        });
        Intent intent = getIntent();
        String res = intent.getStringExtra(CHOICE);
        final int choice = Integer.parseInt((res));
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choice == 1) {
                    Intent intent = new Intent(Dateinterval.this, ShowIncomesWithinDateinterval.class);
                    intent.putExtra(STARTDATE, simpleStartDate);
                    intent.putExtra(STOPDATE, simpleEndDate);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Dateinterval.this, ShowExpensesWithinDateinterval.class);
                    intent.putExtra(STARTDATE, simpleStartDate);
                    intent.putExtra(STOPDATE, simpleEndDate);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STARTDATE, simpleStartDate);
    }
    public static String getStartDate() {
        return simpleStartDate;
    }
    public static String getEndDate() {
        return simpleEndDate;
    }
    public void setShowIncomesWithinDateinterval(ShowIncomesWithinDateinterval showIncomesWithinDateinterval){
        this.showIncomesWithinDateinterval = showIncomesWithinDateinterval;
    }
    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
    }
}*/

