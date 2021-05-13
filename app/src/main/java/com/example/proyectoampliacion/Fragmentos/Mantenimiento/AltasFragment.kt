package com.example.proyectoampliacion.Fragmentos.Mantenimiento

import android.app.ActionBar
import android.os.Bundle
import android.service.controls.Control
import android.text.InputType
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.fragment_altas.*


class AltasFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_altas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {



            when(it.getInt("tipo")){

                0->{
                    tvTitulo.setText("Altas Albaranes");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Albaranes";
                    construirFormAlbarán(view);
                }
                1->{
                    tvTitulo.setText("Altas Almacén");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Almacén";
                    construirFormAlmacen(view);
                }
                2->{
                    tvTitulo.setText("Altas Clientes");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Clientes";
                    construirFormClientes();
                }
                3->{
                    tvTitulo.setText("Altas Delegación");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Delegación";
                    construirFormDelegacion(view);
                }
                4->{
                    tvTitulo.setText("Altas Empleados");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Empleados";
                    construirFormEmpleados(view);
                }
                5->{
                    tvTitulo.setText("Altas Facturas");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Facturas";
                    cosntruirFormFactura(view);
                }
                6->{
                    tvTitulo.setText("Altas Partes");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Partes";
                    construirFormParte(view);
                }
                7->{
                    tvTitulo.setText("Altas Presupuestos");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Presupuestos";
                }
            }

        }



    }

    class ControlDinamico( cod:Int , nombre:String) {
        var cod: Int
        var nombre: String

        init {
            this.nombre = nombre
            this.cod = cod
        }
    }

    fun enviarDatosServidor(){

        val progreso:ProgressBar = ProgressBar(this.context)
        progreso.isVisible = false

        val contenedorDialog:LinearLayout = LinearLayout(this.context);

        contenedorDialog.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDialog.orientation = LinearLayout.HORIZONTAL;

        contenedorDialog.gravity= Gravity.CENTER;
        contenedor.addView(contenedorDialog);

    }


    fun construirFormAlbarán( view: View){

        val txtFecha:EditText = EditText(this.context);
        val txtProveedor:EditText = EditText(this.context);
        val btnCancelar:Button = Button(this.context);
        val btnGuardar:Button = Button(this.context);
        val btnLimpiar:Button = Button(this.context);

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1,"Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2,"Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3,"Guardad")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;

        val contenedorFecha:LinearLayout = LinearLayout(this.context);
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        val contenedorProveedor:LinearLayout = LinearLayout(this.context);
        contenedorProveedor.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones:LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        contenedorFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        contenedorProveedor.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorProveedor.orientation = LinearLayout.HORIZONTAL;


        txtFecha.hint = "Introduzca Fecha";
        txtProveedor.hint = "Introduzca Proveedor";

        txtFecha.width = 800;
        txtFecha.maxLines = 1;
        txtFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtProveedor.width = 800;
        txtProveedor.maxLines = 1;
        txtProveedor.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        btnCancelar.text = "Cancelar";
        btnGuardar.text = "Guardar";
        btnLimpiar.text = "Limpiar";


        contenedorBotones.gravity = Gravity.CENTER;
        contenedorBotones.addView(btnGuardar);
        contenedorBotones.addView(btnCancelar);
        contenedorBotones.addView(btnLimpiar)
        contenedorFecha.gravity= Gravity.CENTER;
        contenedorFecha.addView(txtFecha);
        contenedorProveedor.gravity= Gravity.CENTER;
        contenedorProveedor.addView(txtProveedor);





        contenedor.addView(contenedorFecha);
        contenedor.addView(contenedorProveedor);
        contenedor.addView(contenedorBotones)

        val botonLimpiar:Button = view?.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {


        }
        val botonGuardar:Button = view?.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {


        }
        val botonCancelar:Button = view?.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {


        }
    }

    fun construirFormAlmacen(view: View){

        val txtNombre:EditText = EditText(this.context);
        val spTipo:Spinner = Spinner(this.context);
        val txtPrecio:EditText = EditText(this.context);
        val txtStock:EditText = EditText(this.context);
        val btnCancelar:Button = Button(this.context);
        val btnGuardar:Button = Button(this.context);
        val btnLimpiar:Button = Button(this.context);

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1,"Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2,"Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3,"Guardad")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;

        val contenedorNombre:LinearLayout = LinearLayout(this.context);
        contenedorNombre.orientation = LinearLayout.HORIZONTAL;
        val contenedorTipo:LinearLayout = LinearLayout(this.context);
        contenedorTipo.orientation = LinearLayout.HORIZONTAL;
        val contenedorPrecio:LinearLayout = LinearLayout(this.context);
        contenedorPrecio.orientation = LinearLayout.HORIZONTAL;
        val contenedorStock:LinearLayout = LinearLayout(this.context);
        contenedorStock.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones:LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        contenedorNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorNombre.orientation = LinearLayout.HORIZONTAL;
        contenedorTipo.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorTipo.orientation = LinearLayout.HORIZONTAL;
        contenedorPrecio.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorPrecio.orientation = LinearLayout.HORIZONTAL;
        contenedorStock.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorStock.orientation = LinearLayout.HORIZONTAL;
        contenedorBotones.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        txtNombre.hint = "Introduzca Nombre";
        txtPrecio.hint = "Introduzca Precio unidad";
        txtStock.hint = "Introduzca stock";

        txtNombre.width = 800;
        txtNombre.maxLines = 1;
        txtNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtPrecio.width = 800;
        txtPrecio.maxLines = 1;
        txtPrecio.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtPrecio.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL;
        txtStock.width = 800;
        txtStock.maxLines = 1;
        txtStock.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtStock.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL;


        btnCancelar.text = "Cancelar";
        btnGuardar.text = "Guardar";
        btnLimpiar.text = "Limpiar";


        contenedorBotones.gravity = Gravity.CENTER;
        contenedorBotones.addView(btnGuardar);
        contenedorBotones.addView(btnCancelar);
        contenedorBotones.addView(btnLimpiar)
        contenedorNombre.gravity= Gravity.CENTER;
        contenedorNombre.addView(txtNombre);
        contenedorTipo.gravity= Gravity.CENTER;
        contenedorTipo.addView(spTipo);
        contenedorPrecio.gravity= Gravity.CENTER;
        contenedorPrecio.addView(txtPrecio);
        contenedorStock.gravity= Gravity.CENTER;
        contenedorStock.addView(txtStock);

        contenedor.addView(contenedorNombre);
        contenedor.addView(contenedorTipo);
        contenedor.addView(contenedorPrecio);
        contenedor.addView(contenedorStock);
        contenedor.addView(contenedorBotones);

        val botonLimpiar:Button = view?.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {


        }
        val botonGuardar:Button = view?.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {


        }
        val botonCancelar:Button = view?.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {


        }
    }

    fun construirFormDelegacion(view: View){

        val txtIdentificacion:EditText = EditText(this.context);
        val txtDireccion:EditText = EditText(this.context);
        val txtProvincia:EditText = EditText(this.context);
        val txtCiudad:EditText = EditText(this.context);
        val txtcPostal:EditText = EditText(this.context);
        val txtEmail:EditText = EditText(this.context);
        val txtTelefono:EditText = EditText(this.context);
        val btnCancelar:Button = Button(this.context);
        val btnGuardar:Button = Button(this.context);
        val btnLimpiar:Button = Button(this.context);

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1,"Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2,"Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3,"Guardad")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;


        val contenedorIdentificacion:LinearLayout = LinearLayout(this.context);
        contenedorIdentificacion.orientation = LinearLayout.HORIZONTAL;
        val contenedorDireccion:LinearLayout = LinearLayout(this.context);
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        val contenedorProvincia:LinearLayout = LinearLayout(this.context);
        contenedorProvincia.orientation = LinearLayout.HORIZONTAL;
        val contenedorCiudad:LinearLayout = LinearLayout(this.context);
        contenedorCiudad.orientation = LinearLayout.HORIZONTAL;
        val contenedorcPostal:LinearLayout = LinearLayout(this.context);
        contenedorcPostal.orientation = LinearLayout.HORIZONTAL;
        val contenedorEmail:LinearLayout = LinearLayout(this.context);
        contenedorEmail.orientation = LinearLayout.HORIZONTAL;
        val contenedorTelefono:LinearLayout = LinearLayout(this.context);
        contenedorTelefono.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones:LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        contenedorIdentificacion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorIdentificacion.orientation = LinearLayout.HORIZONTAL;
        contenedorDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        contenedorProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorProvincia.orientation = LinearLayout.HORIZONTAL;
        contenedorCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorCiudad.orientation = LinearLayout.HORIZONTAL;
        contenedorcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorcPostal.orientation = LinearLayout.HORIZONTAL;
        contenedorBotones.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;
        contenedorEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorEmail.orientation = LinearLayout.HORIZONTAL;
        contenedorTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorTelefono.orientation = LinearLayout.HORIZONTAL;


        txtCiudad.hint = "Introduzca Ciudad";
        txtDireccion.hint = "Introduzca la Dirección";
        txtEmail.hint = "Introduzca Email";
        txtProvincia.hint = "Introduzca la Provincia";
        txtTelefono.hint = "Introduzca el Teléfono";
        txtcPostal.hint = "Introduzca el Codigo Postal";
        txtIdentificacion.hint = "Introduzca Identificación";

        txtCiudad.width = 800;
        txtCiudad.maxLines = 1;
        txtCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDireccion.width = 800;
        txtDireccion.maxLines = 1;
        txtDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtEmail.width = 800;
        txtEmail.maxLines = 1;
        txtEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        txtTelefono.width = 800;
        txtTelefono.maxLines = 1;
        txtTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtTelefono.inputType = InputType.TYPE_CLASS_PHONE
        txtcPostal.width = 800;
        txtcPostal.maxLines = 1;
        txtcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtcPostal.inputType = InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS
        txtIdentificacion.width = 800;
        txtIdentificacion.maxLines = 1;
        txtIdentificacion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtProvincia.width = 800;
        txtProvincia.maxLines = 1;
        txtProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));


        btnCancelar.text = "Cancelar";
        btnGuardar.text = "Guardar";
        btnLimpiar.text = "Limpiar";

        contenedorBotones.gravity = Gravity.CENTER;
        contenedorBotones.addView(btnGuardar);
        contenedorBotones.addView(btnCancelar);
        contenedorBotones.addView(btnLimpiar)
        contenedorIdentificacion.gravity= Gravity.CENTER;
        contenedorIdentificacion.addView(txtIdentificacion);
        contenedorDireccion.gravity= Gravity.CENTER;
        contenedorDireccion.addView(txtDireccion);
        contenedorProvincia.gravity= Gravity.CENTER;
        contenedorProvincia.addView(txtProvincia);
        contenedorCiudad.gravity= Gravity.CENTER;
        contenedorCiudad.addView(txtCiudad);
        contenedorcPostal.gravity= Gravity.CENTER;
        contenedorcPostal.addView(txtcPostal);
        contenedorEmail.gravity= Gravity.CENTER;
        contenedorEmail.addView(txtEmail);
        contenedorTelefono.gravity= Gravity.CENTER;
        contenedorTelefono.addView(txtTelefono);


        contenedor.addView(contenedorIdentificacion);
        contenedor.addView(contenedorDireccion);
        contenedor.addView(contenedorCiudad);
        contenedor.addView(contenedorProvincia);
        contenedor.addView(contenedorcPostal);
        contenedor.addView(contenedorTelefono);
        contenedor.addView(contenedorEmail);
        contenedor.addView(contenedorBotones);

        val botonLimpiar:Button = view?.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {


        }
        val botonGuardar:Button = view?.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {


        }
        val botonCancelar:Button = view?.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {


        }
    }

    fun construirFormParte(view: View){

        val slCliente:Spinner = Spinner(this.context);
        val txtFecha:EditText = EditText(this.context);
        val txtDetalles:EditText = EditText(this.context);
        val txtObservaciones:EditText = EditText(this.context);
        val slTipo:Spinner = Spinner(this.context);
        val slEstado:Spinner = Spinner(this.context);
        val btnCancelar:Button = Button(this.context);
        val btnGuardar:Button = Button(this.context);
        val btnLimpiar:Button = Button(this.context);

        val contenedorSpTipo:LinearLayout = LinearLayout(this.context);
        contenedorSpTipo.orientation = LinearLayout.HORIZONTAL;
        val contenedorFecha:LinearLayout = LinearLayout(this.context);
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        val contenedorSpEstado:LinearLayout = LinearLayout(this.context);
        contenedorSpEstado.orientation = LinearLayout.HORIZONTAL;
        val contenedorSpCliente:LinearLayout = LinearLayout(this.context);
        contenedorSpCliente.orientation = LinearLayout.HORIZONTAL;
        val contenedorDetalles:LinearLayout = LinearLayout(this.context);
        contenedorDetalles.orientation = LinearLayout.HORIZONTAL;
        val contenedorObservaciones:LinearLayout = LinearLayout(this.context);
        contenedorObservaciones.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones:LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;


        contenedorSpCliente.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorSpCliente.orientation = LinearLayout.HORIZONTAL;
        contenedorSpTipo.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorSpTipo.orientation = LinearLayout.HORIZONTAL;
        contenedorFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        contenedorDetalles.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDetalles.orientation = LinearLayout.HORIZONTAL;
        contenedorObservaciones.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorObservaciones.orientation = LinearLayout.HORIZONTAL;
        contenedorBotones.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;
        contenedorSpEstado.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorSpEstado.orientation = LinearLayout.HORIZONTAL;

        txtFecha.hint = "Introduzca Fecha";
        txtDetalles.hint = "Introduzca los Detalles";
        txtObservaciones.hint = "Introduzca Observaciones";

        txtFecha.width = 800;
        txtFecha.maxLines = 1;
        txtFecha.inputType = InputType.TYPE_CLASS_DATETIME
        txtFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDetalles.width = 800;
        txtDetalles.maxLines = 6;
        txtDetalles.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtObservaciones.width = 800;
        txtObservaciones.maxLines = 6;
        txtObservaciones.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        btnCancelar.text = "Cancelar";
        btnGuardar.text = "Guardar";
        btnLimpiar.text = "Limpiar";


        contenedorBotones.gravity = Gravity.CENTER;
        contenedorBotones.addView(btnGuardar);
        contenedorBotones.addView(btnCancelar);
        contenedorBotones.addView(btnLimpiar)
        contenedorFecha.gravity= Gravity.CENTER;
        contenedorFecha.addView(txtFecha);
        contenedorSpCliente.gravity= Gravity.CENTER;
        contenedorSpCliente.addView(slCliente);
        contenedorSpEstado.gravity= Gravity.CENTER;
        contenedorSpEstado.addView(slEstado);
        contenedorSpTipo.gravity= Gravity.CENTER;
        contenedorSpTipo.addView(slTipo);
        contenedorDetalles.gravity= Gravity.CENTER;
        contenedorDetalles.addView(txtDetalles);
        contenedorObservaciones.gravity= Gravity.CENTER;
        contenedorObservaciones.addView(txtObservaciones);

        contenedor.addView(contenedorSpCliente);
        contenedor.addView(contenedorDetalles);
        contenedor.addView(contenedorSpTipo);
        contenedor.addView(contenedorObservaciones);
        contenedor.addView(contenedorFecha);
        contenedor.addView(contenedorSpEstado);
        contenedor.addView(contenedorBotones);

    }

    fun cosntruirFormFactura(view: View){

        val slCliente:Spinner = Spinner(this.context);
        val txtFecha:EditText = EditText(this.context);
        val txtPrecioSinIva:EditText = EditText(this.context);
        val txtConcepto:EditText = EditText(this.context);
        val btnCancelar:Button = Button(this.context);
        val btnGuardar:Button = Button(this.context);
        val btnLimpiar:Button = Button(this.context);

        val contenedorSpCliente:LinearLayout = LinearLayout(this.context);
        contenedorSpCliente.orientation = LinearLayout.HORIZONTAL;
        val contenedorFecha:LinearLayout = LinearLayout(this.context);
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        val contenedorPrecio:LinearLayout = LinearLayout(this.context);
        contenedorPrecio.orientation = LinearLayout.HORIZONTAL;
        val contenedorConcepto:LinearLayout = LinearLayout(this.context);
        contenedorConcepto.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones:LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1,"Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2,"Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3,"Guardad")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;

        contenedorSpCliente.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorSpCliente.orientation = LinearLayout.HORIZONTAL;
        contenedorFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        contenedorPrecio.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorPrecio.orientation = LinearLayout.HORIZONTAL;
        contenedorConcepto.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorConcepto.orientation = LinearLayout.HORIZONTAL;

        txtFecha.hint = "Introduzca Fecha"
        txtPrecioSinIva.hint = "Introduzca Precio sin IVA"
        txtConcepto.hint = "Introduzca Concepto";


        txtFecha.width = 800;
        txtFecha.maxLines = 1;
        txtFecha.inputType = InputType.TYPE_CLASS_DATETIME
        txtFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtPrecioSinIva.width = 800;
        txtPrecioSinIva.maxLines = 1;
        txtPrecioSinIva.inputType = InputType.TYPE_CLASS_NUMBER
        txtPrecioSinIva.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtConcepto.width = 800;
        txtConcepto.maxLines = 1;
        txtConcepto.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        btnCancelar.text = "Cancelar"
        btnGuardar.text = "Guardar"
        btnLimpiar.text = "Limpiar"


        contenedorBotones.gravity = Gravity.CENTER
        contenedorBotones.addView(btnGuardar);
        contenedorBotones.addView(btnCancelar);
        contenedorBotones.addView(btnLimpiar)
        contenedorFecha.gravity= Gravity.CENTER;
        contenedorFecha.addView(txtFecha);
        contenedorPrecio.gravity= Gravity.CENTER;
        contenedorPrecio.addView(txtPrecioSinIva);
        contenedorConcepto.gravity= Gravity.CENTER;
        contenedorConcepto.addView(txtConcepto);
        contenedorSpCliente.gravity= Gravity.CENTER;
        contenedorSpCliente.addView(slCliente);

        contenedor.addView(contenedorSpCliente);
        contenedor.addView(contenedorFecha);
        contenedor.addView(contenedorPrecio);
        contenedor.addView(contenedorConcepto);
        contenedor.addView(contenedorBotones);

        val botonLimpiar:Button = view?.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {


        }
        val botonGuardar:Button = view?.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {


        }
        val botonCancelar:Button = view?.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {


        }
    }

    fun construirFormEmpleados(view: View){

        val txtUsuario:EditText = EditText(this.context);
        val txtPassword:EditText = EditText(this.context);
        val txtNombre:EditText = EditText(this.context);
        val txtApellidos:EditText = EditText(this.context);
        val txtCiudad:EditText = EditText(this.context);
        val txtcPostal:EditText = EditText(this.context);
        val txtDni:EditText = EditText(this.context);
        val txtEmail:EditText = EditText(this.context);
        val txtTelefono:EditText = EditText(this.context);
        val txtNacimiento:EditText = EditText(this.context);
        val txtDireccion:EditText = EditText(this.context);
        val txtProvincia:EditText = EditText(this.context);
        val btnCancelar:Button = Button(this.context);
        val btnGuardar:Button = Button(this.context);
        val btnLimpiar:Button = Button(this.context);
        val slDelegacion:Spinner = Spinner(this.context);
        val cbAdministrador:CheckBox= CheckBox(this.context);
        val cbInstalador:CheckBox= CheckBox(this.context);
        val cbGestor:CheckBox= CheckBox(this.context);


        val contenedorUsuario:LinearLayout = LinearLayout(this.context);
        contenedorUsuario.orientation = LinearLayout.HORIZONTAL;
        val contenedorPassword:LinearLayout = LinearLayout(this.context);
        contenedorPassword.orientation = LinearLayout.HORIZONTAL;
        val contenedorSlDelegacion:LinearLayout = LinearLayout(this.context);
        contenedorSlDelegacion.orientation = LinearLayout.HORIZONTAL;
        val contenedorNombre:LinearLayout = LinearLayout(this.context);
        contenedorNombre.orientation = LinearLayout.HORIZONTAL;
        val contenedorApellidos:LinearLayout = LinearLayout(this.context);
        contenedorApellidos.orientation = LinearLayout.HORIZONTAL;
        val contenedorCiudad:LinearLayout = LinearLayout(this.context);
        contenedorCiudad.orientation = LinearLayout.HORIZONTAL;
        val contenedorcPostal:LinearLayout = LinearLayout(this.context);
        contenedorcPostal.orientation = LinearLayout.HORIZONTAL;
        val contenedorDni:LinearLayout = LinearLayout(this.context);
        contenedorDni.orientation = LinearLayout.HORIZONTAL;
        val contenedorEmail:LinearLayout = LinearLayout(this.context);
        contenedorEmail.orientation = LinearLayout.HORIZONTAL;
        val contenedorTelefono:LinearLayout = LinearLayout(this.context);
        contenedorTelefono.orientation = LinearLayout.HORIZONTAL;
        val contenedorNacimiento:LinearLayout = LinearLayout(this.context);
        contenedorNacimiento.orientation = LinearLayout.HORIZONTAL;
        val contenedorDireccion:LinearLayout = LinearLayout(this.context)
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        val contenedorProvincia:LinearLayout = LinearLayout(this.context);
        contenedorProvincia.orientation = LinearLayout.HORIZONTAL;
        val contenedorRol:LinearLayout = LinearLayout(this.context);
        contenedorRol.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones:LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1,"Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2,"Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3,"Guardad")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;

        contenedorUsuario.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorUsuario.orientation = LinearLayout.HORIZONTAL;
        contenedorPassword.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorPassword.orientation = LinearLayout.HORIZONTAL;
        contenedorSlDelegacion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorSlDelegacion.orientation = LinearLayout.HORIZONTAL;
        contenedorNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorNombre.orientation = LinearLayout.HORIZONTAL;
        contenedorApellidos.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorApellidos.orientation = LinearLayout.HORIZONTAL;
        contenedorCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorCiudad.orientation = LinearLayout.HORIZONTAL;
        contenedorDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        contenedorDni.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDni.orientation = LinearLayout.HORIZONTAL;
        contenedorEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorEmail.orientation = LinearLayout.HORIZONTAL;
        contenedorProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorProvincia.orientation = LinearLayout.HORIZONTAL;
        contenedorTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorTelefono.orientation = LinearLayout.HORIZONTAL;
        contenedorcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorcPostal.orientation = LinearLayout.HORIZONTAL;
        contenedorRol.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorRol.orientation = LinearLayout.HORIZONTAL;

        txtUsuario.hint = "Introduzca Usuario"
        txtPassword.hint = "Introduzca Contraseña"
        txtNombre.hint = "Introduzca Nombre";
        txtApellidos.hint = "Introduzca Apellidos";
        txtcPostal.hint = "Introduzca Codigo Postal ";
        txtDireccion.hint = "Introduzca Dirección"
        txtCiudad.hint = "Introduzca Ciudad";
        txtProvincia.hint = "Introduzca Provincia";
        txtEmail.hint = "Introduzca  Email"
        txtTelefono.hint = "Introduzca Teléfono";
        txtDni.hint = "Introduzca D.N.I";
        txtNacimiento.hint = "Introduza Fecha de Nacimiento";

        txtPassword.width = 800;
        txtPassword.maxLines = 1;
        txtPassword.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        txtPassword.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtUsuario.width = 800;
        txtUsuario.maxLines = 1;
        txtUsuario.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtNombre.width = 800;
        txtNombre.maxLines = 1;
        txtNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtApellidos.width= 800;
        txtApellidos.maxLines = 1
        txtApellidos.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtCiudad.width= 800;
        txtCiudad.maxLines = 1
        txtCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDireccion.width= 800;
        txtDireccion.maxLines = 1
        txtDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtEmail.width= 800;
        txtEmail.maxLines = 1
        txtEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtProvincia.width= 800;
        txtProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtcPostal.width= 800;
        txtcPostal.maxLines = 1
        txtcPostal.inputType = InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS;
        txtcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtTelefono.width= 800
        txtTelefono.maxLines = 1
        txtTelefono.inputType = InputType.TYPE_CLASS_PHONE
        txtTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDni.width= 800;
        txtDni.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtNacimiento.width= 800;
        txtNacimiento.maxLines = 1
        txtNacimiento.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtNacimiento.inputType = InputType.TYPE_CLASS_DATETIME;

        btnCancelar.text = "Cancelar"
        btnGuardar.text = "Guardar"
        btnLimpiar.text = "Limpiar"

        cbAdministrador.text = "Administrador";
        cbGestor.text = "Gestor"
        cbInstalador.text = "Instalador"


        contenedorBotones.gravity = Gravity.CENTER
        contenedorBotones.addView(btnGuardar);
        contenedorBotones.addView(btnCancelar);
        contenedorBotones.addView(btnLimpiar)

        contenedorUsuario.gravity= Gravity.CENTER;
        contenedorUsuario.addView(txtUsuario);
        contenedorSlDelegacion.gravity= Gravity.CENTER;
        contenedorSlDelegacion.addView(slDelegacion);
        contenedorPassword.gravity= Gravity.CENTER;
        contenedorPassword.addView(txtPassword);
        contenedorRol.gravity= Gravity.CENTER;
        contenedorRol.addView(cbAdministrador);
        contenedorRol.addView(cbGestor);
        contenedorRol.addView(cbInstalador);
        contenedorNombre.gravity= Gravity.CENTER;
        contenedorNombre.addView(txtNombre);
        contenedorApellidos.gravity= Gravity.CENTER;
        contenedorApellidos.addView(txtApellidos);
        contenedorCiudad.gravity= Gravity.CENTER;
        contenedorCiudad.addView(txtCiudad);
        contenedorDireccion.gravity= Gravity.CENTER;
        contenedorDireccion.addView(txtDireccion);
        contenedorDni.gravity= Gravity.CENTER;
        contenedorDni.addView(txtDni);
        contenedorEmail.gravity= Gravity.CENTER;
        contenedorEmail.addView(txtEmail);
        contenedorProvincia.gravity= Gravity.CENTER;
        contenedorProvincia.addView(txtProvincia);
        contenedorcPostal.gravity= Gravity.CENTER;
        contenedorcPostal.addView(txtcPostal);
        contenedorTelefono.gravity= Gravity.CENTER;
        contenedorTelefono.addView(txtTelefono);
        contenedorNacimiento.gravity= Gravity.CENTER;
        contenedorNacimiento.addView(txtNacimiento);


        contenedor.addView(contenedorUsuario);
        contenedor.addView(contenedorPassword);
        contenedor.addView(contenedorNombre);
        contenedor.addView(contenedorApellidos);
        contenedor.addView(contenedorDireccion);
        contenedor.addView(contenedorCiudad);
        contenedor.addView(contenedorProvincia);
        contenedor.addView(contenedorEmail);
        contenedor.addView(contenedorTelefono);
        contenedor.addView(contenedorcPostal);
        contenedor.addView(contenedorDni);
        contenedor.addView(contenedorNacimiento);
        contenedor.addView(contenedorRol);
        contenedor.addView(contenedorSlDelegacion);
        contenedor.addView(contenedorBotones);

        val botonLimpiar:Button = view?.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {


        }
        val botonGuardar:Button = view?.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {


        }
        val botonCancelar:Button = view?.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {


        }
    }

    fun construirFormClientes(){

        val txtNombre:EditText = EditText(this.context);
        val txtApellidos:EditText = EditText(this.context);
        val txtCiudad:EditText = EditText(this.context);
        val txtcPostal:EditText = EditText(this.context);
        val txtDni:EditText = EditText(this.context);
        val txtEmail:EditText = EditText(this.context);
        val txtTelefono:EditText = EditText(this.context);
        val txtNacimiento:EditText = EditText(this.context);
        val txtDireccion:EditText = EditText(this.context);
        val txtProvincia:EditText = EditText(this.context);
        val cbEstado:CheckBox= CheckBox(this.context);
        val btnCancelar:Button = Button(this.context);
        val btnGuardar:Button = Button(this.context);
        val btnLimpiar:Button = Button(this.context);

        val contenedorNombre:LinearLayout = LinearLayout(this.context);
        contenedorNombre.orientation = LinearLayout.HORIZONTAL;
        val contenedorApellidos:LinearLayout = LinearLayout(this.context);
        contenedorApellidos.orientation = LinearLayout.HORIZONTAL;
        val contenedorCiudad:LinearLayout = LinearLayout(this.context);
        contenedorCiudad.orientation = LinearLayout.HORIZONTAL;
        val contenedorcPostal:LinearLayout = LinearLayout(this.context);
        contenedorcPostal.orientation = LinearLayout.HORIZONTAL;
        val contenedorDni:LinearLayout = LinearLayout(this.context);
        contenedorDni.orientation = LinearLayout.HORIZONTAL;
        val contenedorEmail:LinearLayout = LinearLayout(this.context);
        contenedorEmail.orientation = LinearLayout.HORIZONTAL;
        val contenedorTelefono:LinearLayout = LinearLayout(this.context);
        contenedorTelefono.orientation = LinearLayout.HORIZONTAL;
        val contenedorNacimiento:LinearLayout = LinearLayout(this.context);
        contenedorNacimiento.orientation = LinearLayout.HORIZONTAL;
        val contenedorDireccion:LinearLayout = LinearLayout(this.context)
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        val contenedorProvincia:LinearLayout = LinearLayout(this.context);
        contenedorProvincia.orientation = LinearLayout.HORIZONTAL;
        val contenedorEstado:LinearLayout = LinearLayout(this.context);
        contenedorEstado.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones:LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        cbEstado.text = "Alta";

        contenedorNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorNombre.orientation = LinearLayout.HORIZONTAL;
        contenedorApellidos.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorApellidos.orientation = LinearLayout.HORIZONTAL;
        contenedorCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorCiudad.orientation = LinearLayout.HORIZONTAL;
        contenedorDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        contenedorDni.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDni.orientation = LinearLayout.HORIZONTAL;
        contenedorEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorEmail.orientation = LinearLayout.HORIZONTAL;
        contenedorProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorProvincia.orientation = LinearLayout.HORIZONTAL;
        contenedorTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorTelefono.orientation = LinearLayout.HORIZONTAL;
        contenedorcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorcPostal.orientation = LinearLayout.HORIZONTAL;
        contenedorEstado.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorEstado.orientation = LinearLayout.HORIZONTAL;

        txtNombre.hint = "Introduzca Nombre";
        txtApellidos.hint = "Introduzca Apellidos";
        txtcPostal.hint = "Introduzca Codigo Postal ";
        txtDireccion.hint = "Introduzca Dirección"
        txtCiudad.hint = "Introduzca Ciudad";
        txtProvincia.hint = "Introduzca Provincia";
        txtEmail.hint = "Introduzca  Email"
        txtTelefono.hint = "Introduzca Teléfono";
        txtDni.hint = "Introduzca D.N.I";
        txtNacimiento.hint = "Introduza Fecha de Nacimiento";


        txtNombre.width = 800;
        txtNombre.maxLines = 1;
        txtNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtApellidos.width= 800;
        txtApellidos.maxLines = 1
        txtApellidos.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtCiudad.width= 800;
        txtCiudad.maxLines = 1
        txtCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDireccion.width= 800;
        txtDireccion.maxLines = 1
        txtDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtEmail.width= 800;
        txtEmail.maxLines = 1
        txtEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtProvincia.width= 800;
        txtProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtcPostal.width= 800;
        txtcPostal.maxLines = 1
        txtcPostal.inputType = InputType.TYPE_CLASS_NUMBER;
        txtcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtTelefono.width= 800
        txtTelefono.maxLines = 1
        txtTelefono.inputType = InputType.TYPE_CLASS_PHONE
        txtTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDni.width= 800;
        txtDni.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtNacimiento.width= 800;
        txtNacimiento.maxLines = 1
        txtNacimiento.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtNacimiento.inputType = InputType.TYPE_CLASS_DATETIME;

        btnCancelar.text = "Cancelar"
        btnGuardar.text = "Guardar"
        btnLimpiar.text = "Limpiar"

        contenedorBotones.gravity = Gravity.CENTER
        contenedorBotones.addView(btnGuardar);
        contenedorBotones.addView(btnCancelar);
        contenedorBotones.addView(btnLimpiar)

        contenedorEstado.gravity= Gravity.CENTER;
        contenedorEstado.addView(cbEstado);
        contenedorNombre.gravity= Gravity.CENTER;
        contenedorNombre.addView(txtNombre);
        contenedorApellidos.gravity= Gravity.CENTER;
        contenedorApellidos.addView(txtApellidos);
        contenedorCiudad.gravity= Gravity.CENTER;
        contenedorCiudad.addView(txtCiudad);
        contenedorDireccion.gravity= Gravity.CENTER;
        contenedorDireccion.addView(txtDireccion);
        contenedorDni.gravity= Gravity.CENTER;
        contenedorDni.addView(txtDni);
        contenedorEmail.gravity= Gravity.CENTER;
        contenedorEmail.addView(txtEmail);
        contenedorProvincia.gravity= Gravity.CENTER;
        contenedorProvincia.addView(txtProvincia);
        contenedorcPostal.gravity= Gravity.CENTER;
        contenedorcPostal.addView(txtcPostal);
        contenedorTelefono.gravity= Gravity.CENTER;
        contenedorTelefono.addView(txtTelefono);
        contenedorNacimiento.gravity= Gravity.CENTER;
        contenedorNacimiento.addView(txtNacimiento);

        contenedor.addView(contenedorNombre);
        contenedor.addView(contenedorApellidos);
        contenedor.addView(contenedorDireccion);
        contenedor.addView(contenedorCiudad);
        contenedor.addView(contenedorProvincia);
        contenedor.addView(contenedorEmail);
        contenedor.addView(contenedorTelefono);
        contenedor.addView(contenedorcPostal);
        contenedor.addView(contenedorDni);
        contenedor.addView(contenedorNacimiento);
        contenedor.addView(contenedorEstado);
        contenedor.addView(contenedorBotones);

    }
}