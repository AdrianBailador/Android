package com.example.sistema_ventas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.sistema_ventas.R;
import com.example.sistema_ventas.adaptador.VentaHistorialItemProductoRecycler;
import com.example.sistema_ventas.adaptador.VentaHistorialItemRecycler;
import com.example.sistema_ventas.data.modelo.VentaCabecera;
import com.example.sistema_ventas.data.modelo.VentaDetalle;
import com.example.sistema_ventas.data.util.Mensaje;
import com.example.sistema_ventas.esquemaSqlite.crud.Delete;
import com.example.sistema_ventas.esquemaSqlite.crud.Select;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class VentaHistorialDetalleActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.avhdActvCliente)
    TextInputEditText cliente;

    @BindView(R.id.avhdActvFecha)
    TextInputEditText fecha;

    @BindView(R.id.avhdTietObservacion)
    TextInputEditText observacion;

    @BindView(R.id.avhdRvProductosSeleccionados)
    RecyclerView recyclerView;

    @BindView(R.id.avhdTietTotal)
    TextInputEditText total;



    RecyclerView.LayoutManager layoutManager;
   VentaHistorialItemProductoRecycler adaptador;

   List<VentaDetalle> listaDetalle = new ArrayList<>();


   boolean bCancelado = false;
   VentaCabecera ventaCabecera;



    @BindView(R.id.avhEtBuscarCliente)
    EditText buscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_historial_detalle);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());

        if(getIntent().hasExtra("itemVentaCabecera")){
            ventaCabecera = (VentaCabecera)getIntent().getSerializableExtra("itemVentaCabecera");
            cliente.setText(ventaCabecera.getClie_nombre());
            fecha.setText(ventaCabecera.getVc_fecha().concat(" ").concat(ventaCabecera.getVc_hora()));
            observacion.setText(ventaCabecera.getVc_comentario());

        }
        cargarDetalle();
    }

    @OnClick(R.id.avhdBCancelar)
    void onClickCancelar(){
        new AlertDialog.Builder(this).setTitle("Ventas realizadas").setMessage("¿Deseas cancelar esta venta?").setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton(android.R.string.yes, (dialog, which) -> {
            Delete.eliminarVenta(getApplicationContext(), listaDetalle, ventaCabecera.getVc_id());
            new Mensaje(getApplicationContext()).mensajeToas("Venta cancelada");
            bCancelado = true;
            salirActivity();

        }).setNegativeButton(android.R.string.no,null).show();
    }


    private void cargarDetalle(){
        Select.seleccionarVentaDetalle(getApplicationContext(), listaDetalle, ventaCabecera.getVc_id());
        cargarRecycler();
    }

    private void cargarRecycler() {
        adaptador = new VentaHistorialItemProductoRecycler(listaDetalle,total);


        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptador);
        adaptador.sumarItems();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
               salirActivity();
               return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        salirActivity();
    }

     void salirActivity() {
        if(bCancelado){
            setResult(Activity.RESULT_OK, new Intent());
        }
        finish();
    }
}
