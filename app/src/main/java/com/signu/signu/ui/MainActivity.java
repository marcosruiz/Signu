package com.signu.signu.ui;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.signu.signu.R;
import com.signu.signu.model.PDFFile;
import com.signu.signu.model.PdfManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MAIN ACTIVITY LOG";
    ArrayList<File> fileList = new ArrayList<>();
    ArrayList<PDFFile> pdfFileList = new ArrayList<>();
    int pdf_image_default = R.drawable.ic_description_black_48dp;
    private static Context context;
    Button buttonImport;
    Button buttonExport;
    FileChooserDialogFragment fcd;
    PdfManager pdfManager;
    Snackbar snackbar;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        setContentView(R.layout.activity_main);
        System.out.println("Apk starts");
        // PDF List
        listView = (ListView) findViewById(R.id.list);
        MyAdapter myAdapter= new MyAdapter(this, R.layout.activity_main, pdfFileList);
        listView.setAdapter(myAdapter);
        // PDF Files
        pdfManager = new PdfManager(getAppContext());
        File downloadsDir = pdfManager.getPDFDir();

        if(downloadsDir.isDirectory()){
            for (File f : downloadsDir.listFiles()){
                if(isPdf(f)){
                    fileList.add(f);
                    Log.e(LOG_TAG, "File found " + f.getName());
                }
            }
        }

        //Buttons import and export
        buttonExport = (Button) findViewById(R.id.export_pdf_button);
        buttonImport = (Button) findViewById(R.id.import_pdf_button);

        buttonImport.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        buttonExport.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO pdfManager.saveOnExternalStorage();
            }
        });

    }

    public void showDialog(){
        DialogFragment newFragment = FileChooserDialogFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }

    public boolean isPdf(File f){
        if(f.isFile()){
            if(f.getName().endsWith(".pdf")){
                return true;
            }
        }
        return false;
    }

    public void importResult(File f){
        Log.i("MainActivity", "importResult");
        //Import pdf
        try {
            pdfManager.saveOnInternalStorage(f);
            fileList.add(f);


        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(MainActivity.getAppContext() , f.getName() + " NOT imported", Toast.LENGTH_SHORT).show();
        }


    }

    public void exportResult(File f){
        Log.i("MainActivity", "exportResult");
        // Export pdf
        try {
            pdfManager.saveOnExternalStorage(f);
            fileList.add(f);
            Toast.makeText(MainActivity.getAppContext() , f.getName() + " exported", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.getAppContext() , f.getName() + " NOT exported", Toast.LENGTH_SHORT).show();
        }

    }

    class MyAdapter extends ArrayAdapter<PDFFile> implements View.OnClickListener{

        public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PDFFile> objects) {
            super(context, resource, objects);
        }

        @Override
        public int getCount() {
            return fileList.size();
        }

        @Override
        public PDFFile getItem(int position) {
            return pdfFileList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.pdf_list_item, null);

            ImageView imagePdf = (ImageView)convertView.findViewById(R.id.image_pdf);
            TextView textName = (TextView) convertView.findViewById(R.id.text_name_pdf);
            TextView textSize = (TextView) convertView.findViewById(R.id.text_size_pdf);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            imagePdf.setImageResource(pdf_image_default);
            textName.setText(fileList.get(position).getName());
            textSize.setText((fileList.get(position).length()/1000) + " KB");

            return convertView;
        }

        @Override
        public void onClick(View v) {
            Log.e(LOG_TAG, "Enter to onClick");
            v.findViewById(R.id.text_name_pdf);
            CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkbox);
            if(!checkBox.isSelected()){
                Log.e(LOG_TAG, "Checkbox selected");
                checkBox.setSelected(true);
                //TODO Dejar indicado que se ha seleccionado que ha sido seleccionado

            }else{
                Log.e(LOG_TAG, "Checkbox desselected");
                checkBox.setSelected(false);
            }
        }
    }

    public static Context getAppContext(){
        return context;
    }
}
