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

import it.libero.alessandragenca.notemanagerandroidclient.commons.ErrorCodes;
import it.libero.alessandragenca.notemanagerandroidclient.commons.InvalidKeyException;

public class GetSizeActivity extends AppCompatActivity {


    private static final String TAG = "ALE_DICTIONARY";

  //  private Gson gson;
    private String baseURI = "http://10.0.2.2:8182/NoteRegApplication/size";

    SharedPreferences editor;
    public final static String prefName="Preference";

 //   private EditText username;
  //  private EditText password;
    private TextView textOUT;

    public class GetSizeRestTask extends AsyncTask<String, Void, String> {



        protected String doInBackground(String... params) {



            ClientResource cr;
            Gson gson = new Gson();

            String URI = baseURI;
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

                textOUT.setText("Number of memorized notes:" +res);

            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_size);
        editor=getSharedPreferences(prefName,MODE_PRIVATE);
       // username = (EditText) findViewById(R.id.usernameGetNoteSize);
        //password = (EditText) findViewById(R.id.passwordGetNoteSize);
        textOUT = (TextView) findViewById(R.id.noteOutputSize);
        //username.setSingleLine();
        //password.setSingleLine();
        //textOUT.setSingleLine();
        textOUT.setScroller(new Scroller(getApplicationContext()));
        textOUT.setMaxLines(2);

        textOUT.setHorizontalScrollBarEnabled(true);
        textOUT.setMovementMethod(new ScrollingMovementMethod());


   //     gson = new Gson();



    }

    public void goget(View v) {
        final String username=editor.getString("username","");
        final String password=editor.getString("password","");
        //Toast.makeText(getApplicationContext(),username.getText().toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),password.getText().toString(), Toast.LENGTH_SHORT).show();
        new GetSizeActivity.GetSizeRestTask().execute(username, password);


    }
}
