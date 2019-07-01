package com.example.marble;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBLite extends SQLiteOpenHelper {


    public DBLite( Context context) {
        super(context, "Marble_DB", null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE OrderS( orderID INTEGER PRIMARY KEY, Prudect_Number INTEGER, Name TEXT, Total NUMERIC, Price NUMERIC, QTY NUMERIC, image TEXT)");

    }

    public void Deleteitem(String pk)
    {
        String Key="orderID";
        SQLiteDatabase db=getWritableDatabase();
        db.delete("OrderS",Key + " = ?",new String[]{String.valueOf(pk)});
        db.close();


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void AddTOCard(String Proud_Number, String Name, String qty,String price ,String tota,String image)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues data=new ContentValues();
        data.put("Prudect_Number",Proud_Number);
        data.put("Name",Name);
        data.put("QTY",qty);
        data.put("Price",price);
        data.put("Total",tota);
        data.put("image",image);

        db.insert("OrderS",null,data);

    }

    public int GetCount()
    {
        SQLiteDatabase DB=getReadableDatabase();
        Cursor cn=DB.rawQuery("Select count(*)from OrderS",null);
        if (cn.moveToFirst())

        return cn.getInt(0);
        else
            return 0;
    }

    public void DeleteCart()
    {
        SQLiteDatabase m=getWritableDatabase();
        m.execSQL("Delete from OrderS");
    }


    public double Getsum()
    {
        SQLiteDatabase DB=getReadableDatabase();
        Cursor cn=DB.rawQuery("Select sum(total)from OrderS",null);
        if (cn.moveToFirst())

            return cn.getInt(0);
        else
            return 0;
    }


    public void UpdateQTY(String orderID ,String qty,String total )
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues data=new ContentValues();
        data.put("QTY",qty);
        data.put("Total",total);
        String Key="orderID";
        db.update("OrderS",data,Key + " = ?",new String[]{String.valueOf(orderID)});
        db.close();

    }




    public List<Basket_itemData>Get_Baskt(Context cn)
    {
        List<Basket_itemData> data=new ArrayList<>();
        SQLiteDatabase DB=getReadableDatabase();

        Cursor cBasket=DB.rawQuery("Select * from OrderS",null);
           while (cBasket.moveToNext())
           {
               Basket_itemData model=new Basket_itemData();
               model.setOrderID(cBasket.getString(0));
               model.setPrudect_Number(cBasket.getString(1));
               model.setName(cBasket.getString(2));
               model.setPrice(cBasket.getString(4));
               model.setQTY(cBasket.getString(3));
               model.setTotal(cBasket.getString(5));
               model.setImage(cBasket.getString(6));


               data.add(model);
           }
           return data;
    }
}
