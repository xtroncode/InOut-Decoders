package co.hackinout.www.inout_decoders;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by meet on 31/10/15.
 */
public class NewPatientActivity extends AppCompatActivity {
    protected JSONObject patientJson = null;
    private ParseObject patientObject;
    private ParseObject patientHistory;
    private ParseObject patientRestrictions;
    private ParseObject patientGeneral;
    private CheckBox bloodPressure,asthma,cardiac,lungDiseases,diabetes,liver,kidney,kneeProblem,spinalProblems,smoking,alcohol;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.new_patient_toolbar);
        setSupportActionBar(toolbar);
        AppCompatButton mAddDetails = (AppCompatButton) findViewById(R.id.new_patient_history);
        Intent intent = getIntent();
        patientHistory = new ParseObject("History");
        patientGeneral = new ParseObject("General");
        patientRestrictions = new ParseObject("Restrictions");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Patients");
        //query.fromLocalDatastore();
        query.whereEqualTo("UID", intent.getDataString());

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Log.d("name", object.getString("Name"));
                    patientObject = object;

                    patientHistory = patientObject.getParseObject("History");
                    patientRestrictions = patientObject.getParseObject("Restrictions");
                    patientGeneral = patientObject.getParseObject("General");

                    setExistingValues();
                } else {
                    Log.d("Error", "errrr");
                }
            }
        });




        mAddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                patientObject.put("History",patientHistory);
                patientObject.put("Restrictions",patientRestrictions);
                patientObject.put("General",patientGeneral);
                patientObject.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            showDeatilsChangeStatus(true);
                        } else {
                            showDeatilsChangeStatus(false);
                        }
                    }
                });
                            }
        });

    }
    private void showDeatilsChangeStatus(boolean success){
        if(success){
            Toast.makeText(this, "History change successfull", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"History change failed",Toast.LENGTH_SHORT).show();
        }
    }

    private void setExistingValues(){
        if (patientHistory!=null && patientGeneral!=null && patientRestrictions!=null) {
            bloodPressure = (CheckBox) findViewById(R.id.blood_pressure);
            bloodPressure.setChecked(patientHistory.getBoolean("High_Blood_Pressure"));
            asthma = (CheckBox) findViewById(R.id.asthma);
            asthma.setChecked(patientHistory.getBoolean("Asthma"));
            cardiac = (CheckBox) findViewById(R.id.cardiac);
            cardiac.setChecked(patientHistory.getBoolean("Cardiac"));
            lungDiseases = (CheckBox) findViewById(R.id.lung_disease);
            lungDiseases.setChecked(patientHistory.getBoolean("Lung_Disease"));

            diabetes = (CheckBox) findViewById(R.id.diabetes);
            diabetes.setChecked(patientRestrictions.getBoolean("Diabetes"));
            liver = (CheckBox) findViewById(R.id.liver);
            liver.setChecked(patientRestrictions.getBoolean("Liver"));
            kidney = (CheckBox) findViewById(R.id.kidney);
            kidney.setChecked(patientRestrictions.getBoolean("Kidney"));
            kneeProblem = (CheckBox) findViewById(R.id.knee);
            kneeProblem.setChecked(patientRestrictions.getBoolean("Knee_Problems"));
            spinalProblems = (CheckBox) findViewById(R.id.spinal);
            spinalProblems.setChecked(patientRestrictions.getBoolean("Spinal_Problems"));

            smoking = (CheckBox) findViewById(R.id.smoking);
            smoking.setChecked(patientGeneral.getBoolean("Smoking"));
            alcohol = (CheckBox) findViewById(R.id.alcohol);
            alcohol.setChecked(patientGeneral.getBoolean("Alcohol"));
        }
        else{
            patientHistory = new ParseObject("History");
            patientGeneral = new ParseObject("General");
            patientRestrictions = new ParseObject("Restrictions");
        }

    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.blood_pressure:

                    patientHistory.put("High_Blood_Pressure",checked);

                // I'm lactose intolerant
                break;
            case R.id.asthma:

                    patientHistory.put("Asthma",checked);

                break;
            case R.id.cardiac:

                    patientHistory.put("Cardiac",checked);

                break;
            case R.id.lung_disease:

                    patientHistory.put("Lung_Disease",checked);

                break;
            case R.id.diabetes:

                    patientRestrictions.put("Diabetes",checked);

                break;
            case R.id.liver:

                    patientRestrictions.put("Liver",checked);

                break;
            case R.id.kidney:

                    patientRestrictions.put("Kidney",checked);

                break;
            case R.id.knee:

                    patientRestrictions.put("Knee_Problems",checked);

                break;
            case R.id.spinal:

                    patientRestrictions.put("Spinal_Problems",checked);

                break;
            case R.id.smoking:

                    patientGeneral.put("Smoking",checked);

                break;
            case R.id.alcohol:

                    patientGeneral.put("Alcohol",checked);

                break;
        }
    }










}

