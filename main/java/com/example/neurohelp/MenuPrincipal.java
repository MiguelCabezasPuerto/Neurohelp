package com.example.neurohelp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MenuPrincipal extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(getApplicationContext(),"Bienvenido/a"+mAuth.getCurrentUser().getDisplayName(),Toast.LENGTH_LONG).show();
    }

    public void onClickLogout(View view){
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            mAuth.signOut();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void onClickPreguntasFrecuentes(View view){
        Intent intent=new Intent(getApplicationContext(),PreguntasFrecuentes.class);
        startActivityForResult(intent,2);
    }
    public void onClickConfiguracion(View view){
        Intent intent=new Intent(getApplicationContext(),Configuracion.class);
        startActivityForResult(intent,2);
    }
    public void onClickCompartir(View view){
        Intent intent=new Intent(getApplicationContext(),Compartir.class);
        startActivityForResult(intent,2);
    }
    public void onClickAcercade(View view){
        Intent intent=new Intent(getApplicationContext(),AcercaDe.class);
        startActivityForResult(intent,2);
    }
    public void onClickJuegos(View view){
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.DefaultCompany.Arquitecturatarjeta");
        startActivityForResult(LaunchIntent,2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}