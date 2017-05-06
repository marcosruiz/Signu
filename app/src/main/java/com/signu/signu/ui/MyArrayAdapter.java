package com.signu.signu.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.signu.signu.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcos on 06/05/2017.
 */

class MyArrayAdapter extends ArrayAdapter<File>{

    private static final String LOG_TAG = "MY ARRAY ADAPTER LOG";
    int pdf_image_default = R.drawable.ic_description_black_48dp;
    ArrayList<File> selectedFileList = new ArrayList<>();
    Activity activity;


    int actualMode = 0;

    public MyArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<File> objects) {
        super(context, resource, objects);
        this.activity = (Activity) context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public File getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = activity.getLayoutInflater().inflate(R.layout.pdf_list_item, null);

        ImageView imagePdf = (ImageView)convertView.findViewById(R.id.image_pdf);
        TextView textName = (TextView) convertView.findViewById(R.id.text_name_pdf);
        TextView textSize = (TextView) convertView.findViewById(R.id.text_size_pdf);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

        imagePdf.setImageResource(pdf_image_default);
        textName.setText(super.getItem(position).getName());
        textSize.setText((super.getItem(position).length()/1000) + " KB");

        return convertView;
    }
}
