package com.example.marble;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;

public class Register extends AppCompatActivity {

    EditText txtUsername,txtPassword,txtEmail,txtAddress,txtPhone,txtFullname;
    Button btnCraeatAccuont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //تعريف المتغيرات
        txtFullname=findViewById(R.id.txtFullName);
        txtUsername=findViewById(R.id.txtusername);
        txtPassword=findViewById(R.id.txtpassword);
        txtEmail=findViewById(R.id.txtemail);
        txtAddress=findViewById(R.id.txtaddress);
        txtPhone=findViewById(R.id.txtphone);
        btnCraeatAccuont=findViewById(R.id.btnCraeatAccuont);

        //كود التحقق من الايميل
//        email.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                String ss = "[a-zA-Z0-9._-]+@[" + "a-z]+\\.+[a-z]+";
//                if (email.getText().toString().matches(ss)) {
//                    ;
//                } else
//                    email.setError("Invaild Email Address (user@domain)");
//
//            }
//        });




        //كود انشاء الحساب
        btnCraeatAccuont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Database db = new Database();
                Connection conn = db.ConnectDB();

                if (conn == null)
                    Toast.makeText(Register.this, "الرجاء الاتصال بالانترنت", Toast.LENGTH_SHORT).show();
                else {
                    if (txtFullname.getText().toString().isEmpty()) {
                        txtFullname.setError("الرجاء ادخال الاسم بالكامل");
                        txtFullname.requestFocus();
                    } else {
                        if (txtPassword.getText().toString().isEmpty()) {
                            txtPassword.setError("الرجاء ادخال كلمه المرور");
                            txtPassword.requestFocus();
                        } else {
                            if (txtUsername.getText().toString().isEmpty()) {
                                txtUsername.setError("الرجاء ادخال اسم المستخدم");
                                txtUsername.requestFocus();
                            } else {
                                if (txtEmail.getText().toString().isEmpty()) {
                                    txtEmail.setError("الجاء ادخال الائميل صحيح");
                                    txtEmail.requestFocus();
                                } else {
                                    if (txtAddress.getText().toString().isEmpty()) {
                                        txtAddress.setError("الجاء ادخال العنوان");
                                        txtAddress.requestFocus();
                                    } else {
                                        if (txtPhone.getText().toString().isEmpty()) {
                                            txtPhone.setError("الجاء ادخال الائميل صحيح");
                                            txtPhone.requestFocus();
                                        } else {
                                            String msg = db.RUNDML("insert into Users values('" + txtFullname.getText() + "','" + txtUsername.getText() + "','" + txtEmail.getText() + "','" + txtPassword.getText() + "','" + txtAddress.getText() + "','" + txtPhone.getText() + "')");
                                            if (msg.equals("Ok")) {
                                                AlertDialog.Builder h = new AlertDialog.Builder(Register.this)
                                                        .setTitle("رخــــــــــام")
                                                        .setMessage("تم انشاء الحســــــــــاب    :) ")
                                                        .setIcon(R.drawable.logo)
                                                        .setPositiveButton("شكــــرا", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                startActivity(new Intent(Register.this, Login.class));
                                                            }
                                                        });
                                                h.create();
                                                h.show();
                                            } else if (msg.contains("PRIMARY KEY")) {
                                                AlertDialog.Builder h = new AlertDialog.Builder(Register.this)
                                                        .setTitle("رخــــــــــام")
                                                        .setMessage("هذا الحساب موجود بالفعل :) ")
                                                        .setIcon(R.drawable.logo)
                                                        .setPositiveButton("شكــــرا", null);
                                                h.create();
                                                h.show();
                                            } else
                                                Toast.makeText(Register.this, "" + msg, Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }
                            }

                        }

                    }
                }
            }
        });




//                                            String msg = db.RUNDML("insert into Users values('" + FullName.getText() + "','" + username.getText() + "','" + email.getText() + "','" + password.getText() + "','" + Address.getText() + "','" + Phone + "')");
//                                            if (msg.equals("Ok")) {
//
//                                                AlertDialog.Builder h = new AlertDialog.Builder(Register.this)
//                                                        .setTitle("رخــــام")
//                                                        .setMessage("مرحبا بك لك تم انشاء الحساب بنجاح :) ")
//                                                        .setIcon(R.drawable.logo)
//                                                        .setPositiveButton("شكرا", new DialogInterface.OnClickListener() {
//                                                            @Override
//                                                            public void onClick(DialogInterface dialog, int which) {
//                                                                startActivity(new Intent(Register.this, Login.class));
//                                                            }
//                                                        });
//                                                h.create();
//                                                h.show();
//                                            } else if (msg.contains("PRIMARY KEY")) {
//                                                AlertDialog.Builder h = new AlertDialog.Builder(Register.this)
//                                                        .setTitle("رخــــام")
//                                                        .setMessage("هذا الحساب موجود بالفعل :) ")
//                                                        .setIcon(R.drawable.logo)
//                                                        .setPositiveButton("شكرا", null);
//                                                h.create();
//                                                h.show();
//                                            } else
//                                                Toast.makeText(Register.this, "معذرتاً لك حدث خطا" + msg, Toast.LENGTH_SHORT).show();
//
//
//                                        }
//                                    }
//                                }
//                            }
//
//                        }
//
//                    }
//                }
//            }
//        });

    }
    public void onBackPressed() {
        finishAffinity();
    }

}

