package com.signu.signu.ui;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.signu.signu.R;
import com.signu.signu.model.PdfManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MAIN ACTIVITY LOG";
    ArrayList<PDFFile> fileList = new ArrayList<>();

    private static Context context;
    Button buttonImport;
    Button buttonExport;
    Button buttonDelete;
    FileChooserDialogFragment fcd;
    PdfManager pdfManager;
    Snackbar snackbar;
    ListView listView;

    MyBaseAdapter myBaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();


        setContentView(R.layout.activity_main);
        System.out.println("Apk starts");
        // PDF List
        listView = (ListView) findViewById(R.id.pdf_list);

        myBaseAdapter = new MyBaseAdapter(this, fileList);
        listView.setOnItemClickListener(myBaseAdapter);
        listView.setOnItemLongClickListener(myBaseAdapter);
        listView.setAdapter(myBaseAdapter);

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
                for(PDFFile f : fileList){
                    if(f.isChecked()){
                        exportResult(f);
                    }
                }
                myBaseAdapter.init();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ArrayList<PDFFile> toDelete = new ArrayList<PDFFile>();
                for(PDFFile f : fileList){
                    if(f.isChecked()){
                        toDelete.add(f);
                    }
                }
                for(PDFFile f : toDelete){
                    fileList.remove(f);
                    pdfManager.deleteFile(f);
                }
                myBaseAdapter.init();
            }
        });

        // PDF Files
        pdfManager = new PdfManager(getAppContext());
        fillFileList();

    }

    public void fillFileList(){
        File pdfDir = pdfManager.getPDFDir();
        fileList.clear();
        if(pdfDir.isDirectory()){
            for (File f : pdfDir.listFiles()){
                if(isPdf(f)){
                    PDFFile pdfFile = new PDFFile(f);
                    fileList.add(pdfFile);
                    Log.e(LOG_TAG, "File found " + f.getName());
                }
            }
        }
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
            fillFileList();
            myBaseAdapter.init();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void exportResult(File f){
        Log.i("MainActivity", "exportResult");
        // Export pdf
        try {
            pdfManager.saveOnExternalStorage(f);

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
        buttonExport.setVisibility(View.VISIBLE);
        buttonDelete.setVisibility(View.VISIBLE);
    }

    public void disableButtons(){
        buttonExport.setVisibility(View.INVISIBLE);
        buttonDelete.setVisibility(View.INVISIBLE);
    }
}
