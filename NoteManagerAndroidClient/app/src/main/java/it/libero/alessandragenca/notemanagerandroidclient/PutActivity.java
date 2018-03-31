package it.libero.alessandragenca.notemanagerandroidclient;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
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

public class PutActivity extends AppCompatActivity {

    private static final String TAG = "ALE_DICTIONARY";

 //   private Gson gson;
    private String baseURI = "http://10.0.2.2:8182/NoteRegApplication/";

    private EditText text;
    private EditText textTitle;
//    private EditText username;
 //   private EditText password;
    private TextView textOUT;
 //   private Button button;

    SharedPreferences editor;
    public final static String prefName="Preference";

    public class PutRestTask extends AsyncTask<String, Void, String> {

  //      private Note n;

        protected String doInBackground(String... params) {
            String title = params[0];
            String text = params[1];

            ClientResource cr;
            Gson gson = new Gson();

            String URI = baseURI + "notes/"+title;
            String jsonResponse = null;
            cr = new ClientResource(URI);
            Note n=null;
            ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
            ChallengeResponse authentication = new ChallengeResponse(scheme, params[2], params[3]);
            cr.setChallengeResponse(authentication);

            try {

                jsonResponse = cr.get().getText();
                if (cr.getStatus().getCode() == ErrorCodes.INVALID_KEY_CODE)
                    throw gson.fromJson(jsonResponse, InvalidKeyException.class);
                else n=gson.fromJson(jsonResponse, Note.class);

            } catch (ResourceException | IOException e1) {
                if (org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED.equals(cr.getStatus())) {
                    // Unauthorized access
                    jsonResponse= "Access unauthorized by the server, check your credentials";
                    Log.e(TAG, jsonResponse);
                    return jsonResponse;
                } else {

                    jsonResponse = "Error: " + cr.getStatus().getCode() + " - " + cr.getStatus().getDescription() +
                            " - " + cr.getStatus().getReasonPhrase();
                    Log.e(TAG, jsonResponse);
                    return jsonResponse;
                }

            } catch (InvalidKeyException e2) {
                String error2 = "Error: " + cr.getStatus().getCode() + " - " + e2.getMessage();
                Log.e(TAG, error2);
                return jsonResponse;
            }

            String URI2 = baseURI + "notes";
            //modifico il testo della nota
            n.setText(text);
            cr = new ClientResource(URI2);
            cr.setChallengeResponse(authentication);

            try {
                jsonResponse = cr.put(gson.toJson(n, Note.class)).getText();
                if (cr.getStatus().getCode() == ErrorCodes.INVALID_KEY_CODE)
                    throw gson.fromJson(jsonResponse, InvalidKeyException.class);
                System.out.println("Response :" + gson.fromJson(jsonResponse, String.class));

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
        setContentView(R.layout.activity_put);

        //username = (EditText) findViewById(R.id.username);
        //password = (EditText) findViewById(R.id.password);
        textTitle = (EditText) findViewById(R.id.title);
        text = (EditText) findViewById(R.id.text);
  //      button = (Button) findViewById(R.id.buttonModify);
        textOUT = (TextView) findViewById(R.id.note);
   //     gson = new Gson();
        textOUT.setTextColor(Color.BLUE);
        textOUT.setTextSize(3, 10);
        //username.setSingleLine();
        //password.setSingleLine();
        textTitle.setSingleLine();
        text.setSingleLine();
        textOUT.setScroller(new Scroller(getApplicationContext()));
        textOUT.setMaxLines(2);

        textOUT.setHorizontalScrollBarEnabled(true);
        textOUT.setMovementMethod(new ScrollingMovementMethod());
        //textOUT.setSingleLine();


        editor=getSharedPreferences(prefName,MODE_PRIVATE);


    }

    public void goput(View v) {


        final String username=editor.getString("username","");
        final String password=editor.getString("password","");

        if (textTitle.getText().toString().equalsIgnoreCase("")) { // Nel campo input deve essere inserita la key
            textOUT.setText("Insert Title");
            //Toast.makeText(getApplicationContext(),username.getText().toString(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),password.getText().toString(), Toast.LENGTH_SHORT).show();

        }
        else if (text.getText().toString().equalsIgnoreCase("")){
            textOUT.setText("insert text");

        }

        else {
            new PutActivity.PutRestTask().execute(textTitle.getText().toString(), text.getText().toString(),username, password);
        }

    }
}
