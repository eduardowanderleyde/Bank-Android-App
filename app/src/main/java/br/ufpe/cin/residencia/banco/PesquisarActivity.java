package br.ufpe.cin.residencia.banco;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaAdapter;

//Ver anotações TODO no código
public class PesquisarActivity extends AppCompatActivity {
    BancoViewModel viewModel;
    ContaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Define o layout da activity
        setContentView(R.layout.activity_pesquisar);

        //Inicializa o viewModel
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        // Inicialização dos elementos da interface do usuário
        EditText aPesquisar = findViewById(R.id.pesquisa);
        Button btnPesquisar = findViewById(R.id.btn_Pesquisar);
        RadioGroup tipoPesquisa = findViewById(R.id.tipoPesquisa);
        RecyclerView rvResultado = findViewById(R.id.rvResultado);

        //Configura e define um adaptador ao recycler view
        adapter = new ContaAdapter(getLayoutInflater());
        rvResultado.setLayoutManager(new LinearLayoutManager(this));
        rvResultado.setAdapter(adapter);

        // Adicionar um ouvinte de texto para o EditText de pesquisa
        aPesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Antes de o texto ser alterado
                //Nada a fazer
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Durante a alteração do texto

                // Verificar o tipo de pesquisa selecionado no RadioGroup
                switch (tipoPesquisa.getCheckedRadioButtonId()) {
                    case R.id.peloCPFcliente:
                        if (charSequence.length() > 0) {

                            // Chamar o método de pesquisa por CPF no ViewModel
                            viewModel.procurarCpf(charSequence.toString());

                            // Observar os resultados da pesquisa no ViewModel e atualizar o adapter
                            viewModel.getContaCpf().observe(PesquisarActivity.this, contas -> {
                                adapter.setContas(contas);
                            });
                        }
                        break;
                    case R.id.peloNomeCliente:
                        if (charSequence.length() > 0) {

                            // Chamar o método de pesquisa por Nome no ViewModel
                            viewModel.procurarClientes(charSequence.toString());

                            // Observar os resultados da pesquisa no ViewModel e atualizar o adapter
                            viewModel.getContaNome().observe(PesquisarActivity.this, contas -> {
                                adapter.setContas(contas);
                            });
                        }
                        break;
                    case R.id.peloNumeroConta:
                        if (charSequence.length() > 0) {

                            // Chamar o método de pesquisa por Numero da conta no ViewModel
                            viewModel.procurarNumero(charSequence.toString());

                            // Observar os resultados da pesquisa no ViewModel e atualizar o adapter
                            viewModel.getContaNumero().observe(PesquisarActivity.this, contas -> {
                                adapter.setContas(contas);
                            });
                        }
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Depois que o texto for alterado
                //Nada a fazer
            }
        });

        // Adicionar um ouvinte de clique para o botão de pesquisa
        btnPesquisar.setOnClickListener(
                v -> {
                    String oQueFoiDigitado = aPesquisar.getText().toString();
                    // Verificar o tipo de pesquisa selecionado no RadioGroup
                    switch (tipoPesquisa.getCheckedRadioButtonId()) {
                        case R.id.peloCPFcliente:
                            // Chamar o método de pesquisa por CPF no ViewModel
                            viewModel.procurarCpf(oQueFoiDigitado);

                            // Observar os resultados da pesquisa no ViewModel e atualizar o adapter
                            viewModel.getContaCpf().observe(PesquisarActivity.this, contas -> {
                                adapter.setContas(contas);
                            });
                            break;
                        case R.id.peloNomeCliente:
                            // Chamar o método de pesquisa por Nome no ViewModel
                            viewModel.procurarClientes(oQueFoiDigitado);

                            // Observar os resultados da pesquisa no ViewModel e atualizar o adapter
                            viewModel.getContaNome().observe(PesquisarActivity.this, contas -> {
                                adapter.setContas(contas);
                            });
                            break;
                        case R.id.peloNumeroConta:
                            // Chamar o método de pesquisa por Numero da conta no ViewModel
                            viewModel.procurarNumero(oQueFoiDigitado);

                            // Observar os resultados da pesquisa no ViewModel e atualizar o adapter
                            viewModel.getContaNumero().observe(PesquisarActivity.this, contas -> {
                                adapter.setContas(contas);
                            });
                            break;
                    }
                }
        );

    }
}