/*
 * Copyright (c) 2023.
 * Clayton Eduard F Chaves
 */

package com.claytoneduard.organizze.model;

import com.claytoneduard.organizze.config.ConfiguracaoFIrebase;
import com.claytoneduard.organizze.helper.Base64Custon;
import com.claytoneduard.organizze.helper.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Movimentacao {

    private String data;
    private String categoria;
    private String descricao;
    private String tipo;
    private double valor;


    public Movimentacao() {
    }

    public void salvar(String dataEscolhida) {

        //recuperar o email do user logado
        FirebaseAuth auth = ConfiguracaoFIrebase.getFirebaseAutenticacao();
        //captura o user logado
        String idUsuario = Base64Custon.codificarBase64(auth.getCurrentUser().getEmail());
        //passar mes ano
        String mesAno = DateCustom.mesAnoDataEscolhida(dataEscolhida);

        DatabaseReference reference = ConfiguracaoFIrebase.getFirebaseDatabase();
        reference.child("movimentacao")
                .child(idUsuario)
                .child(mesAno)
                .push()
                .setValue(this);
    }



    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
