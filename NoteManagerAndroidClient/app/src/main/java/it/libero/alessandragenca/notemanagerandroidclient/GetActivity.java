package it.libero.alessandragenca.notemanagerandroidclient;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import it.libero.alessandragenca.notemanagerandroidclient.commons.Note;

/**
 * Created by Alessandra on 12/11/2017.
 */

public class GetActivity extends AppCompatActivity {


    private final String TAG = "ALE_DICTIONARY";

    private Gson gson;
    private String baseURI = "http://10.0.2.2:8182/NoteRegApplication/";

    private EditText textTitle;
    private EditText username;
    private EditText password;
    private TextView textOUT;
    private TextView textOUT2;
    private Button buttonforgetactivity;
    private TextView textOUT3;

    public class GetRestTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String title = params[0];

            Note n;

            ClientResource cr;
            Gson gson = new Gson();

            String URI = baseURI + "notes/"+title;
            String jsonResponse = null;
            cr = new ClientResource(URI);

            ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
            ChallengeResponse authentication = new ChallengeResponse(scheme, params[1], params[2]);
            cr.setChallengeResponse(authentication);

            try {
                jsonResponse = cr.get().getText();
                if (cr.getStatus().getCode() == ErrorCodes.INVALID_KEY_CODE)
                    throw gson.fromJson(jsonResponse, InvalidKeyException.class);
                     n = gson.fromJson(jsonResponse, Note.class);
                    jsonResponse=n.toString();

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

               /* if (n != null) {
                    textOUT.setText(n.getTitle());

                    textOUT2.setText(n.getText());
                    textOUT3.setText(n.getDate().toString());
                } else {*/
                    textOUT.setText(res);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        buttonforgetactivity = (Button) findViewById(R.id.buttongetactivity);

        username = (EditText) findViewById(R.id.usernameGetNote);
        password = (EditText) findViewById(R.id.passwordGetNote);
        textTitle = (EditText) findViewById(R.id.titleForGet);
        textOUT = (TextView) findViewById(R.id.noteOutput);

        textOUT.setScroller(new Scroller(getApplicationContext()));
        textOUT.setMaxLines(3);
        textOUT.setHorizontalScrollBarEnabled(true);
        textOUT.setMovementMethod(new ScrollingMovementMethod());
        textOUT.setTextColor(Color.BLUE);
        textOUT.setTextSize(3, 10);
        username.setSingleLine();
        password.setSingleLine();
        textTitle.setSingleLine();
        //textOUT.setSingleLine();

        gson = new Gson();

        buttonforgetactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textTitle.getText().toString().equalsIgnoreCase("")) { // Nel campo input deve essere inserita la key
                    textOUT.setText("Insert Title");
                    //Toast.makeText(getApplicationContext(),username.getText().toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),password.getText().toString(), Toast.LENGTH_SHORT).show();
                }

                else
                    new GetActivity.GetRestTask().execute(textTitle.getText().toString(),
                            username.getText().toString(), password.getText().toString());
            }

        });
    }
}
