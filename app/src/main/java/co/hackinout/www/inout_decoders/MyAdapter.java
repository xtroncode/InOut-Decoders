package co.hackinout.www.inout_decoders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by meet on 1/11/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        TextView card_symptoms;
        TextView card_predicted_disease;
        TextView card_medicines;
        public ViewHolder(View v) {
            super(v);
            cv = (CardView)v.findViewById(R.id.cv);
            card_symptoms = (TextView) v.findViewById(R.id.card_symptoms);
            card_predicted_disease = (TextView)v.findViewById(R.id.card_predicted_diseases);
            card_medicines = (TextView) v.findViewById(R.id.card_medicines);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    List<ParseObject> cases;

    MyAdapter(List<ParseObject> cases){
        this.cases = cases;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.case_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.card_symptoms.setText(cases.get(position).getString("Symptoms"));
        holder.card_predicted_disease.setText(cases.get(position).getString("PredictedDisease"));
        holder.card_medicines.setText(cases.get(position).getString("Medicines"));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cases.size();
    }
}
