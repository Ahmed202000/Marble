package com.example.marble;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Login extends AppCompatActivity {

    EditText txtuser,txtpass;
    TextView txtforget;
    Button btnlogin,btnRegister;
    CheckBox chk;
    public static String user,url;

    ImageView shopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        shopping=(ImageView)findViewById(R.id.imgShopping);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Login.this,Basket_item.class);
                startActivity(intent);
                finish();
            }
        });
        //داله العدد
        TextView txtNumber=findViewById(R.id.txtNumber_Of_Product);
        DBLite D=new DBLite(Login.this);
        txtNumber.setText(""+D.GetCount());



        btnRegister=(Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });


        SharedPreferences sh=getSharedPreferences("MarbleSH",MODE_PRIVATE);
        final String username=sh.getString("User_UserName",null);
        String pass=sh.getString("Password",null);
        if(username!=null)
        {
            user=username;
            url=sh.getString("url",null);
            startActivity(new Intent(Login.this, Category.class));
        }


        txtuser=(EditText)findViewById(R.id.txtloginuser);
        txtpass=(EditText)findViewById(R.id.txtloginpass);
        chk=(CheckBox)findViewById(R.id.chkloginme);
        btnlogin=findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Database db=new Database();
                Connection conn=db.ConnectDB();
                if(conn==null)
                    Toast.makeText(Login.this, "الرجـــاء الاتصال بالانترنت", Toast.LENGTH_SHORT).show();
                else
                {
                    ResultSet rlogin=db.RunSearch("Select * from Users where User_UserName='"+txtuser.getText()+"'");
                    try {
                        if(rlogin.next())
                        {
                            rlogin=db.RunSearch("Select * from Users where User_UserName='"+txtuser.getText()+"' and Password='"+txtpass.getText()+"'");

                            if(rlogin.next()) {

                                if (chk.isChecked()) {
                                    getSharedPreferences("MarbleSH", MODE_PRIVATE)
                                            .edit()
                                            .putString("User_UserName", txtuser.getText().toString())
                                            .putString("Password", txtpass.getText().toString())
                                            .putString("url", rlogin.getString(7))
                                            .commit();
                                }
                                url = rlogin.getString(7);
                                user = txtuser.getText().toString();
                                startActivity(new Intent(Login.this,shpping.class));
                            }
                            else
                            {
                                AlertDialog.Builder m=new AlertDialog.Builder(Login.this)
                                        .setTitle("تسجيل الدخول...")
                                        .setMessage("التاكد مكن كتابه كلمه المرور !!!  ")
                                        .setIcon(R.drawable.logo)
                                        .setPositiveButton("شكـــــراً",null);

                                m.create();
                                m.show();
                            }
                        }
                        else
                        {
                            AlertDialog.Builder m=new AlertDialog.Builder(Login.this)
                                    .setTitle("تسجيل الدخول...")
                                    .setMessage("التاكد من كتابه اسم المستخدم ")
                                    .setIcon(R.drawable.logo)
                                    .setPositiveButton("شكـــــراً",null);

                            m.create();
                            m.show();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        txtforget=findViewById(R.id.txtforget);
        txtforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater=LayoutInflater.from(Login.this);
                View vv= inflater.inflate(R.layout.forgetpass,null);
                final EditText txtemail=(EditText)vv.findViewById(R.id.txtforgetemail);
                AlertDialog.Builder forget=new AlertDialog.Builder(Login.this);
                forget.setView(vv);
                forget.setPositiveButton("تم الارسال", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        final Database db=new Database();
                        db.ConnectDB();
                        ResultSet rs=db.RunSearch("Select * from Users where Email='"+txtemail.getText()+"'");
                        try {
                            if(rs.next())
                            {
                                Random r=new Random();
                                final   int newpass=r.nextInt(99999-11111+1)+11111;

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {


                                            final String username = "yourmobileapp2017@gmail.com";
                                            final String password = "okok2017";
                                            Properties props = new Properties();
                                            props.put("mail.smtp.auth", "true");
                                            props.put("mail.smtp.starttls.enable", "true");
                                            props.put("mail.smtp.host", "smtp.gmail.com");
                                            props.put("mail.smtp.port", "587");

                                            Session session = Session.getInstance(props,
                                                    new Authenticator() {

                                                        protected PasswordAuthentication getPasswordAuthentication() {
                                                            return new PasswordAuthentication(username, password);
                                                        }
                                                    });

                                            try {
                                                Message message = new MimeMessage(session);
                                                message.setFrom(new InternetAddress("yourmobileapp2017@gmail.com"));
                                                message.setRecipients(Message.RecipientType.TO,
                                                        InternetAddress.parse(txtemail.getText().toString()));

                                                message.setSubject("Forget Password  - Marble App-");
                                                message.setText("Your New Password is : "+newpass);
                                                Transport.send(message);

                                                db.RUNDML("update Users set Password='"+newpass+"' where Email='"+txtemail.getText()+"'");


                                            } catch (MessagingException e) {
                                                Toast.makeText(getApplication(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                                                throw new RuntimeException(e);
                                            }
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                }).start();
                                Toast.makeText(getApplication(), "تم ارسال الباسور الجديد علي الائميل الذي ادخلته", Toast.LENGTH_LONG).show();


                            }
                            else
                                Toast.makeText(Login.this, "هذا البريد الإلكتروني غير موجود", Toast.LENGTH_SHORT).show();



                        } catch (SQLException e) {
                            e.printStackTrace();
                        }





                    }
                }).setNegativeButton("شكــــــــــرا",null);

                forget.create();
                forget.show();


            }
        });


    }
    public void onBackPressed() {
        finishAffinity();
    }

}

