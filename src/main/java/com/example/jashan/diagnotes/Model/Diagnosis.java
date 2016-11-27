package com.example.jashan.diagnotes.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jashan Shewakramani
 * Description: Base class for modelling diagnosis objects
 */

public class Diagnosis {
    private String mDiagnosisTitle;
    private double mProbability;
    private String mContactNumber;
    private String mJsonData;

    public static final String TAG = Diagnosis.class.getSimpleName();

    public Diagnosis(JSONObject jsonObject) {
        try {
            mDiagnosisTitle = jsonObject.getJSONObject("diagnosis").getString("disease");
            mContactNumber = jsonObject.getString("phone");
            mProbability = jsonObject.getJSONObject("diagnosis").getDouble("probability");
            mJsonData = jsonObject.toString();
        } catch (JSONException e) {
            Log.d(TAG, "Error while creating Diagnosis from JSON: " + jsonObject.toString());
        }

    }

    public String getDiagnosisTitle() {
        return mDiagnosisTitle;
    }

    public void setDiagnosisTitle(String diagnosisTitle) {
        mDiagnosisTitle = diagnosisTitle;
    }

    public double getProbability() {
        return mProbability;
    }

    public void setProbability(double probability) {
        mProbability = probability;
    }

    public String getContactNumber() {
        return mContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        mContactNumber = contactNumber;
    }

    public String getJsonData() {
        return mJsonData;
    }

    public void setJsonData(String jsonData) {
        mJsonData = jsonData;
    }
}
