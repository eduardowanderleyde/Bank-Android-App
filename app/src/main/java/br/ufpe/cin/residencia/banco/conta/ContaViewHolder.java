package br.ufpe.cin.residencia.banco.conta;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.Locale;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class ContaViewHolder extends RecyclerView.ViewHolder {
    TextView nomeCliente = null; // TextView para exibir o nome do cliente
    TextView infoConta = null;// TextView para exibir informações da conta
    ImageView icone = null;// ImageView para exibir um ícone relacionado à conta

    public ContaViewHolder(@NonNull View linha) {
        super(linha);
        // Inicializa a TextView do nome do cliente
        this.nomeCliente = linha.findViewById(R.id.nomeCliente);

        // Inicializa a TextView de informações da conta
        this.infoConta = linha.findViewById(R.id.infoConta);

        // Inicializa a ImageView do ícone
        this.icone = linha.findViewById(R.id.icone);
    }

    void bindTo(Conta c) {
        // Utiliza um StringBuilder para otimizar processamento e memória ao construir uma string
        StringBuilder sb = new StringBuilder();
        sb.append(c.numero)
                .append(" | ")
                .append("Saldo atual: ")
                // Adiciona o saldo formatado em moeda brasileira
                .append(NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))
                        .format(c.saldo));

        // Define o nome do cliente na TextView correspondente
        this.nomeCliente.setText(c.nomeCliente);

        // Define as informações da conta na TextView correspondente usando a string gerada pelo StringBuilder
        this.infoConta.setText(sb.toString());

        // Define o ícone como "delete" se o saldo for negativo
        if (c.saldo < 0) {
            icone.setImageResource(R.drawable.delete);
        }

        // Adiciona um listener de clique no item da lista
        this.addListener(c.numero);
    }

    public void addListener(String numeroConta) {
        this.itemView.setOnClickListener(
                v -> {
                    Context c = this.itemView.getContext();
                    Intent i = new Intent(c, EditarContaActivity.class);
                    // Utiliza a função putExtra da Intent para enviar o número
                    // da conta como informação extra para a outra Activity
                    i.putExtra("numeroConta", String.valueOf(numeroConta));
                    c.startActivity(i); // Inicia a Activity de edição da conta
                }
        );
    }
}
