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

import android.view.WindowManager;
import android.widget.EditText;


import androidx.appcompat.widget.Toolbar;

import com.example.sistema_ventas.R;
import com.example.sistema_ventas.adaptador.ClienteItemRecycler;

import com.example.sistema_ventas.data.modelo.Cliente;

import com.example.sistema_ventas.esquemaSqlite.crud.Select;

import java.util.ArrayList;
import java.util.List;

public class ClienteActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.acRvCliente)
    RecyclerView recyclerView;


    @BindView(R.id.acEtBuscarCliente)
    EditText buscador;

    RecyclerView.LayoutManager layoutManager;
    ClienteItemRecycler adaptador;

    List<Cliente> listaCliente = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);


        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //ocultar teclado

        layoutManager = new LinearLayoutManager(getApplicationContext());
        cargarLista();

        buscador.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cargarLista();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.acFabNuevoCliente)
    public void clickNuevoCliente() {
        irActivity(true, new Cliente());
    }

    private void cargarLista() {
        Select.seleccionarClientes(getApplicationContext(), listaCliente, buscador.getText().toString());
        cargarRecycler(listaCliente);
    }

    private void cargarRecycler(List<Cliente> listaCliente) {
        adaptador = new ClienteItemRecycler(listaCliente, new ClienteItemRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(Cliente cliente, int position) {
                irActivity(false, cliente);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptador);
    }


    private void irActivity(boolean bNuevo, Cliente cliente) {
        Intent intent = new Intent(getApplicationContext(), ClienteDetalleActivity.class);

        intent.putExtra("bNuevo", bNuevo);
        intent.putExtra("itemCliente", cliente);

        startActivityForResult(intent, 1);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK) {
            cargarLista();
        }
    }
}



