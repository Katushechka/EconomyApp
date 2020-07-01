package com.example.p1;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static me.dm7.barcodescanner.zxing.ZXingScannerView.*;
public class GeneralInfo extends AppCompatActivity {
    int status = 0;
    private ZXingScannerView scannerView;
    private EconomyViewModel incomeViewModel;
    static final String CHOICE = "choice" ;
    private ButtonsFragment buttonsFragment;
    private FabFragment fabFragment;
    private PersonalInfoFragment personalInfoFragment;
    private static final String NAME  = "name";
    static final String SURNAME = "surname";
    private String name;
    private String surname;
    private ZXingScannerView scanner;
    static final String BARCODE = "barcode";
    private boolean scan = false;
    private  String resultCode;


    ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalinfo);

            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

            personalInfoFragment = new PersonalInfoFragment();
            setpersonalInfoFragment(personalInfoFragment, false);

            buttonsFragment = new ButtonsFragment();
            buttonsFragment.setGeneralInfo(this);
            setbuttonsFragment(buttonsFragment, false);

            fabFragment = new FabFragment();
            fabFragment.setGeneralInfo(this);
            setfabFragment(fabFragment, false);
            setPersonalInfo();


    }

    public void setPersonalInfo(){
        Intent intent = getIntent();
        name = intent.getStringExtra(NAME);
        surname = intent.getStringExtra(SURNAME);
        personalInfoFragment.setText(name, surname);
    }

    public void btbfabIncomeClicked(){
        Intent intent = new Intent (GeneralInfo.this, SetNote.class);
        intent.putExtra(CHOICE, "1");
        startActivity(intent);
    }

    public void btbfabExpenseClicked(){
        Intent intent = new Intent (GeneralInfo.this, SetNote.class);
        intent.putExtra(CHOICE, "2");
        startActivity(intent);
    }
    public void btnGeneralInfoClicked(){
        Intent intent = new Intent (GeneralInfo.this, Information.class);
        intent.putExtra(NAME, name);
        intent.putExtra(SURNAME, surname);
        startActivity(intent);
    }

    public void scanCode(View view) {

        scanner = new ZXingScannerView(this);
        scanner.setResultHandler(new ZXingScannerResultHandler());


        setContentView(scanner);
        scanner.startCamera();

    }
    class ZXingScannerResultHandler implements ResultHandler {
       private TextView setBarcode;
        private Button saveInfo;


        @Override
        public void handleResult(Result result) {

            resultCode = result.getText();
            Toast.makeText(GeneralInfo.this, resultCode, Toast.LENGTH_SHORT).show();


            setContentView(R.layout.activity_scanner);
            setBarcode =findViewById(R.id.setBarcode);
            setBarcode.setText(" " +resultCode);
            scanner.stopCamera();
            scan=true;
            Log.e("SÄTTER SCAN TRUE","Sätter scan true");

            saveInfo = findViewById(R.id.saveInfo);
            saveInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent (GeneralInfo.this, SetNote.class);
                    intent.putExtra(BARCODE, resultCode);
                    intent.putExtra(CHOICE, "2");
                    startActivity(intent);
                    scan=false;

                }
            });


            Log.e("SÄTTER SCAN FALSE","Sätter scan false");
        }
    }


    public void btnSetIncomesClicked(){  //i klassen GeneralInfo
        Intent intent = new Intent (GeneralInfo.this, ShowIncomes.class);
        startActivity(intent);
    }
    public void btnSetExpenseClicked(){  //i klassen GeneralInfo
        Intent intent = new Intent (GeneralInfo.this, ShowExpense.class);
        startActivity(intent);
    }


    public void setpersonalInfoFragment (Fragment fragment, boolean backstack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container1,(Fragment)fragment);
        fragmentTransaction.commit();
    }

    public void setbuttonsFragment (Fragment fragment, boolean backstack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container2,(Fragment)fragment);
        fragmentTransaction.commit();
    }


    public void setfabFragment (Fragment fragment, boolean backstack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container3, (Fragment) fragment);
        fragmentTransaction.commit();
    }
}



















/*
package com.example.p1;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import androidx.lifecycle.Observer;*/
/**//*

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static me.dm7.barcodescanner.zxing.ZXingScannerView.*;

public class GeneralInfo extends AppCompatActivity {
    int status = 0;
    private ZXingScannerView scannerView;
    private EconomyViewModel incomeViewModel;
    static final String CHOICE = "choice" ;
    private ButtonsFragment buttonsFragment;
    private FabFragment fabFragment;
    private PersonalInfoFragment personalInfoFragment;
    private static final String NAME  = "name";
    static final String SURNAME = "surname";
    private String name;
    private String surname;
    private ZXingScannerView scanner;
    static final String BARCODE = "barcode";
    private boolean scan = false;
    private  String resultCode;
    private TextView setBarcode;
    private Button saveInfo;

    ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalinfo);


        if(savedInstanceState != null) {
            scan = savedInstanceState.getBoolean("scan");
            if (scan ==true) {
                resultCode = savedInstanceState.getString("resultScan");
                restartScan();
            }
        } else {


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        personalInfoFragment = new PersonalInfoFragment();
        setpersonalInfoFragment(personalInfoFragment, false);

        buttonsFragment = new ButtonsFragment();
        buttonsFragment.setGeneralInfo(this);
        setbuttonsFragment(buttonsFragment, false);

        fabFragment = new FabFragment();
        fabFragment.setGeneralInfo(this);
        setfabFragment(fabFragment, false);
        setPersonalInfo();

        }
    }

    public void setPersonalInfo(){
        Intent intent = getIntent();
        name = intent.getStringExtra(NAME);
        surname = intent.getStringExtra(SURNAME);
        personalInfoFragment.setText(name, surname);
    }

    public void btbfabIncomeClicked(){
        Intent intent = new Intent (GeneralInfo.this, SetNote.class);
        intent.putExtra(CHOICE, "1");
        startActivity(intent);
    }

    public void btbfabExpenseClicked(){
        Intent intent = new Intent (GeneralInfo.this, SetNote.class);
        intent.putExtra(CHOICE, "2");
        startActivity(intent);
    }
    public void btnGeneralInfoClicked(){
        Intent intent = new Intent (GeneralInfo.this, Information.class);
        intent.putExtra(NAME, name);
        intent.putExtra(SURNAME, surname);
        startActivity(intent);
    }

    public void scanCode(View view) {

        scanner = new ZXingScannerView(this);
        scanner.setResultHandler(new ZXingScannerResultHandler());


        setContentView(scanner);
        scanner.startCamera();

    }
    class ZXingScannerResultHandler implements ResultHandler {
      */
/*  private TextView setBarcode;
        private Button saveInfo;*//*



        @Override
        public void handleResult(Result result) {

            resultCode = result.getText();
            Toast.makeText(GeneralInfo.this, resultCode, Toast.LENGTH_SHORT).show();


            setContentView(R.layout.activity_scanner);
            setBarcode =findViewById(R.id.setBarcode);
            setBarcode.setText(" " +resultCode);
            scanner.stopCamera();
            scan=true;
            Log.e("SÄTTER SCAN TRUE","Sätter scan true");

            saveInfo = findViewById(R.id.saveInfo);
            saveInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent (GeneralInfo.this, SetNote.class);
                    intent.putExtra(BARCODE, resultCode);
                    intent.putExtra(CHOICE, "2");
                    startActivity(intent);
                    scan=false;

                }
            });


            Log.e("SÄTTER SCAN FALSE","Sätter scan false");
        }
    }

    public void restartScan(){
        setContentView(R.layout.activity_scanner);
        setBarcode =findViewById(R.id.setBarcode);
        setBarcode.setText(" " +resultCode);
        saveInfo = findViewById(R.id.saveInfo);
        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (GeneralInfo.this, SetNote.class);
                intent.putExtra(BARCODE, resultCode);
                intent.putExtra(CHOICE, "2");
                startActivity(intent);
                scan=false;

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("scan", scan);

        outState.putString("resultScan", resultCode);
    }



    public void btnSetIncomesClicked(){  //i klassen GeneralInfo
        Intent intent = new Intent (GeneralInfo.this, ShowIncomes.class);
        startActivity(intent);
    }
    public void btnSetExpenseClicked(){  //i klassen GeneralInfo
        Intent intent = new Intent (GeneralInfo.this, ShowExpense.class);
        startActivity(intent);
    }


    public void setpersonalInfoFragment (Fragment fragment, boolean backstack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container1,(Fragment)fragment);
        fragmentTransaction.commit();
    }

    public void setbuttonsFragment (Fragment fragment, boolean backstack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container2,(Fragment)fragment);
        fragmentTransaction.commit();
    }


    public void setfabFragment (Fragment fragment, boolean backstack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container3, (Fragment) fragment);
        fragmentTransaction.commit();
    }
}
*/
