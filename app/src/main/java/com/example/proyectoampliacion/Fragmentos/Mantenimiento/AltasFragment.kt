package com.example.proyectoampliacion.Fragmentos.Mantenimiento

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_altas.*
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class AltasFragment : Fragment(), AdapterView.OnItemSelectedListener{

    var clienteSeleccionado:String? = null;
    var clienteSeleccionado2:String? = null;
    var argumento1:String = "";
    var argumento2:String = "";
    var argumento3:String = "";

    var tipoParte:String = "";
    var estadoParte:String = "";

    var tipoProducto: String ="";

    var estado:String = ""

    val URL_BASE:String = "http://192.168.1.141/symfony/web/app.php/"

    var contenido:Bundle? = null

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            contenido = activity?.intent?.extras
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

             argumento1 = it.getString("nombre").toString()
             argumento2 = it.getString("apellidos").toString()
             argumento3= it.getString("id").toString()


            when(it.getInt("tipo")){

                0 -> {
                    tvTitulo.setText("Altas Albaranes");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Albaranes";
                    construirFormAlbarán(view);
                }
                1 -> {
                    tvTitulo.setText("Altas Almacén");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Almacén";
                    construirFormAlmacen(view);
                }
                2 -> {
                    tvTitulo.setText("Altas Clientes");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Clientes";
                    construirFormClientes(view);
                }
                3 -> {
                    tvTitulo.setText("Altas Delegación");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Delegación";
                    construirFormDelegacion(view);
                }
                4 -> {
                    tvTitulo.setText("Altas Empleados");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Empleados";
                    construirFormEmpleados(view);
                }
                5 -> {
                    tvTitulo.setText("Altas Facturas");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Facturas";
                    cosntruirFormFactura(view);
                }
                6 -> {
                    tvTitulo.setText("Altas Partes");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Partes";
                    construirFormParte(view);
                }
                7 -> {
                    tvTitulo.setText("Altas Presupuestos");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Altas Presupuestos";
                    construirFormPresupuestos(view);
                }
            }

        }



    }

    class ControlDinamico(cod: Int, nombre: String) {
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

        contenedorDialog.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDialog.orientation = LinearLayout.HORIZONTAL;

        contenedorDialog.gravity= Gravity.CENTER;
        contenedor.addView(contenedorDialog);

    }

    fun construirFormPresupuestos(view: View){

        val txtFecha:EditText = EditText(this.context);
        val txtDireccion:EditText = EditText(this.context);
        val spEstado:Spinner = Spinner(this.context);
        val btnCancelar:Button = Button(this.context);
        val btnGuardar:Button = Button(this.context);
        val btnLimpiar:Button = Button(this.context);

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1, "Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2, "Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3, "Guardar")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;

        val contenedorFecha:LinearLayout = LinearLayout(this.context);
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        val contenedorEstado:LinearLayout = LinearLayout(this.context);
        contenedorEstado.orientation = LinearLayout.HORIZONTAL;
        val contenedorDireccion:LinearLayout = LinearLayout(this.context);
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones:LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        contenedorFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        contenedorDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        contenedorEstado.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorEstado.orientation = LinearLayout.HORIZONTAL;


        txtFecha.width = 800;
        txtFecha.maxLines = 1;
        txtFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDireccion.width = 800;
        txtDireccion.maxLines = 1;
        txtDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        spEstado.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        txtFecha.hint = "Introduzca Fecha";
        txtDireccion.hint = "Introduzca Dirección Instalación";

        btnCancelar.text = "Cancelar";
        btnGuardar.text = "Guardar";
        btnLimpiar.text = "Limpiar";


        contenedorBotones.gravity = Gravity.CENTER;
        contenedorBotones.addView(btnGuardar);
        contenedorBotones.addView(btnCancelar);
        contenedorBotones.addView(btnLimpiar)
        contenedorFecha.gravity= Gravity.CENTER;
        contenedorFecha.addView(txtFecha);
        contenedorEstado.gravity= Gravity.CENTER;
        contenedorEstado.addView(spEstado);
        contenedorDireccion.gravity= Gravity.CENTER;
        contenedorDireccion.addView(txtDireccion);


        contenedor.addView(contenedorDireccion);
        contenedor.addView(contenedorFecha);
        contenedor.addView(contenedorEstado)
        contenedor.addView(contenedorBotones)

        val botonLimpiar:Button = view.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {


        }
        val botonGuardar:Button = view.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {


        }
        val botonCancelar:Button = view.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {


        }
    }


    fun construirFormAlbarán(view: View){

        val txtFecha:EditText = EditText(this.context);
        val txtProveedor:EditText = EditText(this.context);
        val btnCancelar:Button = Button(this.context);
        val btnGuardar:Button = Button(this.context);
        val btnLimpiar:Button = Button(this.context);

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1, "Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2, "Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3, "Guardar")

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

        contenedorFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        contenedorProveedor.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorProveedor.orientation = LinearLayout.HORIZONTAL;


        txtFecha.hint = "Introduzca Fecha";
        txtProveedor.hint = "Introduzca Proveedor";

        txtFecha.width = 800;
        txtFecha.maxLines = 1;
        txtFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtProveedor.width = 800;
        txtProveedor.maxLines = 1;
        txtProveedor.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

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

        val botonLimpiar:Button = view.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {


        }
        val botonGuardar:Button = view.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {


        }
        val botonCancelar:Button = view.findViewById(eventoBotonCancelar.cod)

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

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1, "Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2, "Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3, "Guardar")

        val listaOpcionesTipo:List<String> = listOf("<- Seleccione una opción ->","PRODUCTO","SERVICIO");

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

        contenedorNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorNombre.orientation = LinearLayout.HORIZONTAL;
        contenedorTipo.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorTipo.orientation = LinearLayout.HORIZONTAL;
        contenedorPrecio.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorPrecio.orientation = LinearLayout.HORIZONTAL;
        contenedorStock.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorStock.orientation = LinearLayout.HORIZONTAL;
        contenedorBotones.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        txtNombre.hint = "Introduzca Nombre";
        txtPrecio.hint = "Introduzca Precio unidad";
        txtStock.hint = "Introduzca stock";

        txtNombre.width = 800;
        txtNombre.maxLines = 1;
        txtNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtPrecio.width = 800;
        txtPrecio.maxLines = 1;
        txtPrecio.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtPrecio.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL;
        txtStock.width = 800;
        txtStock.maxLines = 1;
        txtStock.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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

        contenedorNombre.setPadding(0,50,0,0);
        contenedorPrecio.setPadding(0,50,0,0);
        contenedorStock.setPadding(0,50,0,0);
        contenedorTipo.setPadding(0,50,0,0);
        contenedorBotones.setPadding(0,200,0,100);


        contenedor.addView(contenedorNombre);
        contenedor.addView(contenedorTipo);
        contenedor.addView(contenedorPrecio);
        contenedor.addView(contenedorStock);
        contenedor.addView(contenedorBotones);

        val botonLimpiar:Button = view.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {

            txtNombre.setText("");
            txtPrecio.setText("");
            txtStock.setText("");

        }
        val botonGuardar:Button = view.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {

            annadirProducto(txtNombre.text.toString(),tipoProducto,txtPrecio.text.toString(),txtStock.text.toString())
        }
        val botonCancelar:Button = view.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.menuPrincipalFragment);
        }

        val adaptadorTipo:ArrayAdapter<String> = ArrayAdapter(view.context,android.R.layout.simple_spinner_item,listaOpcionesTipo)
        spTipo.adapter = adaptadorTipo
        spTipo.onItemSelectedListener = this
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

        val eventoBotonLimpiar:ControlDinamico = ControlDinamico(1, "Limpiar")
        val eventoBotonCancelar:ControlDinamico = ControlDinamico(2, "Cancelar")
        val eventoBotonGuardar:ControlDinamico = ControlDinamico(3, "Guardar")


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

        contenedorIdentificacion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorIdentificacion.orientation = LinearLayout.HORIZONTAL;
        contenedorDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        contenedorProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorProvincia.orientation = LinearLayout.HORIZONTAL;
        contenedorCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorCiudad.orientation = LinearLayout.HORIZONTAL;
        contenedorcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorcPostal.orientation = LinearLayout.HORIZONTAL;
        contenedorBotones.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;
        contenedorEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorEmail.orientation = LinearLayout.HORIZONTAL;
        contenedorTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
        txtCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDireccion.width = 800;
        txtDireccion.maxLines = 1;
        txtDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtEmail.width = 800;
        txtEmail.maxLines = 1;
        txtEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        txtTelefono.width = 800;
        txtTelefono.maxLines = 1;
        txtTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtTelefono.inputType = InputType.TYPE_CLASS_PHONE
        txtcPostal.width = 800;
        txtcPostal.maxLines = 1;
        txtcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtcPostal.inputType = InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS
        txtIdentificacion.width = 800;
        txtIdentificacion.maxLines = 1;
        txtIdentificacion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtProvincia.width = 800;
        txtProvincia.maxLines = 1;
        txtProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


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

        val botonLimpiar:Button = view.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {

            txtCiudad.setText("");
            txtDireccion.setText("");
            txtEmail.setText("");
            txtIdentificacion.setText("");
            txtProvincia.setText("");
            txtTelefono.setText("");
            txtcPostal.setText("");

        }

        val botonGuardar:Button = view.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {

             annadirDelegacion(txtIdentificacion.text.toString(),txtDireccion.text.toString(),txtProvincia.text.toString(),txtCiudad.text.toString(),txtcPostal.text.toString(),txtEmail.text.toString(),txtTelefono.text.toString())

        }

        val botonCancelar:Button = view.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.menuPrincipalFragment);

        }
    }

    fun construirFormParte(view: View){

        val txtCliente:EditText = EditText(this.context);
        val txtFecha:EditText = EditText(this.context);
        val txtDetalles:EditText = EditText(this.context);
        val txtObservaciones:EditText = EditText(this.context);
        val slTipo:Spinner = Spinner(this.context);
        val slEstado:Spinner = Spinner(this.context);
        val btnCancelar:Button = Button(this.context);
        val btnGuardar:Button = Button(this.context);
        val btnLimpiar:Button = Button(this.context);
        val btnBuscar:Button = Button(this.context)

        val listaOpcionesEstado:List<String> = listOf("<- Seleccione una opción ->","ABIERTO","CERRADO");
        val listaOpcionesTipo:List<String> = listOf("<- Seleccione una opción ->","INSTALACIÓN","MANTENIMIENTO","AVERIA");

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


        val eventoBotonLimpiar:ControlDinamico = ControlDinamico(1, "Limpiar")
        val eventoBotonCancelar:ControlDinamico = ControlDinamico(2, "Cancelar")
        val eventoBotonGuardar:ControlDinamico = ControlDinamico(3, "Guardar")

        val eventoBotonBuscar:ControlDinamico = ControlDinamico(4, "Buscar")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;
        btnBuscar.text = eventoBotonBuscar.nombre
        btnBuscar.id =  eventoBotonBuscar.cod

        contenedorSpCliente.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorSpCliente.orientation = LinearLayout.HORIZONTAL;
        contenedorSpTipo.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorSpTipo.orientation = LinearLayout.HORIZONTAL;
        contenedorFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        contenedorDetalles.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDetalles.orientation = LinearLayout.HORIZONTAL;
        contenedorObservaciones.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorObservaciones.orientation = LinearLayout.HORIZONTAL;
        contenedorBotones.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;
        contenedorSpEstado.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorSpEstado.orientation = LinearLayout.HORIZONTAL;

        contenedorSpTipo.setPadding(0,50,0,0);
        contenedorDetalles.setPadding(0,50,0,0);
        contenedorObservaciones.setPadding(0,50,0,0);
        contenedorFecha.setPadding(0,50,0,0);
        contenedorSpEstado.setPadding(0,50,0,0);
        contenedorBotones.setPadding(0,200,0,100);

        txtFecha.hint = "Introduzca Fecha";
        txtDetalles.hint = "Introduzca los Detalles";
        txtObservaciones.hint = "Introduzca Observaciones";

        txtFecha.width = 900;
        txtFecha.maxLines = 1;
        txtFecha.inputType = InputType.TYPE_CLASS_DATETIME
        txtFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDetalles.width = 900;
        txtDetalles.maxLines = 6;
        txtDetalles.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtObservaciones.width = 900;
        txtObservaciones.maxLines = 6;
        txtObservaciones.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtCliente.width = 750;
        txtCliente.maxLines = 1;
        txtCliente.isEnabled = false;
        txtCliente.hint = "Buscar Cliente ..."


        contenedorBotones.gravity = Gravity.CENTER;
        contenedorBotones.addView(btnGuardar);
        contenedorBotones.addView(btnCancelar);
        contenedorBotones.addView(btnLimpiar)
        contenedorFecha.gravity= Gravity.CENTER;
        contenedorFecha.addView(txtFecha);
        contenedorSpCliente.gravity= Gravity.CENTER;
        contenedorSpCliente.addView(txtCliente);
        contenedorSpCliente.addView(btnBuscar);
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

        val botonLimpiar:Button = view.findViewById(eventoBotonLimpiar.cod)


        arguments?.let {



            if (argumento1.equals("null")  || argumento2.equals("null")  || argumento3.equals("null") ){

                txtCliente.setText(" ")
                clienteSeleccionado = "";
            }
            else{

                txtCliente.setText(argumento1 + " " +argumento2);
                clienteSeleccionado = argumento3;

            }

        }

        botonLimpiar.setOnClickListener {

            txtCliente.setText("");
            txtDetalles.setText("");
            txtFecha.setText("");
            txtObservaciones.setText("");

        }
        val botonGuardar:Button = view.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {

            annadirParte(clienteSeleccionado.toString(),txtFecha.text.toString(),txtDetalles.text.toString(),txtObservaciones.text.toString(),tipoParte,estadoParte)
        }
        val botonCancelar:Button = view.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.menuPrincipalFragment);
        }

        val botonBuscar:Button = view.findViewById(eventoBotonBuscar.cod)

        botonBuscar.setOnClickListener(){  vista ->

            var bundle:Bundle = Bundle()

            bundle.putInt("tipo",7)

            Navigation.findNavController(vista).navigate(R.id.consultaClienteFragment,bundle);

        }




        val adaptadorEstado:ArrayAdapter<String> = ArrayAdapter(view.context,android.R.layout.simple_spinner_item,listaOpcionesEstado)
        slEstado.adapter = adaptadorEstado
        slEstado.onItemSelectedListener = this

        val adaptadorTipo:ArrayAdapter<String> = ArrayAdapter(view.context,android.R.layout.simple_spinner_item,listaOpcionesTipo)
        slTipo.adapter = adaptadorTipo
        slTipo.onItemSelectedListener = this

    }

    fun cosntruirFormFactura(view: View){

        val txtCliente:EditText = EditText(this.context);
        val txtFecha:EditText = EditText(this.context);
        val txtPrecioSinIva:EditText = EditText(this.context);
        val txtConcepto:EditText = EditText(this.context);
        val btnCancelar:Button = Button(this.context);
        val btnGuardar:Button = Button(this.context);
        val btnLimpiar:Button = Button(this.context);
        val btnBuscar:Button = Button(this.context)

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

        val eventoBotonLimpiar:ControlDinamico = ControlDinamico(1, "Limpiar")
        val eventoBotonCancelar:ControlDinamico = ControlDinamico(2, "Cancelar")
        val eventoBotonGuardar:ControlDinamico = ControlDinamico(3, "Guardar")

        val eventoBotonBuscar:ControlDinamico = ControlDinamico(4, "Buscar")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;
        btnBuscar.text = eventoBotonBuscar.nombre
        btnBuscar.id =  eventoBotonBuscar.cod

        contenedorSpCliente.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorSpCliente.orientation = LinearLayout.HORIZONTAL;
        contenedorFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        contenedorPrecio.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorPrecio.orientation = LinearLayout.HORIZONTAL;
        contenedorConcepto.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorConcepto.orientation = LinearLayout.HORIZONTAL;

        txtFecha.hint = "Introduzca Fecha"
        txtPrecioSinIva.hint = "Introduzca Precio sin IVA"
        txtConcepto.hint = "Introduzca Concepto";


        txtFecha.width = 800;
        txtFecha.maxLines = 1;
        txtFecha.inputType = InputType.TYPE_CLASS_DATETIME
        txtFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtPrecioSinIva.width = 800;
        txtPrecioSinIva.maxLines = 1;
        txtPrecioSinIva.inputType = InputType.TYPE_CLASS_NUMBER
        txtPrecioSinIva.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtConcepto.width = 800;
        txtConcepto.maxLines = 1;
        txtConcepto.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnCancelar.text = "Cancelar"
        btnGuardar.text = "Guardar"
        btnLimpiar.text = "Limpiar"
        txtCliente.width = 750;
        txtCliente.maxLines = 1;
        txtCliente.isEnabled = false;
        txtCliente.hint = "Buscar Cliente ..."

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
        contenedorSpCliente.addView(txtCliente);
        contenedorSpCliente.addView(btnBuscar);

        contenedor.addView(contenedorSpCliente);
        contenedor.addView(contenedorFecha);
        contenedor.addView(contenedorPrecio);
        contenedor.addView(contenedorConcepto);
        contenedor.addView(contenedorBotones);

        arguments?.let {


            if (argumento1.equals("null") || argumento2.equals("null") || argumento3.equals("null")) {

                txtCliente.setText(" ")
                clienteSeleccionado = "";
            } else {

                txtCliente.setText(argumento1 + " " + argumento2);
                clienteSeleccionado = argumento3;

            }
        }

        val botonLimpiar:Button = view.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {


        }
        val botonGuardar:Button = view.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {


            clienteSeleccionado?.let { it1 -> annadirFactura(it1,txtFecha.text.toString(),txtConcepto.text.toString(),txtPrecioSinIva.text.toString(),contenido?.getString("empleado")) }

        }
        val botonCancelar:Button = view.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {


        }

        val botonBuscar:Button = view.findViewById(eventoBotonBuscar.cod)

        botonBuscar.setOnClickListener(){  vista ->

            var bundle:Bundle = Bundle()

            bundle.putInt("tipo",5)

            Navigation.findNavController(vista).navigate(R.id.consultaClienteFragment,bundle);

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
        val btnBuscar:Button = Button(this.context)
        val slDelegacion:EditText = EditText(this.context);
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

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1, "Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2, "Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3, "Guardar")

        var eventoBotonBuscar:ControlDinamico = ControlDinamico(4, "Buscar")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;

        btnBuscar.text = eventoBotonBuscar.nombre
        btnBuscar.id = eventoBotonBuscar.cod

        contenedorUsuario.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorUsuario.orientation = LinearLayout.HORIZONTAL;
        contenedorPassword.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorPassword.orientation = LinearLayout.HORIZONTAL;
        contenedorSlDelegacion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorSlDelegacion.orientation = LinearLayout.HORIZONTAL;
        contenedorNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorNombre.orientation = LinearLayout.HORIZONTAL;
        contenedorApellidos.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorApellidos.orientation = LinearLayout.HORIZONTAL;
        contenedorCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorCiudad.orientation = LinearLayout.HORIZONTAL;
        contenedorDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        contenedorDni.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDni.orientation = LinearLayout.HORIZONTAL;
        contenedorEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorEmail.orientation = LinearLayout.HORIZONTAL;
        contenedorProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorProvincia.orientation = LinearLayout.HORIZONTAL;
        contenedorTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorTelefono.orientation = LinearLayout.HORIZONTAL;
        contenedorcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorcPostal.orientation = LinearLayout.HORIZONTAL;
        contenedorRol.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
        txtPassword.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtUsuario.width = 800;
        txtUsuario.maxLines = 1;
        txtUsuario.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtNombre.width = 800;
        txtNombre.maxLines = 1;
        txtNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtApellidos.width= 800;
        txtApellidos.maxLines = 1
        txtApellidos.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtCiudad.width= 800;
        txtCiudad.maxLines = 1
        txtCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDireccion.width= 800;
        txtDireccion.maxLines = 1
        txtDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtEmail.width= 800;
        txtEmail.maxLines = 1
        txtEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtProvincia.width= 800;
        txtProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtcPostal.width= 800;
        txtcPostal.maxLines = 1
        txtcPostal.inputType = InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS;
        txtcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtTelefono.width= 800
        txtTelefono.maxLines = 1
        txtTelefono.inputType = InputType.TYPE_CLASS_PHONE
        txtTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDni.width= 800;
        txtDni.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtNacimiento.width= 800;
        txtNacimiento.maxLines = 1
        txtNacimiento.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtNacimiento.inputType = InputType.TYPE_CLASS_DATETIME;
        slDelegacion.width = 750
        slDelegacion.maxLines = 1
        slDelegacion.isEnabled = false
        slDelegacion.hint = "Buscar Delegación ..."



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
        contenedorSlDelegacion.addView(btnBuscar);
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

        contenedorUsuario.setPadding(0,50,0,0);
        contenedorSlDelegacion.setPadding(0,50,0,0);
        contenedorPassword.setPadding(0,50,0,0);
        contenedorRol.setPadding(0,150,0,0);
        contenedorNombre.setPadding(0,50,0,0);
        contenedorApellidos.setPadding(0,50,0,0);
        contenedorCiudad.setPadding(0,50,0,0);
        contenedorDireccion.setPadding(0,50,0,0);
        contenedorDni.setPadding(0,50,0,0);
        contenedorEmail.setPadding(0,50,0,0);
        contenedorProvincia.setPadding(0,50,0,0);
        contenedorcPostal.setPadding(0,50,0,0);
        contenedorTelefono.setPadding(0,50,0,0);
        contenedorNacimiento.setPadding(0,50,0,0);
        contenedorBotones.setPadding(0,200,0,100);


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
        contenedor.addView(contenedorSlDelegacion);
        contenedor.addView(contenedorRol);
        contenedor.addView(contenedorBotones);

        val botonLimpiar:Button = view.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {


        }
        val botonGuardar:Button = view.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {


        }
        val botonCancelar:Button = view.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {


        }
    }

    fun construirFormClientes(view: View){

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

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1, "Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2, "Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3, "Guardar")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;


        cbEstado.text = "Alta";

        contenedorNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorNombre.orientation = LinearLayout.HORIZONTAL;
        contenedorApellidos.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorApellidos.orientation = LinearLayout.HORIZONTAL;
        contenedorCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorCiudad.orientation = LinearLayout.HORIZONTAL;
        contenedorDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        contenedorDni.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDni.orientation = LinearLayout.HORIZONTAL;
        contenedorEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorEmail.orientation = LinearLayout.HORIZONTAL;
        contenedorProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorProvincia.orientation = LinearLayout.HORIZONTAL;
        contenedorTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorTelefono.orientation = LinearLayout.HORIZONTAL;
        contenedorcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorcPostal.orientation = LinearLayout.HORIZONTAL;
        contenedorEstado.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
        txtNombre.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtApellidos.width= 800;
        txtApellidos.maxLines = 1
        txtApellidos.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtCiudad.width= 800;
        txtCiudad.maxLines = 1
        txtCiudad.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDireccion.width= 800;
        txtDireccion.maxLines = 1
        txtDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtEmail.width= 800;
        txtEmail.maxLines = 1
        txtEmail.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtProvincia.width= 800;
        txtProvincia.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtcPostal.width= 800;
        txtcPostal.maxLines = 1
        txtcPostal.inputType = InputType.TYPE_CLASS_NUMBER;
        txtcPostal.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtTelefono.width= 800
        txtTelefono.maxLines = 1
        txtTelefono.inputType = InputType.TYPE_CLASS_PHONE
        txtTelefono.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDni.width= 800;
        txtDni.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtNacimiento.width= 800;
        txtNacimiento.maxLines = 1
        txtNacimiento.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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

        val botonLimpiar:Button = view.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {
            txtCiudad.setText("");
            txtDireccion.setText("");
            txtEmail.setText("");
            txtNacimiento.setText("");
            txtProvincia.setText("");
            txtTelefono.setText("");
            txtcPostal.setText("");
            txtApellidos.setText("");
            txtDni.setText("");
            txtNombre.setText("");

        }
        val botonGuardar:Button = view.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {

            if (cbEstado.isChecked){

                estado = "ALTA"
            }
            else{

                estado = "BAJA"
            }
            annadirCliente(txtNombre.text.toString(),txtApellidos.text.toString(),txtDireccion.text.toString(),txtProvincia.text.toString(),txtCiudad.text.toString(),txtcPostal.text.toString(),txtEmail.text.toString(),txtTelefono.text.toString(),txtNacimiento.text.toString(),estado,txtDni.text.toString())

        }
        val botonCancelar:Button = view.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.menuPrincipalFragment);
        }
    }


    fun annadirFactura(cliente:String,fecha:String,concepto:String,precio:String,empleado:String?){

        var JSON:MediaType =  MediaType.get("application/json; charset=utf-8")

        val jsonObject= JSONObject();

        jsonObject.put("cliente",cliente);
        jsonObject.put("fecha",fecha);
        jsonObject.put("precio",precio);
        jsonObject.put("concepto",concepto);
        jsonObject.put("empleado",empleado);  // datos del usuario logeado


        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/alta/factura")
            .post(body) //Indicated as get request
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()

            // val jsonArray = JSONObject(response.body()?.string())

            Toast.makeText(this.context,response.body()?.string().toString(),Toast.LENGTH_SHORT).show()

        }catch (e: IOException){

            Toast.makeText(this.context,e.message.toString(),Toast.LENGTH_SHORT).show()
        }
    }


    fun annadirProducto(nombre:String,tipo:String,precio:String,stock:String){

        var JSON:MediaType =  MediaType.get("application/json; charset=utf-8")

        val jsonObject= JSONObject();

        jsonObject.put("nombre",nombre);
        jsonObject.put("tipo",tipo);
        jsonObject.put("precio",precio);
        jsonObject.put("stock",stock);

        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/alta/producto")
            .post(body) //Indicated as get request
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()

            // val jsonArray = JSONObject(response.body()?.string())

            Toast.makeText(this.context,response.body()?.string().toString(),Toast.LENGTH_SHORT).show()

        }catch (e: IOException){

            Toast.makeText(this.context,e.message.toString(),Toast.LENGTH_SHORT).show()
        }
    }



    fun annadirParte(cliente:String,fecha:String,detalles:String,observaciones:String,tipo:String,estado:String){

        var JSON:MediaType =  MediaType.get("application/json; charset=utf-8")

        val jsonObject= JSONObject();

        jsonObject.put("cliente",cliente);
        jsonObject.put("fecha",fecha);
        jsonObject.put("detalle",detalles);
        jsonObject.put("observaciones",observaciones);
        jsonObject.put("tipo",tipo);
        jsonObject.put("estado",estado);
        jsonObject.put("delegacion",contenido?.getString("provincia"));  // datos del usuario logeado
        jsonObject.put("empleado",contenido?.getString("empleado"));  // datos del usuario logeado

        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/alta/parte")
            .post(body) //Indicated as get request
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()

           // val jsonArray = JSONObject(response.body()?.string())

            Toast.makeText(this.context,response.body()?.string().toString(),Toast.LENGTH_SHORT).show()

        }catch (e: IOException){

            Toast.makeText(this.context,e.message.toString(),Toast.LENGTH_SHORT).show()
        }
    }


    fun annadirDelegacion(identificacion:String,direccion:String,provincia:String,ciudad:String,postal:String,email:String,telefono:String){

        var JSON:MediaType =  MediaType.get("application/json; charset=utf-8")

        val jsonObject= JSONObject();

        jsonObject.put("identificacion",identificacion);
        jsonObject.put("direccion",direccion);
        jsonObject.put("provincia",provincia);
        jsonObject.put("ciudad",ciudad);
        jsonObject.put("cPostal",postal);
        jsonObject.put("email",email);
        jsonObject.put("telefono",telefono);

        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/alta/delegacion")
            .post(body) //Indicated as get request
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()

            val jsonArray = JSONObject(response.body()?.string())


        }catch (e: IOException){

            Toast.makeText(this.context,e.message.toString(),Toast.LENGTH_SHORT).show()

        }
    }

    fun annadirCliente(nombre:String,apellidos:String,direccion:String,provincia:String,ciudad:String,postal:String,email:String,telefono:String, nacimiento:String, estado:String ,dni:String){

        var JSON:MediaType =  MediaType.get("application/json; charset=utf-8")

        val jsonObject= JSONObject();

        jsonObject.put("nombre",nombre);
        jsonObject.put("apellidos",apellidos);
        jsonObject.put("direccion",direccion);
        jsonObject.put("provincia",provincia);
        jsonObject.put("nacimiento",nacimiento);
        jsonObject.put("ciudad",ciudad);
        jsonObject.put("cPostal",postal);
        jsonObject.put("email",email);
        jsonObject.put("telefono",telefono);
        jsonObject.put("estado",estado);
        jsonObject.put("dni",dni);


        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/alta/cliente")
            .post(body) //Indicated as get request
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()

            //val jsonArray = JSONObject()

            Toast.makeText(this.context,response.body()?.string().toString(),Toast.LENGTH_LONG).show()

        }catch (e: IOException){

            Toast.makeText(this.context,e.message.toString(),Toast.LENGTH_SHORT).show()

        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val opcion:String = parent?.getItemAtPosition(position).toString()

        when(opcion){

            "INSTALACIÓN" ->{

                tipoParte = "INSTALACIÓN"

            }
            "MANTENIMIENTO" ->{

                tipoParte = "MANTENIMIENTO"
            }
            "AVERIA"->{

                tipoParte = "AVERIA"
            }
            "ABIERTO"->{

                estadoParte = "ABIERTO"
            }
            "CERRADO"->{
                estadoParte = "CERRADO"
            }
            "PRODUCTO"->{

                tipoProducto = "PRODUCTO"
            }
            "SERVICIO"->{

                tipoProducto = "SERVICIO"
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}