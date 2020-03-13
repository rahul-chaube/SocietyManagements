package com.SocietyManagements.ui.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.SocietyManagements.R;
import com.SocietyManagements.controller.Constants;
import com.SocietyManagements.controller.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    String mVerificationId;

    TextInputLayout textInputLayoutEmail, textInputLayoutMobile, textInputLayoutUserName, textInputLayoutPassword, textInputLayoutCnfPassword;
    TextInputEditText textInputEditTextEmail, textInputEditTextMobile, textInputEditTextPassword, textInputEditTextUserName, textInputEditTextCnfPassowrd;
    private FirebaseAuth mAuth;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    String TAG = "Registration Activity";
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();
        textInputLayoutEmail = findViewById(R.id.textInputLayoutUserEmail);
        textInputLayoutMobile = findViewById(R.id.textInputLayoutMobileNumber);
        textInputLayoutUserName = findViewById(R.id.textInputLayoutUserName);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutCnfPassword = findViewById(R.id.textInputLayoutCnfPassword);

        textInputEditTextEmail = findViewById(R.id.userEmail);
        textInputEditTextMobile = findViewById(R.id.userMobileNumber);
        textInputEditTextUserName = findViewById(R.id.userName);
        textInputEditTextPassword = findViewById(R.id.userPassword);
        textInputEditTextCnfPassowrd = findViewById(R.id.userCnfPassword);

        mAuth = FirebaseAuth.getInstance();
//        PhoneAuthProvider.getInstance().verifyPhoneNumber("+917058239556", 60, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                Toast.makeText(RegistrationActivity.this, "Verification Completed ", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//
//                Log.e("Verification Exception ", e.getLocalizedMessage());
//                e.printStackTrace();
//                Toast.makeText(RegistrationActivity.this, "Verification Failed  ", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
//                super.onCodeSent(verificationId, token);
//
//                Log.d("Phone Authentication ", "onCodeSent:" + verificationId);
//                mVerificationId = verificationId;
//                mResendToken = token;
//
//                openDialog();
//            }
//
//            @Override
//            public void onCodeAutoRetrievalTimeOut(String s) {
//                super.onCodeAutoRetrievalTimeOut(s);
//            }
//        });
    }

    private void openDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View view = inflater.inflate(R.layout.dialog_otp, null, false);

        final EditText editTextOTP = view.findViewById(R.id.edittextOTP);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        if (editTextOTP.getText().toString().isEmpty() && editTextOTP.getText().toString().length() < 6) {
                            Toast.makeText(RegistrationActivity.this, "Enter Correct OTP ", Toast.LENGTH_SHORT).show();
                        } else {
                            verifyWithCode(editTextOTP.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();


    }

    void verifyWithCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
//                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
//                                mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
//                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }

    public void createNewUser(View view) {
        boolean isAllSet = true;
        if (textInputEditTextEmail.getText().toString().isEmpty()) {
            Log.e(TAG, "Email error is Disabled ");
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Enter Email ID ");
            isAllSet = false;
        } else if (!Utility.isValidEmail(textInputEditTextEmail.getText().toString().trim())) {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Email ID wrong");
        } else {
            Log.e(TAG, "Email error is Disabled ");
            textInputEditTextEmail.setError(null);
            textInputLayoutEmail.setErrorEnabled(false);
        }

        if (textInputEditTextUserName.getText().toString().isEmpty()) {
            textInputLayoutUserName.setError("Enter User Name ");
            textInputLayoutUserName.setErrorEnabled(true);
            isAllSet = false;
        } else {
            textInputLayoutUserName.setError(null);
            textInputLayoutUserName.setErrorEnabled(false);
        }
        if (textInputEditTextMobile.getText().toString().isEmpty()) {
            textInputLayoutMobile.setError("Enter Mobile Number");
            textInputLayoutMobile.setErrorEnabled(true);
            isAllSet = false;
        } else if (textInputEditTextMobile.getText().toString().trim().length() != 10) {
            textInputLayoutMobile.setError("Invalid Mobile Number");
            textInputLayoutMobile.setErrorEnabled(true);
            isAllSet = false;
        } else {
            textInputLayoutMobile.setErrorEnabled(false);

        }
        if (textInputEditTextPassword.getText().toString().isEmpty()) {
            textInputLayoutPassword.setError("Enter Password");
            isAllSet = false;
        } else {
            textInputLayoutPassword.setError(null);
            textInputLayoutPassword.setErrorEnabled(false);
        }
        if (textInputEditTextCnfPassowrd.getText().toString().isEmpty()) {
            textInputLayoutCnfPassword.setError("Enter Password ");
            textInputLayoutCnfPassword.setErrorEnabled(true);
            isAllSet = false;
        } else if (!textInputEditTextCnfPassowrd.getText().toString().trim().equals(textInputEditTextPassword.getText().toString().trim())) {
            textInputLayoutCnfPassword.setError("Password Not Matched ");
            textInputLayoutCnfPassword.setErrorEnabled(true);
            isAllSet = false;
        } else {
            textInputLayoutCnfPassword.setError(null);
            textInputLayoutCnfPassword.setErrorEnabled(false);

        }


        if (isAllSet) {
            Toast.makeText(this, "All data filled ", Toast.LENGTH_SHORT).show();

            createUser(textInputEditTextEmail.getText().toString().trim(), textInputEditTextPassword.getText().toString().trim(),
                    textInputEditTextUserName.getText().toString().trim(), textInputEditTextMobile.getText().toString().trim());
        }

    }

    private void createUser(final String email, final String password, final String userName, final String mobileNumber) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding User ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    saveUserDetails(task.getResult().getUser().getUid(), email, password, userName, mobileNumber);
                } else {

                    Log.e("Error Auth ",task.getException().getMessage());
                    task.getException().printStackTrace();
                    Toast.makeText(RegistrationActivity.this, " " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void saveUserDetails(String uid, String email, String password, String userName, String mobileNumber) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving User Details ... ");
        progressDialog.show();
        progressDialog.setCancelable(false);

        RegisterUserModel registerUserModel = new RegisterUserModel(email, uid, userName, mobileNumber, password);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(Constants.USER_NODE);

        databaseReference.child(uid).setValue(registerUserModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mAuth.sendPasswordResetEmail(email);
                    Toast.makeText(RegistrationActivity.this, "User Created ", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Error in saving Data ",task.getException().getMessage());
                    task.getException().printStackTrace();
                    Toast.makeText(RegistrationActivity.this, "Failed to save Data ", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();

                finish();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegistrationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
