package com.signu.signu.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.signu.signu.R;

import java.io.File;
import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Marcos on 06/05/2017.
 */

class MyAdapterView implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final String LOG_TAG = "ADAPTER VIEW LOG";
    private ArrayList<File> fileList;
    private ArrayList<File> selectedFileList;
    private final Context context;

    public MyAdapterView(Context context, ArrayList<File> fileList){
        this.context = context;
        this.fileList = fileList;
        selectedFileList = new ArrayList<>();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(LOG_TAG, "onItemClick");

        File f = fileList.get(position);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox_pdf);

        if(selectedFileList.isEmpty()){
            Toast.makeText(context, "File selected", Toast.LENGTH_LONG).show();
        }else{
            if(checkBox.isSelected()){
                // Desselect
                Log.e(LOG_TAG, "Checkbox was selected");
                selectedFileList.remove(f);
                checkBox.setSelected(false);

                if(selectedFileList.isEmpty()) {
                    // Go back to normal mode
                    Button importButton = (Button) view.findViewById(R.id.import_pdf_button);
                    Button deleteButton = (Button) view.findViewById(R.id.import_pdf_button);

                    importButton.setVisibility(View.INVISIBLE);
                    deleteButton.setVisibility(View.INVISIBLE);
                }


            } else{
                // Select
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setChecked(true);
                selectedFileList.add(f);
            }
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(LOG_TAG, "onItemLongClick");

        File f = fileList.get(position);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox_pdf);

        if(!checkBox.isSelected() && selectedFileList.isEmpty()){
            Log.e(LOG_TAG, "Checkbox was no selected");
            selectedFileList.add(f);
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setChecked(true);
            checkBox.setWidth(50);
            // TODO Notify to Main Activity

        }

        return true;
    }
}
