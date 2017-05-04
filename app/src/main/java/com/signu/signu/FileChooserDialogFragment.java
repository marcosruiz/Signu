package com.signu.signu;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Marcos on 04/05/2017.
 */

public class FileChooserDialogFragment extends DialogFragment {

    TextView actualFolder;

    File downloads;
    File currentFolder;
    public ArrayList<String> fileList = new ArrayList<>();
    ListView listFilesFC;
    TextView textFolder;

    public static FileChooserDialogFragment newInstance(int num) {
        FileChooserDialogFragment frag = new FileChooserDialogFragment();
        Bundle args = new Bundle();
        args.putInt("num", num);
        frag.setArguments(args);
        return frag;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        downloads = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        currentFolder = downloads;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Select a PDF");
        View v = inflater.inflate(R.layout.file_explorer, container, false);
        View tv = v.findViewById(R.id.actual_folder);
        textFolder = (TextView) tv;
        textFolder.setText(currentFolder.getName());
        ListDir(currentFolder);

        // Watch for button clicks.
        Button buttonUp = (Button)v.findViewById(R.id.parent_folder_button);
        buttonUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ListDir(currentFolder.getParentFile());
            }
        });

        listFilesFC = (ListView) v.findViewById(R.id.list_actual_folder);
        listFilesFC.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File selected = new File(fileList.get(position));
                if(selected.isDirectory()){
                    ListDir(selected);

                }else{
                    Toast.makeText(MainActivity.getAppContext() , selected.getName() + " selected", Toast.LENGTH_LONG).show();

                }
            }
        });
        return v;
    }

    void ListDir(File f){
        currentFolder = f;
        textFolder.setText(f.getName());
        File[] files = f.listFiles();
        fileList.clear();
        for(File fAux : files){
            fileList.add(fAux.getName());
        }

        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(MainActivity.getAppContext(), android.R.layout.simple_list_item_1, fileList);

    }


    public void doClick(){
        Log.i("FileChooserDialog", "Click");
    }
}
