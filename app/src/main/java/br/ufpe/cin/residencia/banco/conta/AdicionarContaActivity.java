package br.ufpe.cin.residencia.banco.conta;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.Random;

import br.ufpe.cin.residencia.banco.R;
import br.ufpe.cin.residencia.banco.uteis.MonetaryMaskTextWatcher;

//Ver anotações TODO no código
public class AdicionarContaActivity extends AppCompatActivity {

    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_conta);
        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);

        //Obtém os elementos da interface do usuário
        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        Button btnRemover = findViewById(R.id.btnRemover);
        EditText campoNome = findViewById(R.id.nome);
        EditText campoNumero = findViewById(R.id.numero);
        EditText campoCPF = findViewById(R.id.cpf);
        EditText campoSaldo = findViewById(R.id.saldo);

        //retorna o numero de todas as contas no banco de dados
        List<String> numeros = viewModel.getNumeros();

        //Adiciona uma máscara monetária ao campo de valor da operação
        TextWatcher mask = MonetaryMaskTextWatcher.monetario(campoSaldo);
        campoSaldo.addTextChangedListener(mask);

        btnAtualizar.setText("Inserir");
        btnRemover.setVisibility(View.GONE);

        btnAtualizar.setOnClickListener(
                view -> {
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String numeroConta = campoNumero.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();

                    /**
                     * Tratamento para os erros caso deixe algum campo obrigatório vazio.
                     * deve-se utilizar um if para cada campo para que possa ser tratado cada campo
                     * de forma individual, para assim conseguir monitorar de melhor forma
                     * os estados de cada elemento.
                     */

                    //Verifica se a variavel nomeCliente está devidamente preenchida
                    if (!nomeCliente.isEmpty()) {
                        //Verifica se a variavel cpfCliente está devidamente preenchida
                        if (!cpfCliente.isEmpty()) {
                            //Verifica se a variavel numeroConta está devidamente preenchida
                            if (!numeroConta.isEmpty()) {
                                //Verifica se a variavel saldoConta está devidamente preenchida
                                if (!saldoConta.isEmpty()) {
                                    /*Caso todas as variaveis estejam devidamente preenchidas
                                    iremos usar a instancia do viewModel para usarmos o método
                                    inserir presente nele, passando como argumento a classe
                                    Conta que vai receber as variaveis acima em seus parametros.
                                     */

                                    boolean idUnico = false;
                                    if(numeros.size() > 0) {
                                        for (int i = 0; i < numeros.size(); i++) {
                                            if (numeroConta.equals(numeros.get(i))) {
                                                break;
                                            } else {
                                                idUnico = true;
                                            }
                                        }
                                    }
                                    
                                    if(idUnico || numeros.size() <= 0){
                                        double saldo = Double.parseDouble(MonetaryMaskTextWatcher.unmask(saldoConta));

                                        Conta c = new Conta(numeroConta, saldo, nomeCliente, cpfCliente);
                                        viewModel.inserir(c);
                                        this.finish();
                                    }else{
                                        Toast.makeText(this, "Numero de conta já existe!", Toast.LENGTH_SHORT).show();
                                    }
                                    
                                } else {
                                    /*Caso a variavel saldoConta esteja vazia, iremos exibir
                                    seu hintText em vermelho e trocaremos a mensagem para
                                    um informativo de que o campo deve ser preenchido
                                     */
                                    campoSaldo.setHintTextColor(Color.RED);
                                    campoSaldo.setHint("Por favor insira um saldo válido!");
                                }
                            } else {
                                /*Caso a variavel numeroConta esteja vazia, iremos exibir
                                    seu hintText em vermelho e trocaremos a mensagem para
                                    um informativo de que o campo deve ser preenchido
                                     */
                                campoNumero.setHintTextColor(Color.RED);
                                campoNumero.setHint("Por favor insira um numero válido!");
                            }
                        } else {
                            /*Caso a variavel cpfCliente esteja vazia, iremos exibir
                                    seu hintText em vermelho e trocaremos a mensagem para
                                    um informativo de que o campo deve ser preenchido
                                     */
                            campoCPF.setHintTextColor(Color.RED);
                            campoCPF.setHint("Por favor insira um CPF válido!");
                        }
                    } else {
                        /*Caso a variavel nomeCliente esteja vazia, iremos exibir
                                    seu hintText em vermelho e trocaremos a mensagem para
                                    um informativo de que o campo deve ser preenchido
                                     */
                        campoNome.setHintTextColor(Color.RED);
                        campoNome.setHint("Por favor insira um nome válido!");
                    }

                    /*
                      Assim temos um método que verifica os campos presentes na nossa UI e
                      confere se estão em conformidade com as regras de uso definidas pelo
                      desenvolvedor ou pela empresa cliente, e trata os erros e exeções
                      caso algo saia dessas conformidades.
                     */

                }
        );
    }
}