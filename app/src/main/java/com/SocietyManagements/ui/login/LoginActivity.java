package com.SocietyManagements.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.SocietyManagements.R;
import com.SocietyManagements.controller.Constants;
import com.SocietyManagements.controller.PrefManager;
import com.SocietyManagements.ui.forgot.ForgotActivity;
import com.SocietyManagements.ui.home.HomeActivity;
import com.SocietyManagements.ui.registration.RegisterUserModel;
import com.SocietyManagements.ui.registration.RegistrationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText editTextEmail,password;
    TextInputLayout textInputLayouTEmail,textInputLayoutPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail =findViewById(R.id.userEmail);
        password=findViewById(R.id.userPassword);
        textInputLayouTEmail =findViewById(R.id.textInputLayoutMobileNumber);
        textInputLayoutPassword=findViewById(R.id.textInputLayout2);
    }

    public void createNewUser(View view) {

        startActivity(new Intent(this, RegistrationActivity.class));
    }

    public void forgotPassword(View view) {
        startActivity(new Intent(this, ForgotActivity.class));
    }

    public void login(View view) {
        boolean allFieldSet=true;
        if (editTextEmail.getText().toString().isEmpty())
        {
            allFieldSet=false;
        }
        if (password.getText().toString().isEmpty())
        {
            allFieldSet=false;
        }

        if (allFieldSet)
        {
            loginAuth(editTextEmail.getText().toString().trim(),password.getText().toString().trim());
        }

    }

    private void loginAuth(String email, String password) {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Checking Credentials ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    getUserDetails(task.getResult().getUser().getUid());
                }
                else
                {
                    Toast.makeText(LoginActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void getUserDetails(String uid) {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Getting user Details  ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child(Constants.USER_NODE);
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RegisterUserModel registerUserModel=dataSnapshot.getValue(RegisterUserModel.class);
                setUerDetails(registerUserModel);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error wihite Readind ",databaseError.getMessage());
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                databaseError.toException().printStackTrace();
            }
        });
    }

    private void setUerDetails(RegisterUserModel registerUserModel) {
        PrefManager prefManager=new PrefManager(this);
        prefManager.setUserEmail(registerUserModel.getEmail());
        prefManager.setUserID(registerUserModel.getUid());
        prefManager.setUserMobile(registerUserModel.getMobileNumber());
        prefManager.setUserName(registerUserModel.getUserName());
        prefManager.setLogin(true);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
