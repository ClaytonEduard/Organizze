package com.claytoneduard.organizze.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFIrebase {

    private static FirebaseAuth autenticacao;

    //retorna a instancia do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao() {
        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

}
