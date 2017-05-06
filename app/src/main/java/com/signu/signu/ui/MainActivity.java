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
    ArrayList<Boolean> isSelected = new ArrayList<>();

    private static Context context;
    Button buttonImport;
    Button buttonExport;
    Button buttonDelete;
    FileChooserDialogFragment fcd;
    PdfManager pdfManager;
    Snackbar snackbar;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        // PDF Files
        pdfManager = new PdfManager(getAppContext());
        File pdfDir = pdfManager.getPDFDir();

        if(pdfDir.isDirectory()){
            for (File f : pdfDir.listFiles()){
                if(isPdf(f)){
                    fileList.add(f);
                    isSelected.add(false);
                    Log.e(LOG_TAG, "File found " + f.getName());
                }
            }
        }

        setContentView(R.layout.activity_main);
        System.out.println("Apk starts");
        // PDF List
        listView = (ListView) findViewById(R.id.pdf_list);

        MyAdapterView myAdapterView = new MyAdapterView(this, fileList);
        listView.setOnItemClickListener(myAdapterView);
        listView.setOnItemLongClickListener(myAdapterView);

        MyArrayAdapter myArrayAdapter= new MyArrayAdapter(this, R.layout.activity_main, fileList);
        listView.setAdapter(myArrayAdapter);



        //Buttons import and export
        buttonExport = (Button) findViewById(R.id.export_pdf_button);
        buttonImport = (Button) findViewById(R.id.import_pdf_button);
        buttonDelete = (Button) findViewById(R.id.delete_pdf_button);

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
            isSelected.add(false);

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
        getLayoutInflater();

    }

    public static Context getAppContext(){
        return context;
    }

    public void enableButtons(){
        buttonImport.setVisibility(View.VISIBLE);
        buttonDelete.setVisibility(View.VISIBLE);
    }

    public void disableButtons(){
        buttonImport.setVisibility(View.INVISIBLE);
        buttonDelete.setVisibility(View.INVISIBLE);
    }
}
