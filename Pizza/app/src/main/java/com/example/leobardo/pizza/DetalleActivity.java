package com.example.leobardo.pizza;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

public class DetalleActivity extends AppCompatActivity {
    ListView Lista;
    Button btnb;
    EditText edtxtid;
    String[] datos;
    ProgressDialog _progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Lista = (ListView) findViewById(R.id.listd);
        Lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText
                        (DetalleActivity.this, "La Casa De La Pizza", Toast.LENGTH_LONG).show();
            }
        });
        edtxtid = (EditText) findViewById(R.id.edtxtid);

        _progressDialog = new ProgressDialog(this);
        _progressDialog.setCancelable(false);
        _progressDialog.setIndeterminate(true);
        _progressDialog.setMessage("Cargando...");


        btnb = (Button) findViewById(R.id.btnb);
        btnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Consulta();
            }
        });
    }
    private void Consulta(){
        String id = edtxtid.getText().toString();

        _progressDialog.show();
        Ion.with(this)
                .load("GET","http://192.168.43.11" +
                        ":8084/Pizza/webresources/pizzahome/detalles/"+id)
                .progressDialog(_progressDialog)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {

                    @Override
                    public void onCompleted(Exception e,JsonArray result) {
                        _progressDialog.dismiss();
                        if (e != null){
                            e.printStackTrace();
                            Toast.makeText(DetalleActivity.this, "Error De Conexión", Toast.LENGTH_LONG).show();
                        }else {
                            if (result != null){
                                System.out.println("Resultado es: "+result);
                                try{
                                    datos = new String[result.size()];
                                    for (int i=0; i<result.size(); i++){
                                        JSONObject json = new JSONObject(result.get(i).toString());
                                        String nombre = json.getString("nombre");
                                        String precio = json.getString("precio");
                                        String descripcion = json.getString("descripcion");
                                        String restricciones = json.getString("restricciones");
                                        datos[i] = "\nNombre: "+nombre+"\nPrecio: "+precio+"\nDescripción: "+descripcion+"\nRestricciones: "+restricciones+"\n";
                                    }
                                    Llena();
                                }catch (JSONException en){
                                    en.printStackTrace();
                                }
                            }
                        }
                    }
                });
    }

    private void Llena(){
        ArrayAdapter adaptador =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, datos);
        Lista.setAdapter(adaptador);
    }
}
