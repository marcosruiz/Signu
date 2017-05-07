package com.signu.signu.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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

class MyBaseAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private static final String LOG_TAG = "MY BASE ADAPTER LOG";

    private final Context context;
    private final ArrayList<PDFFile> objects;
    private final Activity activity;

    int selectedFiles;
    private boolean showCheckBoxes;

    //Item view
    int pdf_image_default = R.drawable.ic_description_black_48dp;
    TextView textName;
    TextView textSize;
    ImageView imagePdf;
    CheckBox checkBox;


    public MyBaseAdapter(@NonNull Context context, @NonNull List<PDFFile> objects) {
        this.context = context;
        this.objects = (ArrayList) objects;
        this.activity = (Activity) context;
        showCheckBoxes = false;
        selectedFiles = 0;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public PDFFile getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        v = activity.getLayoutInflater().inflate(R.layout.pdf_list_item, null);

        imagePdf = (ImageView) v.findViewById(R.id.image_pdf);
        textName = (TextView) v.findViewById(R.id.text_name_pdf);
        textSize = (TextView) v.findViewById(R.id.text_size_pdf);
        checkBox = (CheckBox) v.findViewById(R.id.checkbox_pdf);

        imagePdf.setImageResource(pdf_image_default);
        textName.setText(objects.get(position).getName());
        textSize.setText((objects.get(position).length()/1000) + " KB");

        if(showCheckBoxes){
            checkBox.setVisibility(View.VISIBLE);
            if(objects.get(position).isChecked()){
                checkBox.setChecked(true);
            }else{
                checkBox.setChecked(false);
            }
        }else{
            checkBox.setVisibility(View.GONE);
        }

        return v;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(LOG_TAG, "onItemClick");

        PDFFile f = objects.get(position);
        CheckBox thisCB = (CheckBox) view.findViewById(R.id.checkbox_pdf);

        if(!showCheckBoxes){
            Toast.makeText(context, "File selected", Toast.LENGTH_LONG).show();
        }else{
            if(thisCB.isChecked()){
                // Desselect
                Log.e(LOG_TAG, "Checkbox was selected");
                int indexOfPdf = objects.indexOf(f);
                objects.get(indexOfPdf).setChecked(false);
                selectedFiles--;
                if(selectedFiles == 0) {
                    Log.e(LOG_TAG, "Dissapear checkboxes");
                    showCheckBoxes = false;

                }
            } else{
                // Select
                Log.e(LOG_TAG, "Checkbox was no selected");
                int indexOfPdf = objects.indexOf(f);
                objects.get(indexOfPdf).setChecked(true);
                selectedFiles++;
            }
            this.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(LOG_TAG, "onItemLongClick");

        if(!showCheckBoxes){
            Log.e(LOG_TAG, "Checkbox was no selected");
            PDFFile f = objects.get(position);

            int indexOfPdf = objects.indexOf(f);
            objects.get(indexOfPdf).setChecked(true);
            selectedFiles++;

            showCheckBoxes = true;
            this.notifyDataSetChanged();
        }

        return true;
    }
}
