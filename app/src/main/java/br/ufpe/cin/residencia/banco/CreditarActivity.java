package br.ufpe.cin.residencia.banco;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.uteis.MonetaryMaskTextWatcher;

public class CreditarActivity extends AppCompatActivity {
    BancoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define o layout da activity
        setContentView(R.layout.activity_operacoes);

        //Inicializa o viewModel
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        //Obtém a lista de numeros do viewModel e adiciona um item "Selecionar Numero da Conta"
        //na primeira posição [0]
        List<String> numeros = viewModel.getNumeros();
        numeros.add(0, "Selecionar Numero da Conta");

        //Obtém os elementos da inferface do usuário
        TextView tipoOperacao = findViewById(R.id.tipoOperacao);
        Spinner numeroContaOrigem = findViewById(R.id.numeroContaOrigem);
        TextView labelContaDestino = findViewById(R.id.labelContaDestino);
        Spinner numeroContaDestino = findViewById(R.id.numeroContaDestino);
        EditText valorOperacao = findViewById(R.id.valor);
        Button btnOperacao = findViewById(R.id.btnOperacao);

        //Esconde a seleção de conta de destino
        labelContaDestino.setVisibility(View.GONE);
        numeroContaDestino.setVisibility(View.GONE);

        //Adiciona uma máscara monetária ao campo de valor da operação
        TextWatcher mask = MonetaryMaskTextWatcher.monetario(valorOperacao);
        valorOperacao.addTextChangedListener(mask);

        //Adiciona um texto adicional ao campo valor da operação
        valorOperacao.setHint(valorOperacao.getHint() + " creditado");

        //Define o tipo de operação e texto do botão de acordo com a operação (Crédito)
        tipoOperacao.setText("CREDITAR");
        btnOperacao.setText("Creditar");

        //Cria um adaptador para o spinner de seleção de conta de origem
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Define o adaptador para o spinner de seleção de conta de origem
        numeroContaOrigem.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(numeros);

        //Define um listener para seleção de uma conta de origem
        numeroContaOrigem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Atualiza o hint do campo de valor da operação com base na conta selecionada
                atualizarHint(i, numeros, valorOperacao);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nada a fazer
            }
        });

        //Define um listener para o botão de operação
        btnOperacao.setOnClickListener(
                v -> {
                    // Obter a posição selecionada do Spinner de número de conta de origem
                    int positionOrigem = numeroContaOrigem.getSelectedItemPosition();

                    // Obter o número de conta de origem com base na posição selecionada
                    String numOrigem = numeros.get(positionOrigem);

                    // Obter o valor da transação do EditText de valor de operação
                    String valorTrans = valorOperacao.getText().toString();

                    // Remover caracteres indesejados do valor da transação, como "R$", espaços e vírgulas
                    double valor = Double.parseDouble(
                            MonetaryMaskTextWatcher.unmask(valorTrans));

                    // Verificar se o valor e o indicie do spinner são maiores que zero
                    if (valor > 0 && positionOrigem > 0) {
                        // Creditar o valor na conta de origem usando o ViewModel
                        viewModel.creditar(numOrigem, valor);
                    }else{
                        Toast.makeText(this, "Parece que algo deu errado", Toast.LENGTH_SHORT).show();
                    }
                    //Finalizar a atividade
                    finish();
                }
        );
    }

    private void atualizarHint(int i, List<String> numeros, EditText valorOperacao) {
        // Verifica se o valor de 'i' é maior ou igual a 1
        if (i >= 1) {
            // Obtém o número de conta de origem com base no valor de 'i'
            String numeroOrigem = numeros.get(i);

            // Busca a conta de origem no ViewModel com base no número de conta obtido
            Conta c = viewModel.buscarPeloNumero(numeroOrigem);

            // Cria uma string com a mensagem de hint para o EditText de valor de operação
            StringBuilder teste = new StringBuilder();
            teste.append("Valor total disponível: ")
                    // Formata o saldo da conta como moeda, com separador de milhar e ponto decimal
                    .append(NumberFormat.
                            getCurrencyInstance(new Locale("pt", "BR")).
                            format(c.saldo).
                            replaceAll(",", "."));
            // Define a mensagem de hint no EditText de valor de operação
            valorOperacao.setHint(teste);
        }
    }

}