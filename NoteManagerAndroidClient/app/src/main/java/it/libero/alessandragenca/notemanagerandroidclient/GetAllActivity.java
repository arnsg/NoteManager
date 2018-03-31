package it.libero.alessandragenca.notemanagerandroidclient;

import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.gson.Gson;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.util.Date;
import java.util.StringTokenizer;

import it.libero.alessandragenca.notemanagerandroidclient.commons.ErrorCodes;
import it.libero.alessandragenca.notemanagerandroidclient.commons.InvalidKeyException;
import it.libero.alessandragenca.notemanagerandroidclient.commons.Note;

public class GetAllActivity extends AppCompatActivity {

    private static final String TAG = "ALE_DICTIONARY";

  //  private Gson gson;
    private String baseURI = "http://10.0.2.2:8182/NoteRegApplication/";



    private TextView textOUT;
    SharedPreferences editor;
    public final static String prefName="Preference";

    public class GetAllRestTask extends AsyncTask<String, Void, String> {



        protected String doInBackground(String... params) {



            ClientResource cr;
            Gson gson = new Gson();

            String URI = baseURI + "notes";
            String jsonResponse = null;
            cr = new ClientResource(URI);

            ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
            ChallengeResponse authentication = new ChallengeResponse(scheme, params[0], params[1]);
            cr.setChallengeResponse(authentication);

            try {

                jsonResponse = cr.get().getText();
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
                Log.e(TAG, error2);
            }

            return jsonResponse;

        }

        @Override
        protected void onPostExecute(String res) {
            textOUT.setTextColor(Color.BLUE);
            textOUT.setTextSize(3, 10);

            if (res!= null) {

                textOUT.setText("Note Titles:");
                StringTokenizer st = new StringTokenizer(res,",");
                while(st.hasMoreElements()){
                    textOUT.setText(textOUT.getText()+"\n"+st.nextToken());
                }


            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all);
        editor=getSharedPreferences(prefName,MODE_PRIVATE);

        //username = (EditText) findViewById(R.id.usernameGetAllNotes);
        //password = (EditText) findViewById(R.id.passwordGetAllNotes);

        textOUT = (TextView) findViewById(R.id.noteOutputGetAll);
        textOUT.setScroller(new Scroller(getApplicationContext()));
        textOUT.setMaxLines(15);
        textOUT.setBackgroundColor(Color.CYAN);
        textOUT.setHorizontalScrollBarEnabled(true);
        textOUT.setMovementMethod(new ScrollingMovementMethod());
        textOUT.setTextColor(Color.BLUE);
        textOUT.setTextSize(3, 10);
        //username.setSingleLine();
        //password.setSingleLine();

    //    gson = new Gson();



    }

    public void goGetAll(View v) {
        final String username=editor.getString("username","");
        final String password=editor.getString("password","");

        //Toast.makeText(getApplicationContext(),username.getText().toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),password.getText().toString(), Toast.LENGTH_SHORT).show();
        new GetAllActivity.GetAllRestTask().execute(username,password);


    }
}
