package com.signu.signu.model;

import java.io.File;

/**
 * Created by Marcos on 05/05/2017.
 */

public class PDFFile {
    boolean isSelected;
    File file;

    public PDFFile(File f){
        if(checkIfPDF()){
            file = f;
            isSelected = false;
        }
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

    public boolean isSelected(){
        return isSelected;
    }
}
