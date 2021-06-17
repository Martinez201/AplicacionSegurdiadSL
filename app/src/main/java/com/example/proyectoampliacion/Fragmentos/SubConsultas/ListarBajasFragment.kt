package com.example.proyectoampliacion.Fragmentos.SubConsultas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proyectoampliacion.Adaptadores.*
import com.example.proyectoampliacion.Classes_Auxiliares.*
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.fragment_lista_modificar.*
import kotlinx.android.synthetic.main.fragment_listar.*
import kotlinx.android.synthetic.main.fragment_listar_bajas.*
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.Exception

class ListarBajasFragment : Fragment() {

    val URL_BASE:String = "http://192.168.1.141/symfony/web/app.php/"
    var tipo:Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listar_bajas, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)

        arguments.let {

            when(it?.getInt("tipo")){

                0->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Albaranes ";
                    obtenerDatosVolleyAlbaran(view);
                    tipo = 0;
                    edtBuscarBAJ.hint = "Por Proveedor"
                }
                1->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Almacén";
                    obtenerDatosVolleyProductos(view);
                    tipo = 1;
                    edtBuscarBAJ.hint = "Por Producto"
                }
                2->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Clientes";
                    obtenerDatosVolleyCliente(view);
                    tipo = 2;
                    edtBuscarBAJ.hint = "Por Apellidos"
                }
                3->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Delegación";
                    obtenerDatosVolleyDelegaciones(view);
                    tipo = 3;
                    edtBuscarBAJ.hint = "Por Nombre"
                }
                4->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Empleados";
                    obtenerDatosVolleyEmpleados(view);
                    tipo = 4;
                    edtBuscarBAJ.hint = "Por Apellidos"
                }
                5->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Facturas";
                    obtenerDatosVolleyFactura(view);
                    edtBuscarBAJ.hint = "Por Concepto"

                    tipo = 5;
                }
                6->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Partes";
                    obtenerDatosVolleyPartes(view);
                    edtBuscarBAJ.hint = "Por observaciones"

                    tipo = 6;
                }
                7->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Presupuestos";
                    obtenerDatosVolleyPresupuestos(view);
                    edtBuscarBAJ.hint = "Por dirección instalación"
                    tipo = 7;
                }
            }

        }

        btnBuscarBAJ.setOnClickListener { it ->

            when(tipo){

                0->{
                    busquedaAlbaranes(view)
                }
                1->{
                    busquedaAlmacen(view)
                }
                2->{

                    busquedaClientes(view)
                }
                3->{

                    busquedaDelegaciones(view)

                }
                4->{

                    busquedaEmpleados(view)
                }
                5->{

                    busquedaFacturas(view)

                }
                6->{
                    busquedaPartes(view)
                }
                7->{

                    busquedaPresupuestos(view)

                }
            }

        }
    }


    fun obtenerDatosVolleyDelegaciones(view: View){

        val queue = Volley.newRequestQueue(this.context)
        val url = URL_BASE+"movil/delegaciones"
        val delegaciones:MutableList<Delegacion> = mutableListOf();

        val jsObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                var datos = response.toString().split(":{");


                for (i in 1..datos.count() - 1){

                    var delegacion = Delegacion(

                        datos[i].split(',')[0].split(':')[1].toInt(),
                        datos[i].split(',')[1].split(':')[1],
                        datos[i].split(',')[2].split(':')[1],
                        datos[i].split(',')[3].split(':')[1],
                        datos[i].split(',')[4].split(':')[1],
                        datos[i].split(',')[5].split(':')[1],
                        datos[i].split(',')[6].split(':')[1],
                        datos[i].split(',')[7].split(':')[1].split('}')[0]
                    );
                    delegaciones.add(delegacion);
                }

                mostarDelegaciones(view,delegaciones);

            },
            { error ->

                Toast.makeText(view.context,error.message.toString(),Toast.LENGTH_SHORT).show();
            }
        )

        queue.add(jsObjectRequest);

    }

    fun obtenerDatosVolleyProductos(view: View){

        val queue = Volley.newRequestQueue(this.context)
        val url = URL_BASE+"movil/productos"
        val productos:MutableList<Almacen> = mutableListOf();

        val jsObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                var datos = response.toString().split(":{");



                for (i in 1..datos.count() - 1){

                    var producto = Almacen(
                        datos[i].split(',')[5].split(':')[1].split('}')[0].toInt(),
                        datos[i].split(',')[0].split(':')[1],
                        datos[i].split(',')[2].split(':')[1],
                        datos[i].split(',')[3].split(':')[1].toDouble(),
                        datos[i].split(',')[1].split(':')[1].toInt(),
                        datos[i].split(',')[4].split(':')[1]
                    );
                    productos.add(producto);
                    mostarProductos(view,productos);
                }

            },
            { error ->

                Toast.makeText(view.context,error.message.toString(),Toast.LENGTH_SHORT).show();
            }
        )

        queue.add(jsObjectRequest);

    }

    fun obtenerDatosVolleyPresupuestos(view: View){

        val queue = Volley.newRequestQueue(this.context)
        val url = URL_BASE+"movil/presupuestos"
        val presupuestos:MutableList<Presupuesto> = mutableListOf();

        val jsObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                var datos = response.toString().split(":{");


                for (i in 1..datos.count() - 1){

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
                    mostarPresupuestos(view,presupuestos);
                }

            },
            { error ->

                Toast.makeText(view.context,error.message.toString(),Toast.LENGTH_SHORT).show();
            }
        )

        queue.add(jsObjectRequest);

    }


    fun obtenerDatosVolleyEmpleados(view: View){

        val queue = Volley.newRequestQueue(this.context)
        val url = URL_BASE+"movil/empleados"
        val empleados:MutableList<Empleado> = mutableListOf();

        val jsObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                var datos = response.toString().split(":{");

                //Toast.makeText(view.context,datos[1].split(',')[26].split(':')[1].split('}')[0],Toast.LENGTH_LONG).show();

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
                        ,datos[i].split(',')[24].split(':')[1].toBoolean()
                        ,datos[i].split(',')[22].split(':')[1].toBoolean()
                        ,datos[i].split(',')[23].split(':')[1].toBoolean()
                        ,datos[i].split(',')[25].split(':')[1]
                        ,datos[i].split(',')[10].split(':')[1]
                        ,datos[i].split(',')[8].split(':')[1]
                        ,datos[i].split(',')[7].split(':')[1]
                        ,datos[i].split(',')[18],
                        datos[i].split(',')[14].split(":[")[1]
                    );
                    empleados.add(empleado);
                    mostarEmpleados(view,empleados);

                }

            },
            { error ->

                Toast.makeText(view.context,error.message.toString(),Toast.LENGTH_SHORT).show();
            }
        )

        queue.add(jsObjectRequest);

    }



    fun obtenerDatosVolleyFactura(view: View){

        val queue = Volley.newRequestQueue(this.context)
        val url = URL_BASE+"movil/facturas"
        val facturas:MutableList<Factura> = mutableListOf();

        val jsObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                var datos = response.toString().split(":{");


                for (i in 1..datos.count() - 1){


                    var factura = Factura(
                        datos[i].split(":[")[2].split(']')[1].split(',')[5].split(':')[1].replace('}',' ').trim().toInt(),
                        datos[i].split(":[")[2].split(']')[0],
                        datos[i].split(":[")[1].split(']')[0],
                        datos[i].split(":[")[2].split(']')[1].split(',')[1].split(':')[1],
                        datos[i].split(":[")[2].split(']')[1].split(',')[2].split(':')[1].toDouble(),
                        datos[i].split(":[")[2].split(']')[1].split(',')[3].split(':')[1].toDouble(),
                        datos[i].split(":[")[2].split(']')[1].split(',')[4].split(':')[1]
                    );

                    facturas.add(factura);
                    mostarFacturas(view,facturas);

                }

            },
            { error ->

                Toast.makeText(view.context,error.message.toString(),Toast.LENGTH_SHORT).show();
            }
        )

        queue.add(jsObjectRequest);

    }


    fun obtenerDatosVolleyAlbaran(view: View){

        val queue = Volley.newRequestQueue(this.context)
        val url = URL_BASE+"movil/albaranes"
        val albaranes:MutableList<Albaran> = mutableListOf();

        val jsObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                var datos = response.toString().split(":{");

                for (i in 1..datos.count() - 1){

                    var albaran = Albaran(
                        datos[i].split(":[")[0].split(',')[0].split(':')[1].toInt(),
                        datos[i].split(":[")[1].split(']')[0],
                        datos[i].split(":[")[0].split(',')[1].split(':')[1],
                        datos[i].split(":[")[0].split(',')[2].split(':')[1]
                    );
                    albaranes.add(albaran);
                    mostarAlbaranes(view,albaranes);
                }

            },
            { error ->

                Toast.makeText(view.context,error.message.toString(),Toast.LENGTH_SHORT).show();
            }
        )

        queue.add(jsObjectRequest);

    }


    fun obtenerDatosVolleyCliente(view: View){

        val queue = Volley.newRequestQueue(this.context)
        val url = URL_BASE+"movil/clientes"
        val personas:MutableList<Cliente> = mutableListOf();

        val jsObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                var datos = response.toString().split(":{");

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
                    mostarPersonas(view,personas);
                }
            },
            { error ->

                Toast.makeText(view.context,error.message.toString(),Toast.LENGTH_SHORT).show();
            }
        )

        queue.add(jsObjectRequest);

    }

    fun obtenerDatosVolleyPartes(view: View){

        val queue = Volley.newRequestQueue(this.context)
        val url = URL_BASE+"movil/partes"
        val partes:MutableList<Parte> = mutableListOf();

        val jsObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                var datos = response.toString().split(":{");


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

                        datos[i].split(":[")[2].split(']')[0].split(',')[2].toInt(),
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
                    mostarPartes(view,partes);
                }
            },
            { error ->

                Toast.makeText(view.context,error.message.toString(),Toast.LENGTH_SHORT).show();
            }
        )

        queue.add(jsObjectRequest);

    }

    fun busquedaPresupuestos(view: View){

        val JSON: MediaType =  MediaType.get("application/json; charset=utf-8")
        val jsonObject= JSONObject();

        jsonObject.put("busqueda", edtBuscarBAJ.text.toString());

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/presupuestos/buscar")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();
            val presupuestos:MutableList<Presupuesto> = mutableListOf();

            if( edtBuscarBAJ.text.toString().isNotEmpty()){

                if (cuerpo.length > 2){

                    var datos = cuerpo.toString().split(":{");

                    for (i in 1..datos.count() - 1){

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
                        mostarPresupuestos(view,presupuestos);
                    }


                }else{

                    Toast.makeText(this.context,"Error: No hay resultados",Toast.LENGTH_LONG).show()
                    obtenerDatosVolleyPresupuestos(view)
                }


            }else{

                obtenerDatosVolleyPresupuestos(view)
            }


        }catch (ex:Exception){

            Toast.makeText(this.context,ex.message.toString(),Toast.LENGTH_LONG).show()
        }

    }

    fun busquedaPartes(view: View){

        val JSON: MediaType =  MediaType.get("application/json; charset=utf-8")
        val jsonObject= JSONObject();

        jsonObject.put("busqueda", edtBuscarBAJ.text.toString());

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/partes/buscar")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();
            val partes:MutableList<Parte> = mutableListOf();

            if( edtBuscarBAJ.text.toString().isNotEmpty()){

                if (cuerpo.length > 2){

                    var datos = cuerpo.toString().split(":{");

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
                        mostarPartes(view,partes);
                    }


                }else{

                    Toast.makeText(this.context,"Error: No hay resultados",Toast.LENGTH_LONG).show()
                    obtenerDatosVolleyPartes(view)
                }


            }else{

                obtenerDatosVolleyPartes(view)
            }


        }catch (ex:Exception){

            Toast.makeText(this.context,ex.message.toString(),Toast.LENGTH_LONG).show()
        }

    }


    fun busquedaFacturas(view: View){

        val JSON: MediaType =  MediaType.get("application/json; charset=utf-8")
        val jsonObject= JSONObject();

        jsonObject.put("busqueda", edtBuscarBAJ.text.toString());

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/facturas/buscar")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();
            val facturas:MutableList<Factura> = mutableListOf();

            if( edtBuscarBAJ.text.toString().isNotEmpty()){

                if (cuerpo.length > 2){

                    var datos = cuerpo.toString().split(":{");


                    for (i in 1..datos.count() - 1){


                        var factura = Factura(
                            datos[i].split(":[")[2].split(']')[1].split(',')[5].split(':')[1].replace('}',' ').trim().toInt(),
                            datos[i].split(":[")[2].split(']')[0],
                            datos[i].split(":[")[1].split(']')[0],
                            datos[i].split(":[")[2].split(']')[1].split(',')[1].split(':')[1],
                            datos[i].split(":[")[2].split(']')[1].split(',')[2].split(':')[1].toDouble(),
                            datos[i].split(":[")[2].split(']')[1].split(',')[3].split(':')[1].toDouble(),
                            datos[i].split(":[")[2].split(']')[1].split(',')[4].split(':')[1]
                        );

                        facturas.add(factura);
                        mostarFacturas(view,facturas);

                    }

                }else{

                    Toast.makeText(this.context,"Error: No hay resultados",Toast.LENGTH_LONG).show()
                    obtenerDatosVolleyFactura(view)
                }


            }else{

                obtenerDatosVolleyFactura(view)
            }


        }catch (ex:Exception){

            Toast.makeText(this.context,ex.message.toString(),Toast.LENGTH_LONG).show()
        }

    }

    fun busquedaAlmacen(view: View){

        val JSON: MediaType =  MediaType.get("application/json; charset=utf-8")
        val jsonObject= JSONObject();

        jsonObject.put("busqueda", edtBuscarBAJ.text.toString());

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/productos/buscar")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();
            val productos:MutableList<Almacen> = mutableListOf();

            if( edtBuscarBAJ.text.toString().isNotEmpty()){

                if (cuerpo.length > 2){

                    var datos = cuerpo.toString().split(":{");

                    for (i in 1..datos.count() - 1){

                        var producto = Almacen(
                            datos[i].split(',')[5].split(':')[1].split('}')[0].toInt(),
                            datos[i].split(',')[0].split(':')[1],
                            datos[i].split(',')[2].split(':')[1],
                            datos[i].split(',')[3].split(':')[1].toDouble(),
                            datos[i].split(',')[1].split(':')[1].toInt(),
                            datos[i].split(',')[4].split(':')[1]
                        );
                        productos.add(producto);
                        mostarProductos(view,productos);
                    }


                }else{

                    Toast.makeText(this.context,"Error: No hay resultados",Toast.LENGTH_LONG).show()
                    obtenerDatosVolleyProductos(view)
                }


            }else{

                obtenerDatosVolleyProductos(view)
            }


        }catch (ex:Exception){

            Toast.makeText(this.context,ex.message.toString(),Toast.LENGTH_LONG).show()
        }

    }

    fun busquedaEmpleados(view: View){

        val JSON: MediaType =  MediaType.get("application/json; charset=utf-8")
        val jsonObject= JSONObject();

        jsonObject.put("busqueda", edtBuscarBAJ.text.toString());

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/empleados/buscar")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();
            val empleados:MutableList<Empleado> = mutableListOf();

            if( edtBuscarBAJ.text.toString().isNotEmpty()){

                if (cuerpo.length > 2){

                    var datos = cuerpo.toString().split(":{");

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
                            ,datos[i].split(',')[10].split(':')[1],
                            datos[i].split(',')[8].split(':')[1],
                            datos[i].split(',')[7].split(':')[1]
                            ,datos[i].split(',')[18],
                            datos[i].split(',')[14].split(":[")[1]
                        );
                        empleados.add(empleado);
                        mostarEmpleados(view,empleados);

                    }

                }else{

                    Toast.makeText(this.context,"Error: No hay resultados",Toast.LENGTH_LONG).show()
                    obtenerDatosVolleyEmpleados(view)
                }


            }else{

                obtenerDatosVolleyEmpleados(view)
            }


        }catch (ex:Exception){

            Toast.makeText(this.context,ex.message.toString(),Toast.LENGTH_LONG).show()
        }

    }

    fun busquedaDelegaciones(view: View){

        val JSON: MediaType =  MediaType.get("application/json; charset=utf-8")
        val jsonObject= JSONObject();

        jsonObject.put("busqueda", edtBuscarBAJ.text.toString());

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/delegaciones/buscar")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();
            val delegaciones:MutableList<Delegacion> = mutableListOf();

            if( edtBuscarBAJ.text.toString().isNotEmpty()){

                if (cuerpo.length > 2){

                    var datos = cuerpo.toString().split(":{");

                    for (i in 1..datos.count() - 1){

                        var delegacion = Delegacion(

                            datos[i].split(',')[0].split(':')[1].toInt(),
                            datos[i].split(',')[1].split(':')[1],
                            datos[i].split(',')[2].split(':')[1],
                            datos[i].split(',')[3].split(':')[1],
                            datos[i].split(',')[4].split(':')[1],
                            datos[i].split(',')[5].split(':')[1],
                            datos[i].split(',')[6].split(':')[1],
                            datos[i].split(',')[7].split(':')[1].split('}')[0]
                        );
                        delegaciones.add(delegacion);
                    }

                    mostarDelegaciones(view,delegaciones);

                }else{

                    Toast.makeText(this.context,"Error: No hay resultados",Toast.LENGTH_LONG).show()
                    obtenerDatosVolleyDelegaciones(view)
                }


            }else{

                obtenerDatosVolleyDelegaciones(view)
            }


        }catch (ex:Exception){

            Toast.makeText(this.context,ex.message.toString(),Toast.LENGTH_LONG).show()
        }

    }


    fun busquedaAlbaranes(view: View){

        val JSON: MediaType =  MediaType.get("application/json; charset=utf-8")
        val jsonObject= JSONObject();

        jsonObject.put("busqueda", edtBuscarBAJ.text.toString());

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/albaranes/buscar")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();
            val albaranes:MutableList<Albaran> = mutableListOf();

            if( edtBuscarBAJ.text.toString().isNotEmpty()){

                if (cuerpo.length > 2){

                    var datos = cuerpo.toString().split(":{");

                    for (i in 1..datos.count() - 1) {

                        var albaran = Albaran(
                            datos[i].split(":[")[0].split(',')[0].split(':')[1].toInt(),
                            datos[i].split(":[")[1].split(']')[0],
                            datos[i].split(":[")[0].split(',')[1].split(':')[1],
                            datos[i].split(":[")[0].split(',')[2].split(':')[1]
                        );

                        albaranes.add(albaran);

                    }
                    mostarAlbaranes(view,albaranes);

                }else{

                    Toast.makeText(this.context,"Error: No hay resultados",Toast.LENGTH_LONG).show()
                    obtenerDatosVolleyAlbaran(view)
                }


            }else{

                obtenerDatosVolleyAlbaran(view)
            }


        }catch (ex:Exception){

            Toast.makeText(this.context,ex.message.toString(),Toast.LENGTH_LONG).show()
        }

    }


    fun busquedaClientes(view: View){

        val JSON: MediaType =  MediaType.get("application/json; charset=utf-8")
        val jsonObject= JSONObject();

        jsonObject.put("busqueda", edtBuscarBAJ.text.toString());

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/clientes/buscar")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();
            val personas:MutableList<Cliente> = mutableListOf();

            if( edtBuscarBAJ.text.toString().isNotEmpty()){

                if(cuerpo.length > 2){

                    var datos = cuerpo.split(":{")


                    for (i in 1..datos.count() -1) {

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

                    mostarPersonas(view,personas);

                }
                else{

                    Toast.makeText(this.context,"Error: No hay resultados",Toast.LENGTH_LONG).show()

                    obtenerDatosVolleyCliente(view)
                }

            }
            else{

                obtenerDatosVolleyCliente(view)
            }


        }catch (ex: Exception){

            Toast.makeText(this.context,ex.message.toString(),Toast.LENGTH_LONG).show()

        }

    }



    fun mostarDelegaciones(view: View,delegaciones:MutableList<Delegacion> ){

        val adaptador = AdaptadorDelegacionBAJ(view.context,delegaciones)

        lvBajas.adapter = adaptador

    }

    fun mostarProductos(view: View,productos:MutableList<Almacen> ){

        val adaptador = AdaptadorAlmacenBAJ(view.context,productos)

        lvBajas.adapter = adaptador
    }

    fun mostarPresupuestos(view: View,presupuestos:MutableList<Presupuesto> ){

        val adaptador = AdaptadorPrespuestoBAJ(view.context,presupuestos)

        lvBajas.adapter = adaptador
    }

    fun mostarPartes(view: View,partes:MutableList<Parte> ){

        val adaptador = AdaptadorPartesBAJ(view.context,partes)

        lvBajas.adapter = adaptador
    }

    fun mostarPersonas(view: View,personas:MutableList<Cliente> ){

        val adaptador = AdaptadorClienteBAJ(view.context,personas)

        lvBajas.adapter = adaptador
    }

    fun mostarAlbaranes(view: View,albaranes:MutableList<Albaran> ){

        val adaptador = AdaptadorAlbaranesBAJ(view.context,albaranes)

        lvBajas.adapter = adaptador
    }

    fun mostarFacturas(view: View,facturas:MutableList<Factura> ){

        val adaptador = AdaptadorFacturasBAJ(view.context,facturas)

        lvBajas.adapter = adaptador
    }

    fun mostarEmpleados(view: View,empleados:MutableList<Empleado> ){

        val adaptador = AdaptadorEmpleadoBAJ(view.context,empleados)

        lvBajas.adapter = adaptador
    }

}