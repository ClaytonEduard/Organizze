package com.claytoneduard.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.claytoneduard.organizze.R;
import com.claytoneduard.organizze.activity.CadastroActivity;
import com.claytoneduard.organizze.activity.LoginActivity;
import com.claytoneduard.organizze.config.ConfiguracaoFIrebase;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //ocultando os botoes
        //setButtonBackVisible(false);
        //setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_1)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_2)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_3)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_4)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build());

    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();//verifica se possui user logado
    }

    // metodo entrar
    public void btEntrar(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    // metodo cadastrar
    public void btCadastrar(View view) {
        startActivity(new Intent(this, CadastroActivity.class));
    }

    // verificar usuario logado
    public void verificarUsuarioLogado() {
        autenticacao = ConfiguracaoFIrebase.getFirebaseAutenticacao();
        //autenticacao.signOut();
        if (autenticacao.getCurrentUser() != null) {
            abrirTelaPrincipal();
        }
    }

    // abrir a tela principal
    public void abrirTelaPrincipal() {
        startActivity(new Intent(this, PrincipalActivity.class));
    }

}