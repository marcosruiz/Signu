package com.signu.signu;

import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    String F_NAME_OUT = "out.txt";
    Context appContext;

    @Before
    public void setup(){
        appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        assertEquals("com.signu.signu", appContext.getPackageName());
    }

    @Test
    public void savePdfTest() throws Exception {
        // Create file in the internal storage
        File fTemp = File.createTempFile("out",".txt");
        PdfManager pdfManager = new PdfManager();

        pdfManager.saveOnExternalStorage(fTemp);
        // Check if the new file is saved
        File dirDownloads = pdfManager.getStorageDir(Environment.DIRECTORY_DOWNLOADS);

        int i = 0;
        File[] listFiles = dirDownloads.listFiles();
        while(i < listFiles.length && !listFiles[i].getName().equals(fTemp.getName()) && listFiles[i].isFile()){
            i++;
        }
        checkContentIsEqual(fTemp, listFiles[i]);
    }

    /**
     * Check if the content of f1 and f2 are equals
     * @param fExpected
     * @param fActual
     * @return
     * @throws FileNotFoundException
     */
    public void checkContentIsEqual(File fExpected, File fActual) throws IOException {
        int SIZE_BUF = 1024;
        InputStream in1 = new FileInputStream(fExpected);
        InputStream in2 = new FileInputStream(fActual);
        byte[] buf1 = new byte[SIZE_BUF];
        byte[] buf2 = new byte[SIZE_BUF];
        int len;
        while ((len = in1.read(buf1)) > 0) {
            in2.read(buf2);
            assertArrayEquals(buf1, buf2);
        }
        in1.close();
        in2.close();
    }
}
