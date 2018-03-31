package it.libero.alessandragenca.notemanagerandroidclient;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import it.libero.alessandragenca.notemanagerandroidclient.commons.ErrorCodes;
import it.libero.alessandragenca.notemanagerandroidclient.commons.InvalidKeyException;
import it.libero.alessandragenca.notemanagerandroidclient.commons.Note;

public class GetByDateActivity extends AppCompatActivity {


    private static final String TAG = "ALE_DICTIONARY";

  //  private Gson gson;
    private String baseURI = "http://10.0.2.2:8182/NoteRegApplication/";
//    private EditText textUsername;
 //   private EditText textPassword;
    private TextView textOUT;
    private Button changeDataButton;
    private static Button getByDateButton;
    private static String date;
    private static int y =0;
    private static int d = 0;
    private static int m=0;

    SharedPreferences editor;
    public final static String prefName="Preference";

    public class PostRestTask extends AsyncTask<String, Void, String> {





        @Override
        protected String doInBackground(String... params) {
            ClientResource cr;
            Gson gson = new Gson();

            String URI = baseURI + "size";
            String jsonResponse = null;
            //Date d = new Date();
            cr = new ClientResource(URI);
            //String dataText=params[0];


            ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
            ChallengeResponse authentication = new ChallengeResponse(scheme, params[0], params[1]);
            cr.setChallengeResponse(authentication);

            GregorianCalendar gc= new GregorianCalendar(y,m,d);
            Date d= gc.getTime();


            try {
                jsonResponse = cr.post(gson.toJson(d,Date.class)).getText();


            } catch (ResourceException | IOException e1) {
                if (org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED.equals(cr.getStatus())) {
                    // Unauthorized access
                    jsonResponse = "Access unauthorized by the server, check your credentials";
                    Log.e(TAG, jsonResponse);
                } else {

                    jsonResponse = "Error: " + cr.getStatus().getCode() + " - " + cr.getStatus().getDescription() +
                            " - " + cr.getStatus().getReasonPhrase();
                    Log.e(TAG, jsonResponse);
                }
            }


            return jsonResponse;
        }



        @Override
        protected void onPostExecute(String res) {
            textOUT.setTextColor(Color.BLUE);
            textOUT.setTextSize(3, 8);

            if (res!=null) {

                textOUT.setText(res);
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_by_date);


        //textUsername = (EditText) findViewById(R.id.usernameGET);
        //textPassword = (EditText) findViewById(R.id.passwordGET);
        textOUT= (TextView) findViewById(R.id.result);
        getByDateButton = (Button) findViewById(R.id.buttongetnotesbydate);
        getByDateButton.setEnabled(false);
        changeDataButton = (Button) findViewById(R.id.dateButton);
        // textOUT.setEnabled(false);
        textOUT.setScroller(new Scroller(getApplicationContext()));
        textOUT.setMaxLines(2);
        textOUT.setHorizontalScrollBarEnabled(true);
        textOUT.setMovementMethod(new ScrollingMovementMethod());
        //textUsername.setSingleLine();
        //textPassword.setSingleLine();
        editor=getSharedPreferences(prefName,MODE_PRIVATE);


      //  gson = new Gson();

        changeDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Create a new DatePickerFragment
                DialogFragment newFragment = new DatePickerFragment();
                // Display DatePickerFragment
                newFragment.show(getFragmentManager(), "DatePicker");


            }

        });


        getByDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username=editor.getString("username","");
                final String password=editor.getString("password","");
                new PostRestTask().execute(username, password);


            }

        });
    }








    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(),this, year, month, day);
        }


        public void onDateSet(DatePicker view, int year, int month, int day) {
            y=year;
            m= month;
            d=day;

            date= year+"/"+month+"/"+day;
            getByDateButton.setEnabled(true);
            //GregorianCalendar gregorianCalendar = new GregorianCalendar(view.getYear(),view.getMonth(), view.getDayOfMonth());

            //date = gregorianCalendar.getTime();
            //Toast.makeText(getApplicationContext(),date.toString(),Toast.LENGTH_SHORT).show();

        }

    }



}
