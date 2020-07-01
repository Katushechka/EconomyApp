package com.example.p1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText surname;
    private Button buttonCreateNewAccount;

    SharedPreferences sharedPreferences;
    static final String mypreference = "mypref";
    static final String NAME = "name";
    static final String SURNAME = "surname";

    private static final int FINGERPRINT_PERMISSION_REQUEST_CODE = 0;
    private FingerprintManager fingerprintManager;
    private Dialog fingerPrintDialog;
    private CancellationSignal cancellationSignal;
    private static final String KEY = "key";
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstname = (EditText)findViewById(R.id.firstname);
        surname = (EditText)findViewById(R.id.surname);
        buttonCreateNewAccount = (Button)findViewById(R.id.create_new_account);

        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE); //MODE_PRIVATE ingen annan app kan ändra SharedPreferences
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(NAME)){
            firstname.setText(sharedPreferences.getString(NAME, ""));
        }
        if(sharedPreferences.contains(SURNAME)){
            surname.setText(sharedPreferences.getString(SURNAME, ""));
        }
        buttonCreateNewAccount.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
                initFingerprintSensor(v);
          /*      Intent intent = new Intent (MainActivity.this, GeneralInfo.class);
                intent.putExtra(NAME, firstname.getText().toString());
                intent.putExtra(SURNAME, surname.getText().toString());
                startActivity(intent);*/


            }
        });
        fingerprintManager = (FingerprintManager)getSystemService(FINGERPRINT_SERVICE);
    }

    public void save(View v){
        String name = firstname.getText().toString();
        String passw = surname.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME, name);
        editor.putString(SURNAME, passw);
        editor.commit();
        retrive(v);
    }

    public void retrive(View v){
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(NAME)){
            firstname.setText(sharedPreferences.getString(NAME, ""));
        }
        if(sharedPreferences.contains(SURNAME)){
            surname.setText(sharedPreferences.getString(SURNAME, ""));
        }
    }

    public void initFingerprintSensor(View v) {
        checkIfFingerprintSensorIsAvailable();
    }

    public void checkIfFingerprintSensorIsAvailable() {
        if(fingerprintManager.isHardwareDetected()) {
            if(fingerprintManager.hasEnrolledFingerprints()) {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)!= PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.USE_FINGERPRINT}, FINGERPRINT_PERMISSION_REQUEST_CODE);
                } else {
                    showDialog();
                }
            } else {
                showAlertDialog("Fingerprint is not registered", "Go to Settings -> Security -> Fingerprint (for registering a fingerprint)");
            }
        } else {
            showAlertDialog("Fingerprint sensor is not found", "Fingerprint sensor is not found on your phone.");
        }
    }

    public void showAlertDialog(String title, String message){
        new android.app.AlertDialog.Builder(this).setTitle(title).setMessage(message).setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }})
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[]
            permissions, int[] state) {
        if (requestCode == FINGERPRINT_PERMISSION_REQUEST_CODE && state[0] == PackageManager.PERMISSION_GRANTED) {
            showDialog();
        }
    }

    public void showDialog() {
        if(fingerprintSettings()) {
            fingerPrintDialog = new Dialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.fingerprint_dialog, null, false);
            fingerPrintDialog.setContentView(view);
            Button cancel = (Button) view.findViewById(R.id.cancelbutton);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    cancellationSignal.cancel();
                    fingerPrintDialog.dismiss();
                }
            });
            fingerPrintDialog.setCanceledOnTouchOutside(false);
            fingerPrintDialog.setCancelable(false);
            fingerPrintDialog.show();
        }
        else{
            showAlertDialog("Error", "Error in initiating finger print cipher or key!");
        }
    }

    public boolean fingerprintSettings() {
        cancellationSignal = new CancellationSignal();
        if(key() && cipher()) {
            fingerprintManager.authenticate(new FingerprintManager.CryptoObject(cipher), cancellationSignal, 0, new AuthenticationListener(), null);
            return true;
        } else {
            return false;
        }
    }

    public boolean key() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setUserAuthenticationRequired(true).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
            keyGenerator.generateKey();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cipher() {
        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY, null);
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyStoreException | CertificateException |
                UnrecoverableKeyException | IOException |
                NoSuchAlgorithmException | InvalidKeyException |
                NoSuchPaddingException e) {
            return false;
        }
    }

    class AuthenticationListener extends FingerprintManager.AuthenticationCallback{
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
        }
        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        }
        @Override
        public void onAuthenticationFailed() {
            Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onAuthenticationSucceeded
                (FingerprintManager.AuthenticationResult result) {
            Intent intent = new Intent (MainActivity.this, GeneralInfo.class);
            intent.putExtra(NAME, firstname.getText().toString());
            intent.putExtra(SURNAME, surname.getText().toString());
            startActivity(intent);
            fingerPrintDialog.dismiss();
        }
    }

   /* public void setFragment(Fragment fragment, boolean backstack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        if(backstack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }*/
}
