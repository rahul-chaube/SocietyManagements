package com.SocietyManagements.ui.forgot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.SocietyManagements.R;
import com.SocietyManagements.controller.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {
 TextInputLayout textInputLayout;
 TextInputEditText textInputEditTextEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        textInputLayout=findViewById(R.id.textInputLayout);
        textInputEditTextEmail=findViewById(R.id.userEmail);

    }

    public void forgotPassword(View view) {

        boolean allSet=true;
        if (textInputEditTextEmail.getText().toString().isEmpty())
        {
            textInputLayout.setError("Enter Email id ");
            allSet=false;
        }
        else if (!Utility.isValidEmail(textInputEditTextEmail.getText().toString()) )
        {
            allSet=false;
            textInputLayout.setError("Invalid Email id ");
        }
        else
        {
            sendForgotMail(textInputEditTextEmail.getText().toString());
        }



    }

    private void sendForgotMail(String email) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ... ");
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(email.trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful())
                {
                    Toast.makeText(ForgotActivity.this, "Check Your Email , Password Reset link is send ", Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {
                    Toast.makeText(ForgotActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
