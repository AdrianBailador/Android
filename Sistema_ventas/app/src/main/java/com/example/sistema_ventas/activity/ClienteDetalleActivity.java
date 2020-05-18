package com.example.sistema_ventas.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sistema_ventas.R;
import com.example.sistema_ventas.data.modelo.Cliente;
import com.example.sistema_ventas.data.modelo.Producto;
import com.example.sistema_ventas.data.preferencia.SessionPreferences;
import com.example.sistema_ventas.data.util.Mensaje;
import com.example.sistema_ventas.esquemaSqlite.crud.Delete;
import com.example.sistema_ventas.esquemaSqlite.crud.Insert;
import com.example.sistema_ventas.esquemaSqlite.crud.Update;
import com.example.sistema_ventas.esquemaSqlite.tablas.ClienteTabla;
import com.example.sistema_ventas.esquemaSqlite.tablas.ProductoTabla;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



    public class ClienteDetalleActivity extends AppCompatActivity {


        @BindView(R.id.acdTietTel)
        EditText telefono;
        @BindView(R.id.acdTietMail)
        EditText email;
        @BindView(R.id.acdTietNombre)
        EditText nombre;
        @BindView(R.id.acdTietDireccion)
        EditText direccion;

        @BindView(R.id.toolbar)
        Toolbar toolbar;

        @BindView(R.id.acdLLAgregar)
        LinearLayout agregar;

        @BindView(R.id.acdLLModificar)
        LinearLayout modificar;

        boolean bNuevo = true, bModificado = false;

        Cliente cliente;

        Mensaje mensaje;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cliente_detalle);
            ButterKnife.bind(this);

            setSupportActionBar(toolbar);

            //Ocultar el teclado
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            //Pantalla completa, y se borre la barra superior
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Boton de retroceder

            mensaje = new Mensaje(getApplicationContext());

            //Para los intentos
            if(getIntent().hasExtra("bNuevo")){
                bNuevo = getIntent().getBooleanExtra("bNuevo",true);
            }




            agregar.setVisibility(bNuevo ? View.VISIBLE : View.INVISIBLE);
            modificar.setVisibility(!bNuevo ? View.VISIBLE : View.INVISIBLE);

            if(!bNuevo){
                cliente = (Cliente) getIntent().getSerializableExtra("itemCliente");
                cargarVista(cliente);
            }
            nombre.requestFocus();
        }



        @OnClick(R.id.acdBAgregar)
        public void clickAgregar(){

            if(nombre.getText().length()>0) {
                int codigo = SessionPreferences.get(getApplicationContext()).getCliente();

                Insert.registrar(getApplicationContext(),
                        new Cliente(
                                codigo,
                                nombre.getText().toString(),
                                telefono.getText().toString(),
                                email.getText().toString(),
                                direccion.getText().toString()),
                        ClienteTabla.TABLA);

                bModificado = true;
                mensaje.mensajeToasGuardar();
                cargarVista(new Cliente(0, "", "", "", ""));
            }else {
                mensaje.mensajeToas("Ingrese un nombre");
                nombre.requestFocus();
            }

        }

        @OnClick(R.id.acdBModificar)
        public void clickModificar(){

            if(nombre.getText().length()>0) {
                Update.actualizar(getApplicationContext(),


                        new Cliente(
                                cliente.getClie_id(),
                                nombre.getText().toString(),
                                telefono.getText().toString(),
                                email.getText().toString(),
                                direccion.getText().toString()),
                        ClienteTabla.TABLA);

                bModificado = true;
                mensaje.mensajeToasGuardar();
                salirActivity();
            }else{
                mensaje.mensajeToas("Ingrese un nombre");
                nombre.requestFocus();
            }
        }


        @OnClick(R.id.acdBEliminar)
        public void clickEliminar(){
            new AlertDialog.Builder(this).setTitle("Cliente").setMessage("¿Desea eliminar el cliente?").setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Delete.eliminar(getApplicationContext(),cliente.getClie_id(),ClienteTabla.TABLA);
                            bModificado = true;
                            mensaje.mensajeToasEliminar();
                            salirActivity();

                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        }





        private void cargarVista(Cliente cliente) {

            nombre.setText(cliente.getClie_nombre());
            telefono.setText(cliente.getClie_num_tel());
            email.setText(cliente.getClie_email());
            direccion.setText(cliente.getClie_direccion());
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case android.R.id.home:
                    salirActivity();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        @Override
        public void onBackPressed() {
            salirActivity();
        }

        void salirActivity(){
            if(bModificado){
                //Intent intent = new Intent();
                setResult(Activity.RESULT_OK,new Intent());
            }
            finish();
        }




    }



