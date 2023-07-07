package com.claytoneduard.organizze.model;
/*
 * Copyright (c) 2023.
 * Clayton Eduard F Chaves
 */

import com.claytoneduard.organizze.config.ConfiguracaoFIrebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {

    private String idUsuario;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {
    }

    public String getNome() {
        return nome;
    }

    //metodo salva user
    public void salvar() {
        DatabaseReference firebase = ConfiguracaoFIrebase.getFirebaseDatabase();
        firebase.child("usuarios")
                .child(this.idUsuario)
                .setValue(this);// seta os dados do usuario
    }
    @Exclude // exclui o dado para salvar no firebase
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude // exclui o dado para salvar no firebase
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
