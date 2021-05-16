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
                    tvTituloLis.setText("Listados Albaranes");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Albaranes";
                }
                1->{
                    tvTituloLis.setText("Listados Almacén");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Almacén";
                }
                2->{
                    tvTituloLis.setText("Listados Clientes");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Clientes";
                }
                3->{
                    tvTituloLis.setText("Listados Delegación");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Delegación";
                }
                4->{
                    tvTituloLis.setText("Listados Empleados");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Empleados";
                }
                5->{
                    tvTituloLis.setText("Listados Facturas");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Facturas";
                }
                6->{
                    tvTituloLis.setText("Listados Partes");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Partes";
                }
                7->{
                    tvTituloLis.setText("Listados Presupuestos");
                    (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Listados Presupuestos";
                }
            }

        }

        obtenerDatosVolley()
    }


    fun obtenerDatosVolley(){

        val queue = Volley.newRequestQueue(this.context)
        val url = "http://192.168.1.141/symfony/web/app.php/movil/clientes"

        val jsObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->

                    tvMostrar.text = response.toString().split("},")[1].split(":{")[1] // queda partir por , y después por :

                },
                Response.ErrorListener { error ->

                    tvMostrar.text = error.message.toString()
                    
                }
        )

        queue.add(jsObjectRequest);


        try {

        } catch (e: Exception) {

            Toast.makeText(this.context,e.message,Toast.LENGTH_SHORT).show();

        } finally {

        }


    }



}