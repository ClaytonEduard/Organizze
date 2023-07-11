/*
 * Copyright (c) 2023.
 * Clayton Eduard F Chaves
 */

package com.claytoneduard.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.claytoneduard.organizze.R;
import com.claytoneduard.organizze.config.ConfiguracaoFIrebase;
import com.claytoneduard.organizze.helper.Base64Custon;
import com.claytoneduard.organizze.helper.DateCustom;
import com.claytoneduard.organizze.model.Movimentacao;
import com.claytoneduard.organizze.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ReceitasActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFIrebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFIrebase.getFirebaseAutenticacao();
    private Double receitaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        campoValor = findViewById(R.id.editValor);
        campoData = findViewById(R.id.editData);
        campoCategoria = findViewById(R.id.editCategoria);
        campoDescricao = findViewById(R.id.editDescricao);

        //preenche o campo data com a date atual
        campoData.setText(DateCustom.dataAtual());
        recuperarReceitaTotal();
    }


    public Boolean validarCamposReceita() {
        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();

        // verificação se houve digitação
        if (!textoValor.isEmpty()) {
            if (!textoData.isEmpty()) {
                if (!textoCategoria.isEmpty()) {
                    if (!textoDescricao.isEmpty()) {
                        return true;
                    } else {
                        Toast.makeText(ReceitasActivity.this,
                                "Descrição não foi preenchida",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(ReceitasActivity.this,
                            "Categoria não foi preenchida",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(ReceitasActivity.this,
                        "Data não foi preenchida",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(ReceitasActivity.this,
                    "Valor não foi preenchido",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public void salvarReceita(View view) {

        if (validarCamposReceita()) {
            movimentacao = new Movimentacao();
            String data = campoData.getText().toString();
            Double valorRecuperado = Double.parseDouble(campoValor.getText().toString());

            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("r");

            //atualizar a despesa
            Double receitaAutalizada = receitaTotal + valorRecuperado;
            atualizarReceita(receitaAutalizada);
            movimentacao.salvar(data);
            Toast.makeText(this, "Receita Salva com sucesso", Toast.LENGTH_SHORT).show();
            limparCampos();
        }
    }

    // recuperar receita;
    public void recuperarReceitaTotal() {

        String emailUser = autenticacao.getCurrentUser().getEmail();
        String idUser = Base64Custon.codificarBase64(emailUser);
        DatabaseReference userRef = firebaseRef.child("usuarios").child(idUser);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void atualizarReceita(Double receita) {
        String emailUser = autenticacao.getCurrentUser().getEmail();
        String idUser = Base64Custon.codificarBase64(emailUser);
        DatabaseReference userRef = firebaseRef.child("usuarios").child(idUser);
        userRef.child("receitaTotal").setValue(receita);
    }

    public void limparCampos() {
        campoData.setText(DateCustom.dataAtual());
        campoValor.setText("R$00.00");
        campoCategoria.setText("");
        campoDescricao.setText("");
    }

}