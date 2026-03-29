package com.example.projetws;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd, btnListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnListe = findViewById(R.id.btnListe);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEtudiant.class);
            startActivity(intent);
        });

        btnListe.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListeEtudiant.class);
            startActivity(intent);
        });
    }
}
