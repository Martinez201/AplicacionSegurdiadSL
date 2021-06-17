package com.example.proyectoampliacion.Adaptadores

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.proyectoampliacion.Classes_Auxiliares.Cliente
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_baj_cliente.view.*
import kotlinx.android.synthetic.main.vista_clientes.view.*
import kotlinx.android.synthetic.main.vista_mod_clientes.view.*
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.IOException

class AdaptadorClienteBAJ (private val mContext: Context, private val listaClientes: List<Cliente>):
    ArrayAdapter<Cliente>(mContext,0,listaClientes) {

    val URL_BASE:String = "http://192.168.1.141/symfony/web/app.php/"

    var elemento:Int = 0;

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_baj_cliente,parent,false);

        val elementoActual = listaClientes[position];

        layout.tvNombreBAJCLI.text = elementoActual.nombre;
        layout.tvApellidosBAJCLI.text = elementoActual.apellidos;
        layout.tvDireccionBAJCLI.text = elementoActual.direccion + " "+ elementoActual.ciudad + " " +elementoActual.provincia;
        layout.tvTelefonoBAJCLI.text = elementoActual.telefono;

        if (elementoActual.estado.equals("true")){

            layout.btnEstadoBAJCLI.text = "ALTA";
            layout.btnEstadoBAJCLI.setTextColor(Color.WHITE);
            layout.btnEstadoBAJCLI.setBackgroundColor(Color.parseColor("#28a745"));

        }else{

            layout.btnEstadoBAJCLI.text = "BAJA";
            layout.btnEstadoBAJCLI.setTextColor(Color.WHITE);
            layout.btnEstadoBAJCLI.setBackgroundColor(Color.parseColor("#dc3545"));
        }

        layout.btnBorrarBAJCLI.setBackgroundColor(Color.parseColor("#343a40"));
        layout.btnBorrarBAJCLI.setOnClickListener(){

            elemento = elementoActual.id
            dialogoBorrar()
        }


        return layout;
    }

    fun dialogoBorrar(){

        val builder = AlertDialog.Builder(this.context)
        builder.setTitle("Atención")
        builder.setMessage("¿Está seguro de que quiere borrar el registro?")


        builder.setPositiveButton("SI") { dialog, which ->

            borrarCliente()

        }

        builder.setNegativeButton("NO") { dialog, which ->

        }

        builder.show()

    }

    fun borrarCliente(){

        var JSON: MediaType =  MediaType.get("application/json; charset=utf-8")

        val jsonObject= JSONObject();

        jsonObject.put("id",elemento);


        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/cliente/borrar")
            .post(body) //Indicated as get request
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()


            if (response.body()?.string().toString().contains("Succes")){

                Toast.makeText(this.context,"Cliente borrado con éxito", Toast.LENGTH_SHORT).show()
            }
            else{

                Toast.makeText(this.context,"Error: no se ha podido borrar el Cliente", Toast.LENGTH_SHORT).show()
            }

        }catch (e: IOException){

            Toast.makeText(this.context,e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}