package com.example.proyectoampliacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnIniciar.setOnClickListener(){

            comprobar();

        }
    }

    fun comprobar(){

        val queue = Volley.newRequestQueue(applicationContext)
        val url = "http://192.168.1.141/symfony/web/app.php/movil/login"
        val jsonObject= JSONObject();

        jsonObject.put("usuario",txtUsuario.text.toString());
        jsonObject.put("password",txtPassword.text.toString());

        val jsonObjectRequest = JsonObjectRequest(url,jsonObject,

            { response ->

                Toast.makeText(applicationContext,response.toString(), Toast.LENGTH_SHORT).show();

            },

            {   it->

                Toast.makeText(applicationContext,it.message.toString(), Toast.LENGTH_SHORT).show();
            }

        )

        queue.add(jsonObjectRequest);
    }


}