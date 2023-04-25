package br.ufpe.cin.residencia.banco.uteis;// Uso:
// Declarar um objeto TextWatcher
// O valor do TextWatcher é o retorno da função Mask.insert, com dois parametros: string com o formato da mascara e a caixa de texto que irá receber a mascara
// ou o retorno da função Mask.monetario, apenas com a caixa de texto que será receberá o valor monetario
// Adicionar na caixa de texto o evento TextWatcher
// Ex:
// TextWatcher cpfMask = Mask.insert("###.###.###-##, editCpf);
// cpfMask.addTextChangedListener(editCpf)
// TextWatcher salarioMask = Mask.monetario(editSalario);
// salarioMask.addTextChangedListener(editSalario);

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.Locale;

public abstract class MonetaryMaskTextWatcher {

    // Método estático para remover a máscara de uma string monetária
    public static String unmask(String s) {
        return s.replaceAll("[R$]", "").replace(".", "")
                .replace(",", ".").replaceAll("[\\s]", "");
    }

    // Método estático para criar um TextWatcher para formatar um valor monetário em um EditText
    public static TextWatcher monetario(final EditText ediTxt) {
        return new TextWatcher() {
            // Variável para guardar o valor formatado atual
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            // Método chamado quando o texto é alterado
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    ediTxt.removeTextChangedListener(this);

                    // Remove caracteres de formatação
                    String cleanString = s.toString().replaceAll("[R$,.\\s]", "");

                    // Converte para double
                    double parsed = Double.parseDouble(cleanString);
                    // Formata como moeda
                    String formatted = NumberFormat.getCurrencyInstance(
                            new Locale("pt", "BR")).format((parsed / 100));

                    current = formatted.replaceAll("[R$\\s]", "");// Remove o símbolo de moeda
                    ediTxt.setText("R$ " + current);// Define o texto formatado
                    ediTxt.setSelection(ediTxt.getText().length());// Define a posição do cursor

                    ediTxt.addTextChangedListener(this);
                }
            }

        };
    }
}