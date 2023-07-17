/*
 * Copyright (c) 2023.
 * Clayton Eduard F Chaves
 */

package com.claytoneduard.organizze.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.claytoneduard.organizze.R;
import com.claytoneduard.organizze.config.ConfiguracaoFIrebase;
import com.claytoneduard.organizze.databinding.ActivityPrincipalBinding;
import com.claytoneduard.organizze.helper.Base64Custon;
import com.claytoneduard.organizze.model.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;

public class PrincipalActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityPrincipalBinding binding;

    private MaterialCalendarView calendarView;
    private TextView textoSaudacao, textoSaldo;
    private Double despesaTotal = 0.0;
    private Double receitaTotal = 0.0;
    private Double resumoUsuario = 0.0;
    private FirebaseAuth autenticacao = ConfiguracaoFIrebase.getFirebaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFIrebase.getFirebaseDatabase();
    private DatabaseReference userRef;
    private ValueEventListener valueEventListenerUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        calendarView = findViewById(R.id.calendarView);
        configuraCalendarView();

        textoSaudacao = findViewById(R.id.textSaldacao);
        textoSaldo = findViewById(R.id.textSaldo);

    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarResumo();
    }

    // metodo recuperar dados no usuario
    public void recuperarResumo() {
        String emailUser = autenticacao.getCurrentUser().getEmail();
        String idUser = Base64Custon.codificarBase64(emailUser);
        userRef = firebaseRef.child("usuarios").child(idUser);

        Log.i("Evento", "evento foi adicionado!");
        valueEventListenerUsuario = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // recuperar dados do usuarios
                Usuario usuario = snapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();
                receitaTotal = usuario.getReceitaTotal();
                resumoUsuario = receitaTotal - despesaTotal;

                // formatar os dados para exibir o saldo
                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                String resutadoFormatado = decimalFormat.format(resumoUsuario);
                textoSaudacao.setText("Olá, " + usuario.getNome());
                textoSaldo.setText("R$ " + resutadoFormatado);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configuraCalendarView() {
        CharSequence meses[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        calendarView.setTitleMonths(meses);

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSair:
                autenticacao.signOut();
                // desloggar o usuario e enviar para tela principal
                startActivity(new Intent(this, MainActivity.class));
                // finalizar a activity princial
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void adicionarReceita(View view) {
        startActivity(new Intent(this, ReceitasActivity.class));
    }

    public void adicionarDespesa(View view) {
        startActivity(new Intent(this, DespesasActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Evento", "evento foi removido!");
        userRef.removeEventListener(valueEventListenerUsuario);
    }
}