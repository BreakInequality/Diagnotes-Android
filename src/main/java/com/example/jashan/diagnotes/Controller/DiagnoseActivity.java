package com.example.jashan.diagnotes.Controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jashan.diagnotes.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DiagnoseActivity extends AppCompatActivity {

    public static final String TAG = DiagnoseActivity.class.getSimpleName();

    TextView mNameView;
    TextView mPhoneView;
    TextView mMetaView;
    TextView mSymptomView;
    TextView mDescriptionView;
    TextView mProbabilityView;
    Button mCallButton;
    Button mSmsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);
        Intent intent = getIntent();
        JSONObject patientInfo = null;
        try {
            patientInfo = new JSONObject(intent.getStringExtra("json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (patientInfo != null) {
            mNameView = (TextView) findViewById(R.id.patient_name_view);
            mPhoneView = (TextView) findViewById(R.id.phone_number_view);
            mMetaView = (TextView) findViewById(R.id.patient_meta_view);
            mSymptomView = (TextView) findViewById(R.id.symptom_name_view);
            mDescriptionView = (TextView) findViewById(R.id.description_view);
            mProbabilityView = (TextView) findViewById(R.id.probability_view);
            mCallButton = (Button) findViewById(R.id.call_button);
            mSmsButton = (Button) findViewById(R.id.send_sms_button);

            try {
                mNameView.setText(patientInfo.getString("name"));
                mPhoneView.setText(String.format("Tel: %s", patientInfo.getString("phone")));
                mMetaView.setText(patientInfo.getInt("age") + ", " + patientInfo.getString("sex"));
                mSymptomView.setText(patientInfo.getJSONObject("diagnosis").getString("disease"));
                mProbabilityView.setText(String.format("%s%%",
                        patientInfo.getJSONObject("diagnosis").getDouble("probability") * 100));

                mDescriptionView.setText(String.format("For more information, see: \n%s", patientInfo.getString("url")));

                final JSONObject finalPatientInfo = patientInfo;
                mCallButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i = new Intent(Intent.ACTION_CALL,
                                    Uri.parse("tel:" + finalPatientInfo.getString("phone")));
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            if (ContextCompat.checkSelfPermission
                                    (DiagnoseActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                                startActivity(i);
                            else
                                Log.d(TAG, "No permissions for calling");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                mSmsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentt = new Intent(Intent.ACTION_VIEW);
                        intentt.setData(Uri.parse("sms:"));
                        intentt.setType("vnd.android-dir/mms-sms");
                        intentt.putExtra(Intent.EXTRA_TEXT, "");
                        try {
                            intentt.putExtra("address", finalPatientInfo.getString("phone"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivityForResult(Intent.createChooser(intentt, ""), 0);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
