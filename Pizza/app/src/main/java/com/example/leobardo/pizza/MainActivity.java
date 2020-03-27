package com.example.leobardo.pizza;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button btnr,btnl;
    EditText edtemail,edtpass;
    ProgressDialog _progressDialog;
    String [] datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtemail = (EditText) findViewById(R.id.edtemail);
        edtpass = (EditText) findViewById(R.id.edtpass);

        btnl = (Button) findViewById(R.id.btnl);
        btnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Verifica();
            }
        });


        btnr = (Button) findViewById(R.id.btnr);
        btnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        RegistroActivity.class);
                startActivity(intent);
            }
        });
        _progressDialog = new ProgressDialog(this);
        _progressDialog.setCancelable(false);
        _progressDialog.setIndeterminate(true);
        _progressDialog.setMessage("Verificando...");
    }

    private void Verifica(){
        String user = edtemail.getText().toString();
        String pass = edtpass.getText().toString();

        if (user.length() > 10 && pass.length() > 3){
            _progressDialog.show();
            Ion.with(this)
                    .load("GET","http://192.168.43.11:8084/Pizza/webresources/pizzahome/in/"+pass+","+user)
                    .progressDialog(_progressDialog)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            _progressDialog.dismiss();
                            if (e != null){
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Error De Conexión", Toast.LENGTH_LONG).show();
                            }else {
                                if (result != null){
                                    System.out.println("Resultado es: "+result);
                                    try{
                                        JSONObject json = new JSONObject(result);
                                        boolean error = json.getBoolean("error");
                                        String mensaje = json.getString("mesj");
                                        Toast.makeText(MainActivity.this,mensaje, Toast.LENGTH_LONG).show();
                                        if (!error){
                                            ir();
                                        }
                                    }catch (JSONException en){
                                        en.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "Campos Inválidos", Toast.LENGTH_LONG).show();
        }
    }

    private void ir() {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
    }
}




