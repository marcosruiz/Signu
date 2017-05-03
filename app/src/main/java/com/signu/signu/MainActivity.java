package com.signu.signu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String LOG = "MAIN ACTIVITY LOG";
    ArrayList<File> pdfList = new ArrayList<>();
    int pdf_image_default = R.drawable.ic_description_black_48dp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Apk starts");
        // PDF List
        ListView listView = (ListView) findViewById(R.id.list);
        MyAdapter myAdapter= new MyAdapter();
        listView.setAdapter(myAdapter);
        // PDF Files
        PdfManager pdfManager = new PdfManager();
        File downloadsDir = pdfManager.getDownladsStorageDir();

        if(downloadsDir.isDirectory()){
            for (File f : downloadsDir.listFiles()){
                if(isPdf(f)){
                    pdfList.add(f);
                    Log.e(LOG, "File found " + f.getName());
                }
            }
        }
    }

    public boolean isPdf(File f){
        if(f.isFile()){
            if(f.getName().endsWith(".pdf")){
                return true;
            }
        }
        return false;
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return pdfList.size();
        }

        @Override
        public Object getItem(int position) {
            return pdfList.get(position);
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

            imagePdf.setImageResource(pdf_image_default);
            textName.setText(pdfList.get(position).getName());
            textSize.setText((pdfList.get(position).length()/1000) + " KB");
            return convertView;
        }
    }
}
