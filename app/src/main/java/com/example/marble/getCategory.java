package com.example.marble;

import android.content.Context;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class getCategory {
    public List<CategoryData> getData(Context cn)
    {

        List<CategoryData> data=new ArrayList<>();
        Database db = new Database();
        Connection coon = db.ConnectDB();

        if (coon == null)
            Toast.makeText(cn, "الرجاء الاتصال بالانترنت", Toast.LENGTH_SHORT).show();

         else {

            ResultSet rs = db.RunSearch("Select * from Category");
            try {
                while (rs.next()) {
                    CategoryData c = new CategoryData();
                    c.setCategory_Number(rs.getString(1));
                    c.setCat_Name(rs.getString(2));
                    c.setCat_logo(rs.getString(3));
                    data.add(c);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return data;
    }
}
