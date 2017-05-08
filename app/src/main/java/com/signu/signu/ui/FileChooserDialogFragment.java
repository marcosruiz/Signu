package com.signu.signu.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.signu.signu.R;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Marcos on 04/05/2017.
 */

public class FileChooserDialogFragment extends DialogFragment {

    TextView actualFolder;

    File downloads;
    File currentFolder;
    public ArrayList<String> fileListString = new ArrayList<>();
    public ArrayList<File> fileList = new ArrayList<>();
    ListView listFilesFC;
    TextView textFolder;

    public static FileChooserDialogFragment newInstance() {
        FileChooserDialogFragment frag = new FileChooserDialogFragment();
        return frag;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloads = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        currentFolder = downloads;
        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_Material_DialogWhenLarge;
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Select a PDF");
        View v = inflater.inflate(R.layout.file_explorer, container, false);
        View tv = v.findViewById(R.id.actual_folder);
        textFolder = (TextView) tv;
        textFolder.setText(currentFolder.getName());

        // Watch for button clicks.
        Button buttonUp = (Button)v.findViewById(R.id.parent_folder_button);
        buttonUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ListDir(currentFolder.getParentFile());
            }
        });
        Button buttonClose = (Button)v.findViewById(R.id.close_button);
        buttonClose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dismiss();
            }
        });

        listFilesFC = (ListView) v.findViewById(R.id.list_actual_folder);

        listFilesFC.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File selected = fileList.get(position);
                if(selected.isDirectory()){
                    ListDir(selected);
                }else{
                    ((MainActivity)getActivity()).importResult(selected);

                    Snackbar snackbar = Snackbar
                            .make(view, "File imported", Snackbar.LENGTH_LONG);
//                            .setAction("UNDO", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Snackbar snackbar1 = Snackbar.make(view, "File unimported", Snackbar.LENGTH_SHORT);
//                                    snackbar1.show();
//                                }
//                            });
                    snackbar.show();
                }
            }
        });

        ListDir(downloads);


        return v;
    }

    void ListDir(File f){
        currentFolder = f;
        textFolder.setText(f.getName());

        File[] files = f.listFiles();
        fileList.clear();

        for(File fAux : files){
            fileList.add(fAux);
        }

        fileListString.clear();
        for(File fAux : fileList){
            fileListString.add(fAux.getName());
        }

        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(MainActivity.getAppContext(), R.layout.fc_list_item, fileListString);


        listFilesFC.setAdapter(directoryList);

    }

    public void doClick(){
        Log.i("FileChooserDialog", "Click");
    }
}
