package com.example.jashan.diagnotes.Model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jashan.diagnotes.Controller.DiagnoseActivity;
import com.example.jashan.diagnotes.R;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

/**
 * Created by Jashan Shewakramani
 * Description: Adapter to display diagnosis data
 */

public class DiagnosisAdapter extends RecyclerView.Adapter<DiagnosisAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Diagnosis> mDiagnoses;

    public DiagnosisAdapter(Context context, ArrayList<Diagnosis> diagnoses) {
        mContext = context;
        mDiagnoses = diagnoses;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.diagnosis_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Diagnosis item = mDiagnoses.get(position);
        holder.diagnosisTitleView.setText(WordUtils.capitalize(item.getDiagnosisTitle()));
        holder.diagnosisProbabilityView.setText(String.format("Probability: %s%%",
                Math.round(item.getProbability() * 100000) / 1000));
        holder.patientTelephoneView.setText(item.getContactNumber());

        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DiagnoseActivity.class);
                intent.putExtra("diagnosis", holder.diagnosisTitleView.getText());
                intent.putExtra("prob", holder.diagnosisProbabilityView.getText());
                intent.putExtra("tel", holder.patientTelephoneView.getText());
                intent.putExtra("json", mDiagnoses.get(position).getJsonData());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiagnoses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView diagnosisTitleView;
        TextView patientTelephoneView;
        TextView diagnosisProbabilityView;
        View parentView;

        ViewHolder(View itemView) {
            super(itemView);
            parentView = itemView;
            diagnosisTitleView = (TextView) itemView.findViewById(R.id.diagnosis_title);
            patientTelephoneView = (TextView) itemView.findViewById(R.id.diagnosis_phone);
            diagnosisProbabilityView = (TextView) itemView.findViewById(R.id.diagnosis_probability);
        }
    }
}
