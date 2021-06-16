package com.example.proyectoampliacion.Fragmentos.Mantenimiento

import android.app.ActionBar
import android.os.Bundle
import android.os.StrictMode
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import com.example.proyectoampliacion.Classes_Auxiliares.*
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.fragment_altas.*
import kotlinx.android.synthetic.main.fragment_listar.*
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.ExecutionException


class ModificarFragment : Fragment(), AdapterView.OnItemSelectedListener {

    val URL_BASE:String = "http://192.168.1.141/symfony/web/app.php/"
    var elemento:Int = 0
    var delegacionSeleccionada:Int = 0

    var argumento1:String = "";
    var argumento2:String = "";
    var argumento3:String = "";
    var argumento4:String = "";
    var argumento5:String = "";

    var tipoParte:String = "";
    var estadoParte:String = "";
    var tipoProducto: String ="";
    var estadoPrespuesto:String = "";

    var empleadoAlbaran = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modificar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {

            elemento = it.getInt("elemento")

            argumento1 = it.getString("nombre").toString()
            argumento2 = it.getString("apellidos").toString()
            argumento3= it.getString("id").toString()
            argumento4 = it.getString("idDe").toString()
            argumento5 = it.getString("buffer").toString()

            when(it.getInt("tipo_formulario")){

                0->{
                    tvTitulo.setText("Modificar Albaranes");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Modificar Albaranes";
                    construirFormAlbarán(view);
                }
                1->{
                    tvTitulo.setText("Modificar Almacén");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Modificar Almacén";
                    construirFormAlmacen(view);
                }
                2->{
                    tvTitulo.setText("Modificar Clientes");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Modificar Clientes";
                    construirFormClientes(view);
                }
                3->{
                    tvTitulo.setText("Modificar Delegación");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Modificar Delegación";
                    construirFormDelegacion(view);
                }
                4->{
                    tvTitulo.setText("Modificar Empleados");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Modificar Empleados";
                    construirFormEmpleados(view);
                }
                5->{
                    tvTitulo.setText("Modificar Facturas");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Modificar Facturas";
                    cosntruirFormFactura(view);
                }
                6->{
                    tvTitulo.setText("Modificar Partes");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Modificar Partes";
                    construirFormParte(view);
                }
                7->{
                    tvTitulo.setText("Modificar Presupuestos");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Modificar Presupuestos";
                    construirFormPresupuestos(view);
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

    fun construirFormPresupuestos(view: View){

        var presupuesto = obtenerDatosVolleyPresupuestos(view,elemento)

        val txtFecha: EditText = EditText(this.context);
        val txtDireccion: EditText = EditText(this.context);
        val spEstado: Spinner = Spinner(this.context);
        val btnCancelar: Button = Button(this.context);
        val btnGuardar: Button = Button(this.context);
        val btnLimpiar: Button = Button(this.context);

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1,"Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2,"Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3,"Guardar")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;

        val listaOpcionesTipo:List<String> = listOf("<- Seleccione una opción ->","EN TRAMITE","CERRADO");

        val contenedorFecha: LinearLayout = LinearLayout(this.context);
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        val contenedorEstado: LinearLayout = LinearLayout(this.context);
        contenedorEstado.orientation = LinearLayout.HORIZONTAL;
        val contenedorDireccion: LinearLayout = LinearLayout(this.context);
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones: LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        contenedorFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        contenedorDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        contenedorEstado.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorEstado.orientation = LinearLayout.HORIZONTAL;


        txtFecha.width = 800;
        txtFecha.maxLines = 1;
        txtFecha.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        txtDireccion.width = 800;
        txtDireccion.maxLines = 1;
        txtDireccion.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        spEstado.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));


        txtFecha.hint = "Introduzca Fecha";
        txtDireccion.hint = "Introduzca Dirección Instalación";


        contenedorFecha.setPadding(0,50,0,0);
        contenedorEstado.setPadding(0,50,0,0);
        contenedorDireccion.setPadding(0,50,0,0);
        contenedorBotones.setPadding(0,200,0,100);


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


        txtFecha.setText(presupuesto[0].fecha)
        txtDireccion.setText(presupuesto[0].instalacion)


        val botonLimpiar: Button = view?.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {

            txtFecha.setText("")
            txtDireccion.setText("")
            spEstado.setSelection(0)

        }
        val botonGuardar: Button = view?.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {


        }
        val botonCancelar: Button = view?.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {


        }

        if (presupuesto[0].estado.toBoolean()){

            spEstado.setSelection(1)

        }else{

            spEstado.setSelection(2)
        }


        val adaptadorTipo:ArrayAdapter<String> = ArrayAdapter(view.context,android.R.layout.simple_spinner_item,listaOpcionesTipo)
        spEstado.adapter = adaptadorTipo
        spEstado.onItemSelectedListener = this

    }


    fun construirFormAlbarán( view: View){

        var albaran = obtenerDatosVolleyAlbaran(view,elemento)

        val txtFecha: EditText = EditText(this.context);
        val txtProveedor: EditText = EditText(this.context);
        val btnCancelar: Button = Button(this.context);
        val btnGuardar: Button = Button(this.context);
        val btnLimpiar: Button = Button(this.context);

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1,"Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2,"Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3,"Guardar")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;

        val contenedorFecha: LinearLayout = LinearLayout(this.context);
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        val contenedorProveedor: LinearLayout = LinearLayout(this.context);
        contenedorProveedor.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones: LinearLayout = LinearLayout(this.context);
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


        contenedorFecha.setPadding(0,50,0,0);
        contenedorProveedor.setPadding(0,50,0,0);
        contenedorBotones.setPadding(0,200,0,100);

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

        txtProveedor.setText(albaran[0].proveedor)
        txtFecha.setText(albaran[0].fecha)

        empleadoAlbaran = albaran[0].empleado.split(',')[2]  // hace falta para luego las modificaciones


        val botonLimpiar: Button = view?.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {

            txtFecha.setText("")
            txtProveedor.setText("")

        }
        val botonGuardar: Button = view?.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {

            modificarAlbaran(albaran[0].id,txtFecha.text.toString(),txtProveedor.text.toString())

        }
        val botonCancelar: Button = view?.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.menuPrincipalFragment);

        }
    }

    fun construirFormAlmacen(view: View){

        var producto = obtenerDatosVolleyProductos(view, elemento);

        val txtNombre: EditText = EditText(this.context);
        val spTipo: Spinner = Spinner(this.context);
        val txtPrecio: EditText = EditText(this.context);
        val txtStock: EditText = EditText(this.context);
        val btnCancelar: Button = Button(this.context);
        val btnGuardar: Button = Button(this.context);
        val btnLimpiar: Button = Button(this.context);

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1,"Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2,"Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3,"Guardar")

        val listaOpcionesTipo:List<String> = listOf("<- Seleccione una opción ->","PRODUCTO","SERVICIO");


        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;

        val contenedorNombre: LinearLayout = LinearLayout(this.context);
        contenedorNombre.orientation = LinearLayout.HORIZONTAL;
        val contenedorTipo: LinearLayout = LinearLayout(this.context);
        contenedorTipo.orientation = LinearLayout.HORIZONTAL;
        val contenedorPrecio: LinearLayout = LinearLayout(this.context);
        contenedorPrecio.orientation = LinearLayout.HORIZONTAL;
        val contenedorStock: LinearLayout = LinearLayout(this.context);
        contenedorStock.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones: LinearLayout = LinearLayout(this.context);
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

        contenedorNombre.setPadding(0,50,0,0);
        contenedorPrecio.setPadding(0,50,0,0);
        contenedorStock.setPadding(0,50,0,0);
        contenedorTipo.setPadding(0,50,0,0);
        contenedorBotones.setPadding(0,200,0,100);

        btnCancelar.text = "Cancelar";
        btnGuardar.text = "Guardar";
        btnLimpiar.text = "Limpiar";

        txtNombre.setText(producto[0].nombre);
        txtPrecio.setText(producto[0].precio.toString());
        txtStock.setText(producto[0].cantidad.toString());

        var idProducto = producto[0].id;

        if (producto[0].tipo.equals("Producto")){

            spTipo.setSelection(1)

        }else{

            spTipo.setSelection(2)
        }

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

        val botonLimpiar: Button = view?.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {

            txtNombre.setText("");
            txtPrecio.setText("");
            txtStock.setText("");

        }
        val botonGuardar: Button = view?.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {

            modificarProducto(
                idProducto.toString(),
                txtNombre.text.toString(),
                tipoProducto,
                txtPrecio.text.toString(),
                txtStock.text.toString()
            )

        }
        val botonCancelar: Button = view?.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.menuPrincipalFragment);
        }

        val adaptadorTipo:ArrayAdapter<String> = ArrayAdapter(view.context,android.R.layout.simple_spinner_item,listaOpcionesTipo)
        spTipo.adapter = adaptadorTipo
        spTipo.onItemSelectedListener = this

    }

    fun construirFormDelegacion(view: View){

        var delegacion = obtenerDatosVolleyDelegaciones(view,elemento)

        val txtIdentificacion: EditText = EditText(this.context);
        val txtDireccion: EditText = EditText(this.context);
        val txtProvincia: EditText = EditText(this.context);
        val txtCiudad: EditText = EditText(this.context);
        val txtcPostal: EditText = EditText(this.context);
        val txtEmail: EditText = EditText(this.context);
        val txtTelefono: EditText = EditText(this.context);
        val btnCancelar: Button = Button(this.context);
        val btnGuardar: Button = Button(this.context);
        val btnLimpiar: Button = Button(this.context);

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1,"Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2,"Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3,"Guardar")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;


        val contenedorIdentificacion: LinearLayout = LinearLayout(this.context);
        contenedorIdentificacion.orientation = LinearLayout.HORIZONTAL;
        val contenedorDireccion: LinearLayout = LinearLayout(this.context);
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        val contenedorProvincia: LinearLayout = LinearLayout(this.context);
        contenedorProvincia.orientation = LinearLayout.HORIZONTAL;
        val contenedorCiudad: LinearLayout = LinearLayout(this.context);
        contenedorCiudad.orientation = LinearLayout.HORIZONTAL;
        val contenedorcPostal: LinearLayout = LinearLayout(this.context);
        contenedorcPostal.orientation = LinearLayout.HORIZONTAL;
        val contenedorEmail: LinearLayout = LinearLayout(this.context);
        contenedorEmail.orientation = LinearLayout.HORIZONTAL;
        val contenedorTelefono: LinearLayout = LinearLayout(this.context);
        contenedorTelefono.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones: LinearLayout = LinearLayout(this.context);
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


        contenedorIdentificacion.setPadding(0,50,0,0);
        contenedorDireccion.setPadding(0,50,0,0);
        contenedorCiudad.setPadding(0,50,0,0);
        contenedorProvincia.setPadding(0,50,0,0);
        contenedorcPostal.setPadding(0,50,0,0);
        contenedorTelefono.setPadding(0,50,0,0);
        contenedorEmail.setPadding(0,50,0,0);
        contenedorBotones.setPadding(0,200,0,100);

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


        txtCiudad.setText(delegacion[0].ciudad);
        txtDireccion.setText(delegacion[0].direccion);
        txtEmail.setText(delegacion[0].email);
        txtIdentificacion.setText(delegacion[0].nombre);
        txtProvincia.setText(delegacion[0].provincia);
        txtTelefono.setText(delegacion[0].telefono);
        txtcPostal.setText(delegacion[0].cPostal);

        var idDelegacion = delegacion[0].id;


        val botonLimpiar: Button = view?.findViewById(eventoBotonLimpiar.cod);

        botonLimpiar.setOnClickListener {

            txtCiudad.setText("");
            txtDireccion.setText("");
            txtEmail.setText("");
            txtIdentificacion.setText("");
            txtProvincia.setText("");
            txtTelefono.setText("");
            txtcPostal.setText("");

        }
        val botonGuardar: Button = view?.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {


        }
        val botonCancelar: Button = view?.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {


        }
    }

    fun construirFormParte(view: View){

        var parte = obtenerDatosVolleyPartes(view,elemento)

        val txtCliente:EditText = EditText(this.context);
        val txtFecha: EditText = EditText(this.context);
        val txtDetalles: EditText = EditText(this.context);
        val txtObservaciones: EditText = EditText(this.context);
        val slTipo: Spinner = Spinner(this.context);
        val slEstado: Spinner = Spinner(this.context);
        val btnCancelar: Button = Button(this.context);
        val btnGuardar: Button = Button(this.context);
        val btnLimpiar: Button = Button(this.context);

        val contenedorSpTipo: LinearLayout = LinearLayout(this.context);
        contenedorSpTipo.orientation = LinearLayout.HORIZONTAL;
        val contenedorFecha: LinearLayout = LinearLayout(this.context);
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        val contenedorSpEstado: LinearLayout = LinearLayout(this.context);
        contenedorSpEstado.orientation = LinearLayout.HORIZONTAL;
        val contenedorSpCliente: LinearLayout = LinearLayout(this.context);
        contenedorSpCliente.orientation = LinearLayout.HORIZONTAL;
        val contenedorDetalles: LinearLayout = LinearLayout(this.context);
        contenedorDetalles.orientation = LinearLayout.HORIZONTAL;
        val contenedorObservaciones: LinearLayout = LinearLayout(this.context);
        contenedorObservaciones.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones: LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        val listaOpcionesEstado:List<String> = listOf("<- Seleccione una opción ->","ABIERTO","CERRADO");
        val listaOpcionesTipo:List<String> = listOf("<- Seleccione una opción ->","INSTALACIÓN","MANTENIMIENTO","AVERIA");

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1,"Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2,"Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3,"Guardar")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;

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

        contenedorSpTipo.setPadding(0,50,0,0);
        contenedorDetalles.setPadding(0,50,0,0);
        contenedorObservaciones.setPadding(0,50,0,0);
        contenedorFecha.setPadding(0,50,0,0);
        contenedorSpEstado.setPadding(0,50,0,0);
        contenedorBotones.setPadding(0,200,0,100);


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
        contenedorSpCliente.addView(txtCliente);
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

        txtCliente.setText(parte[0].cliente.nombre + " " + parte[0].cliente.apellidos);
        txtDetalles.setText(parte[0].detalles);
        txtFecha.setText(parte[0].fecha);
        txtObservaciones.setText(parte[0].observaciones);

        var idParte = parte[0].id

        if (parte[0].tipo.toInt() == 1 || parte[0].tipo.toInt() == 0){

            slTipo.setSelection(1)

        }else if (parte[0].tipo.toInt() == 2){

            slTipo.setSelection(2)

        }else{

            slTipo.setSelection(3)
        }

        if (parte[0].estado.toBoolean()){

            slEstado.setSelection(1)

        }else{

            slEstado.setSelection(2)

        }




        val botonLimpiar: Button = view?.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {

            txtCliente.setText("");
            txtDetalles.setText("");
            txtFecha.setText("");
            txtObservaciones.setText("");

        }
        val botonGuardar: Button = view?.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {


        }
        val botonCancelar: Button = view?.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {


        }

        val adaptadorEstado:ArrayAdapter<String> = ArrayAdapter(view.context,android.R.layout.simple_spinner_item,listaOpcionesEstado)
        slEstado.adapter = adaptadorEstado
        slEstado.onItemSelectedListener = this

        val adaptadorTipo:ArrayAdapter<String> = ArrayAdapter(view.context,android.R.layout.simple_spinner_item,listaOpcionesTipo)
        slTipo.adapter = adaptadorTipo
        slTipo.onItemSelectedListener = this

    }

    fun cosntruirFormFactura(view: View){

        var factura = obtenerDatosVolleyFactura(view,elemento)

        val txtCliente:EditText = EditText(this.context);
        val txtFecha: EditText = EditText(this.context);
        val txtPrecioSinIva: EditText = EditText(this.context);
        val txtConcepto: EditText = EditText(this.context);
        val btnCancelar: Button = Button(this.context);
        val btnGuardar: Button = Button(this.context);
        val btnLimpiar: Button = Button(this.context);

        val contenedorSpCliente: LinearLayout = LinearLayout(this.context);
        contenedorSpCliente.orientation = LinearLayout.HORIZONTAL;
        val contenedorFecha: LinearLayout = LinearLayout(this.context);
        contenedorFecha.orientation = LinearLayout.HORIZONTAL;
        val contenedorPrecio: LinearLayout = LinearLayout(this.context);
        contenedorPrecio.orientation = LinearLayout.HORIZONTAL;
        val contenedorConcepto: LinearLayout = LinearLayout(this.context);
        contenedorConcepto.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones: LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1,"Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2,"Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3,"Guardar")

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
        txtCliente.width = 800;
        txtCliente.maxLines = 1;
        txtCliente.isEnabled = false;
        txtCliente.hint = "Buscar Cliente ..."


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
        contenedorSpCliente.addView(txtCliente);

        contenedorConcepto.setPadding(0,50,0,0);
        contenedorFecha.setPadding(0,50,0,0);
        contenedorPrecio.setPadding(0,50,0,0);
        contenedorSpCliente.setPadding(0,50,0,0);
        contenedorBotones.setPadding(0,200,0,100);



        contenedor.addView(contenedorSpCliente);
        contenedor.addView(contenedorFecha);
        contenedor.addView(contenedorPrecio);
        contenedor.addView(contenedorConcepto);
        contenedor.addView(contenedorBotones);


        txtCliente.setText(factura[0].cliente.split(',')[0] + factura[0].cliente.split(',')[1])
        txtConcepto.setText(factura[0].concepto)
        txtFecha.setText(factura[0].fecha)
        txtPrecioSinIva.setText(factura[0].precioSIva.toString())

        var facturaId = factura[0].id.toString()
        var empleadoId = factura[0].empleado.split(',')[2]
        var clienteId = factura[0].cliente.split(',')[2]

        val botonLimpiar: Button = view?.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {

            txtCliente.setText("")
            txtConcepto.setText("")
            txtFecha.setText("")
            txtPrecioSinIva.setText("")

        }
        val botonGuardar: Button = view?.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {

            modificarFactura(facturaId,empleadoId,clienteId,txtFecha.text.toString(),txtPrecioSinIva.text.toString().toDouble(),txtPrecioSinIva.text.toString().toDouble(),txtConcepto.text.toString())

        }
        val botonCancelar: Button = view?.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.menuPrincipalFragment);

        }
    }

    fun construirFormEmpleados(view: View){

       var empleado = obtenerDatosVolleyEmpleados(view,elemento)


        val txtUsuario:EditText = EditText(this.context);
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
        val slDelegacion:EditText = EditText(this.context);
        val cbAdministrador:CheckBox= CheckBox(this.context);
        val cbInstalador:CheckBox= CheckBox(this.context);
        val cbGestor:CheckBox= CheckBox(this.context);
        val cbComercial:CheckBox = CheckBox(this.context)

        var admin = "FALSE";
        var gestor = "FALSE";
        var comercial = "FALSE";
        var instalador = "FALSE";


        val contenedorUsuario:LinearLayout = LinearLayout(this.context);
        contenedorUsuario.orientation = LinearLayout.HORIZONTAL;
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
        val contenedorRol2:LinearLayout = LinearLayout(this.context);
        contenedorRol2.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones:LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        var eventoBotonLimpiar: AltasFragment.ControlDinamico =
            AltasFragment.ControlDinamico(1, "Limpiar")
        var eventoBotonCancelar: AltasFragment.ControlDinamico =
            AltasFragment.ControlDinamico(2, "Cancelar")
        var eventoBotonGuardar: AltasFragment.ControlDinamico =
            AltasFragment.ControlDinamico(3, "Guardar")


        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;


        contenedorUsuario.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorUsuario.orientation = LinearLayout.HORIZONTAL;
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
        contenedorRol2.setLayoutParams(ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contenedorRol2.orientation = LinearLayout.HORIZONTAL;

        txtUsuario.hint = "Introduzca Usuario"
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
        slDelegacion.width = 800
        slDelegacion.maxLines = 1
        slDelegacion.isEnabled = false
        slDelegacion.hint = "Buscar Delegación ..."



        cbAdministrador.text = "Administrador";
        cbGestor.text = "Gestor"
        cbInstalador.text = "Instalador"
        cbComercial.text = "Comercial"


        contenedorBotones.gravity = Gravity.CENTER
        contenedorBotones.addView(btnGuardar);
        contenedorBotones.addView(btnCancelar);
        contenedorBotones.addView(btnLimpiar)

        contenedorUsuario.gravity= Gravity.CENTER;
        contenedorUsuario.addView(txtUsuario);
        contenedorSlDelegacion.gravity= Gravity.CENTER;
        contenedorSlDelegacion.addView(slDelegacion);
        contenedorRol.gravity= Gravity.CENTER;
        contenedorRol.addView(cbAdministrador);
        contenedorRol.addView(cbGestor);
        contenedorRol2.gravity= Gravity.CENTER;
        contenedorRol2.addView(cbInstalador);
        contenedorRol2.addView(cbComercial);
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
        contenedorRol.setPadding(0,150,0,0);
        contenedorRol2.setPadding(0,150,0,0);
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
        contenedor.addView(contenedorRol2);
        contenedor.addView(contenedorSlDelegacion);
        contenedor.addView(contenedorBotones);

        var empleadoId = empleado[0].id

        txtNacimiento.setText(empleado[0].edad)
        txtApellidos.setText(empleado[0].apellidos)
        txtCiudad.setText(empleado[0].ciudad)
        txtDireccion.setText(empleado[0].direccion)
        txtEmail.setText(empleado[0].email)
        txtNombre.setText(empleado[0].nombre)
        txtProvincia.setText(empleado[0].provincia)
        txtTelefono.setText(empleado[0].telefono)
        txtUsuario.setText(empleado[0].usuario)
        txtcPostal.setText(empleado[0].cPostal)
        txtDni.setText(empleado[0].dni)
        slDelegacion.setText(empleado[0].delegacionNombre)

        delegacionSeleccionada = empleado[0].delegacionId.toInt()

        if (empleado[0].gestor.toString().equals("true")){

            cbGestor.isChecked = true
        }

        if (empleado[0].admin.toString().equals("true")){

            cbAdministrador.isChecked = true
        }

        if (empleado[0].comercial.toString().equals("true")){

            cbComercial.isChecked = true
        }

        if (empleado[0].instalador.toString().equals("true")){

            cbInstalador.isChecked = true
        }



        val botonLimpiar: Button = view?.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {

            txtNombre.setText("")
            txtApellidos.setText("")
            txtDireccion.setText("")
            txtCiudad.setText("")
            txtProvincia.setText("")
            txtEmail.setText("")
            txtTelefono.setText("")
            txtcPostal.setText("")
            txtDni.setText("")
            txtUsuario.setText("")
            cbAdministrador.isChecked = false
            cbGestor.isChecked = false
            cbComercial.isChecked = false
            cbInstalador.isChecked = false
        }
        val botonGuardar: Button = view?.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {

            modificarEmpleado(
                empleadoId,
                txtNombre.text.toString(),
                txtApellidos.text.toString(),
                txtUsuario.text.toString(),
                txtDireccion.text.toString(),
                txtCiudad.text.toString(),
                txtProvincia.text.toString(),
                txtEmail.text.toString(),
                txtTelefono.text.toString(),
                txtcPostal.text.toString(),
                txtDni.text.toString(),
                txtNacimiento.text.toString(),
                cbAdministrador.isChecked,
                cbGestor.isChecked,
                cbComercial.isChecked,
                cbInstalador.isChecked,
                empleadoId,
                delegacionSeleccionada
            );

        }
        val botonCancelar: Button = view?.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.menuPrincipalFragment);
        }
    }

    fun construirFormClientes(view: View){

        var cliente = obtenerDatosVolleyCliente(view,elemento)

        val txtNombre: EditText = EditText(this.context);
        val txtApellidos: EditText = EditText(this.context);
        val txtCiudad: EditText = EditText(this.context);
        val txtcPostal: EditText = EditText(this.context);
        val txtDni: EditText = EditText(this.context);
        val txtEmail: EditText = EditText(this.context);
        val txtTelefono: EditText = EditText(this.context);
        val txtNacimiento: EditText = EditText(this.context);
        val txtDireccion: EditText = EditText(this.context);
        val txtProvincia: EditText = EditText(this.context);
        val cbEstado: CheckBox = CheckBox(this.context);
        val btnCancelar: Button = Button(this.context);
        val btnGuardar: Button = Button(this.context);
        val btnLimpiar: Button = Button(this.context);

        val contenedorNombre: LinearLayout = LinearLayout(this.context);
        contenedorNombre.orientation = LinearLayout.HORIZONTAL;
        val contenedorApellidos: LinearLayout = LinearLayout(this.context);
        contenedorApellidos.orientation = LinearLayout.HORIZONTAL;
        val contenedorCiudad: LinearLayout = LinearLayout(this.context);
        contenedorCiudad.orientation = LinearLayout.HORIZONTAL;
        val contenedorcPostal: LinearLayout = LinearLayout(this.context);
        contenedorcPostal.orientation = LinearLayout.HORIZONTAL;
        val contenedorDni: LinearLayout = LinearLayout(this.context);
        contenedorDni.orientation = LinearLayout.HORIZONTAL;
        val contenedorEmail: LinearLayout = LinearLayout(this.context);
        contenedorEmail.orientation = LinearLayout.HORIZONTAL;
        val contenedorTelefono: LinearLayout = LinearLayout(this.context);
        contenedorTelefono.orientation = LinearLayout.HORIZONTAL;
        val contenedorNacimiento: LinearLayout = LinearLayout(this.context);
        contenedorNacimiento.orientation = LinearLayout.HORIZONTAL;
        val contenedorDireccion: LinearLayout = LinearLayout(this.context)
        contenedorDireccion.orientation = LinearLayout.HORIZONTAL;
        val contenedorProvincia: LinearLayout = LinearLayout(this.context);
        contenedorProvincia.orientation = LinearLayout.HORIZONTAL;
        val contenedorEstado: LinearLayout = LinearLayout(this.context);
        contenedorEstado.orientation = LinearLayout.HORIZONTAL;
        val contenedorBotones: LinearLayout = LinearLayout(this.context);
        contenedorBotones.orientation = LinearLayout.HORIZONTAL;

        var eventoBotonLimpiar:ControlDinamico = ControlDinamico(1,"Limpiar")
        var eventoBotonCancelar:ControlDinamico = ControlDinamico(2,"Cancelar")
        var eventoBotonGuardar:ControlDinamico = ControlDinamico(3,"Guardar")

        btnLimpiar.id = eventoBotonLimpiar.cod;
        btnLimpiar.text =  eventoBotonLimpiar.nombre;
        btnGuardar.id = eventoBotonGuardar.cod;
        btnGuardar.text = eventoBotonGuardar.nombre;
        btnCancelar.id = eventoBotonCancelar.cod;
        btnCancelar.text = eventoBotonCancelar.nombre;


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

        contenedorcPostal.setPadding(0,50,0,0);
        contenedorTelefono.setPadding(0,50,0,0);
        contenedorProvincia.setPadding(0,50,0,0);
        contenedorNombre.setPadding(0,50,0,0);
        contenedorNombre.setPadding(0,50,0,0);
        contenedorNacimiento.setPadding(0,50,0,0);
        contenedorEstado.setPadding(0,50,0,0);
        contenedorEmail.setPadding(0,50,0,0);
        contenedorDni.setPadding(0,50,0,0);
        contenedorDireccion.setPadding(0,50,0,0);
        contenedorCiudad.setPadding(0,50,0,0);
        contenedorApellidos.setPadding(0,50,0,0);
        contenedorDireccion.setPadding(0,50,0,0);
        contenedorBotones.setPadding(0,200,0,100);

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

        txtNombre.setText(cliente[0].nombre)
        txtApellidos.setText(cliente[0].apellidos)
        txtDireccion.setText(cliente[0].direccion)
        txtCiudad.setText(cliente[0].ciudad)
        txtProvincia.setText(cliente[0].provincia)
        txtEmail.setText(cliente[0].email)
        txtTelefono.setText(cliente[0].telefono)
        txtcPostal.setText(cliente[0].cPostal)
        txtDni.setText(cliente[0].dni)
        txtNacimiento.setText(cliente[0].edad)

        if (cliente[0].estado.toString().equals("true")){

            cbEstado.isChecked = true
        }


        val botonLimpiar: Button = view?.findViewById(eventoBotonLimpiar.cod)

        botonLimpiar.setOnClickListener {

            txtNombre.setText("")
            txtApellidos.setText("")
            txtDireccion.setText("")
            txtCiudad.setText("")
            txtProvincia.setText("")
            txtEmail.setText("")
            txtTelefono.setText("")
            txtcPostal.setText("")
            txtDni.setText("")
            txtNacimiento.setText("")

            cbEstado.isChecked = false

        }
        val botonGuardar: Button = view?.findViewById(eventoBotonGuardar.cod)

        botonGuardar.setOnClickListener {


        }
        val botonCancelar: Button = view?.findViewById(eventoBotonCancelar.cod)

        botonCancelar.setOnClickListener {


        }
    }

    fun obtenerDatosVolleyFactura(view: View, id:Int):MutableList<Factura> {

        val JSON: MediaType = MediaType.get("application/json; charset=utf-8")

        val jsonObject = JSONObject();

        val facturas:MutableList<Factura> = mutableListOf();

        jsonObject.put("busqueda", id);

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON, jsonObject.toString())


        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE + "movil/factura/buscar/form")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json; charset=utf-8")
            .build()

        var llamada: Call = client.newCall(request)

        try {


            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();

            if (cuerpo.length > 2) {

                var datos = cuerpo.split(":{");

                for (i in 1..datos.count() - 1){


                    var factura = Factura(
                        datos[i].split(":[")[2].split(']')[1].split(',')[5].split(':')[1].replace('}',' ').trim().toInt(),
                        datos[i].split(":[")[2].split(']')[0],
                        datos[i].split(":[")[1].split(']')[0],
                        datos[i].split(":[")[2].split(']')[1].split(',')[1].split(':')[1],
                        datos[i].split(":[")[2].split(']')[1].split(',')[2].split(':')[1].toDouble(),
                        datos[i].split(":[")[2].split(']')[1].split(',')[3].split(':')[1].toDouble(),
                        datos[1].split(":[")[2].split(']')[1].split(',')[4].split(':')[1]
                    );

                    facturas.add(factura);

                }

            }else{

                Toast.makeText(this.context, "Error: No hay resultados", Toast.LENGTH_LONG).show()
            }

        }catch (ex:Exception){

            Toast.makeText(this.context, ex.message.toString(), Toast.LENGTH_LONG).show()

        }

        return facturas
    }


    fun obtenerDatosVolleyDelegaciones(view: View , id:Int):MutableList<Delegacion> {

        val JSON: MediaType = MediaType.get("application/json; charset=utf-8")
        val jsonObject = JSONObject();

        jsonObject.put("busqueda", id);

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON, jsonObject.toString())

        val delegaciones:MutableList<Delegacion> = mutableListOf();


        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE + "movil/delegacion/buscar/form")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {
            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();

            if (cuerpo.length > 2) {

                var datos = cuerpo.split(":{");



                for (i in 1..datos.count() - 1) {



                    var delegacion = Delegacion(

                        datos[i].split(',')[0].split(':')[1].toInt(),
                        datos[i].split(',')[1].split(':')[1],
                        datos[i].split(',')[2].split(':')[1],
                        datos[i].split(',')[3].split(':')[1],
                        datos[i].split(',')[4].split(':')[1],
                        datos[1].split(',')[5].split(':')[1],
                        datos[1].split(',')[6].split(':')[1],
                        datos[1].split(',')[7].split(':')[1].split('}')[0]
                    );
                    delegaciones.add(delegacion);
                }

            }else{

                Toast.makeText(this.context, "Error: No hay resultados", Toast.LENGTH_LONG).show()

            }

        }catch (ex:Exception){

            Toast.makeText(this.context, "Error: No hay resultados", Toast.LENGTH_LONG).show()
        }

        return delegaciones
    }

    fun obtenerDatosVolleyProductos(view: View, id:Int):MutableList<Almacen>{

        val JSON: MediaType = MediaType.get("application/json; charset=utf-8")
        val jsonObject = JSONObject();

        val productos:MutableList<Almacen> = mutableListOf();

        jsonObject.put("busqueda", id);

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON, jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE + "movil/producto/buscar/form")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();

            if (cuerpo.length > 2) {

                var datos = cuerpo.split(":{");

                for (i in 1..datos.count() - 1){

                    var producto = Almacen(
                        datos[1].split(',')[5].split(':')[1].split('}')[0].toInt(),
                        datos[1].split(',')[0].split(':')[1],
                        datos[1].split(',')[2].split(':')[1],
                        datos[1].split(',')[3].split(':')[1].toDouble(),
                        datos[1].split(',')[1].split(':')[1].toInt(),
                        datos[1].split(',')[4].split(':')[1]
                    );
                    productos.add(producto);
                }

            }else{

                Toast.makeText(this.context, "Error: No hay resultados", Toast.LENGTH_LONG).show()
            }

        }catch (ex:Exception){

            Toast.makeText(this.context, ex.message.toString(), Toast.LENGTH_LONG).show()

        }

        return productos
    }



    fun obtenerDatosVolleyAlbaran(view: View, id:Int):MutableList<Albaran>{

        val JSON: MediaType =  MediaType.get("application/json; charset=utf-8")
        val jsonObject= JSONObject();

        val albaranes:MutableList<Albaran> = mutableListOf();

        jsonObject.put("busqueda",id);

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/albaran/buscar/form")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()


        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();

            if (cuerpo.length > 2){

                var datos = cuerpo.split(":{");


                for (i in 1..datos.count() - 1){

                    var albaran = Albaran(
                        datos[i].split(":[")[0].split(',')[0].split(':')[1].toInt(),
                        datos[i].split(":[")[1].split(']')[0],
                        datos[i].split(":[")[0].split(',')[1].split(':')[1],
                        datos[i].split(":[")[0].split(',')[2].split(':')[1]
                    );

                    albaranes.add(albaran);
                }

            }else{

                Toast.makeText(this.context, "Error: No hay resultados", Toast.LENGTH_LONG).show()

            }

        }catch (ex:Exception){

            Toast.makeText(this.context, ex.message.toString(), Toast.LENGTH_LONG).show()

        }


        return  albaranes
    }



    fun obtenerDatosVolleyEmpleados(view: View, id:Int):MutableList<Empleado>{


        val JSON: MediaType =  MediaType.get("application/json; charset=utf-8")
        val jsonObject= JSONObject();

        jsonObject.put("busqueda",id);

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/empleados/buscar/form")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)
        val empleados:MutableList<Empleado> = mutableListOf();

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();

                if (cuerpo.length > 2){

                    var datos = cuerpo.split(":{");

                    for (i in 1..datos.count() - 1){

                        var empleado = Empleado(datos[i].split(',')[26].split('"')[2].replace(':',' ').replace('}',' ').trim().toInt()
                            ,datos[i].split(',')[14].split('[')[1].toInt()
                            ,datos[i].split(',')[0].split(':')[1]
                            ,datos[i].split(',')[1].split(':')[1]
                            ,datos[i].split(',')[2].split(':')[1]
                            ,datos[i].split(',')[3].split(':')[1]
                            ,datos[i].split(',')[4].split(':')[1]
                            ,datos[i].split(',')[6].split(':')[1]
                            ,datos[i].split(',')[9].split(':')[1]
                            ,datos[i].split(',')[5].split(':')[1]
                            ,datos[i].split(',')[21].split(':')[1].toBoolean()
                            ,datos[i].split(',')[22].split(':')[1].toBoolean()
                            ,datos[i].split(',')[23].split(':')[1].toBoolean()
                            ,datos[i].split(',')[24].split(':')[1].toBoolean()
                            ,datos[i].split(',')[25].split(':')[1]
                            ,datos[i].split(',')[10].split(':')[1]
                            ,datos[i].split(',')[8].split(':')[1]
                            ,datos[i].split(',')[7].split(':')[1]
                            ,datos[i].split(',')[18],
                            datos[i].split(',')[14].split(":[")[1]
                            );

                        empleados.add(empleado);

                    }



                }else{

                    Toast.makeText(this.context,"Error: No hay resultados",Toast.LENGTH_LONG).show()
                }


        }catch (ex:Exception){

            Toast.makeText(this.context,ex.message.toString(),Toast.LENGTH_LONG).show()
        }


        return empleados
    }

    fun obtenerDatosVolleyCliente(view: View, id:Int):MutableList<Cliente>{

        val JSON: MediaType =  MediaType.get("application/json; charset=utf-8")
        val jsonObject= JSONObject();

        jsonObject.put("busqueda",id);

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val personas:MutableList<Cliente> = mutableListOf();


        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/cliente/buscar/form")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();

            if (cuerpo.length > 2){

                var datos = cuerpo.split(":{");


                for (i in 1..datos.count() - 1){

                    var persona = Cliente(
                        datos[i].split(',')[0].split(':')[1],
                        datos[i].split(',')[1].split(':')[1],
                        datos[i].split(',')[8].split(':')[1],
                        datos[i].split(',')[2].split(':')[1],
                        datos[i].split(',')[9].split(':')[1],
                        datos[i].split(',')[6].split(':')[1],
                        datos[i].split(',')[10].split(':')[1],
                        datos[i].split(',')[11].split(':')[1].split('}')[0].toInt(),
                        datos[i].split(',')[3].split(':')[1],
                        datos[i].split(',')[5].split(':')[1],
                        datos[i].split(',')[4].split(':')[1],
                        datos[i].split(',')[7].split(':')[1]
                    )
                    personas.add(persona);
                }

            }else{

                Toast.makeText(this.context,"Error: No hay resultados",Toast.LENGTH_LONG).show()
            }


        }catch (ex:Exception){

            Toast.makeText(this.context,ex.message.toString(),Toast.LENGTH_LONG).show()
        }

        return personas
    }


    fun obtenerDatosVolleyPresupuestos(view: View, id:Int): MutableList<Presupuesto> {

        val JSON: MediaType = MediaType.get("application/json; charset=utf-8")
        val jsonObject = JSONObject();

        jsonObject.put("busqueda", id);

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON, jsonObject.toString())


        val presupuestos: MutableList<Presupuesto> = mutableListOf();


        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE + "movil/presupuesto/buscar/form")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()


        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();

            if (cuerpo.length > 2) {

                var datos = cuerpo.split(":{");



                for (i in 1..datos.count() - 1) {

                     var empleado = Empleado(

                         datos[i].split(":[")[1].split(']')[0].split(',')[2].toInt(),
                         0,
                         datos[i].split(":[")[1].split(']')[0].split(',')[0],
                         datos[i].split(":[")[1].split(']')[0].split(',')[1],
                         "",
                         "",
                         "",
                         "",
                         "",
                         "",
                         false,
                         false,
                         false,
                         false,
                         "",
                         "",
                         "",
                         "",
                         "",
                         ""
                     );

                     var presupuesto = Presupuesto(

                         datos[i].split(":[")[0].split(',')[0].split(':')[1].toInt(),
                         empleado,
                         datos[i].split(":[")[0].split(',')[1].split(':')[1],
                         datos[i].split(":[")[1].split(']')[1].split(',')[1].split(':')[1],
                         datos[i].split(":[")[1].split(']')[1].split(',')[2].split(':')[1],
                         datos[i].split(":[")[1].split(']')[1].split(',')[3].split(':')[1].split('}')[0]

                     );

                     presupuestos.add(presupuesto);
                 }

            } else {

                Toast.makeText(this.context, "Error: No hay resultados", Toast.LENGTH_LONG).show()
            }


        } catch (ex: Exception) {

            Toast.makeText(this.context, ex.message.toString(), Toast.LENGTH_LONG).show()
        }



        return presupuestos
    }


    fun obtenerDatosVolleyPartes(view: View, id:Int): MutableList<Parte>{

        val JSON: MediaType = MediaType.get("application/json; charset=utf-8")
        val jsonObject = JSONObject();

        val url = URL_BASE+"movil/partes"
        val partes:MutableList<Parte> = mutableListOf();

        jsonObject.put("busqueda", id);

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON, jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE + "movil/parte/buscar/form")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {
            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();

            if (cuerpo.length > 2) {

                var datos = cuerpo.split(":{");

                Toast.makeText(this.context, datos[1], Toast.LENGTH_LONG).show()
                for (i in 1..datos.count() - 1){

                    var cliente = Cliente(

                        datos[i].split(":[")[1].split(']')[0].split(',')[0],
                        datos[i].split(":[")[1].split(']')[0].split(',')[1],
                        "",
                        "",
                        "",
                        "",
                        "",
                        datos[i].split(":[")[1].split(']')[0].split(',')[2].toInt(),
                        "",
                        "",
                        "",
                        ""

                    );

                    var empleado = Empleado(

                        datos[1].split(":[")[2].split(']')[0].split(',')[2].toInt(),
                        0,
                        datos[i].split(":[")[2].split(']')[0].split(',')[0],
                        datos[i].split(":[")[2].split(']')[0].split(',')[1],
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        false,
                        false,
                        false,
                        false,
                        "",
                        "",
                        "","","","");

                    var delegacion = Delegacion(

                        datos[i].split(":[")[3].split(',')[0].toInt(),
                        datos[i].split(":[")[3].split(',')[4],
                        datos[i].split(":[")[3].split(',')[1],
                        datos[i].split(":[")[3].split(',')[3],
                        "",
                        "",
                        "",
                        ""
                    );

                    var parte = Parte(

                        datos[i].split(":[")[3].split(']')[1].split(':')[1].split('}')[0].toInt(),
                        cliente,
                        empleado,
                        datos[i].split(":[")[2].split(']')[1].split(',')[1].split(':')[1],
                        datos[i].split(":[")[2].split(']')[1].split(',')[2].split(':')[1],
                        datos[i].split(":[")[2].split(']')[1].split(',')[3].split(':')[1],
                        datos[i].split(":[")[2].split(']')[1].split(',')[4].split(':')[1],
                        delegacion,
                        datos[i].split(":[")[1].split(']')[1].split(',')[1].split(':')[1]
                    )
                    partes.add(parte);
                }

            }else{

                Toast.makeText(this.context, "Error: No hay resultados", Toast.LENGTH_LONG).show()
            }


        }catch (ex:Exception){

            Toast.makeText(this.context, ex.message.toString(), Toast.LENGTH_LONG).show()
        }


        return partes
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
                estadoPrespuesto = "CERRADO"
            }
            "PRODUCTO"->{

                tipoProducto = "Producto"
            }
            "SERVICIO"->{

                tipoProducto = "Servicio"
            }
            "EN TRAMITE"->{

                estadoPrespuesto = "EN TRAMITE"
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun modificarEmpleado(id:Int
                          ,nombre:String,
                          apellidos:String,
                          usuario:String,
                          direccion:String,
                          ciudad:String,
                          provincia:String,
                          email:String,
                          telefono:String,
                          cPostal:String,
                          dni:String,
                          nacimiento:String,
                          admin:Boolean,
                          gestor:Boolean,
                          comercial:Boolean,
                          instalador:Boolean,
                          empleado:Int,
                          delegacion: Int
    ) {

        var JSON:MediaType =  MediaType.get("application/json; charset=utf-8")

        val jsonObject= JSONObject();

        jsonObject.put("nombre",nombre);
        jsonObject.put("usuario",usuario);
        jsonObject.put("apellidos",apellidos);
        jsonObject.put("direccion",direccion);
        jsonObject.put("ciudad",ciudad);
        jsonObject.put("provincia",provincia);
        jsonObject.put("email",email);
        jsonObject.put("telefono",telefono);
        jsonObject.put("cPostal",cPostal);
        jsonObject.put("dni",dni);
        jsonObject.put("nacimiento",nacimiento);
        jsonObject.put("admin",admin);
        jsonObject.put("gestor",gestor);
        jsonObject.put("comercial",comercial);
        jsonObject.put("instalador",instalador);
        jsonObject.put("empleado",empleado);
        jsonObject.put("delegacion",delegacion);
        jsonObject.put("id",id);


        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/empleado/modificar")
            .post(body) //Indicated as get request
            .header("Accept", "application/json")
            .header("Content-Type", "application/json;charset=utf-8")
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


    fun modificarProducto(id:String,nombre:String,tipo:String,precio:String,stock:String){

        var JSON:MediaType =  MediaType.get("application/json; charset=utf-8")

        val jsonObject= JSONObject();

        jsonObject.put("nombre",nombre);
        jsonObject.put("tipo",tipo);
        jsonObject.put("stock",stock);
        jsonObject.put("precio",precio);
        jsonObject.put("productoId",id);


        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/producto/modificar")
            .post(body) //Indicated as get request
            .header("Accept", "application/json")
            .header("Content-Type", "application/json;charset=utf-8")
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

    fun modificarFactura(id:String,empleado:String,cliente:String,fecha:String,precioS:Double, precio:Double, concepto:String){

        var JSON:MediaType =  MediaType.get("application/json; charset=utf-8")

        val jsonObject= JSONObject();

        jsonObject.put("cliente",cliente);
        jsonObject.put("fecha",fecha);
        jsonObject.put("precio",precio);
        jsonObject.put("precioC",precioS);
        jsonObject.put("concepto",concepto);
        jsonObject.put("empleado",empleado);
        jsonObject.put("facturaId",id);


        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/factura/modificar")
            .post(body) //Indicated as get request
            .header("Accept", "application/json")
            .header("Content-Type", "application/json;charset=utf-8")
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

    fun modificarAlbaran(id:Int,fecha:String,proveedor:String){

        var JSON:MediaType =  MediaType.get("application/json; charset=utf-8")

        val jsonObject= JSONObject();

        jsonObject.put("fecha",fecha);
        jsonObject.put("proveedor",proveedor);
        jsonObject.put("id",id);


        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/albaran/modificar")
            .post(body) //Indicated as get request
            .header("Accept", "application/json")
            .header("Content-Type", "application/json;charset=utf-8")
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

    
}