package br.ufpe.cin.residencia.banco.conta;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//ESTA CLASSE NAO PRECISA SER MODIFICADA!
@Entity(tableName = "contas")
public class Conta {
    @PrimaryKey
    @NonNull
    public String numero;
    public double saldo;
    @NonNull
    public String nomeCliente;
    @NonNull
    public String cpfCliente;

    public Conta(@NonNull String numero, double saldo, @NonNull String nomeCliente, @NonNull String cpfCliente) {
        this.numero = numero;
        this.saldo = saldo;
        this.nomeCliente = nomeCliente;
        this.cpfCliente = cpfCliente;
    }
}
