package com.example.leobardo.pizza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class MenuActivity extends AppCompatActivity {
    Button btnp,btnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnp = (Button) findViewById(R.id.btnp);
        btnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,
                        ProductosActivity.class);
                startActivity(intent);
            }
        });

        btnd = (Button) findViewById(R.id.btnd);
        btnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,
                        DetalleActivity.class);
                startActivity(intent);
            }
        });
    }
}
