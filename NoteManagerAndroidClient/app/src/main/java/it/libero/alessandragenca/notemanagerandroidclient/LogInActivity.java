package it.libero.alessandragenca.notemanagerandroidclient;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.io.IOException;

import it.libero.alessandragenca.notemanagerandroidclient.R;
import it.libero.alessandragenca.notemanagerandroidclient.commons.ErrorCodes;

public class LogInActivity extends AppCompatActivity {

    private EditText mUsernameView;
    private EditText mPasswordView;
    public final static String prefName="Preference";
    private String TAG = "Note_APPLICATION";
    private SharedPreferences.Editor editor;
    private Snackbar sn;
    private Button mLoginInButton;
    private Button mSignInButton;
    private final int MY_PERMISSIONS_REQUEST=123;
    private SharedPreferences preferences;
    private AsyncTask logintask;
    private static int MAX_TARDINESS=5000;
    private static Activity activity;
    private String baseURI = "http://10.0.2.2:8182/NoteRegApplication/";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        setContentView(R.layout.activity_log_in);
        preferences=getSharedPreferences(prefName,MODE_PRIVATE);
        editor = getSharedPreferences(prefName, MODE_PRIVATE).edit();

        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mLoginInButton = (Button) findViewById(R.id.login_button);

        preferences = getSharedPreferences(prefName, MODE_PRIVATE);




                mLoginInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mUsernameView.getText().toString().equals("") || mPasswordView.getText().toString().equals("")) {
                            View parent = (View) findViewById(R.id.activity_login_page);
                            sn.make(parent, "Insert Data", Snackbar.LENGTH_SHORT).show();
                        } else
                            new LoginRestTask().execute(String.valueOf(mUsernameView.getText()), String.valueOf(mPasswordView.getText()));
                    }
                });


                mSignInButton = (Button) findViewById(R.id.login_signin);
                mSignInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myIntent = new Intent(LogInActivity.this, RegActivity.class);
                        startActivity(myIntent);


                    }
                });

            }






    public LoginRestTask createLoginRestTask() {
        return new LoginRestTask();
    }


    public class LoginRestTask extends AsyncTask<String, Void, Integer> {

        private Gson gson = new Gson();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            logintask = this;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    logintask.cancel(true);
                    Log.i("TASK", "Cancellato");
                    if (!(logintask.getStatus() == Status.FINISHED)) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(LogInActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        });
                    }
                }
            }, MAX_TARDINESS);
        }


        protected Integer doInBackground(String... params) {


            SharedPreferences editor = getSharedPreferences(prefName, MODE_PRIVATE);
            String URI = "http://" + editor.getString("IP", "10.0.2.2") + ":" + editor.getString("port", "8182") + "/NoteRegApplication/" + "users";
            gson = new Gson();
            ClientResource cr = new ClientResource(URI);
            String gsonResponse = null;
            Boolean response;
            Log.i("Connection", "Connection establishing");

            try {
                gsonResponse = cr.put(gson.toJson(params[0] + ";" + params[1], String.class)).getText();
                if (cr.getStatus().getCode() == 200) {
                    response = gson.fromJson(gsonResponse, Boolean.class);
                    if (response)
                        return 0;
                    return 2;

                } else if (cr.getStatus().getCode() == ErrorCodes.INVALID_KEY_CODE) {
                    return 1;


                } else {
                    return 2;

                }


            } catch (ResourceException | IOException e1) {
                String text = "Error: " + cr.getStatus().getCode() + " - " + cr.getStatus().getDescription() + " - " + cr.getStatus().getReasonPhrase();
                Log.e(TAG, text);
                return 3;
            }
        }


        protected void onPostExecute(Integer c) {

            if (c == 0) {
                Intent myIntent = new Intent(LogInActivity.this, MainActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                editor.putString("username", String.valueOf(mUsernameView.getText()));
                editor.putString("password", String.valueOf(mPasswordView.getText()));

                editor.commit();

                startActivity(myIntent);


            } else if (c == 1) {
                Toast.makeText(getApplicationContext(), "Unregistered User", Toast.LENGTH_SHORT).show();


            } else if (c == 2) {
                Toast.makeText(getApplicationContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),password.getText().toString(), Toast.LENGTH_SHORT).show();

            }


        }
    }

}
