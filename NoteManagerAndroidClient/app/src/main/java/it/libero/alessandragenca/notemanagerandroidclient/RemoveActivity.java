package it.libero.alessandragenca.notemanagerandroidclient;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;

import com.google.gson.Gson;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.io.IOException;

import it.libero.alessandragenca.notemanagerandroidclient.commons.ErrorCodes;
import it.libero.alessandragenca.notemanagerandroidclient.commons.InvalidKeyException;

public class RemoveActivity extends AppCompatActivity {


        private final String TAG = "ALE_DICTIONARY";

        private Gson gson;
        private String baseURI = "http://10.0.2.2:8182/NoteRegApplication/";
        private EditText textIN;
        private EditText password;
        private EditText username;
        private TextView textOUT;

        public class RemoveRestTask extends AsyncTask<String, Void, String> {


            @Override
            protected  String doInBackground(String... params) {

                ClientResource cr;
                Gson gson = new Gson();
                String URI = baseURI + "notes/" + params[0];
                String jsonResponse = null;
                cr = new ClientResource(URI);
                ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
                ChallengeResponse authentication = new ChallengeResponse(scheme, params[1], params[2]);
                cr.setChallengeResponse(authentication);


                try {
                    jsonResponse = cr.delete().getText();
                    if (cr.getStatus().getCode()== ErrorCodes.INVALID_KEY_CODE)
                        throw gson.fromJson(jsonResponse, InvalidKeyException.class);

                } catch (ResourceException | IOException e1) {
                    if (org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED.equals(cr.getStatus())) {
                        // Unauthorized access
                        jsonResponse= "Access unauthorized by the server, check your credentials";
                        Log.e(TAG, jsonResponse);
                    } else {

                        jsonResponse = "Error: " + cr.getStatus().getCode() + " - " + cr.getStatus().getDescription() +
                                " - " + cr.getStatus().getReasonPhrase();
                        Log.e(TAG, jsonResponse);
                    }

                } catch (InvalidKeyException e2) {
                    jsonResponse = "Error: " + cr.getStatus().getCode() + " - " + e2.getMessage();
                    Log.e(TAG, jsonResponse);
                }

                return jsonResponse;
            }


            @Override
            protected void onPostExecute(String res) {
                textOUT.setTextColor(Color.BLUE);
                textOUT.setTextSize(3, 10);
                if (res!=null) {
                    textOUT.setText(res);
                }
            }

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_remove);

            textIN = (EditText) findViewById(R.id.titleToRemove);
            username = (EditText) findViewById(R.id.usernameRemoveNote);
            password = (EditText) findViewById(R.id.passwordRemoveNote);
            textOUT = (TextView) findViewById(R.id.noteOutputR);
            textOUT.setScroller(new Scroller(getApplicationContext()));
            textOUT.setMaxLines(2);

            textOUT.setHorizontalScrollBarEnabled(true);
            textOUT.setMovementMethod(new ScrollingMovementMethod());
            textIN.setSingleLine();
            //textOUT.setSingleLine();
            username.setSingleLine();
            password.setSingleLine();
            textOUT.setTextColor(Color.BLUE);
            textOUT.setTextSize(3, 10);
            gson = new Gson();
        }

        public void goremove (View v) {

            if (textIN.getText().toString().equalsIgnoreCase("")) { // Nel campo input deve essere inserita la key
                textOUT.setText("Insert Title ");

            }
            else {
                new RemoveActivity.RemoveRestTask().execute(textIN.getText().toString(), username.getText().toString(), password.getText().toString());
            }

        }
    }
