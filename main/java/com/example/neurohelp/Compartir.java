package com.example.neurohelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Compartir extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;
    TextView nombreUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setCheckedItem(R.id.nav_share);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUser.reload();
        View header = navigationView.getHeaderView(0);
        nombreUsuario=header.findViewById(R.id.nombreUsuario);
        nombreUsuario.setText(currentUser.getDisplayName().toString());
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            Intent intent=new Intent(getApplicationContext(),MenuPrincipal.class);
            setResult(RESULT_OK,intent);
            finish();
        } else if (id == R.id.nav_juegos) {
            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.DefaultCompany.Arquitecturatarjeta");
            startActivityForResult(LaunchIntent,2);

        } else if (id == R.id.nav_configuracion) {
            Intent intent=new Intent(getApplicationContext(),Configuracion.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_faq) {
            Intent intent=new Intent(getApplicationContext(),PreguntasFrecuentes.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_about) {
            Intent intent=new Intent(getApplicationContext(),AcercaDe.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_logout) {
            mAuth = FirebaseAuth.getInstance();
            if(mAuth.getCurrentUser()!=null){
                mAuth.signOut();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        drawerLayout.closeDrawers();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickCompartirOtros(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "¡Ayuda a tus hijos con Neurohelp! http://ladespensadelahuertacharra.c1.biz/");
        startActivity(Intent.createChooser(intent, "Share with"));
    }
    public void onClickCompartirFacebook(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "¡Ayuda a tus hijos con Neurohelp! http://ladespensadelahuertacharra.c1.biz/");
        intent.setPackage("com.facebook.katana");
        startActivity(intent);
    }
    public void onClickCompartirWhatsApp(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "¡Ayuda a tus hijos con Neurohelp! http://ladespensadelahuertacharra.c1.biz/");
        intent.setPackage("com.whatsapp");
        startActivity(intent);
    }
    public void onClickCompartirTwitter(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "¡Ayuda a tus hijos con Neurohelp! http://ladespensadelahuertacharra.c1.biz/");
        intent.setPackage("com.twitter.android");
        startActivity(intent);
    }
    public void onClickCompartirCorreo(View view){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"NeuroHelp");
        emailIntent.putExtra(Intent.EXTRA_TEXT,"¡Ayuda a tus hijos con Neurohelp! http://ladespensadelahuertacharra.c1.biz/");

        try {
            view.getContext().startActivity(Intent.createChooser(emailIntent, "Compartir"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,"Error en el envío",Toast.LENGTH_LONG).show();
        }
    }
    public void onClickJuegos(View view){
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.DefaultCompany.Arquitecturatarjeta");
        finish();
    }
}