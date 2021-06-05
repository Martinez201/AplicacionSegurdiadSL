package com.example.proyectoampliacion.Fragmentos.Mantenimiento

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.proyectoampliacion.Adaptadores.*
import com.example.proyectoampliacion.Classes_Auxiliares.*
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.fragment_altas.*
import kotlinx.android.synthetic.main.fragment_bajas.*
import kotlinx.android.synthetic.main.fragment_listar.*
import org.json.JSONObject

class ListarFragment : Fragment() {


    var tipo:Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {



            when(it.getInt("tipo")){

                0->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Albaranes";
                    obtenerDatosVolleyAlbaran(view);
                    tipo = 0;
                }
                1->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Almacén";
                    obtenerDatosVolleyProductos(view);
                    tipo = 1;
                }
                2->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Clientes";
                    obtenerDatosVolleyCliente(view);
                    tipo = 2;
                }
                3->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Delegación";
                    obtenerDatosVolleyDelegaciones(view);
                    tipo = 3;
                }
                4->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Empleados";
                    obtenerDatosVolleyEmpleados(view);
                    tipo = 4;
                }
                5->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Facturas";
                    obtenerDatosVolleyFactura(view);
                    tipo = 5;
                }
                6->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Partes";
                    obtenerDatosVolleyPartes(view);
                    tipo = 6;
                }
                7->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Presupuestos";
                    obtenerDatosVolleyPresupuestos(view);
                    tipo = 7;
                }
            }

        }

        btnBuscar.setOnClickListener { it ->


        }

    }



    fun obtenerDatosVolleyDelegaciones(view: View){

        val queue = Volley.newRequestQueue(this.context)
        val url = "http://192.168.1.141/symfony/web/app.php/movil/delegaciones"
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
                                datos[1].split(',')[5].split(':')[1],
                                datos[1].split(',')[6].split(':')[1].split('}')[0]
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
        val url = "http://192.168.1.141/symfony/web/app.php/movil/productos"
        val productos:MutableList<Almacen> = mutableListOf();

        val jsObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->

                    var datos = response.toString().split(":{");



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
        val url = "http://192.168.1.141/symfony/web/app.php/movil/presupuestos"
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
        val url = "http://192.168.1.141/symfony/web/app.php/movil/empleados"
        val empleados:MutableList<Empleado> = mutableListOf();

        val jsObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->

                    var datos = response.toString().split(":{");


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
                                ,datos[i].split(',')[10].split(':')[1]);
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
        val url = "http://192.168.1.141/symfony/web/app.php/movil/facturas"
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
                                datos[1].split(":[")[2].split(']')[1].split(',')[4].split(':')[1]
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
        val url = "http://192.168.1.141/symfony/web/app.php/movil/albaranes"
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
                                datos[1].split(":[")[0].split(',')[2].split(':')[1]
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
        val url = "http://192.168.1.141/symfony/web/app.php/movil/clientes"
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
        val url = "http://192.168.1.141/symfony/web/app.php/movil/partes"
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
                                        ""
                            );

                            var delegacion = Delegacion(

                                    datos[i].split(":[")[3].split(',')[0].toInt(),
                                    datos[i].split(":[")[3].split(',')[4],
                                    datos[i].split(":[")[3].split(',')[1],
                                    datos[i].split(":[")[3].split(',')[3],
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
                                    delegacion

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


    fun mostarDelegaciones(view: View,delegaciones:MutableList<Delegacion> ){

        val adaptador = MiAdaptadorDelegacion(view.context,delegaciones)

        lvClientes.adapter = adaptador

    }

    fun mostarProductos(view: View,productos:MutableList<Almacen> ){

        val adaptador = MiAdaptadorAlmacen(view.context,productos)

        lvClientes.adapter = adaptador
    }

    fun mostarPresupuestos(view: View,presupuestos:MutableList<Presupuesto> ){

        val adaptador = MiAdaptadorPresupuesto(view.context,presupuestos)

        lvClientes.adapter = adaptador
    }

    fun mostarPartes(view: View,partes:MutableList<Parte> ){

        val adaptador = MiAdaptadorPartes(view.context,partes)

        lvClientes.adapter = adaptador
    }

    fun mostarPersonas(view: View,personas:MutableList<Cliente> ){

        val adaptador = MiAdaptadorClientes(view.context,personas)

        lvClientes.adapter = adaptador
    }

    fun mostarAlbaranes(view: View,albaranes:MutableList<Albaran> ){

        val adaptador = MiAdaptadorAlbaranes(view.context,albaranes)

        lvClientes.adapter = adaptador
    }

    fun mostarFacturas(view: View,facturas:MutableList<Factura> ){

        val adaptador = MiAdaptadorFactura(view.context,facturas)

        lvClientes.adapter = adaptador
    }

    fun mostarEmpleados(view: View,empleados:MutableList<Empleado> ){

        val adaptador = MiAdaptadorEmpleados(view.context,empleados)

        lvClientes.adapter = adaptador
    }




}