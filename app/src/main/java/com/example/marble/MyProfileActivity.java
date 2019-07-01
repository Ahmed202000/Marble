package com.example.marble;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText usernameU,passwordU,emailU,AddressU,PhoneU,FullNameU;
    Button btnUpdateProfile;
    ImageView imgprof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //تعريف المتغيرات
        FullNameU=findViewById(R.id.txtFullNameU);
        usernameU=findViewById(R.id.txtusernameU);
        passwordU=findViewById(R.id.txtpasswordU);
        emailU=findViewById(R.id.txtemailU);
        AddressU=findViewById(R.id.txtaddressU);
        PhoneU=findViewById(R.id.txtphoneU);
        imgprof=findViewById(R.id.imgprof);
        btnUpdateProfile=findViewById(R.id.btnUpdateProfile);



        Database dbd=new Database();
        dbd.ConnectDB();
        ResultSet rprofile=dbd.RunSearch("Select * from Users where User_UserName='"+Login.user+"'");
        try {
            if(rprofile.next())
            {
                FullNameU.setText(rprofile.getString(1));
                usernameU.setText(rprofile.getString(2));
                emailU.setText(rprofile.getString(3));
                passwordU.setText(rprofile.getString(4));
                AddressU.setText(rprofile.getString(5));
                PhoneU.setText(rprofile.getString(6 ));
                PicassoClinte.downloadImage(this,rprofile.getString(7),imgprof);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Database dbd=new Database();
                dbd.ConnectDB();
                String msg=dbd.RUNDML("Update Users set Full_Name='"+FullNameU.getText()+"',Email='"+emailU.getText()+"',Password='"+passwordU.getText()+"',Address='"+AddressU.getText()+"',Phone='"+PhoneU.getText()+"'where CustUsername ='"+usernameU.getText()+"'");
                if (msg.equals("Ok"))
                {

                    AlertDialog.Builder h = new AlertDialog.Builder(MyProfileActivity.this)
                            .setTitle("الصفحه الشخصيه")
                            .setMessage("تم تعديل البيانات الشخصيه بنجاح ")
                            .setIcon(R.drawable.logo)
                            .setPositiveButton("شكرا", null);
                    h.create();
                    h.show();
                }
            }
        });


//        imgprof=(ImageView)findViewById(R.id.imgprof);
//        imgprof.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST);
//            }
//        });



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        TextView txtwelcome=headerView.findViewById(R.id.txtwelcome);
        ImageView imguser=headerView.findViewById(R.id.imguser);
        txtwelcome.setText("اهـــلا بك > "+Login.user);
        PicassoClinte.downloadImage(MyProfileActivity.this,Login.url,imguser);


        Menu menu = navigationView.getMenu();
        MenuItem item2 = menu.findItem(R.id.nav_orders);
        MenuItem cart = menu.findItem(R.id.nav_carte);

        DBLite d=new DBLite(MyProfileActivity.this);
        cart.setTitle("My Cart : ( "+d.GetCount()+" )");

        Database db=new Database();
        db.ConnectDB();

        ResultSet rorder=db.RunSearch("Select * from Orders where User_UserName='"+Login.user+"'");
        try {
            if(rorder.next())
            {
                item2.setVisible(true);
                cart.setVisible(true);
            }
            else
            {
                item2.setVisible(false);
                cart.setVisible(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            startActivity(new Intent(MyProfileActivity.this,Login.class));

        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(MyProfileActivity.this,MyProfileActivity.class));


        } else if (id == R.id.nav_carte) {

            startActivity(new Intent(MyProfileActivity.this,Basket_item.class));


        } else if (id == R.id.nav_orders) {
            startActivity(new Intent(MyProfileActivity.this,Basket_item.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_rating) {

            startActivity(new Intent(MyProfileActivity.this,RatingActivity.class));



        } else if (id == R.id.nav_unsubscribe) {

            AlertDialog.Builder msg=new AlertDialog.Builder(MyProfileActivity.this)
                    .setTitle("حذف الحساب")
                    .setMessage("هل تريد حذف الحساب نهائي")
                    .setIcon(R.drawable.sad)
                    .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Database db=new Database();
                            db.ConnectDB();
                            String Delete=db.RUNDML("Delete from Users where User_UserName='"+Login.user+"'");
                            if (Delete.equals("Ok"))
                            {
                                getSharedPreferences("MarbleSH",MODE_PRIVATE)
                                        .edit()
                                        .clear()
                                        .commit();

                                startActivity(new Intent(MyProfileActivity.this,Login.class));

                            }
                            else {
                                Delete = db.RUNDML("update Users set Status='0' where User_UserName='" + Login.user + "'");
                                if (Delete.equals("Ok")) {
                                    getSharedPreferences("MarbleSH", MODE_PRIVATE)
                                            .edit()
                                            .clear()
                                            .commit();
                                    Toast.makeText(MyProfileActivity.this, "تم الحذف بنجاح", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MyProfileActivity.this, Login.class));

                                }
                            }

                        }
                    }).setNegativeButton("لا",null);

            msg.create();
            msg.show();


        }
        else if (id == R.id.Exit_app)
        {
            getSharedPreferences("MarbleSH",MODE_PRIVATE)
                    .edit()
                    .clear()
                    .commit();
            startActivity(new Intent(MyProfileActivity.this,Login.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
