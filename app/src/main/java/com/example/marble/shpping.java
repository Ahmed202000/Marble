package com.example.marble;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;

public class shpping extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText txtlastname,txtferistname,txtaddShPPING,txtPhone_shpp;
    Button btncart,btnDete_order;
    Spinner sppShppingType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shpping);

        txtlastname=findViewById(R.id.txtlastname);
        txtferistname=findViewById(R.id.txtferistname);
        txtaddShPPING=findViewById(R.id.txtaddShPPING);
        txtPhone_shpp=findViewById(R.id.txtPhone_shpp);
        btncart=findViewById(R.id.btncart);
        btnDete_order=findViewById(R.id.btnDete_order);
        sppShppingType=findViewById(R.id.sppShppingType);




        ArrayAdapter adapter= ArrayAdapter.createFromResource(
                       this,
                R.array.Spinner,
                R.layout.color_spinner
        );

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        sppShppingType.setAdapter(adapter);
        sppShppingType.setOnItemSelectedListener(this);




        //كود السله

        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(shpping.this,Basket_item.class));
            }
        });

        //كود تفاصيل الطلب
        btnDete_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Database db = new Database();
                Connection conn = db.ConnectDB();

                if (conn == null)
                    Toast.makeText(shpping.this, "الرجاء الاتصال بالانترنت", Toast.LENGTH_SHORT).show();
                else {
                    if (txtlastname.getText().toString().isEmpty()) {
                        txtlastname.setError("الرجاء ادخال  اسمك الاول");
                        txtlastname.requestFocus();
                    } else {
                        if (txtferistname.getText().toString().isEmpty()) {
                            txtferistname.setError("الرجاء ادخال  اسم العائله");
                            txtferistname.requestFocus();
                        } else {
                            if (txtaddShPPING.getText().toString().isEmpty()) {
                                txtaddShPPING.setError("الرجاء ادخال عنوان الشحن ");
                                txtaddShPPING.requestFocus();
                            } else {
                                if (txtPhone_shpp.getText().toString().isEmpty()) {
                                    txtPhone_shpp.setError("الجاء ادخال  تليفون الشحن");
                                    txtPhone_shpp.requestFocus();

                                        } else {
                                            String msg = db.RUNDML("insert into Shipping values('" +sppShppingType.getSelectedItem() + "','" + txtaddShPPING.getText() + "','" + txtlastname.getText() + "','" + txtferistname.getText() + "','" + txtPhone_shpp.getText() + "')");
                                            if (msg.equals("Ok")) {

                                                                startActivity(new Intent(shpping.this, OrderDetails.class));

                                            } else
                                                Toast.makeText(shpping.this, "" + msg, Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }
                            }

                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
