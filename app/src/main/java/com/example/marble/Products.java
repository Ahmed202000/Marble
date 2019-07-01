package com.example.marble;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Products extends AppCompatActivity {
    ListView gvPro;
    getProduct g = new getProduct();
    ProductData datamodel;
    AdapterProduct adapterProduct;
    ArrayList<ProductData> data;

    public static String productNo;
    public static String imgLogoProduct;

    TextView txtnameproduct;
    EditText txtProduct_quntinty;
    ImageView logo;

    ImageView shopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        //تعريف المتغيرات
        gvPro = findViewById(R.id.gvProduct);
        txtProduct_quntinty=(EditText) findViewById(R.id.txtProduct_quntinty);
        txtnameproduct=findViewById(R.id.txtnameproduct);
        logo=findViewById(R.id.imglogo);
        TextView txtNumber=findViewById(R.id.txtNumber_Of_Product);


        shopping=(ImageView)findViewById(R.id.imgShopping);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Products.this,Basket_item.class);
                startActivity(intent);
                finish();
            }
        });
        //داله العدد
        DBLite D=new DBLite(Products.this);
        txtNumber.setText(""+D.GetCount());




        // لاظهار البيانات من الداتا بيز
        final SwipeRefreshLayout swp = (SwipeRefreshLayout) findViewById(R.id.swpPro);

        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                data = new ArrayList<>(g.getData(Products.this, Category.cateno));
                adapterProduct = new AdapterProduct(Products.this, data);
                gvPro.setAdapter(adapterProduct);
                swp.setRefreshing(false);
            }
        });


        data = new ArrayList<>(g.getData(Products.this, Category.cateno));
        adapterProduct = new AdapterProduct(Products.this, data);
        gvPro.setAdapter(adapterProduct);

        ////عند الضغط علي logo
        gvPro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                datamodel = data.get(i);
                productNo = datamodel.getCategory_Number();


                // create bottom sheet dialog
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Products.this);

                // create layout inflater
                LayoutInflater layoutInflater = LayoutInflater.from(Products.this);

                View view1 = layoutInflater.inflate(R.layout.layout_product_data, null);


                bottomSheetDialog.setContentView(view1);


                ImageView img = view1.findViewById(R.id.imglogo);
                TextView txtnameProduct = view1.findViewById(R.id.txtnameproduct);
                TextView txtP_price = view1.findViewById(R.id.txtPrice_Meters);
                final EditText txtquntinty=view1.findViewById(R.id.txtProduct_quntinty);
                Button btnBy = view1.findViewById(R.id.btnBuy);


                txtnameProduct.setText(datamodel.getProduct_Name());
                txtP_price.setText(datamodel.getProducts_Price_Meters());

                imgLogoProduct = datamodel.getProduct_logo();

                if (datamodel.getProduct_logo() != null && datamodel.getProduct_logo().length() > 0) {
                    PicassoClinte.downloadImage(Products.this, datamodel.getProduct_logo(), img);
                }

                // عند الضغط علي زرار الشراء





                btnBy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DBLite d = new DBLite(Products.this);

                        double total = Double.parseDouble(datamodel.getProducts_Price_Meters()) * Double.parseDouble(txtquntinty.getText().toString()+1);

                        d.AddTOCard(datamodel.getProduct_Number(), datamodel.getProduct_Name(), txtquntinty.getText().toString(), datamodel.getProducts_Price_Meters(), String.valueOf(total), datamodel.getProduct_logo());

                        Toast.makeText(Products.this, "تمت الاضافه في السله", Toast.LENGTH_SHORT).show();

                        bottomSheetDialog.dismiss();
                    }



                });


                bottomSheetDialog.show();


            }

        });



    }


}




