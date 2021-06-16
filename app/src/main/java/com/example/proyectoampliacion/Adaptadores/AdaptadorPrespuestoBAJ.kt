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
import com.example.proyectoampliacion.Classes_Auxiliares.Presupuesto
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_baj_partes.view.*
import kotlinx.android.synthetic.main.vista_baj_presupuesto.view.*
import kotlinx.android.synthetic.main.vista_mod_presupuesto.view.*
import kotlinx.android.synthetic.main.vista_mod_presupuesto.view.btnEstadoPREMOD

class AdaptadorPrespuestoBAJ (private val mContext: Context, private val listaPresupuestos: List<Presupuesto>): ArrayAdapter<Presupuesto>(mContext,0,listaPresupuestos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_baj_presupuesto,parent,false);

        val elementoActual = listaPresupuestos[position];

        layout.tvEmpleadoPREMOD.text = elementoActual.empleado.nombre + " " + elementoActual.empleado.apellidos;
        layout.tvFechaPREMOD.text = elementoActual.fecha;
        layout.tvIntalacionPREMOD.text = elementoActual.instalacion;


        if (elementoActual.estado.equals("true")){

            layout.btnEstadoPREBAJ.text = "ABIERTO";
            layout.btnEstadoPREBAJ.setTextColor(Color.WHITE);
            layout.btnEstadoPREBAJ.setBackgroundColor(Color.RED);

        }else{

            layout.btnEstadoPREBAJ.text = "CERRADO";
            layout.btnEstadoPREBAJ.setTextColor(Color.WHITE);
            layout.btnEstadoPREBAJ.setBackgroundColor(Color.GREEN);
        }

        layout.btnEditarPREBAJ.setBackgroundColor(Color.RED)

        layout.btnEditarPREBAJ.setOnClickListener(){

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