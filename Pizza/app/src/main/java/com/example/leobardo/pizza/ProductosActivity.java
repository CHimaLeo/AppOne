package com.example.leobardo.pizza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.app.ProgressDialog;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;




public class ProductosActivity extends AppCompatActivity {
    ListView Lista;
    String[] datos;
    ProgressDialog _progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        Lista = (ListView) findViewById(R.id.listp);
        Lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText
                        (ProductosActivity.this, "La Casa De La Pizza", Toast.LENGTH_LONG).show();
        }
        });
        _progressDialog = new ProgressDialog(this);
        _progressDialog.setCancelable(false);
        _progressDialog.setIndeterminate(true);
        _progressDialog.setMessage("Cargando...");

        Consulta();
    }

    private void Consulta(){

        _progressDialog.show();
        Ion.with(this)
                .load("GET","http://192.168.43.11:8084/Pizza/webresources/pizzahome/productos/")
                .progressDialog(_progressDialog)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {

                    @Override
                    public void onCompleted(Exception e,JsonArray result) {
                        _progressDialog.dismiss();
                        if (e != null){
                            e.printStackTrace();
                            Toast.makeText(ProductosActivity.this, "Error De Conexión", Toast.LENGTH_LONG).show();
                        }else {
                            if (result != null){
                                System.out.println("Resultado es: "+result);
                                try{
                                    datos = new String[result.size()];
                                    for (int i=0; i<result.size(); i++){
                                        JSONObject json = new JSONObject(result.get(i).toString());
                                        String iden = json.getString("idproducto");
                                        String nombre = json.getString("nombre");
                                        String precio = json.getString("precio");
                                        datos[i] = "\nIdentificador: "+iden+"\nNombre: "+nombre+"\t\tPrecio: "+precio+"\n";
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
