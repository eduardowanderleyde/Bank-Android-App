package br.ufpe.cin.residencia.banco.conta;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ContaRepository {
    private ContaDAO dao;
    private LiveData<List<Conta>> contas;
    private MutableLiveData<List<Conta>> searchResultsClientes = new MutableLiveData<>();
    private MutableLiveData<List<Conta>> searchResultsCPF = new MutableLiveData<>();
    private MutableLiveData<List<Conta>> searchResultsNumero = new MutableLiveData<>();

    public ContaRepository(ContaDAO dao) {
        this.dao = dao;
        this.contas = dao.contas();
    }

    public LiveData<List<Conta>> getContas() {
        return contas;
    }


    /**
     * Insere uma nova conta no banco de dados em uma thread separada.
     *
     * @param c A conta a ser inserida
     */
    @WorkerThread
    public void inserir(Conta c) {
        new Thread(() -> dao.adicionar(c)).start();
    }

    /**
     * Atualiza uma conta em uma Thread separada.
     *
     * @param c A conta a ser atualizada
     */
    @WorkerThread
    public void atualizar(Conta c) {
        new Thread(() ->
                dao.atualizar(c)).start();

        System.out.println("TESTE NO REPOSITORIO " + c.saldo);
    }

    /**
     * Remove uma conta do banco de dados em uma thread separada.
     *
     * @param c A conta a ser removida
     */
    @WorkerThread
    public void remover(Conta c) {
        new Thread(() -> dao.deletarConta(c)).start();
    }

    @WorkerThread
    public Conta buscarPeloNumero(String numeroConta) {
        // Cria uma instância de FutureTask com a operação a ser executada em segundo plano
        FutureTask<Conta> futureConta = new FutureTask<>(() -> dao.buscarPorNumeroConta(numeroConta));
        // Inicia uma nova thread para executar a FutureTask
        new Thread(futureConta).start();
        try {
            // Espera pelo término da thread e obtém o resultado
            return futureConta.get();
        } catch (InterruptedException | ExecutionException e) {
            // Trate as exceções adequadamente
            throw new RuntimeException(e);
        }
    }

    public void procurarCliente(String query) {
        // Executar a busca no banco de dados em uma thread separada (usando AsyncTask, RxJava, etc.)
        // e atualizar o LiveData com os resultados
        // por exemplo:
        String searchQuery = "%" + query + "%"; // Adiciona o '%' para permitir a busca com o operador LIKE
        dao.buscarPorNomeCliente(searchQuery).observeForever(new Observer<List<Conta>>() {

            /**
             * Called when the data is changed.
             *
             * @param contas The new data
             */
            @Override
            public void onChanged(List<Conta> contas) {
                searchResultsClientes.setValue(contas);
            }
        });
    }

    public LiveData<List<Conta>> getsearchResultsClientes() {
        return searchResultsClientes;
    }

    public void procurarCPF(String query) {
        // Executar a busca no banco de dados em uma thread separada (usando AsyncTask, RxJava, etc.)
        // e atualizar o LiveData com os resultados
        // por exemplo:
        String searchQuery = "%" + query + "%"; // Adiciona o '%' para permitir a busca com o operador LIKE
        dao.buscarCpfClienteLike(searchQuery).observeForever(new Observer<List<Conta>>() {

            /**
             * Called when the data is changed.
             *
             * @param contas The new data
             */
            @Override
            public void onChanged(List<Conta> contas) {
                searchResultsCPF.setValue(contas);
            }
        });
    }

    public LiveData<List<Conta>> getsearchResultsCPF() {
        return searchResultsCPF;
    }

    public void procurarNumero(String query) {
        // Executar a busca no banco de dados em uma thread separada (usando AsyncTask, RxJava, etc.)
        // e atualizar o LiveData com os resultados
        String searchQuery = "%" + query + "%"; // Adiciona o '%' para permitir a busca com o operador LIKE
        dao.buscarNumeroContaLike(searchQuery).observeForever(new Observer<List<Conta>>() {

            /**
             * Método para buscar uma lista de contas no banco de dados
             * de acordo com os dados passados
             * @param contas  as contas que correspondem com a pesquisa
             */
            @Override
            public void onChanged(List<Conta> contas) {
                searchResultsNumero.setValue(contas);
            }
        });
    }

    //retorna as contas obtidas do banco de dados
    public LiveData<List<Conta>> getsearchResultsNumero() {
        return searchResultsNumero;
    }

    //Atualiza o saldo em uma nova Thread
    public void atualizarSaldo(double saldo, String numeroConta) {
        new Thread(() -> dao.mudarSaldo(saldo, numeroConta)).start();
    }

    // Obtém a lista de números de conta em uma nova thread
    public List<String> getNumeros() {
        FutureTask<List<String>> futureTask =
                new FutureTask<>(() -> dao.getNumeros());
        new Thread(futureTask).start();
        try {
            return futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    //Obtém a lista de saldos em uma nova Thread
    public List<Double> saldos() {

        FutureTask<List<Double>> futureTask =
                new FutureTask<>(() -> dao.getSaldo());

        new Thread(futureTask).start();

        try {
            return futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
