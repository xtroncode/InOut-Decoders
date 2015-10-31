package co.hackinout.www.inout_decoders;



import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.DialogFragment;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.parse.FindCallback;
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
public class PatientActivity extends AppCompatActivity {
        protected JSONObject patientJson = null;
        private ParseObject patientObject;

        private TextView name, gender, yob,pc;
        private EditText weight,height;
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_patient);
            Toolbar toolbar = (Toolbar) findViewById(R.id.patient_toolbar);
            setSupportActionBar(toolbar);

            name = (TextView) findViewById(R.id.name);
            gender = (TextView) findViewById(R.id.gender);
            yob = (TextView) findViewById(R.id.yob);
            pc = (TextView) findViewById(R.id.pc);
            weight = (EditText) findViewById(R.id.weight);
            height = (EditText) findViewById(R.id.height);
            AppCompatButton mChangeDetails = (AppCompatButton) findViewById(R.id.btn_change_details);
            Intent intent = getIntent();
           // Log.d("Intent string",intent.getDataString());

            ProcessPatientData(intent.getDataString());

            mChangeDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    patientObject.put("Weight", Integer.parseInt(weight.getText().toString()));
                    patientObject.put("Height", Integer.parseInt(height.getText().toString()));
                    patientObject.saveInBackground();
                }
            });


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

                                patientObject.put("loc", patientJson.getString("loc"));
                                patientObject.put("vtc", patientJson.getString("vtc"));
                                patientObject.put("po", patientJson.getString("po"));
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

                        }

                        ShowScannedData(patientObject);
                    }
                });
            }catch(JSONException e){
                e.printStackTrace();
            }



        }

    private void ShowScannedData(ParseObject object){

            name.setText(patientObject.getString("name"));
            gender.setText(patientObject.getString("gender"));
            yob.setText(patientObject.getString("yob"));
            pc.setText(patientObject.getString("pc"));

    }






}
