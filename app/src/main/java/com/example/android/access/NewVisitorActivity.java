package com.example.android.access;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.security.SecureRandom;


public class NewVisitorActivity extends AppCompatActivity {
    //declaring intent constants
    public static final String EXTRA_VFIRSTNAME = "com.example.android.access.VFIRSTNAME";
    public static final String EXTRA_VSURNAME = "com.example.android.access.VSURNAME";
    public static final String EXTRA_HOSTID = "com.example.android.access.HOSTID";
    public static final String EXTRA_VCODE = "com.example.android.access.VCODE";

    //declaring views
    private EditText etVisitorFirstname;
    private EditText etVisitorSurname;
    private EditText etHostId;
    private EditText etVisitorCode;
    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visitor);

        //linking views
        etVisitorFirstname = findViewById(R.id.visitor_firstname);
        etVisitorSurname = findViewById(R.id.visitor_surname);
        etHostId = findViewById(R.id.host_id);
        etVisitorCode = findViewById(R.id.visitor_code);
        btnSubmit = findViewById(R.id.visitor_submit);

        //setting visitor code
        etVisitorCode.setText(generatePass());


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVisitor(view);
            }
        });
    }

    private String generatePass() {
        int passCode;

        SecureRandom secureRandom = new SecureRandom();
        // String passcode = String.valueOf(secureRandom.nextInt(10000));
        passCode = secureRandom.nextInt(10000);

        while (passCode < 1000) {
            passCode = secureRandom.nextInt(10000);
        }
        String visitorCode = String.valueOf(passCode);

        return visitorCode;

    }


    private void addVisitor(View view) {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(etVisitorFirstname.getText()) || TextUtils.isEmpty(etVisitorSurname.getText())
                || TextUtils.isEmpty(etHostId.getText()) || TextUtils.isEmpty(etVisitorCode.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
            Snackbar.make(view, "nonoei", Snackbar.LENGTH_SHORT).show();


        } else {
            //extrators
            String visitorFirstname = etVisitorFirstname.getText().toString();
            String visitorSurname = etVisitorSurname.getText().toString();
            String hostId = etHostId.getText().toString();
            String visitorCode = etVisitorCode.getText().toString();

            //package intent result
            replyIntent.putExtra(EXTRA_VFIRSTNAME, visitorFirstname);
            replyIntent.putExtra(EXTRA_VSURNAME, visitorSurname);
            replyIntent.putExtra(EXTRA_HOSTID, hostId);
            replyIntent.putExtra(EXTRA_VCODE, visitorCode);

            setResult(RESULT_OK, replyIntent);
            finish();
        }


    }
}
