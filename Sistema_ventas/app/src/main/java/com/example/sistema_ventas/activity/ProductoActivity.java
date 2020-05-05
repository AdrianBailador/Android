package com.example.sistema_ventas.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;


import androidx.appcompat.widget.Toolbar;

import com.example.sistema_ventas.R;
import com.example.sistema_ventas.adaptador.ProductoItemRecycler;
import com.example.sistema_ventas.data.modelo.Producto;
import com.example.sistema_ventas.esquemaSqlite.crud.Select;

import java.util.ArrayList;
import java.util.List;

public class ProductoActivity extends AppCompatActivity {

    @BindView(R.id.apRvProducto)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.apEtBuscarProducto)
    EditText buscador;

    RecyclerView.LayoutManager layoutManager;
    ProductoItemRecycler adapter;

    List<Producto> listaProducto = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);


        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //ocultar teclado

        layoutManager = new LinearLayoutManager(getApplicationContext());
        cargarLista();

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cargarLista();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }



    private void cargarLista(){
        Select.seleccionarProductos(getApplicationContext(),listaProducto,buscador.getText().toString());
        cargarRecycler(listaProducto);
    }

    private void cargarRecycler(List<Producto> listaProducto) {
        adapter = new ProductoItemRecycler(listaProducto, new ProductoItemRecycler.OnItemClickListener() {
            @Override
            public void OnClickItem(Producto producto, int posicion) {
                irActivity(false,producto);

            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.apFabNuevoProducto)
    public void clickNuevoProducto(){
        irActivity(true,new Producto());
    }


    void irActivity(boolean bNuevo, Producto itemProducto){
        Intent intent = new Intent(getApplicationContext(), ProductoDetalleActivity.class);

        intent.putExtra("bNuevo", bNuevo);
        intent.putExtra("itemProducto", itemProducto);

        startActivityForResult(intent,1);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) cargarLista();
    }
}
