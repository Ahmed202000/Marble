package com.example.marble;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AdapterBasket_item extends ArrayAdapter<Basket_itemData> {
    Context context;
    ArrayList <Basket_itemData> mCategoryList;

    public AdapterBasket_item(Context context , ArrayList <Basket_itemData> mCategoryList){
        super(context, R.layout.basket_item,mCategoryList);
        this.context = context;
        this.mCategoryList =mCategoryList;
    }

    public class Holder {
        ImageView imglogo,imgadd,imgminu,Imglogo;
        TextView txtpro_name,txtpro_price,txtpro_Total,txtpro_Amuent;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        final Basket_itemData data = getItem(position);

        final Holder viewHolder;

        if (convertView == null){

            viewHolder = new Holder();


            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.basket_item,parent ,false);


            viewHolder.txtpro_name = (TextView) convertView.findViewById(R.id.txtpro_name);
            viewHolder.txtpro_price = (TextView) convertView.findViewById(R.id.txtpro_price);
            viewHolder.txtpro_Amuent = (TextView) convertView.findViewById(R.id.ltxtpro_Amuent);
            viewHolder.txtpro_Total = (TextView) convertView.findViewById(R.id.txtpro_Total);
            viewHolder.imglogo = (ImageView) convertView.findViewById(R.id.imglogo);
            viewHolder.Imglogo = (ImageView) convertView.findViewById(R.id.imglogo);


            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (Holder) convertView.getTag();
        }

        viewHolder.txtpro_name.setText(data.getName());
        viewHolder.txtpro_price.setText(data.getPrice());
        viewHolder.txtpro_Amuent.setText(data.getQTY());
        viewHolder.txtpro_Total.setText(data.getTotal());
        PicassoClinte.downloadImage(context,data.getImage(),viewHolder.Imglogo);


        viewHolder.imgminu =(ImageView) convertView.findViewById(R.id.imgminus);
        viewHolder.imgminu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qty=Integer.parseInt(viewHolder.txtpro_Amuent.getText().toString());
                if (qty>1) {
                    qty--;
                    viewHolder.txtpro_Amuent.setText(String.valueOf(qty));

                    DBLite db=new DBLite(context);
                    double total=qty*Double.parseDouble(data.getPrice());
                    db.UpdateQTY(data.getOrderID(),String.valueOf(qty),String.valueOf(total));
                    viewHolder.txtpro_Total.setText(String.valueOf(total));
                     Basket_item.txtfinal.setText(String.valueOf(db.Getsum()));

                }
            }
        });


        viewHolder.imgadd = (ImageView) convertView.findViewById(R.id.imgadd);

        viewHolder.imgadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qty=Integer.parseInt(viewHolder.txtpro_Amuent.getText().toString());
                qty++;
                viewHolder.txtpro_Amuent.setText(String.valueOf(qty));

                DBLite db=new DBLite(context);
                double total=qty*Double.parseDouble(data.getPrice());
                db.UpdateQTY(data.getOrderID(),String.valueOf(qty),String.valueOf(total));
                viewHolder.txtpro_Total.setText(String.valueOf(total));
                Basket_item.txtfinal.setText(String.valueOf(db.Getsum()));


            }
        });



        //  viewHolder.mImage

        return convertView;
    }




}
