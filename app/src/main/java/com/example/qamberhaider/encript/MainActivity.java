package com.example.qamberhaider.encript;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {


    EditText Data, Password;
    TextView Result_main;
    Button Enc_button, Dec_button;
    String Result_String;
    String AES = "AES";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Data = (EditText) findViewById(R.id.data);
        Password = (EditText) findViewById(R.id.passwordId);
        Result_main = (TextView) findViewById(R.id.result);
        Enc_button = (Button) findViewById(R.id.encr_btn);
        Dec_button = (Button) findViewById(R.id.dec_btn);

        Enc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Result_String = encrypt(
                            Data.getText().toString(),
                            Password.getText().toString());
//
//                    if (TextUtils.isEmpty(Result_String)){
//                        Data.setError("Required");
//                        return;
//                    }
//                    if (TextUtils.isEmpty(Result_String)){
//                        Password.setError("Required");
//                        return;
//
//                    }
//
                    Result_main.setText(Result_String);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Dec_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Result_String = decrypt(Result_String,Password.getText().toString());

//                    if (TextUtils.isEmpty(Result_String)){
//                        Data.setError("Required");
//                        return;
//                    }
//                    if (TextUtils.isEmpty(Result_String)){
//                        Password.setError("Required");
//                        return;
//
//                    }

                } catch (Exception e) {
                   Toast.makeText(MainActivity.this, "Incorrect or invalid null exception", Toast.LENGTH_SHORT).show();


                    e.printStackTrace();
                }
                Result_main.setText(Result_String);

            }
        });

    }

    private String decrypt(String Result_String, String pass) throws Exception {

        SecretKeySpec key = generateKey(pass);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodeValue = Base64.decode(Result_String, Base64.DEFAULT);
        byte[] Dec_val = c.doFinal(decodeValue);
        String decrptedValue = new String(Dec_val);
        return decrptedValue;

    }

    private String encrypt(String info, String pass) throws Exception {
        SecretKeySpec key = generateKey(pass);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte [] EncVal = c.doFinal(info.getBytes());
        String encrptedVal = Base64.encodeToString(EncVal,Base64.DEFAULT);
        return encrptedVal;

    }

    private SecretKeySpec generateKey(String pass) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = pass.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }
}
