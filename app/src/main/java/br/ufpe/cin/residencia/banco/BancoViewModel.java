package br.ufpe.cin.residencia.banco;

import android.app.Application;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaAdapter;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;

//Ver anotações TODO no código
public class BancoViewModel extends AndroidViewModel {
    private ContaRepository repository; // Instância do repositório de contas
    public LiveData<List<Conta>> contas;// LiveData que representa a lista de contas

    public BancoViewModel(@NonNull Application application) {
        super(application);
        // Inicializa o repositório com a instância do banco de dados
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());

        // Inicializa a LiveData com a lista de contas do repositório
        this.contas = repository.getContas();
    }

    // Método para transferência de valor entre contas
    void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        // Busca as contas de origem e destino no banco de dados
        Conta contaOrigem = repository.buscarPeloNumero(numeroContaOrigem);
        Conta contaDestino = repository.buscarPeloNumero(numeroContaDestino);

        // Valida se há saldo disponível na conta de origem
        if (contaOrigem.saldo >= valor) {

            // Se houver saldo, realiza a transferência
            Creditar(valor, contaDestino);// Chama o método creditar para adicionar o valor à conta destino
            Debitar(valor, contaOrigem); // Chama o método debitar para subtrair o valor da conta origem
        } else {

            // Exibe um Toast informando que não há saldo suficiente para a transferência
            Toast.makeText(getApplication().getBaseContext(),
                    "Saldo insuficiente para essa transação",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Método para creditar valor em uma conta específica
    void creditar(String numeroConta, double valor) {

        // Chama o método Creditar na conta para adicionar o valor
        Conta c = repository.buscarPeloNumero(numeroConta);
        Creditar(valor, c);
    }

    // Método para debitar valor de uma conta específica
    void debitar(String numeroConta, double valor) {
        // Chama o método Debitar na conta para subtrair o valor
        Conta c = repository.buscarPeloNumero(numeroConta);
        Debitar(valor, c);
    }

    /**
     * Busca uma conta pelo número da conta.
     *
     * @param numeroConta O número da conta a ser buscada.
     * @return A conta encontrada com o número informado.
     */
    Conta buscarPeloNumero(String numeroConta) {
        return repository.buscarPeloNumero(numeroConta);
    }

    /**
     * Realiza uma operação de crédito em uma conta, atualizando o saldo da conta e do banco de dados automaticamente.
     *
     * @param valor O valor a ser creditado na conta.
     * @param c     A conta na qual o valor será creditado.
     */
    void Creditar(double valor, Conta c) {
        c.saldo += valor;
        repository.atualizarSaldo(c.saldo, c.numero);
    }

    /**
     * Realiza uma operação de débito em uma conta, atualizando o saldo da conta e do banco de dados automaticamente.
     *
     * @param valor O valor a ser debitado da conta.
     * @param c     A conta da qual o valor será debitado.
     */

    void Debitar(double valor, Conta c) {
        double saldo = c.saldo - valor;
        repository.atualizarSaldo(saldo, c.numero);
    }

    /**
     * Obtém uma lista de números de contas do banco de dados.
     *
     * @return A lista de números de contas.
     */
    List<String> getNumeros() {
        return repository.getNumeros();
    }

    /**
     * Obtém uma lista de saldos das contas do banco de dados.
     *
     * @return A lista de saldos das contas.
     */

    List<Double> getSaldos() {
        return repository.saldos();
    }

    /**
     * Obtém uma lista de contas com base em uma consulta pelo nome dos clientes.
     *
     * @return A lista de contas correspondentes à consulta pelo nome dos clientes.
     */
    LiveData<List<Conta>> getContaNome(){
        return repository.getsearchResultsClientes();
    }

    /**
     * Realiza uma consulta por nome de clientes no banco de dados de contas.
     *
     * @param query A consulta a ser realizada pelo nome dos clientes.
     */
    public void procurarClientes(String query){
        repository.procurarCliente(query);
    }

    /**
     * Obtém uma lista de contas com base em uma consulta pelo CPF dos clientes.
     *
     * @return A lista de contas correspondentes à consulta pelo CPF dos clientes.
     */
    LiveData<List<Conta>> getContaCpf(){
        return repository.getsearchResultsCPF();
    }

    /**
     * Realiza uma consulta por CPF de clientes no banco de dados de contas.
     *
     * @param query A consulta a ser realizada pelo CPF dos clientes.
     */
    public void procurarCpf(String query){
        repository.procurarCPF(query);
    }

    /**
     * Obtém uma lista de contas com base em uma consulta pelo número das contas.
     *
     * @return A lista de contas correspondentes à consulta pelo número das contas.
     */
    LiveData<List<Conta>> getContaNumero(){
        return repository.getsearchResultsNumero();
    }

    /**
     * Realiza uma consulta por número de contas no banco de dados de contas.
     *
     * @param query A consulta a ser realizada pelo número das contas.
     */
    public void procurarNumero(String query){
        repository.procurarNumero(query);
    }

}
