package br.ufpe.cin.residencia.banco.conta;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.ufpe.cin.residencia.banco.BancoDB;

//Ver métodos anotados com TODO
public class ContaViewModel extends AndroidViewModel {

    private ContaRepository repository; // Instância do repositório de contas
    public LiveData<List<Conta>> contas; // LiveData que representa a lista de contas

    //MutableList que representa a conta atual
    private MutableLiveData<Conta> _contaAtual = new MutableLiveData<>();
    public LiveData<Conta> contaAtual = _contaAtual; //LiveData que representa a conta atual

    public ContaViewModel(@NonNull Application application) {
        super(application);

        // Inicializa o repositório com a instância do banco de dados
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());

        // Inicializa a LiveData com a lista de contas do repositório
        this.contas = repository.getContas();
    }

    //Método para criar nova conta
    void inserir(Conta c) {
        repository.inserir(c);
    }

    //Método para observar as contas para o adaper
    public void observarContas(ContaAdapter adapter) {
        contas.observeForever(adapter::setContas);
    }

    /**
     * Obtém uma lista de números de contas do banco de dados.
     *
     * @return A lista de números de contas.
     */

    List<String> getNumeros() {
        return repository.getNumeros();
    }

    //Método para atualizar uma conta
    void atualizar(Conta c) {
        repository.atualizar(c);
    }

    //Método para remover uma conta
    void remover(Conta c) {
         repository.remover(c);
    }

    //Função apra buscar uma conta pelo nome
    Conta buscarPeloNumero(String numeroConta) {
        return repository.buscarPeloNumero(numeroConta);
    }
}
