package com.signu.signu.model;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.util.Log;

import com.signu.signu.ui.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Marcos on 03/05/2017.
 */

public class PdfManager {

    private static final String LOG_TAG = "PDF MANAGER LOG";
    private static final String PDF_DIR = "Pdfs";
    private static final String CERT_DIR = "Certificates";
    private File pdfDir;
    private File certDir;
    private File rootAppDir;
    private Context context;

    public PdfManager(Context context){
        this.context = context;
        rootAppDir = context.getFilesDir();
        pdfDir = new File(rootAppDir , PDF_DIR);
        certDir = new File(rootAppDir , CERT_DIR);
        pdfDir.mkdir();
        certDir.mkdir();
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getExternalFile(String filename){
        return new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
    }

    /**
     * Devuelve una instancia de File que reresenta el directorio correspondiente dentro de Downloads
     * @return
     */
    public File getDownladsStorageDir() {
        return getStorageDir(Environment.DIRECTORY_DOWNLOADS);
    }

    public File getPDFDir(){
        return pdfDir;
    }

    public File getStorageDir(String folder){
        assertTrue(isExternalStorageReadable());
        return Environment.getExternalStoragePublicDirectory(folder);
    }

    public void saveOnExternalStorage(File internalFile) throws IOException {
        assertTrue(isExternalStorageWritable());
        File documents = new File(Environment.DIRECTORY_DOWNLOADS);
        if(!documents.exists()){
            documents.mkdir();
        }
        File externalFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), internalFile.getName());
        copy(internalFile, externalFile);
    }

    public void saveOnInternalStorage(File externalFile) throws IOException {
        assertTrue(isExternalStorageReadable());
        File internalFile = new File(pdfDir, externalFile.getName());
        copy(externalFile, internalFile);
    }

    private void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public void deleteFile(File f){
        f.delete();
    }



}
