package com.signu.signu.ui;

import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by Marcos on 05/05/2017.
 */

public class PDFFile extends File {
    private boolean isChecked;
    private File file;

    public PDFFile(@NonNull String pathname) {
        super(pathname);
        isChecked = false;
    }

    public PDFFile(@NonNull File file){
        super(file.getAbsolutePath());
        isChecked = false;
    }

    private boolean checkIfPDF(){
        return file.getName().endsWith(".pdf");
    }

    public File getFile(){
        return file;
    }

    public void setFile(File f){
        file = f;
    }

    public boolean isChecked(){
        return isChecked;
    }

    public void setChecked(boolean b){
        isChecked = b;
    }
}
