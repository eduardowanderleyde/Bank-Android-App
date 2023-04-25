package br.ufpe.cin.residencia.banco.conta;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.NumberFormat;
import java.util.Locale;

import br.ufpe.cin.residencia.banco.BancoDB;
import br.ufpe.cin.residencia.banco.R;
import br.ufpe.cin.residencia.banco.uteis.MonetaryMaskTextWatcher;

//Ver anotações TODO no código
public class EditarContaActivity extends AppCompatActivity {

    public static final String KEY_NUMERO_CONTA = "numeroConta";
    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Define um layout para a atividade
        setContentView(R.layout.activity_adicionar_conta);

        //Inicializa o view model
        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);

        //Obtém os dados da interface do usuário
        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        Button btnRemover = findViewById(R.id.btnRemover);
        EditText campoNome = findViewById(R.id.nome);
        EditText campoNumero = findViewById(R.id.numero);
        EditText campoCPF = findViewById(R.id.cpf);
        EditText campoSaldo = findViewById(R.id.saldo);

        //Adiciona uma máscara monetária ao campo de valor da operação
        TextWatcher mask = MonetaryMaskTextWatcher.monetario(campoSaldo);
        campoSaldo.addTextChangedListener(mask);

        //Desabilita o campo numero na interface do usuário
        campoNumero.setEnabled(false);

        //Obtém um intent passado para essa atividade
        Intent i = getIntent();

        //Obtém o valor KEY_NUMERO_CONTA passado a essa atividade
        String numeroConta = i.getStringExtra(KEY_NUMERO_CONTA);

        //Buscar uma conta pelo numero no view model
        Conta conta = viewModel.buscarPeloNumero(numeroConta);

        //Preenche os campos da interface com os dados resgatados do view model
        campoNome.setText(conta.nomeCliente);
        campoNumero.setText(conta.numero);
        campoCPF.setText(conta.cpfCliente);
        campoSaldo.setText( //Formata o numero como monetário
                NumberFormat
                        .getCurrencyInstance(
                                new Locale("pt", "BR"))
                        .format(conta.saldo));
        btnAtualizar.setText("Editar");

        //Define um listener para o botão atualizar
        btnAtualizar.setOnClickListener(
                v -> {
                    //Obtém os dados dos campos da interface
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();

                    //Verifica se os campos estão preenchidos
                    if (!nomeCliente.isEmpty()) {
                        if (!cpfCliente.isEmpty()) {
                            if (!saldoConta.isEmpty()) {

                                // Remover caracteres indesejados do valor da transação, como "R$", espaços e vírgulas
                                double valor = Double.parseDouble(
                                        MonetaryMaskTextWatcher.unmask(saldoConta));

                                // Cria um novo objeto conta com os dados dos campos da interface
                                Conta c = new Conta(numeroConta, valor, nomeCliente, cpfCliente);

                                //Atualiza a conta usando o view model
                                viewModel.atualizar(c);

                                //finaliza a atividade
                                this.finish();
                            } else {
                                //Retorna o valor 0.0 caso o campo saldo conta esteja vazio
                                saldoConta = String.valueOf(0.0);
                            }
                        } else {

                            //Exibe hints de erro na interface
                            campoCPF.setHint("Forneça um CPF válido!!");
                            campoCPF.setHintTextColor(Color.RED);
                        }
                    } else {

                        //Exibe hints de erro na interface
                        campoNome.setHint("Forneça um nome!");
                        campoNome.setHintTextColor(Color.RED);
                    }
                }
        );

        //Define um listener para o botão remover
        btnRemover.setOnClickListener(v -> {
            //Obtém os dados dos campos da interface
            String nomeCliente = campoNome.getText().toString();
            String cpfCliente = campoCPF.getText().toString();
            String saldoConta = campoSaldo.getText().toString();

            // Remover caracteres indesejados do valor da transação, como "R$", espaços e vírgulas
            double valor = Double.parseDouble(
                    MonetaryMaskTextWatcher.unmask(saldoConta));

            //Criando um objeto Conta
            Conta c = new Conta(numeroConta, valor, nomeCliente, cpfCliente);

            //Deletar a conta usando o view model
            viewModel.remover(c);

            //Finaliza a atividade
            this.finish();
        });
    }
}