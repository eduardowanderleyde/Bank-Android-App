package br.ufpe.cin.residencia.banco.conta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.ufpe.cin.residencia.banco.R;

public class ContasActivity extends AppCompatActivity {
    ContaAdapter adapter;
    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define o layout da atividade
        setContentView(R.layout.activity_contas);

        // Inicializa o ViewModel
        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);

        // Obtém a RecyclerView do layout
        RecyclerView recyclerView = findViewById(R.id.rvContas);

        // Inicializa o adaptador da RecyclerView
        adapter = new ContaAdapter(getLayoutInflater());

        // Observa as mudanças na lista de contas no ViewModel e atualiza o adaptador
        viewModel.observarContas(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        Button adicionarConta = findViewById(R.id.btn_Adiciona);

        // Configura um listener de clique no botão para iniciar a atividade de adicionar conta
        adicionarConta.setOnClickListener(
                v -> startActivity(new Intent(this, AdicionarContaActivity.class))
        );
    }
}