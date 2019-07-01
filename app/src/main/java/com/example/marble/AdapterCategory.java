package com.example.marble;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AdapterCategory extends ArrayAdapter<CategoryData> {
    Context context;
    ArrayList < CategoryData > mCategoryList;

    public AdapterCategory(Context context , ArrayList <CategoryData> mCategoryList){
        super(context, R.layout.category,mCategoryList);
        this.context = context;
        this.mCategoryList =mCategoryList;
    }

    public class Holder {
        TextView txtname;
        ImageView imgcategory;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        CategoryData  data = getItem(position);

        Holder viewHolder;

        if (convertView == null){

            viewHolder = new Holder();


            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.category,parent ,false);


            viewHolder.txtname = (TextView) convertView.findViewById(R.id.txtCategoryName);
            viewHolder.imgcategory = (ImageView) convertView.findViewById(R.id.imgCategory);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (Holder) convertView.getTag();
        }

        viewHolder.txtname.setText(data.getCat_Name());
        PicassoClinte.downloadImage(context,data.getCat_logo(),viewHolder.imgcategory);


      //  viewHolder.mImage

        return convertView;
    }




}
