package com.example.proyectoampliacion.Fragmentos.Mantenimiento

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.proyectoampliacion.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_bajas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BajasFragment : Fragment() {
   

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
 
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bajas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {

            //prueba.setText("TIPO ==> " + it.getInt("tipo"))

        }

        obtenerDatosVolley();

    }

    fun obtenerDatosVolley(){


       val queue = Volley.newRequestQueue(this.context)
        val url = "http://192.168.1.141/symfony/web/app.php/movil/clientes"

        val jsObjectRequest = JsonObjectRequest(
                 Request.Method.GET, url, null,
                 Response.Listener { response -> prueba.text = response.toString()
                 },
                 Response.ErrorListener { error ->
                     prueba.text = "Comete una mierda"
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