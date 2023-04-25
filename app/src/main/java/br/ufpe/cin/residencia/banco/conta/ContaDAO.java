package br.ufpe.cin.residencia.banco.conta;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContaDAO {

    /**
     * Adiciona um novo valor a tabela
     * @param c o objeto a ser inserido
     */
    @Insert(entity = Conta.class)
    void adicionar(Conta c);

    /**
     * Atualiza as colunas de uma tabela aonde o objeto for correspondente
     *
     * @param c Conta para atualizar
     */
    @Update
    void atualizar(Conta c);

    /**
     * Seleciona todas as entradas em uma tabela
     * @return um LiveData com uma lista de todas as contas na tabela
     */
    @Query("SELECT * FROM contas ORDER BY numero ASC")
    LiveData<List<Conta>> contas();

    /**
     * Seleciona todos os saldos do banco de dados
     * @return uma lista com todos os saldos do banco de dados
     */
    @Query("SELECT saldo FROM contas")
    List<Double> getSaldo();

    /**
     * Busca contas na tabela pelo nome utilizando o LIKE para ver todas as contas com as letras
     * desejadas para facilitar a busca
     * @param nomeCliente o nome a ser buscado na tabela
     * @return um LiveData com uma lista de todas as contas que correspondem ao nome passado
     */
    @Query("SELECT * FROM contas WHERE nomeCliente LIKE :nomeCliente")
    LiveData<List<Conta>> buscarPorNomeCliente(String nomeCliente);

    /**
     * Busca contas na tabela pelo cpf utilizando o LIKE para ver todas as contas com os numeros
     * desejados para facilitar a busca
     * @param cpfCliente o cpf a ser buscado na tabela
     * @return um LiveData com uma lista de todas as contas que correspondem ao cpf passado
     */
    @Query("SELECT * FROM contas WHERE cpfCliente LIKE :cpfCliente")
    LiveData<List<Conta>> buscarCpfClienteLike(String cpfCliente);

    /**
     * Busca contas na tabela pelo numero utilizando o LIKE para ver todas as contas com os numeros
     * desejados para facilitar a busca
     * @param numeroConta o numero a ser buscado na tabela
     * @return um LiveData com uma lista de todas as contas que correspondem ao numero passado
     */
    @Query("SELECT * FROM contas WHERE numero LIKE :numeroConta")
    LiveData<List<Conta>> buscarNumeroContaLike(String numeroConta);

    /**
     * Busca uma conta na tabela pelo numero
     * @param numeroConta o numero a ser pesquisado
     * @return um objeto conta com a conta pesquisada
     */
    @Query("SELECT * FROM contas WHERE numero = :numeroConta")
    Conta buscarPorNumeroConta(String numeroConta);

    /**
     * Deleta uma conta da tabela
     * @param c a conta a ser deletada
     */
    @Delete
    void deletarConta(Conta c);


    /**
     * Atualizar saldo de uma conta na tabela
     * @param saldo valor a ser atualizado no saldo
     * @param numeroConta numero da conta a ser atualizada
     */
    @Query("UPDATE contas SET saldo = :saldo WHERE numero = :numeroConta")
    void mudarSaldo(double saldo, String numeroConta);

    /**
     * Seleciona todas as entradas da tabela pelo numero
     * @return um List com todos os numeros das contas na tabela
     */
    @Query("SELECT numero FROM contas")
    List<String> getNumeros();
}
