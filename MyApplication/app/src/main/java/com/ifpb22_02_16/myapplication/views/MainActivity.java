package com.ifpb22_02_16.myapplication.views;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.ifpb22_02_16.myapplication.asyncTask.BuscaAsyncTask;
import com.ifpb22_02_16.myapplication.R;
import com.ifpb22_02_16.myapplication.interfaceApp.NomesInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements TextWatcher, NomesInterface {
    @Bind(R.id.edt_busca)
    EditText mEdt_busca;

    @Bind(R.id.lv_nomes)
    ListView mLv_nomes;

    private static int TAMANHO_MINIMO_TEXTO = 3;

    List<String> nomes;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        nomes = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes);

        mLv_nomes.setAdapter(adapter);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        Log.i("beforeTextChanged", " Olha aqui" + charSequence);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

        Log.i("onTextChanged", " Olha aqui" + charSequence);
        String nome = charSequence.toString();

        try {

            if (nome.length() >= TAMANHO_MINIMO_TEXTO) {
                JSONObject json = new JSONObject();
                json.put("fullName", nome);

                BuscaAsyncTask buscarNomeAsyncTask = new BuscaAsyncTask(this);
                buscarNomeAsyncTask.execute(json);

                nomes.add(nome);
                adapter.notifyDataSetChanged();

            }

        } catch (JSONException e) {

            Log.e("EditTextListener", e.getMessage());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

        Log.i("afterTextChanged", " Olha aqui" + s);
    }

    @Override
    public void BuscaNome(List<String> nome) {
        nomes.clear();
        nomes.addAll(nome);
        adapter.notifyDataSetChanged();
    }
}
