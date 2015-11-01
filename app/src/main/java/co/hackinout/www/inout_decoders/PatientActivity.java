package co.hackinout.www.inout_decoders;



import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.DialogFragment;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.FindCallback;
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
public class PatientActivity extends AppCompatActivity {
        protected JSONObject patientJson = null;
        private ParseObject patientObject;

        private TextView name, gender, yob,pc;
        private EditText weight,height;
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_patient);
           // Toolbar toolbar = (Toolbar) findViewById(R.id.patient_toolbar);
            //setSupportActionBar(toolbar);

            name = (TextView) findViewById(R.id.name);
            gender = (TextView) findViewById(R.id.gender);
            yob = (TextView) findViewById(R.id.yob);
            pc = (TextView) findViewById(R.id.pc);
            weight = (EditText) findViewById(R.id.weight);
            height = (EditText) findViewById(R.id.height);
            AppCompatButton mChangeDetails = (AppCompatButton) findViewById(R.id.btn_change_details);
            AppCompatButton mPatientHistory = (AppCompatButton) findViewById(R.id.view_patient_history);
            AppCompatButton mPreviousCases = (AppCompatButton) findViewById(R.id.view_previous_cases);

            Intent intent = getIntent();
           // Log.d("Intent string",intent.getDataString());

            ProcessPatientData(intent.getDataString());

            mChangeDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    patientObject.put("Weight", Integer.parseInt(weight.getText().toString()));
                    patientObject.put("Height", Integer.parseInt(height.getText().toString()));
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
            mPatientHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PatientActivity.this,NewPatientActivity.class);
                    intent.setData(Uri.parse(patientObject.getString("UID")));
                    startActivity(intent);

                }
            });

            mPreviousCases.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PatientActivity.this,PreviousCasesActivity.class);
                    intent.setData(Uri.parse(patientObject.getString("UID")));
                    startActivity(intent);

                }
            });


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_case);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PatientActivity.this,NewCaseActivity.class);
                    intent.setData(Uri.parse(patientObject.getString("UID")));
                    startActivity(intent);

                }
            });

        }

        private void showDeatilsChangeStatus(boolean success){
            if(success){
                Toast.makeText(this,"Detail change successfull",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"Detail change failed",Toast.LENGTH_SHORT).show();
            }
        }
        private void ProcessPatientData(String ScanData){


            try {
                patientJson = XML.toJSONObject(ScanData).getJSONObject("PrintLetterBarcodeData");

            }
            catch(JSONException e){
                e.printStackTrace();
            }

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Patients");
            try {
                query.whereEqualTo("UID", patientJson.getString("uid"));
                query.include("History");
                query.include("Restrictions");
                query.include("General");

                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (object == null) {
                            Log.d("UID", "The getFirst request failed.");
                            try {
                                patientObject = new ParseObject("Patients");
                                patientObject.put("UID", patientJson.getString("uid"));
                                patientObject.put("Name", patientJson.getString("name"));
                                patientObject.put("gender", patientJson.getString("gender"));
                                patientObject.put("yob", patientJson.getString("yob"));


                                patientObject.put("vtc", patientJson.getString("vtc"));

                                patientObject.put("dist", patientJson.getString("dist"));
                                patientObject.put("state", patientJson.getString("state"));
                                patientObject.put("pc", patientJson.getString("pc"));
                                try {
                                    patientObject.put("house", patientJson.getString("house"));
                                }catch (JSONException error){
                                    patientObject.put("house", "");
                                }
                                try {
                                    patientObject.put("street", patientJson.getString("street"));
                                }catch (JSONException error){
                                    patientObject.put("street", "");
                                }
                                try {
                                    patientObject.put("lm", patientJson.getString("lm"));
                                }catch (JSONException error){
                                    patientObject.put("lm", "");
                                }
                                try {
                                    patientObject.put("loc", patientJson.getString("loc"));
                                }catch (JSONException error){
                                    patientObject.put("loc", "");
                                }
                                try {
                                    patientObject.put("po", patientJson.getString("po"));
                                }catch (JSONException error){
                                    patientObject.put("po", "");
                                }
                                patientObject.pinInBackground();
                                Intent intent = new Intent(PatientActivity.this,NewPatientActivity.class);
                                intent.setData(Uri.parse(patientObject.getString("UID")));
                                startActivity(intent);
                                patientObject.saveInBackground();




                            }catch (JSONException err){
                                err.printStackTrace();
                            }

                        } else {
                            Log.d("UID-200", "Retrieved the object.");
                            patientObject = object;
                            patientObject.pinInBackground();
                        }

                        ShowScannedData(patientObject);
                    }
                });
            }catch(JSONException e){
                e.printStackTrace();
            }



        }

    private void ShowScannedData(ParseObject object){
        Resources res = getResources();

            name.setText( res.getString(R.string.name,patientObject.getString("Name")));
            gender.setText( res.getString(R.string.gender,patientObject.getString("gender")));
            yob.setText( res.getString(R.string.yob,patientObject.getString("yob")));
            pc.setText( res.getString(R.string.pc,patientObject.getString("pc")));

    }






}
