package com.example.y.readwritefile;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText add_data_et;
    Button add_data_bt;
    TextView display_data_tv;
    Button delete_data_bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_data_bt = (Button) findViewById(R.id.add_data_bt);
        delete_data_bt = (Button) findViewById(R.id.delete_data_bt);
        add_data_et = (EditText) findViewById(R.id.add_data_et);
        display_data_tv = (TextView) findViewById(R.id.display_data_tv);

        add_data_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Mytask().execute();
            }
        });
        delete_data_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletefile();
            }
        });
    }

    public void writeexternalstorage(){
        String state ;
        state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File (Root.getAbsolutePath()+"/MyAppFile");
            if(!Dir.exists()){
                Dir.mkdir();
            }
            File file  = new File(Dir,"MyMessage.txt");
            String message = add_data_et.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                add_data_et.setText("");
                Toast.makeText(this, "Message Saved", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(this, "External Storage Not Mounted", Toast.LENGTH_SHORT).show();
        }

    }

    public void readexternalstoragee(){
        File Root = Environment.getExternalStorageDirectory();
        File Dir = new File (Root.getAbsolutePath()+"/MyAppFile");
        File file  = new File(Dir,"MyMessage.txt");
        String message;
        try {
            FileInputStream fileinputstream = new FileInputStream(file);
            InputStreamReader inputstreamreader = new InputStreamReader(fileinputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            StringBuffer stringbuffer = new StringBuffer();
            while((message = bufferedreader.readLine())!=null){
                stringbuffer.append(message+"\n");

            }
            display_data_tv.setText(stringbuffer.toString());
            display_data_tv.setVisibility(View.VISIBLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletefile(){
        File Root = Environment.getExternalStorageDirectory();
        File Dir = new File (Root.getAbsolutePath()+"/MyAppFile");
        File file  = new File(Dir,"MyMessage.txt");
        if(Dir.exists()){
            boolean deleted = file.delete();
            Toast.makeText(this, "File Deleted"+deleted, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "File Not Found", Toast.LENGTH_SHORT).show();
        }

    }
    public class Mytask extends AsyncTask<Void,Void,String>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(Void... voids) {

            return null;
        }



        @Override
        protected void onPostExecute(String o) {

            writeexternalstorage();
            readexternalstoragee();
            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            super.onPostExecute(o);
        }
    }
}
