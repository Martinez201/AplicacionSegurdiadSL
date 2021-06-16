package com.example.proyectoampliacion.Adaptadores

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.proyectoampliacion.Classes_Auxiliares.Albaran
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_baj_albaran.view.*
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.IOException

class AdaptadorAlbaranesBAJ (private val mContext: Context, private val listaAlbaranes: List<Albaran>): ArrayAdapter<Albaran>(mContext,0,listaAlbaranes)  {

    val URL_BASE:String = "http://192.168.1.141/symfony/web/app.php/"

    var elemento:Int = 0;

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_baj_albaran,parent,false);

        val elementoActual = listaAlbaranes[position];

        layout.tvFechaALBAJ.text = elementoActual.fecha;
        layout.tvProveedorALBAJ.text = elementoActual.proveedor;
        layout.tvIdALBAJ.text = elementoActual.id.toString();

        layout.btnBorrarAL.setBackgroundColor(Color.RED)

        layout.btnBorrarAL.setOnClickListener(){

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

            borrarAlbaran()

        }

        builder.setNegativeButton("NO") { dialog, which ->

        }

        builder.show()

    }


    fun borrarAlbaran(){

        var JSON: MediaType =  MediaType.get("application/json; charset=utf-8")

        val jsonObject= JSONObject();

        jsonObject.put("id",elemento);


        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/albaran/borrar")
            .post(body) //Indicated as get request
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()


            if (response.body()?.string().toString().contains("Succes")){

                Toast.makeText(this.context,"Albarán borrado con éxito", Toast.LENGTH_SHORT).show()
            }
            else{

                Toast.makeText(this.context,"Error: no se ha podido borrar el albarán", Toast.LENGTH_SHORT).show()
            }

        }catch (e: IOException){

            Toast.makeText(this.context,e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}