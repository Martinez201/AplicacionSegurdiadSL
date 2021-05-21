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
import com.example.proyectoampliacion.Adaptadores.MiAdaptadorClientes
import com.example.proyectoampliacion.Classes_Auxiliares.Albaran
import com.example.proyectoampliacion.Classes_Auxiliares.Cliente
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
                }
                5->{
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Facturas";
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

    fun obtenerDatosVolleyAlbaran(view: View){

        val queue = Volley.newRequestQueue(this.context)
        val url = "http://172.28.255.238/prueba/web/app.php/movil/albaranes"
        val albaranes:MutableList<Albaran> = mutableListOf();

        val jsObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->

                    var datos = response.toString().split("},")

                    for (i in 0..datos.count() - 1){

                        var ejemplo = datos[i].split(":{")[1]
                        var definitivo =  ejemplo.split(",")
                        //var albaran = Albaran(definitivo[0].split(':')[1].toInt(),definitivo[1].split(':')[1].toInt(),definitivo[0].split(':')[1],definitivo[0].split(':')[1]);
                       // albaranes.add(albaran);
                        //mostarPersonas(view,personas);
                        tvPruebas.text = definitivo[1].split(':')[1]
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
        val url = "http://172.28.255.238/prueba/web/app.php/movil/clientes"
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



}