package com.example.marble;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class Basket_item extends AppCompatActivity {


    public static String productNo;

    GridView gvBasket;

   public static Basket_itemData model;
    DBLite db=new DBLite(this);
    AdapterBasket_item adapterBasket_item;
    ArrayList<Basket_itemData>d;
    public static TextView txtfinal;

    ProgressBar loadLesson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_item);

        gvBasket = findViewById(R.id.gvBasket);

        d= new ArrayList<>(db.Get_Baskt(this));
        adapterBasket_item = new AdapterBasket_item(Basket_item.this, d);
        gvBasket.setAdapter(adapterBasket_item);

        txtfinal=findViewById(R.id.txt_finalTotal);

        DBLite db=new DBLite(this);
        txtfinal.setText(String.valueOf(db.Getsum()));


        gvBasket.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {

                model=d.get(i);
                final String OrderNumber=model.getOrderID();
                AlertDialog.Builder Msg=new AlertDialog.Builder(Basket_item.this)
                        .setTitle("الرخــــام")
                        .setMessage("هل تريد حـــذف المنتج")
                        .setIcon(R.drawable.logo)
                        .setPositiveButton("نعـــم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DBLite db=new DBLite(Basket_item.this);
                                db.Deleteitem(OrderNumber);

                                d= new ArrayList<>(db.Get_Baskt(Basket_item.this));
                                adapterBasket_item = new AdapterBasket_item(Basket_item.this, d);
                                gvBasket.setAdapter(adapterBasket_item);
                                txtfinal.setText(String.valueOf(db.Getsum()));



                            }
                        }).setNegativeButton("لا",null);
                Msg.create();
                Msg.show();

                return false;
            }
        });


    }



    public void SaveCart(View view) {


        Database db=new Database();
        Connection conn=db.ConnectDB();

        if (Login.user==null)
            startActivity(new Intent(Basket_item.this, Login.class));

        else
        if(conn==null) {
            Toast.makeText(this, "الرجاء الاتصال بالانترنت", Toast.LENGTH_SHORT).show();
        }
        else

        {
            String msg=db.RUNDML("insert into Orders values('"+ Calendar.getInstance().getTime().toString()+"','"+txtfinal.getText()+"','"+Login.user+"')");
            if(msg.equals("Ok"))
            {
                ResultSet rmax=db.RunSearch("Select max(OrderNo)from Orders where User_UserName='"+Login.user+"'");
                try {
                    if(rmax.next())
                    {
                        String OrderNo=rmax.getString(1);
                        String omsg=null;

                        for (int x=0;x<d.size();x++)
                        {
                            model=d.get(x);
                            omsg= db.RUNDML("insert into sealsProduct values('"+OrderNo+"','"+model.getPrudect_Number()+"','"+model.getPrice()+"','"+model.getQTY()+"','"+model.getTotal()+"')");

                        }
                        if(omsg.equals("Ok")) {
                            Toast.makeText(this, "تم ارسال الطلب بنجاح", Toast.LENGTH_SHORT).show();
                            DBLite g=new DBLite(Basket_item.this);
                            g.DeleteCart();

                            startActivity(new Intent(Basket_item.this,shpping.class));
                        }
                        else
                            Toast.makeText(this, ""+omsg, Toast.LENGTH_SHORT).show();



                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            else
                Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();


        }

    }
}
