package com.example.proyectoampliacion

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        btnIniciar.setOnClickListener(){

            comprobar();

        }
    }

    fun comprobar(){



        var JSON:MediaType =  MediaType.get("application/json; charset=utf-8")

        val jsonObject= JSONObject();

        jsonObject.put("usuario",txtUsuario.text.toString());
        jsonObject.put("dni",txtPassword.text.toString());

        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: Request = Request.Builder() //Create a request
            .url("http://192.168.1.141/symfony/web/app.php/movil/login")
            .post(body) //Indicated as get request
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()


            if(response.body()?.string().toString().length > 2){


                // val provincia = response.body()?.string().toString().split(":{")[1].split(',')[6].split(':')[1] ;
                //val admin = response.body()?.string().toString().toString().split(":{")[1].split(',')[11].split(':')[1]
                // val instalador =  response.body()?.string().toString().toString().split(":{")[1].split(',')[12].split(':')[1]
                //val gestor = response.body()?.string().toString().toString().split(":{")[1].split(',')[13].split(':')[1]
                //val comercial = response.body()?.string().toString().toString().split(":{")[1].split(',')[14].split(':')[1]

            }
            else{

                Toast.makeText(applicationContext,"Error: Usuario o Dni incorrectos",Toast.LENGTH_LONG).show()
            }



        }catch (e: IOException){

            Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()

        }

    }


}