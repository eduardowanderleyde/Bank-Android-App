package br.ufpe.cin.residencia.banco;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaDAO;

//ESTA CLASSE NAO PRECISA SER MODIFICADA, SE NAO FOR IMPLEMENTAR A FUNCIONALIDADE DE CLIENTES!

// Anotação indicando que essa classe é uma representação de um banco de dados
// e define a entidade Conta e a versão do banco de dados
@Database(entities = {Conta.class}, version = 1)

public abstract class BancoDB extends RoomDatabase {
    // Método abstrato para obter o DAO (Data Access Object) da entidade Conta
    public abstract ContaDAO contaDAO();
    // Nome do banco de dados
    public static final String DB_NAME = "banco.db";

    // Instância única do banco de dados, usando o padrão Singleton
    private static volatile BancoDB INSTANCE;

    // Método sincronizado para obter uma instância do banco de dados
    public synchronized static BancoDB getDB(Context c) {

        // Construção da instância do banco de dados usando o Room.databaseBuilder
        // Recebe o contexto, a classe do banco de dados e o nome do banco de dados como parâmetros
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    c,
                    BancoDB.class,
                    DB_NAME
            ).build();
        }
        return INSTANCE;
    }

}
