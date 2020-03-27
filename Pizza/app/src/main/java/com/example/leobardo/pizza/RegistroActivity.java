package com.example.leobardo.pizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;


public class RegistroActivity extends AppCompatActivity {
    Button btnru;
    EditText edtxname,edtxp,edtxm,edtemail,edtpass,edtxc,edtxnum,edtxcp,edtxcty,edtxt;
    ProgressDialog _progressDialog;
    public String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edtxname = (EditText) findViewById(R.id.edtxname);
        edtxp = (EditText) findViewById(R.id.edtxp);
        edtxm = (EditText) findViewById(R.id.edtxm);
        edtemail = (EditText) findViewById(R.id.edtemail);
        edtpass = (EditText) findViewById(R.id.edtpass);
        edtxc = (EditText) findViewById(R.id.edtxc);
        edtxnum = (EditText) findViewById(R.id.edtxnum);
        edtxcp = (EditText) findViewById(R.id.edtxcp);
        edtxcty = (EditText) findViewById(R.id.edtxcty);
        edtxt = (EditText) findViewById(R.id.edtxt);

        btnru = (Button) findViewById(R.id.btnru);

        btnru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardaCampos();
                //String mensaje = getMensaje();
                //Toast.makeText(RegistroActivity.this,mensaje,Toast.LENGTH_LONG).show();
            }
        });
        _progressDialog = new ProgressDialog(this);
        _progressDialog.setCancelable(false);
        _progressDialog.setIndeterminate(true);
        _progressDialog.setMessage("Guardando...");

    }

    private void guardaCampos(){
        if (isCamposValidos()){
            JsonObject json = new JsonObject();
            json.addProperty("nombre",edtxname.getText().toString());
            json.addProperty("apaterno",edtxp.getText().toString());
            json.addProperty("amaterno",edtxm.getText().toString());
            json.addProperty("email",edtemail.getText().toString());
            json.addProperty("contraseña",edtpass.getText().toString());
            json.addProperty("calle",edtxc.getText().toString());
            json.addProperty("_num",edtxnum.getText().toString());
            json.addProperty("cp",edtxcp.getText().toString());
            json.addProperty("ciudad",edtxcty.getText().toString());
            json.addProperty("tel",edtxt.getText().toString());

            _progressDialog.show();
            Ion.with(this)
                    .load("POST","http://192.168.43.11:8084/Pizza/webresources/pizzahome/registro/")
                    .progressDialog(_progressDialog)
                    .setBodyParameter("nombre",edtxname.getText().toString())
                    .setBodyParameter("apaterno",edtxp.getText().toString())
                    .setBodyParameter("amaterno",edtxm.getText().toString())
                    .setBodyParameter("email",edtemail.getText().toString())
                    .setBodyParameter("contraseña",edtpass.getText().toString())
                    .setBodyParameter("calle",edtxc.getText().toString())
                    .setBodyParameter("_num",edtxnum.getText().toString())
                    .setBodyParameter("cp",edtxcp.getText().toString())
                    .setBodyParameter("ciudad",edtxcty.getText().toString())
                    .setBodyParameter("tel",edtxt.getText().toString())
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            _progressDialog.dismiss();
                            if (e != null){
                                e.printStackTrace();
                                Toast.makeText(RegistroActivity.this, "Error De Conexión", Toast.LENGTH_LONG).show();
                            }else {
                                if (result != null){

                                    System.out.println("Resultado es: "+result);
                                    try{
                                        JSONObject json = new JSONObject(result);
                                        boolean error = json.getBoolean("error");
                                        String mensaje = json.getString("mesj");
                                        Toast.makeText(RegistroActivity.this,mensaje, Toast.LENGTH_LONG).show();
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
        }else {
            Toast.makeText(this,"Campos Inválidos", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isCamposValidos(){
        boolean valido = true;
        if (edtxname.getText().toString().length() < 3){
            valido = false;
        }
        if (edtxp.getText().toString().length() < 3){
            valido = false;
        }
        if (edtxm.getText().toString().length() < 3){
            valido = false;
        }
        if (edtemail.getText().toString().length() < 10){
            valido = false;
        }
        if (edtpass.getText().toString().length() < 3){
            valido = false;
        }
        if (edtxc.getText().toString().length() == 0){
            valido = false;
        }
        if (edtxnum.getText().toString().length() == 0){
            valido = false;
        }
        if (edtxcp.getText().toString().length() < 5){
            valido = false;
        }
        if (edtxcty.getText().toString().length() < 3){
            valido = false;
        }
        if (edtxt.getText().toString().length() < 10){
            valido = false;
        }
        return valido;
    }

    private void ir() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
