package it.libero.alessandragenca.notemanagerandroidclient;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.security.User;

import java.io.IOException;
import java.util.Date;

import it.libero.alessandragenca.notemanagerandroidclient.commons.ErrorCodes;
import it.libero.alessandragenca.notemanagerandroidclient.commons.InvalidKeyException;
import it.libero.alessandragenca.notemanagerandroidclient.commons.Note;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonget = (Button) findViewById(R.id.buttonget);
        Button buttongetnotebydate = (Button) findViewById(R.id.buttonGETNOTEBYDATE);
        Button buttonput = (Button) findViewById(R.id.buttonput);
        Button buttonadd = (Button) findViewById(R.id.buttonadd);
        Button buttonremove= (Button) findViewById(R.id.buttonremove);
        Button buttonremoveAll= (Button) findViewById(R.id.buttonremove2);
        Button buttonGetAll= (Button) findViewById(R.id.buttonGetAllNotes);
        Button buttongetSize =(Button) findViewById(R.id.buttonGetsize);

        buttonget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(MainActivity.this ,GetActivity.class);
                startActivity(Intent);


            }
        });



        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(MainActivity.this ,PostActivity.class);
                startActivity(Intent);


            }
        });


        buttongetnotebydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Intent = new Intent(MainActivity.this ,GetByDateActivity.class);
                startActivity(Intent);


            }
        });


        buttonGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Intent = new Intent(MainActivity.this ,GetAllActivity.class);
                startActivity(Intent);


            }
        });

        buttongetSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Intent = new Intent(MainActivity.this ,GetSizeActivity.class);
                startActivity(Intent);


            }
        });

        buttonput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Intent = new Intent(MainActivity.this ,PutActivity.class);
                startActivity(Intent);


            }
        });




        buttonremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Intent = new Intent(MainActivity.this ,RemoveActivity.class);
                startActivity(Intent);


            }
        });



        buttonremoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Intent = new Intent(MainActivity.this ,RemoveAllActivity.class);
                startActivity(Intent);


            }
        });

    }
}