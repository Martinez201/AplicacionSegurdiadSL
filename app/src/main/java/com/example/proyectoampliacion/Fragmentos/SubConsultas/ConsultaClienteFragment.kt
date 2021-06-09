package com.example.proyectoampliacion.Fragmentos.SubConsultas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proyectoampliacion.Adaptadores.MiAdaptadorPersonas
import com.example.proyectoampliacion.Classes_Auxiliares.Persona
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.fragment_consulta_cliente.*

class ConsultaClienteFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consulta_cliente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)

        obtenerDatosVolleyCliente(view);
    }


    fun obtenerDatosVolleyCliente(view: View){

        val queue = Volley.newRequestQueue(this.context)
        val url = "http://192.168.1.141/symfony/web/app.php/movil/clientes"
        val personas:MutableList<Persona> = mutableListOf();

        val jsObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                var datos = response.toString().split(":{");


                for (i in 1..datos.count() - 1){



                    var persona = Persona(
                        datos[i].split(',')[0].split(':')[1].split('"')[1],
                        datos[i].split(',')[1].split(':')[1].split('"')[1],
                        datos[i].split(',')[11].split(':')[1].split('}')[0].split('"')[0]
                    )
                    personas.add(persona);



                }
                Toast.makeText(view.context,personas.size.toString(), Toast.LENGTH_SHORT).show()

                mostarPersonas(view,personas);
            },
            { error ->

                Toast.makeText(view.context,error.message.toString(), Toast.LENGTH_SHORT).show();
            }
        )

        queue.add(jsObjectRequest);

    }


    fun mostarPersonas(view: View,personas:MutableList<Persona> ){

        val adaptador = MiAdaptadorPersonas(view.context,personas)

        lvPersonas.adapter = adaptador
    }
}