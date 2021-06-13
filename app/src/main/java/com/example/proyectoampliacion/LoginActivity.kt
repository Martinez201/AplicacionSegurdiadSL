package com.example.proyectoampliacion

import android.content.Intent
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
            var cuerpo = response.body()?.string().toString();

            if(cuerpo.length > 2){

                 val provincia = cuerpo.split(":{")[1].split(',')[6].split(':')[1] ;
                 val admin = cuerpo.split(":{")[1].split(',')[11].split(':')[1]
                 val instalador =  cuerpo.split(":{")[1].split(',')[12].split(':')[1]
                 val gestor = cuerpo.split(":{")[1].split(',')[13].split(':')[1]
                 val comercial = cuerpo.split(":{")[1].split(',')[14].split(':')[1]
                 val empleado = cuerpo.split(":{")[1].split(',')[16].split(':')[1]

                val intent:Intent = Intent(this,MainActivity::class.java)
                intent.putExtra("provincia",provincia)
                intent.putExtra("admin",admin)
                intent.putExtra("instalador",instalador)
                intent.putExtra("gestor",gestor)
                intent.putExtra("comercial",comercial)
                intent.putExtra("empleado",empleado)
                startActivity(intent)

            }
            else{

                Toast.makeText(applicationContext,"Error: Usuario o Dni incorrectos",Toast.LENGTH_LONG).show()
            }



        }catch (e: IOException){

            Toast.makeText(applicationContext,e.message.toString(),Toast.LENGTH_SHORT).show()

        }

    }


}