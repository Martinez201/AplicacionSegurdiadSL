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
import com.example.proyectoampliacion.Adaptadores.MiAdaptadorDelegacion
import com.example.proyectoampliacion.Adaptadores.MiAdaptadorDelegacionesConsulta
import com.example.proyectoampliacion.Classes_Auxiliares.Delegacion
import com.example.proyectoampliacion.Classes_Auxiliares.Persona
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.fragment_consulta_cliente.*
import kotlinx.android.synthetic.main.fragment_consulta_delegacion.*
import kotlinx.android.synthetic.main.fragment_listar.*
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.Exception


class ConsultaDelegacionFragment : Fragment() {

    val URL_BASE:String = "http://192.168.1.141/symfony/web/app.php/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consulta_delegacion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)

        obtenerDatosVolleyDelegaciones(view)

        btnBuscarDe.setOnClickListener(){

            if (txtBuscarDE.text.toString().isNotEmpty()){

                busquedaDelegaciones(view)
            }
            else{

                obtenerDatosVolleyDelegaciones(view);
            }
        }
    }

    fun busquedaDelegaciones(view: View){

        val JSON: MediaType =  MediaType.get("application/json; charset=utf-8")
        val jsonObject= JSONObject();

        jsonObject.put("busqueda",txtBuscarDE.text.toString());

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


            if(cuerpo.length > 2){

                var datos = cuerpo.split(":{")

                Toast.makeText(view.context,datos[1], Toast.LENGTH_SHORT).show();

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
                    )
                    delegaciones.add(delegacion);
                }

                mostarDelegaciones(view,delegaciones);

            }
            else{

                Toast.makeText(this.context,"Error: No hay resultados",Toast.LENGTH_LONG).show()
            }



        }catch (ex: Exception){

            Toast.makeText(this.context,ex.message.toString(),Toast.LENGTH_LONG).show()

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
                        datos[1].split(',')[5].split(':')[1],
                        datos[1].split(',')[6].split(':')[1],
                        datos[1].split(',')[7].split(':')[1].split('}')[0]
                    );
                    delegaciones.add(delegacion);
                }

                mostarDelegaciones(view,delegaciones);

            },
            { error ->

                Toast.makeText(view.context,error.message.toString(), Toast.LENGTH_SHORT).show();
            }
        )

        queue.add(jsObjectRequest);

    }


    fun mostarDelegaciones(view: View,delegaciones:MutableList<Delegacion> ){

        val adaptador = MiAdaptadorDelegacionesConsulta(view.context,delegaciones)

        lvDelegaciones.adapter = adaptador

    }

}