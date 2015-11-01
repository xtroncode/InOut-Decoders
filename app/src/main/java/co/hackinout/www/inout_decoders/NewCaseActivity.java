package co.hackinout.www.inout_decoders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created by meet on 31/10/15.
 */
public class NewCaseActivity extends AppCompatActivity {
    protected JSONObject patientJson = null;
    private ParseObject newcase,patientObject;
    //private ParseObject patientHistory;
    //private ParseObject patientRestrictions;
    //private ParseObject patientGeneral;
    //private CheckBox bloodPressure,asthma,cardiac,lungDiseases,diabetes,liver,kidney,kneeProblem,spinalProblems,smoking,alcohol;
    private EditText symptoms,predictedDisease,medicines,followUp;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case);
        Toolbar toolbar = (Toolbar) findViewById(R.id.new_case_toolbar);
        setSupportActionBar(toolbar);
        AppCompatButton mCreateCase = (AppCompatButton) findViewById(R.id.new_patient_case);
        Intent intent = getIntent();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Patients");
        query.fromLocalDatastore();
        query.whereEqualTo("UID", intent.getDataString());

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Log.d("name", object.getString("Name"));
                    patientObject = object;


                } else {
                    Log.d("Error", "errrr");
                }
            }
        });

        symptoms = (EditText) findViewById(R.id.symptoms);
        predictedDisease = (EditText) findViewById(R.id.predicted_disease);
        medicines = (EditText) findViewById(R.id.advised_medicines);


        mCreateCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newcase = new ParseObject("MedCase");
                newcase.put("Symptoms",symptoms.getText().toString());
                newcase.put("PredictedDisease",predictedDisease.getText().toString());
                newcase.put("Medicines",medicines.getText().toString());
                newcase.put("Patient",patientObject);
                newcase.saveInBackground(
                        new SaveCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    showDeatilsChangeStatus(true);
                                } else {
                                    showDeatilsChangeStatus(false);
                                }
                            }
                        }
                );
            }
        });




    }

    private void showDeatilsChangeStatus(boolean success){
        if(success){
            Toast.makeText(this, "New Case Created.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Case creation failed.",Toast.LENGTH_SHORT).show();
        }
    }










}

