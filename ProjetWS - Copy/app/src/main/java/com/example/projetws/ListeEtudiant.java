package com.example.projetws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.example.projetws.beans.Etudiant;

public class ListeEtudiant extends AppCompatActivity {

    private RecyclerView recyclerView;   //  RecyclerView
    private Button btnRetour;
    private RequestQueue requestQueue;
    private static final String url = "http://10.0.2.2/projet/ws/loadEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_etudiant);

        recyclerView = findViewById(R.id.recyclerViewEtudiants);
        btnRetour    = findViewById(R.id.btnRetour);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);

        btnRetour.setOnClickListener(v -> finish());

        loadEtudiants();
    }

    private void loadEtudiants() {
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    Type type = new TypeToken<ArrayList<Etudiant>>(){}.getType();
                    ArrayList<Etudiant> list = new Gson().fromJson(response, type);
                    recyclerView.setAdapter(new EtudiantAdapter(list));
                },
                error -> Toast.makeText(this,
                        "Erreur connexion à la base de données",
                        Toast.LENGTH_SHORT).show()
        );
        requestQueue.add(request);
    }

    static class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.VH> {

        private final ArrayList<Etudiant> liste;

        EtudiantAdapter(ArrayList<Etudiant> liste) { this.liste = liste; }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_etudiant, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            Etudiant e = liste.get(position);
            holder.tvNomPrenom.setText(e.getNom() + " " + e.getPrenom());
            holder.tvVille.setText(e.getVille() + " — " + e.getSexe());
        }

        @Override
        public int getItemCount() { return liste.size(); }

        static class VH extends RecyclerView.ViewHolder {
            TextView tvNomPrenom, tvVille;
            VH(View v) {
                super(v);
                tvNomPrenom = v.findViewById(R.id.tvNomPrenom);
                tvVille     = v.findViewById(R.id.tvVille);
            }
        }
    }
}