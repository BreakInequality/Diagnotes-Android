package com.example.jashan.diagnotes.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jashan.diagnotes.Model.Diagnosis;
import com.example.jashan.diagnotes.Model.DiagnosisAdapter;
import com.example.jashan.diagnotes.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PendingDiagnosisActivity extends AppCompatActivity {

    public static final String TAG = PendingDiagnosisActivity.class.getSimpleName();

    RecyclerView mRecyclerView;
    DiagnosisAdapter mDiagnosisAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_diagnosis);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Pending Diagnoses");

        loadPendingDiagnoses();
    }

    private void loadPendingDiagnoses() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://young-fortress-46749.herokuapp.com/diagnose")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.w(TAG, "Error while fetching pending diagnoses: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "Got response for pending diagnosis: " + response.toString());
                try {
                    JSONArray elements = new JSONArray(response.body().string());
                    ArrayList<Diagnosis> diagnoseList = new ArrayList<>();
                    for (int i = 0; i < elements.length(); i++) {
                        diagnoseList.add(new Diagnosis(elements.getJSONObject(i)));
                    }

                    mDiagnosisAdapter = new DiagnosisAdapter(PendingDiagnosisActivity.this, diagnoseList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView = (RecyclerView) findViewById(R.id.diagnosis_recycler_list);
                            LinearLayoutManager manager = new LinearLayoutManager(PendingDiagnosisActivity.this);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                                    manager.getOrientation());
                            mRecyclerView.setLayoutManager(manager);
                            mRecyclerView.addItemDecoration(dividerItemDecoration);
                            mRecyclerView.setAdapter(mDiagnosisAdapter);
                        }
                    });
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON: " + response.toString());
                }
            }
        });

    }
}
