package co.hackinout.www.inout_decoders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by meet on 1/11/15.
 */
public class PreviousCasesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ParseObject> mDataset ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_cases);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        Intent intent = getIntent();
        try {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Patients");
        //query.fromLocalDatastore();
        query.whereEqualTo("UID", intent.getDataString());
        ParseObject patient = query.getFirst();
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("MedCase");

        query2.whereEqualTo("Patient", patient);

            mDataset = query2.find();

            mAdapter = new MyAdapter(mDataset);
            mRecyclerView.setAdapter(mAdapter);

        }catch (ParseException e){
            e.printStackTrace();
        }

    }
}
