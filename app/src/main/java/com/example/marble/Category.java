package com.example.marble;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Category extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String cateno;

    ListView gvCat;
    getCategory g = new getCategory();
    CategoryData datamodel;
    AdapterCategory adapterCategory;
    ArrayList<CategoryData> data;

    ImageView shopping;
    TextView txtNumber_Of_Product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        TextView txtNumber=findViewById(R.id.txtNumber_Of_Product);
        gvCat = findViewById(R.id.gvCategory);
        shopping=(ImageView)findViewById(R.id.imgShopping);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Category.this,Basket_item.class);
                startActivity(intent);
                finish();
            }
        });
        //داله العدد
        DBLite D=new DBLite(Category.this);
        txtNumber.setText(""+D.GetCount());




        //        //لاظهار البيانات من الداتا بيز
        final SwipeRefreshLayout swp = (SwipeRefreshLayout) findViewById(R.id.swpCat);

        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                data = new ArrayList<>(g.getData(Category.this));
                adapterCategory = new AdapterCategory(Category.this, data);
                gvCat.setAdapter(adapterCategory);
                swp.setRefreshing(false);

            }
        });

        data = new ArrayList<>(g.getData(Category.this));
        adapterCategory = new AdapterCategory(Category.this, data);
        gvCat.setAdapter(adapterCategory);


        ////عند الضغط علي logo
        gvCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                datamodel = data.get(i);
                cateno = datamodel.getCategory_Number();
                startActivity(new Intent(Category.this, Products.class));

            }
        });


        //DrawerLayout
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
        txtwelcome.setText("اهـــلا بك > "+ Login.user);
        PicassoClinte.downloadImage(Category.this, Login.url,imguser);


        Menu menu = navigationView.getMenu();
        MenuItem item2 = menu.findItem(R.id.nav_orders);
        MenuItem cart = menu.findItem(R.id.nav_carte);

        DBLite d=new DBLite(Category.this);
        cart.setTitle("My Cart : ( "+d.GetCount()+" )");


        Database db=new Database();
        db.ConnectDB();

        ResultSet rorder=db.RunSearch("Select * from orders where User_UserName='"+ Login.user+"'");
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
        finishAffinity();
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            startActivity(new Intent(Category.this, Login.class));

        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(Category.this,MyProfileActivity.class));


        } else if (id == R.id.nav_carte) {

            startActivity(new Intent(Category.this,Basket_item.class));


        } else if (id == R.id.nav_orders) {
            startActivity(new Intent(Category.this,Basket_item.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_rating) {

            startActivity(new Intent(Category.this,RatingActivity.class));



        } else if (id == R.id.nav_unsubscribe) {

            AlertDialog.Builder msg=new AlertDialog.Builder(Category.this)
                    .setTitle("حذف الحساب")
                    .setMessage("هل تريد حذف الحساب نهائي")
                    .setIcon(R.drawable.sad)
                    .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Database db=new Database();
                            db.ConnectDB();
                            String Delete=db.RUNDML("Delete from Costumer where CustUsername='"+ Login.user+"'");
                            if (Delete.equals("Ok"))
                            {
                                getSharedPreferences("MarbleSH",MODE_PRIVATE)
                                        .edit()
                                        .clear()
                                        .commit();

                                startActivity(new Intent(Category.this, Login.class));

                            }
                            else {
                                Delete = db.RUNDML("update Costumer set Status='0' where CustUsername='" + Login.user + "'");
                                if (Delete.equals("Ok")) {
                                    getSharedPreferences("MarbleSH", MODE_PRIVATE)
                                            .edit()
                                            .clear()
                                            .commit();
                                    Toast.makeText(Category.this, "تم الحذف بنجاح", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Category.this, Login.class));

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
            startActivity(new Intent(Category.this, Login.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
