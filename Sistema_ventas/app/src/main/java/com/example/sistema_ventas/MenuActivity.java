package com.example.sistema_ventas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.sistema_ventas.activity.ClienteActivity;
import com.example.sistema_ventas.activity.ProductoActivity;
import com.example.sistema_ventas.activity.VentaActivity;
import com.example.sistema_ventas.activity.VentaHistorialActivity;
import com.example.sistema_ventas.esquemaSqlite.ConexionSqliteHelper;
import com.example.sistema_ventas.esquemaSqlite.crud.Select;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ConexionSqliteHelper con;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        con = new ConexionSqliteHelper(MenuActivity.this);
    }



    @OnClick(R.id.anIbProducto)
    public void clickProducto(){
        irActivity(ProductoActivity.class);
    }
    @OnClick(R.id.anIbCliente)
    public void clickCliente(){
        irActivity(ClienteActivity.class);
    }
    @OnClick(R.id.anIbVenta)
    public void clickVenta(){
        irActivity(VentaActivity.class);
    }
    @OnClick(R.id.anIbVentaHistorial)
    public void clickVentaHistorial(){
        irActivity(VentaHistorialActivity.class);
    }
    void irActivity(Class<?> paramClass){
        //intento para mostrar otras actividades
        Intent intent = new Intent(getApplicationContext(), paramClass);


        //limpiamos el historia de actividades
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuBackup:
                Select.backup(getApplicationContext());
                Toast.makeText(this,"Backup Correcto", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuRestore:
                Select.restaurar(getApplicationContext());
                Toast.makeText(this,"Restauración exitosa", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        con.close();
        super.onDestroy();
    }
}
