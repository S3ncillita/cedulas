package com.example.cedulas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private ListView lv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
            lv1 =(ListView) findViewById(R.id.ListaView);
            invocarServicio();
    }
    private void invocarServicio (){

        String URl= "http://181.94.245.250/datos/read.php";
        final ProgressDialog loading = ProgressDialog.show(this,"Por favor espere","Actualizando datos",false,false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                    showLisView(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);


    }

    private void showLisView(JSONObject obj) {
        try{
        List <String> contes = new ArrayList < String > ( ) ;
        JSONArray lista = obj.optJSONArray ("data") ;
        for ( int i = 0 ; i < lista . length () ; i ++ ) {
            JSONObject json_data = lista.getJSONObject(i);
            String conte = json_data.getString("cedula");
                    contes.add(conte);
        }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contes);
            lv1.setAdapter(adapter);

        }catch ( Exception ex ){
            Toast.makeText(this, " Error garga lista : " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
        }
    }

}