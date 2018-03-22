package it.libero.alessandragenca.notemanagerandroidclient;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.io.IOException;
import java.util.Date;

import it.libero.alessandragenca.notemanagerandroidclient.commons.ErrorCodes;
import it.libero.alessandragenca.notemanagerandroidclient.commons.InvalidKeyException;
import it.libero.alessandragenca.notemanagerandroidclient.commons.Note;

import static java.lang.Integer.parseInt;

public class PostActivity extends AppCompatActivity {



        private final String TAG = "ALE_DICTIONARY";

        private Gson gson;
        private String baseURI = "http://10.0.2.2:8182/NoteRegApplication/";
        private EditText text;
        private EditText textTitle;
        // private EditText username;
        //private EditText password;
        private TextView textOUT;

        SharedPreferences editor;
        public final static String prefName="Preference";

    public class PostRestTask extends AsyncTask<String, Void, String> {

        private Note n;

        protected String doInBackground(String... params) {


            String title = params[0];
            String text = params[1];
            Date date = new Date();
            n = new Note(title, text,date);


            ClientResource cr;
            Gson gson = new Gson();

            String URI = baseURI + "notes";
            String jsonResponse = null;
            cr = new ClientResource(URI);

            ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
            ChallengeResponse authentication = new ChallengeResponse(scheme,params[2] ,params[3]);
            cr.setChallengeResponse(authentication);

            try {

                jsonResponse = cr.post(gson.toJson(n, Note.class)).getText();
                if (cr.getStatus().getCode() == ErrorCodes.INVALID_KEY_CODE)
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
                String error2 = "Error: " + cr.getStatus().getCode() + " - " + e2.getMessage();
                jsonResponse = "Errore nell'inserimento del titolo:\n nota già esistente";
                Log.e(TAG, error2);
            }

            return jsonResponse;

        }

        @Override
        protected void onPostExecute(String res) {
            textOUT.setTextColor(Color.BLUE);
            textOUT.setTextSize(3, 10);

            if (res!= null) {

                textOUT.setText(res);

            }


        }
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_post);


             editor=getSharedPreferences(prefName,MODE_PRIVATE);

            //username = (EditText) findViewById(R.id.username);
            //password = (EditText) findViewById(R.id.password);
            textTitle = (EditText) findViewById(R.id.title);
            text = (EditText) findViewById(R.id.text);
            textOUT = (TextView) findViewById(R.id.note);

            //username.setSingleLine();
            //password.setSingleLine();
            textTitle.setSingleLine();
            text.setSingleLine();
            textOUT.setTextColor(Color.BLUE);
            textOUT.setTextSize(3, 10);


            gson = new Gson();


        }

        public void gopost(View v) {


            String username=editor.getString("username","");
            String password=editor.getString("password","");
            Log.e(TAG, username+password);
            //Toast.makeText(getApplicationContext(),username, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),password, Toast.LENGTH_SHORT).show();

            //final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
           // String username= prefs.getString("username", "");
           // String password= prefs.getString("password", "");

            if (textTitle.getText().toString().equalsIgnoreCase("")|| text.getText().toString().equalsIgnoreCase("")) { // Nel campo input deve essere inserita la key
                textOUT.setText("Insert Title and Text");

            }

            else {
                new PostActivity.PostRestTask().execute(textTitle.getText().toString(), text.getText().toString(), username , password);
            }

        }
    }




