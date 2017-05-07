package com.signu.signu.ui;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcos on 06/05/2017.
 */

class MyAdapterView  {

    private static final String LOG_TAG = "ADAPTER VIEW LOG";
    private List<File> objects;
    private List<File> selectedFileList;
    private final Context context;

    public MyAdapterView(Context context, List<File> objects){
        this.context = context;
        this.objects = objects;
        selectedFileList = new ArrayList<>();
    }


}
