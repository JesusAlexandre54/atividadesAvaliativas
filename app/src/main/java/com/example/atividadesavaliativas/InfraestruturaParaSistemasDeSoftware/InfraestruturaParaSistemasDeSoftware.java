package com.example.atividadesavaliativas.InfraestruturaParaSistemasDeSoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.atividadesavaliativas.BancoDeDados.BancoDeDados;
import com.example.atividadesavaliativas.BancoDeDados.BancoDeDadosFim;
import com.example.atividadesavaliativas.BancoDeDados.BancoDeDadosQuestoes;
import com.example.atividadesavaliativas.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class InfraestruturaParaSistemasDeSoftware extends AppCompatActivity {
    Intent i;
    int Placar, Questao;
    boolean fim;
    FirebaseFirestore db;
    String resp1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infraestrutura_para_sistemas_de_software);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        if(intent != null) {
            Placar = intent.getIntExtra("placar", 0);
            Questao = intent.getIntExtra("questaoEscolhida",1);
            fim = intent.getBooleanExtra("fim",fim);

        }else {
            Placar = 0;

        }
        String questao_aleatoria =String.valueOf(Questao);
        db.collection("InfraestruturaParaSistemasDeSoftware").document(questao_aleatoria)
                .addSnapshotListener((documento, error) -> {
                    if (documento!=null){
                        resp1 = documento.getString("resp1");
                    }
                });


        new Handler().postDelayed(() -> {
            if (resp1==null){
                Questao-=1;
                i = new Intent(InfraestruturaParaSistemasDeSoftware.this, InfraestruturaParaSistemasDeSoftwareFim.class);
                i.putExtra("placar", Placar);
                i.putExtra("questaoEscolhida",Questao);
                startActivity(i);

            }else {
                i = new Intent(InfraestruturaParaSistemasDeSoftware.this, InfraestruturaParaSistemasDeSoftwareQuestoes.class);
                i.putExtra("placar", Placar);
                i.putExtra("questaoEscolhida", Questao);

                startActivity(i);
            }
        }, 3000);
    }
}