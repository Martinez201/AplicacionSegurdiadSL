package com.example.proyectoampliacion.Fragmentos.Mantenimiento

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proyectoampliacion.Adaptadores.MiAdaptadorAlbaranes
import com.example.proyectoampliacion.Adaptadores.MiAdaptadorClientes
import com.example.proyectoampliacion.Adaptadores.MiAdaptadorEmpleados
import com.example.proyectoampliacion.Adaptadores.MiAdaptadorFactura
import com.example.proyectoampliacion.Classes_Auxiliares.Albaran
import com.example.proyectoampliacion.Classes_Auxiliares.Cliente
import com.example.proyectoampliacion.Classes_Auxiliares.Empleado
import com.example.proyectoampliacion.Classes_Auxiliares.Factura
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.fragment_altas.*
import kotlinx.android.synthetic.main.fragment_bajas.*
import kotlinx.android.synthetic.main.fragment_listar.*

class ListarFragment : Fragment() {

    var datos:String = "";

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
                }
                1->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Almacén";
                }
                2->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Clientes";
                    obtenerDatosVolleyCliente(view);
                }
                3->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Delegación";
                }
                4->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Empleados";
                    obtenerDatosVolleyEmpleados(view);
                }
                5->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Facturas";
                    obtenerDatosVolleyFactura(view);
                }
                6->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Partes";
                }
                7->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Presupuestos";
                }
            }

        }


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

                    tvPrueba.text = empleados.count().toString()
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

                    tvPrueba.text = datos[1].split(":[")[2].split(']')[1].split(',')[4].split(':')[1]

                    for (i in 1..datos.count() - 1){

                        var trozo1 = datos[i].split(":[")[1]
                        var trozo2 = datos[i].split(":[")[2]


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

                    var datos = response.toString().split("},")


                    for (i in 0..datos.count() - 1){

                        var ejemplo = datos[i].split(":{")[1]
                        var definitivo =  ejemplo.split(",")
                        var tamanio:Int = definitivo[3].split("}}")[0].length
                        var albaran = Albaran(definitivo[0].split(':')[1].toInt(),definitivo[3].substring(11,tamanio).toInt(),definitivo[1].split(':')[1],definitivo[2].split(':')[1]);
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

                    var datos = response.toString().split("},")

                    for (i in 0..datos.count() - 1){

                        var ejemplo = datos[i].split(":{")[1]
                        var definitivo =  ejemplo.split(",")
                        var persona = Cliente(definitivo[0].split(':')[1],definitivo[1].split(':')[1],definitivo[8].split(':')[1]+" "+definitivo[9]+""+definitivo[10],definitivo[2].split(':')[1],definitivo[11].split(':')[1],definitivo[6].split(':')[1],definitivo[12].split(':')[1],definitivo[13].split(':')[1].split('}')[0].toInt());
                        personas.add(persona);
                        mostarPersonas(view,personas);
                        //corregir error en cuanto a la direccion del cliente da error si se pone por ejemplo ==> Avdenida Andalucia 18
                    }
                },
                { error ->

                    Toast.makeText(view.context,error.message.toString(),Toast.LENGTH_SHORT).show();
                }
        )

        queue.add(jsObjectRequest);

    }

    fun obtenerDatosVolleyEmpleado(view: View){

        val queue = Volley.newRequestQueue(this.context)
        val url = "http://192.168.1.141/symfony/web/app.php/movil/clientes"
        val personas:MutableList<Cliente> = mutableListOf();

        val jsObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->

                    var datos = response.toString().split("},")

                    for (i in 0..datos.count() - 1){

                        var ejemplo = datos[i].split(":{")[1]
                        var definitivo =  ejemplo.split(",")
                        var persona = Cliente(definitivo[0].split(':')[1],definitivo[1].split(':')[1],definitivo[8].split(':')[1]+" "+definitivo[9]+""+definitivo[10],definitivo[2].split(':')[1],definitivo[11].split(':')[1],definitivo[6].split(':')[1],definitivo[12].split(':')[1],definitivo[13].split(':')[1].split('}')[0].toInt());
                        personas.add(persona);
                        mostarPersonas(view,personas);
                        //corregir error en cuanto a la direccion del cliente da error si se pone por ejemplo ==> Avdenida Andalucia 18
                    }
                },
                { error ->

                    Toast.makeText(view.context,error.message.toString(),Toast.LENGTH_SHORT).show();
                }
        )

        queue.add(jsObjectRequest);

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