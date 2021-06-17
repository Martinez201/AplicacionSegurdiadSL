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
import com.example.proyectoampliacion.Classes_Auxiliares.Factura
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_baj_factura.view.*
import kotlinx.android.synthetic.main.vista_mod_factura.view.*
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.IOException

class AdaptadorFacturasBAJ (private val mContext: Context, private val listaFacturas: List<Factura>): ArrayAdapter<Factura>(mContext,0,listaFacturas) {

    val URL_BASE:String = "http://192.168.1.141/symfony/web/app.php/"

    var elemento:Int = 0;


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout =
            LayoutInflater.from(mContext).inflate(R.layout.vista_baj_factura, parent, false);

        val elementoActual = listaFacturas[position];

        layout.tvClienteFACBAJ.text = elementoActual.cliente.toString();
        layout.tvEmpleadoFACBAJ.text = elementoActual.empleado.toString();
        layout.tvEmisionFACBAJ.text = elementoActual.fecha
        layout.tvConceptoFACBAJ.text = elementoActual.concepto;

        layout.btnEditarFACBAJ.setBackgroundColor(Color.RED)

        layout.btnEditarFACBAJ.setOnClickListener() {

            var bundle = Bundle();

            bundle.putInt("elemento", elementoActual.id)

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

            borrarFactura()
        }

        builder.setNegativeButton("NO") { dialog, which ->

        }

        builder.show()

    }

    fun borrarFactura(){

        var JSON: MediaType =  MediaType.get("application/json; charset=utf-8")

        val jsonObject= JSONObject();

        jsonObject.put("id",elemento);


        val client = OkHttpClient()

        val body: RequestBody = RequestBody.create(JSON,jsonObject.toString())

        val request: okhttp3.Request = okhttp3.Request.Builder() //Create a request
            .url(URL_BASE+"movil/factura/borrar")
            .post(body) //Indicated as get request
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        var llamada: Call = client.newCall(request)

        try {

            var response = llamada.execute()


            if (response.body()?.string().toString().contains("Succes")){

                Toast.makeText(this.context,"Factura borrada con éxito", Toast.LENGTH_SHORT).show()
            }
            else{

                Toast.makeText(this.context,"Error: no se ha podido borrar la Factura", Toast.LENGTH_SHORT).show()
            }

        }catch (e: IOException){

            Toast.makeText(this.context,e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }


}