package com.example.marble;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RatingActivity extends AppCompatActivity {

    EditText txtcommenmts;
    RatingBar rt;
    Button btnsaverate;
    ListView lstitems;
    Spinner spnorders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        txtcommenmts=findViewById(R.id.txtcomments);
        rt=findViewById(R.id.rtorders);
        btnsaverate=findViewById(R.id.btnrate);
        lstitems=findViewById(R.id.lstitems);
        spnorders=findViewById(R.id.spnorders);


        Database db=new Database();
        db.ConnectDB();
        ArrayList data=new ArrayList();
        ResultSet rs=db.RunSearch("Select * from Orders where OrderNo not in (select OrderNo from Rating)");
        try {
            while (rs.next())
            {

                data.add(rs.getString(1));

            }
        }
        catch (SQLException es)
        {

        }
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,data);
        spnorders.setAdapter(adapter);


        spnorders.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Database db=new Database();
                db.ConnectDB();
                ArrayList data=new ArrayList();
                ResultSet rs=db.RunSearch("Select * from view_orders where OrderNo ='"+spnorders.getSelectedItem()+"'");
                try {
                    while (rs.next())
                    {

                        data.add(rs.getString(3) +"  ///  الاجمالي  "+rs.getString(4));

                    }
                }
                catch (SQLException es)
                {

                }
                ArrayAdapter adapter=new ArrayAdapter(RatingActivity.this,android.R.layout.simple_list_item_1,data);
                lstitems.setAdapter(adapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnsaverate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db=new Database();
                db.ConnectDB();
                String ms=db.RUNDML("insert into Rating values ('"+rt.getRating()+"','"+ txtcommenmts.getText()+"','"+Calendar.getInstance().getTime()+"','"+spnorders.getSelectedItem()+"')");
                if(ms.equals("Ok")) {
                    Toast.makeText(RatingActivity.this, "شكــــــرا لك ", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(RatingActivity.this, ""+ms, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(RatingActivity.this, Category.class));

            }
        });







    }
}
