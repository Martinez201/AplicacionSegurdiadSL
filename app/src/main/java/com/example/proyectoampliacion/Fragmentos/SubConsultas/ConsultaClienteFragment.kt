package com.example.proyectoampliacion.Fragmentos.SubConsultas

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proyectoampliacion.Adaptadores.MIAdaptadorPersonas2
import com.example.proyectoampliacion.Adaptadores.MiAdaptadorPersonas
import com.example.proyectoampliacion.Classes_Auxiliares.Persona
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_consulta_cliente.*
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception


class ConsultaClienteFragment : Fragment() {

    var tipo = 0;
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

        arguments.let {

            tipo = it?.getInt("tipo")!!

        }

        obtenerDatosVolleyCliente(view);

        btnBuscarPer.setOnClickListener() {

            if (txtBuscar.text.toString().isNotEmpty()){
                busquedaClientes(view)
            }
            else{

                obtenerDatosVolleyCliente(view);
            }


        }
    }

    fun busquedaClientes(view: View){

        val JSON:MediaType =  MediaType.get("application/json; charset=utf-8")
        val jsonObject= JSONObject();

        jsonObject.put("busqueda",txtBuscar.text.toString());

        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url("http://192.168.1.141/symfony/web/app.php/movil/clientes/buscar")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()
            var cuerpo = response.body()?.string().toString();
            val personas:MutableList<Persona> = mutableListOf();


            if(cuerpo.length > 2){

                var datos = cuerpo.split(":{")

                for (i in 1..datos.count() - 1) {

                    var persona = Persona(
                        datos[i].split(',')[0].split(':')[1],
                        datos[i].split(',')[1].split(':')[1],
                        datos[i].split(',')[11].split(':')[1].split('}')[0]
                    )
                    personas.add(persona);
                }

                mostarPersonas(view,personas);

            }
            else{

                Toast.makeText(this.context,"Error: No hay resultados",Toast.LENGTH_LONG).show()
            }



        }catch (ex:Exception){

           Toast.makeText(this.context,ex.message.toString(),Toast.LENGTH_LONG).show()

        }

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
        val adaptador2 = MIAdaptadorPersonas2(view.context,personas)

        if (tipo == 5){
            lvPersonas.adapter = adaptador2

        }else{
            lvPersonas.adapter = adaptador

        }

    }

}