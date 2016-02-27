package com.ifpb22_02_16.myapplication.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.ifpb22_02_16.myapplication.interfaceApp.NomesInterface;
import com.ifpb22_02_16.myapplication.requestResponse.HttpService;
import com.ifpb22_02_16.myapplication.requestResponse.Response;
import com.ifpb22_02_16.myapplication.views.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Arthur on 27/02/2016.
 */
public class BuscaAsyncTask extends AsyncTask<JSONObject, Void, Response> {

    public static final String TAG = "BuscaAsyncTask";

    NomesInterface nome;

    public BuscaAsyncTask(MainActivity mainActivity) {
        this.nome = mainActivity;
    }

    @Override
    protected Response doInBackground(JSONObject... vetor) {
        Response response = null;
        JSONObject json = vetor[0];

        try {

            new HttpService();
            response = HttpService.sendJSONPostResquest(json, "get-byname");

        } catch (IOException e) {

            Log.e("EditTextListener", e.getMessage());

        }

        return response;

    }

    @Override
    protected void onPostExecute(Response reponse) {
        try {
            JSONArray jsonArray = new JSONArray(reponse.getContentValue());
            List<String> nomes = new ArrayList<>();
            for (int i = 0;i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("fullName");
                nomes.add(name);
            }

            nome.BuscaNome(nomes);

        } catch (JSONException e) {

        }
    }

}
