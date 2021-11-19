package com.example.neurohelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.File;

public class Configuracion extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;
    TextView nombreUsuario;
    EditText usuario,contrasena,oldContrasena;
    ImageView fotoperfil;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
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
        navigationView.setCheckedItem(R.id.nav_configuracion);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUser.reload();
        View header = navigationView.getHeaderView(0);
        nombreUsuario=header.findViewById(R.id.nombreUsuario);
        usuario=findViewById(R.id.usuario);
        contrasena=findViewById(R.id.contrasena2);
        oldContrasena=findViewById(R.id.contrasena);
        nombreUsuario.setText(currentUser.getDisplayName().toString());
        usuario.setHint((currentUser.getDisplayName().toString()));

        fotoperfil=findViewById(R.id.fotoperfil);



      SharedPreferences myPrefs = getSharedPreferences("FOTOPERFIL"+currentUser.getEmail(), 0);
        SharedPreferences.Editor myPrefsEdit = myPrefs.edit();
        String imagePath = myPrefs.getString("IMAGEURI"+currentUser.getEmail(), null);
            if(imagePath!=null){
                Log.d("Se obtiene imagen",imagePath);
                fotoperfil.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            }

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

        } else if (id == R.id.nav_faq) {
            Intent intent=new Intent(getApplicationContext(),PreguntasFrecuentes.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_about) {
            Intent intent=new Intent(getApplicationContext(),AcercaDe.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_share) {
            Intent intent=new Intent(getApplicationContext(),Compartir.class);
            startActivity(intent);
            finish();
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

    public void onClickImagen(View view){
        Log.d("GALERIA","ABRIR");
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    public void onClickGuardar(View view){
        String nuevoUsuario=usuario.getText().toString();
        String nuevaContrasena=contrasena.getText().toString();
        if(nuevoUsuario!=null && !nuevoUsuario.isEmpty()){
            if(imageUri!=null){
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(nuevoUsuario)
                        .setPhotoUri(imageUri)
                        .build();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                currentUser.reload();
                currentUser.updateProfile(profileUpdates);
                nombreUsuario.setText(nuevoUsuario);
            }else{
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(nuevoUsuario)
                        .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                        .build();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                currentUser.reload();
                currentUser.updateProfile(profileUpdates);
                nombreUsuario.setText(nuevoUsuario);
            }
        }else{
            if(imageUri!=null){
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(imageUri)
                        .build();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                currentUser.reload();
                currentUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),"Foto de perfil modificada",Toast.LENGTH_LONG).show();
                            }
                        });
            }else{
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                        .build();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                currentUser.reload();
                currentUser.updateProfile(profileUpdates);
            }
        }
        if(nuevaContrasena!=null && !nuevaContrasena.isEmpty()){
            if(nuevaContrasena.length()<8){
                Toast.makeText(this,"La nueva contraseña debe contener al menos 8 caracteres",Toast.LENGTH_LONG).show();
                contrasena.setBackgroundResource(R.drawable.borderojo);
                return;
            }
            FirebaseUser currentUser = mAuth.getCurrentUser();
            AuthCredential credential = EmailAuthProvider
                    .getCredential(currentUser.getEmail(), oldContrasena.getText().toString());
            currentUser.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                currentUser.updatePassword(nuevaContrasena).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),"Contraseña actualizada, por seguridad, vuelva a entrar",Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            mAuth.signOut();
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(),"Error, contraseña no actualizada",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(),"Error de autenticación",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(),"Datos actualizados",Toast.LENGTH_LONG).show();
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            fotoperfil.setImageURI(imageUri);


            String [] proj={MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery( imageUri,
                    proj, // Which columns to return
                    null,       // WHERE clause; which rows to return (all rows)
                    null,       // WHERE clause selection arguments (none)
                    null); // Order-by clause (ascending by name)
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            String path= cursor.getString(column_index);
            Log.d("PATH NUEVA IMAGEN",path);

            FirebaseUser currentUser = mAuth.getCurrentUser();
            SharedPreferences myPrefs = getSharedPreferences("FOTOPERFIL"+currentUser.getEmail(), 0);
            SharedPreferences.Editor myPrefsEdit = myPrefs.edit();
            myPrefsEdit.putString("IMAGEURI"+currentUser.getEmail(), path);
            myPrefsEdit.commit();
        }

    }
}
