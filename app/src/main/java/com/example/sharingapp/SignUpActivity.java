package com.example.sharingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextRegisFullname, editTextRegisDateOfBirth, editTextRegisEmail, editTextRegisPassword,
            editTextRegisPasswordConfirm;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisGender;
    private RadioButton radioButtonRegisGenderSelected;

    private static final String TAG = "SignUpActivity";

    private boolean isSignUp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toast.makeText(SignUpActivity.this, "You can register now", Toast.LENGTH_LONG).show();

        // Xu ly dang ki
        progressBar = findViewById(R.id.progressBar);

        editTextRegisFullname = findViewById(R.id.full_name);
        editTextRegisDateOfBirth = findViewById(R.id.dateOfBirth);
        editTextRegisEmail = findViewById(R.id.email_signup);
        editTextRegisPassword = findViewById(R.id.password_signup);
        editTextRegisPasswordConfirm = findViewById(R.id.password_retype_input);

        radioGroupRegisGender = findViewById(R.id.genderRadioGroup);
        radioGroupRegisGender.clearCheck();

        Button buttonSignUp = findViewById(R.id.btn_doneSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisGender.getCheckedRadioButtonId();
                radioButtonRegisGenderSelected = findViewById(selectedGenderId);

//               // Lay du lieu da nhap
                String textFullname = editTextRegisFullname.getText().toString();
                String textDoB = editTextRegisDateOfBirth.getText().toString();
                String textEmail = editTextRegisEmail.getText().toString();
                String textPassword = editTextRegisPassword.getText().toString();
                String textPasswordConfirm = editTextRegisPasswordConfirm.getText().toString();
                String textGender;

                if (TextUtils.isEmpty(textFullname)) {
                    Toast.makeText(SignUpActivity.this, "Please enter your full name", Toast.LENGTH_LONG).show();
                    editTextRegisFullname.setError("Full name is required");
                    editTextRegisFullname.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(SignUpActivity.this, "Please enter your Email", Toast.LENGTH_LONG).show();
                    editTextRegisEmail.setError("Email is required");
                    editTextRegisEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(SignUpActivity.this, "Please re-enter your Email", Toast.LENGTH_LONG).show();
                    editTextRegisEmail.setError("Valid email is required");
                    editTextRegisEmail.requestFocus();
                } else if (TextUtils.isEmpty(textDoB)) {
                    Toast.makeText(SignUpActivity.this, "Please enter your date of birth", Toast.LENGTH_LONG).show();
                    editTextRegisDateOfBirth.setError("Date of birth is required");
                    editTextRegisDateOfBirth.requestFocus();
                } else if (radioGroupRegisGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(SignUpActivity.this, "Please select your gender", Toast.LENGTH_LONG).show();
                    radioButtonRegisGenderSelected.setError("Gender is required");
                    radioButtonRegisGenderSelected.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(SignUpActivity.this, "Please enter your Password", Toast.LENGTH_LONG).show();
                    editTextRegisPassword.setError("Password is required");
                    editTextRegisPassword.requestFocus();
                } else if (textPassword.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "Password should be at least 6 digits", Toast.LENGTH_LONG).show();
                    editTextRegisPassword.setError("Password too weak");
                    editTextRegisPassword.requestFocus();
                } else if (TextUtils.isEmpty(textPasswordConfirm)) {
                    Toast.makeText(SignUpActivity.this, "Please confirm your Password", Toast.LENGTH_LONG).show();
                    editTextRegisPasswordConfirm.setError("Password confirmation is required");
                    editTextRegisPasswordConfirm.requestFocus();
                } else if (!textPassword.equals(textPasswordConfirm)) {
                    Toast.makeText(SignUpActivity.this, "Please same same password", Toast.LENGTH_LONG).show();
                    editTextRegisPasswordConfirm.setError("Password confirmation is required");
                    editTextRegisPasswordConfirm.requestFocus();
                    // xoa mat khau da nhap
                    editTextRegisPassword.clearComposingText();
                    editTextRegisPasswordConfirm.clearComposingText();
                } else {
                    textGender = radioButtonRegisGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                        }
                    }, 2000);

                    signUpUser(textFullname, textDoB, textGender, textEmail, textPassword);
                }

            }

        });
//--------------------------------------------------------------------------------------------------


        // xu ly su kien cho nut back
        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // xu li su kien cho nut signUP
//        Button btn_signup = (Button) findViewById(R.id.btn_doneSignUp);
//        btn_signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LayoutInflater inflater = getLayoutInflater();
//                View layout = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) findViewById(R.id.custom_toast_container));
//
//                TextView text = layout.findViewById(R.id.text_notion);
//                text.setText("Create Successful!!!");
//
//                Toast toast = new Toast(getApplicationContext());
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                toast.setDuration(Toast.LENGTH_SHORT);
//                toast.setView(layout);
//                toast.show();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
//                        startActivity(intent);
//                    }
//                }, 2000 + 1000);
//            }
//        });

        // tao calender khi an vao dateOfbirth
        EditText dateOfBirth = findViewById(R.id.dateOfBirth);
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get current date
                final Calendar current = Calendar.getInstance();
                int result_year = current.get(Calendar.YEAR);
                int result_month = current.get(Calendar.MONTH);
                int result_day = current.get(Calendar.DAY_OF_MONTH);

                //create DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        dateOfBirth.setText(d + "/" + (m + 1) + "/" + y);
                    }
                }, result_year, result_month, result_day);
                datePickerDialog.show();
            }
        });
    }

    private void signUpUser(String textFullname, String textDoB, String textGender, String textEmail, String textPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //Create User Profile
        auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(SignUpActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            //update Display name of user
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullname).build();
                            firebaseUser.updateProfile(profileChangeRequest);

                            //Enter User Data into the Firebase RealtimeData
                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails( textDoB, textGender);

                            //Extracting user reference from  Database for "Register Users"
                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Register Users");
                            referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        //Send  Verification Email
                                        firebaseUser.sendEmailVerification();
                                        Toast.makeText(SignUpActivity.this, "User register successfully. Please vertify your email",
                                                Toast.LENGTH_LONG).show();
                                //Open user Profile after successful registration
                                        Intent intent = new Intent(SignUpActivity.this, UserProfileActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                                                        | Intent.FLAG_ACTIVITY_NEW_TASK );
                                        startActivity(intent);
                                        finish(); //to close Register Activity
                                    }else {
                                        Toast.makeText(SignUpActivity.this, "User register failed. Please try again",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);

                                }
                            });


                            // hien thi "Create Successful!!!"
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) findViewById(R.id.custom_toast_container));

                            TextView text = layout.findViewById(R.id.text_notion);
                            text.setText("Create Successful!!!");

                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                    startActivity(intent);
                                    progressBar.setVisibility(View.GONE);
                                }
                            }, 4700);


                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                editTextRegisPassword.setError("Your password is too weak. Kindly use a mix of alphabets, numbers and special characters");
                                editTextRegisPassword.requestFocus();

                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                editTextRegisPassword.setError("Your email is invalid or already in use. Kindly re-enter");
                                editTextRegisPassword.requestFocus();

                            } catch (FirebaseAuthUserCollisionException e) {
                                editTextRegisPassword.setError("User is already registered with this email. Use another email");
                                editTextRegisPassword.requestFocus();

                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
    @Override
    public void onBackPressed() {
        // Không thực hiện hành động mặc định của nút Back
        // Ví dụ: không cho phép quay lại hoặc thoát khỏi ứng dụng
    }
}