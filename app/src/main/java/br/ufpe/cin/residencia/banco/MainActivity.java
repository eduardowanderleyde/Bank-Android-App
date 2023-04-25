package br.ufpe.cin.residencia.banco;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import br.ufpe.cin.residencia.banco.cliente.ClientesActivity;
import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContasActivity;

//Ver anotações TODO no código
public class MainActivity extends AppCompatActivity {
    BancoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Define o layout da atividade
        setContentView(R.layout.activity_main);

        //Inicializa o view model
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        //Obtém os elementos da interface do usuário
        Button contas = findViewById(R.id.btnContas);
        Button clientes = findViewById(R.id.btnClientes);
        Button transferir = findViewById(R.id.btnTransferir);
        Button debitar = findViewById(R.id.btnDebitar);
        Button creditar = findViewById(R.id.btnCreditar);
        Button pesquisar = findViewById(R.id.btnPesquisar);

        //Remover a linha abaixo se for implementar a parte de Clientes
        clientes.setEnabled(false);

        //Define um listener para o botão de contas
        contas.setOnClickListener(
                v -> {
                    //Inicia a atividade de contas
                    startActivity(new Intent(this, ContasActivity.class));
                }
        );

        //Define um listener para o botão de clientes
        clientes.setOnClickListener(

                //Inicia a atividade de clientes
                v -> startActivity(new Intent(this, ClientesActivity.class))
        );

        //Define um listener para o botão de transferir
        transferir.setOnClickListener(

                //Inicia a atividade transferir
                v -> startActivity(new Intent(this, TransferirActivity.class))
        );

        //Define um listener para o botão de creditar
        creditar.setOnClickListener(

                //inicia a atividade de creditar
                v -> startActivity(new Intent(this, CreditarActivity.class))
        );

        //Define um listener para o botão debitar
        debitar.setOnClickListener(

                //Inicia a atividade debitar
                v -> startActivity(new Intent(this, DebitarActivity.class))
        );

        //Define um listener para o botão pesquisar
        pesquisar.setOnClickListener(

                //Inicia a atividade pesquisar
                v -> startActivity(new Intent(this, PesquisarActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Obtém o textview totalBanco da interface do usuário
        TextView totalBanco = findViewById(R.id.totalDinheiroBanco);

        //Inicializa o view model
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        //Obtém a lista de saldos do view model
        List<Double> saldos = viewModel.getSaldos();

        double total = 0;

        //Define um loop do tamanho da lista de saldos retornado pelo view model
        for (int i = 0; i < saldos.size(); i++) {

            //soma os saldos a variavel total
            total += saldos.get(i);
        }

        //Formata o texto da variavel total como monetário
        totalBanco.setText(
                NumberFormat
                        .getCurrencyInstance(
                                new Locale(
                                        "pt",
                                        "BR"
                                )
                        ).format(total));

    }
}