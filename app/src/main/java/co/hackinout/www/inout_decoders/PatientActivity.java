package co.hackinout.www.inout_decoders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.List;

/**
 * Created by meet on 31/10/15.
 */
public class PatientActivity extends AppCompatActivity {
        protected JSONObject patientJson = null;
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            Intent intent = getIntent();
           // Log.d("Intent string",intent.getDataString());

            ProcessPatientData(intent.getDataString());

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
                                object = new ParseObject("Patients");
                                object.put("UID", patientJson.getString("uid"));
                                object.put("name", patientJson.getString("name"));
                                object.put("gender", patientJson.getString("gender"));
                                object.put("yob", patientJson.getString("yob"));

                                object.put("loc", patientJson.getString("loc"));
                                object.put("vtc", patientJson.getString("vtc"));
                                object.put("po", patientJson.getString("po"));
                                object.put("dist", patientJson.getString("dist"));
                                object.put("state", patientJson.getString("state"));
                                object.put("pc", patientJson.getString("pc"));
                                try {
                                    object.put("house", patientJson.getString("house"));
                                }catch (JSONException error){
                                    object.put("house", "");
                                }
                                try {
                                    object.put("street", patientJson.getString("street"));
                                }catch (JSONException error){
                                    object.put("street", "");
                                }
                                try {
                                    object.put("lm", patientJson.getString("lm"));
                                }catch (JSONException error){
                                    object.put("lm","");
                                }
                                


                                object.saveInBackground();
                            }catch (JSONException err){
                                err.printStackTrace();
                            }

                        } else {
                            Log.d("UID-200", "Retrieved the object.");
                        }
                    }
                });
            }catch(JSONException e){
                e.printStackTrace();
            }

        }



}
