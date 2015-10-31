package co.hackinout.www.inout_decoders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created by meet on 31/10/15.
 */
public class NewPatientActivity extends AppCompatActivity {
    protected JSONObject patientJson = null;
    private ParseObject patientObject;

    private TextView name, gender, yob,pc;
    private EditText weight,height;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.new_patient_toolbar);
        setSupportActionBar(toolbar);

        name = (TextView) findViewById(R.id.name);
        gender = (TextView) findViewById(R.id.gender);
        yob = (TextView) findViewById(R.id.yob);
        pc = (TextView) findViewById(R.id.pc);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        AppCompatButton mAddDetails = (AppCompatButton) findViewById(R.id.btn_change_details);
        Intent intent = getIntent();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Patients");
        query.fromLocalDatastore();
        query.whereEqualTo("UID",intent.getDataString());
        query.getFirstInBackground( new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Log.d("name",object.getString("Name"));
                } else {
                    Log.d("Error","errrr");
                }
            }
        });



        mAddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientObject.put("Weight", Integer.parseInt(weight.getText().toString()));
                patientObject.put("Height", Integer.parseInt(height.getText().toString()));
                patientObject.saveInBackground();
            }
        });


    }










}

