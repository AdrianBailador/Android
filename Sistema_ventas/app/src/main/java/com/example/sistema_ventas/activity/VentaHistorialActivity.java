package com.example.sistema_ventas.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.sistema_ventas.R;
import com.example.sistema_ventas.adaptador.VentaHistorialItemRecycler;
import com.example.sistema_ventas.adaptador.VentaItemRecycler;
import com.example.sistema_ventas.data.modelo.Cliente;
import com.example.sistema_ventas.data.modelo.Producto;
import com.example.sistema_ventas.data.modelo.ProductoVenta;
import com.example.sistema_ventas.data.modelo.VentaCabecera;
import com.example.sistema_ventas.data.preferencia.SessionPreferences;
import com.example.sistema_ventas.data.util.Mensaje;
import com.example.sistema_ventas.data.util.Metodos;
import com.example.sistema_ventas.esquemaSqlite.crud.Insert;
import com.example.sistema_ventas.esquemaSqlite.crud.Select;
import com.example.sistema_ventas.esquemaSqlite.tablas.VentaCabeceraTabla;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VentaHistorialActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.avhRvProducto)
    RecyclerView recyclerView;


    RecyclerView.LayoutManager layoutManager;
    VentaHistorialItemRecycler adaptador;



    @BindView(R.id.avhEtBuscarCliente)
    EditText buscador;



    List<VentaCabecera> ventaCabeceraLista = new ArrayList<>();


    String fecha = "";
    int anio, mes, dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_historial);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //ocultar teclado al iniciar la acivity
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //cargamos el layout con la actividad
        layoutManager = new LinearLayoutManager(getApplicationContext());

       

        filtrarVentas();

        calendarioCargarVariables();

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarVentas();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @OnClick(R.id.avhFabBuscarVenta)
    void clickBuscarVenta(){
        new DatePickerDialog(this,calendarioFlotante,anio,mes,dia).show();
    }

    private void calendarioCargarVariables() {
        Calendar calendar = Calendar.getInstance();
        anio = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        fecha = Metodos.getFecha();
    }

    private DatePickerDialog.OnDateSetListener calendarioFlotante = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            anio = year;
            mes = month;
            dia = dayOfMonth;
            fecha = Metodos.concatenar(new Object[]{anio, "-" , String.format("%02d",(mes + 1)), "-" , String.format("%02d", dia) });
            filtrarVentas();
        }
    };

    private void filtrarVentas() {
        Select.seleccionarVentaCabecera(getApplicationContext(),ventaCabeceraLista,fecha,buscador.getText().toString());
        cargarRecycler(ventaCabeceraLista);
    }




    private void cargarRecycler(List<VentaCabecera> ventaCabeceraLista) {

        adaptador = new VentaHistorialItemRecycler(ventaCabeceraLista, new VentaHistorialItemRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(VentaCabecera ventaCabecera, int position) {
                irActivity(ventaCabecera);

            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptador);



    }
    void irActivity(VentaCabecera ventaCabecera){
        Intent intent = new Intent(getApplicationContext(), VentaHistorialDetalleActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("itemVentaCabecera", ventaCabecera);

        startActivityForResult(intent,1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            filtrarVentas();
        }
    }
}

