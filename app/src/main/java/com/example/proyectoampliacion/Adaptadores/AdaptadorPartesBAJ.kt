package com.example.proyectoampliacion.Adaptadores

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.proyectoampliacion.Classes_Auxiliares.Parte
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_baj_partes.view.*
import kotlinx.android.synthetic.main.vista_mod_partes.view.*

class AdaptadorPartesBAJ (private val mContext: Context, private val listaPartes: List<Parte>): ArrayAdapter<Parte>(mContext,0,listaPartes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_baj_partes,parent,false);

        val elementoActual = listaPartes[position];

        layout.tvClientePARBAJ.text = elementoActual.cliente.nombre + " " + elementoActual.cliente.apellidos;
        layout.tvEmpleadoPARBAJ.text = elementoActual.empleado.nombre + " " +elementoActual.empleado.apellidos;
        layout.tvTipoPARBAJ.text = elementoActual.tipo;
        layout.tvDetallePARBAJ.text = elementoActual.observaciones;
        layout.tvFechaPARBAJ.text = elementoActual.fecha

        if (elementoActual.estado.equals("true")){

            layout.btnEstadoPARBAJ.text = "ABIERTO";
            layout.btnEstadoPARBAJ.setTextColor(Color.WHITE);
            layout.btnEstadoPARBAJ.setBackgroundColor(Color.YELLOW);

        }else{

            layout.btnEstadoPARBAJ.text = "CERRADO";
            layout.btnEstadoPARBAJ.setTextColor(Color.WHITE);
            layout.btnEstadoPARBAJ.setBackgroundColor(Color.GREEN);
        }

        layout.btnBorrarPARBAJ.setBackgroundColor(Color.RED)

        layout.btnBorrarPARBAJ.setOnClickListener(){

            var bundle = Bundle();

            bundle.putInt("elemento",elementoActual.id)

            dialogoBorrar()

        }


        return layout;
    }

    fun dialogoBorrar(){

        val builder = AlertDialog.Builder(this.context)
        builder.setTitle("Atención")
        builder.setMessage("¿Está seguro de que quiere borrar el registro?")


        builder.setPositiveButton("SI") { dialog, which ->

        }

        builder.setNegativeButton("NO") { dialog, which ->

        }

        builder.show()

    }

}