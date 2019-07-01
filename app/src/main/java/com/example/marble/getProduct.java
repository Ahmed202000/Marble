package com.example.marble;

import android.content.Context;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class getProduct {
    public List<ProductData> getData(Context cn, String categoryno)
    {

        List<ProductData> data=new ArrayList<>();
        Database db = new Database();
        Connection coon = db.ConnectDB();

        if (coon == null)
            Toast.makeText(cn, "الرجاء الاتصال بالانترنت", Toast.LENGTH_SHORT).show();

         else {

            ResultSet r = db.RunSearch("Select * from Products where Category_Number='"+categoryno+"'");
            try {
                while (r.next()) {
                    ProductData P= new ProductData();
                    P.setProduct_Number(r.getString(1));
                    P.setProduct_Name(r.getString(2));
                    P.setProduct_logo(r.getString(3));
                    P.setProducts_Price_Meters(r.getString(4));
                    P.setProducts_Amuent(r.getString(5));
                    P.setCategory_Number(r.getString(6));
                    data.add(P);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return data;
    }
}
