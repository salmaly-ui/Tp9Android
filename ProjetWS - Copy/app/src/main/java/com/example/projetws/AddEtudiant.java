package com.example.projetws;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import com.example.projetws.beans.Etudiant;

public class AddEtudiant extends AppCompatActivity implements View.OnClickListener {

    private EditText nom, prenom;
    private Spinner ville;
    private RadioButton m, f;
    private Button add, btnRetour;   //   déclaré ici avec les autres
    private RequestQueue requestQueue;

    private static final String insertUrl = "http://10.0.2.2/projet/ws/createEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);

        nom       = findViewById(R.id.nom);
        prenom    = findViewById(R.id.prenom);
        ville     = findViewById(R.id.ville);
        m         = findViewById(R.id.m);
        f         = findViewById(R.id.f);
        add       = findViewById(R.id.add);
        btnRetour = findViewById(R.id.btnRetour);  //   initialisé dans onCreate

        // Peupler le Spinner depuis strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.villes,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ville.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        add.setOnClickListener(this);
        btnRetour.setOnClickListener(v -> finish());  //  listener dans onCreate
    }

    @Override
    public void onClick(View v) {
        if (v == add) envoyerEtudiant();
    }

    private void envoyerEtudiant() {
        if (nom.getText().toString().trim().isEmpty() ||
                prenom.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, insertUrl,
                response -> {
                    try {
                        Etudiant e = new Gson().fromJson(response, Etudiant.class);
                        Toast.makeText(this,
                                "Étudiant ajouté : " + e.getNom() + " " + e.getPrenom(),
                                Toast.LENGTH_SHORT).show();
                        nom.setText("");
                        prenom.setText("");
                        m.setChecked(true);
                    } catch (Exception ex) {
                        Toast.makeText(this, "Réponse : " + response, Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(this,
                        "Erreur connexion : " + error.getMessage(),
                        Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nom",    nom.getText().toString().trim());
                params.put("prenom", prenom.getText().toString().trim());
                params.put("ville",  ville.getSelectedItem().toString());
                params.put("sexe",   m.isChecked() ? "homme" : "femme");
                return params;
            }
        };

        requestQueue.add(request);
    }
}