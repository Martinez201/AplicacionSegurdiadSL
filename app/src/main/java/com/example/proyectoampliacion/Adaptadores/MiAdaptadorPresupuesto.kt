package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.proyectoampliacion.Classes_Auxiliares.Presupuesto
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_partes.view.*
import kotlinx.android.synthetic.main.vista_presupuesto.view.*

class MiAdaptadorPresupuesto(private val mContext: Context, private val listaPresupuestos: List<Presupuesto>): ArrayAdapter<Presupuesto>(mContext,0,listaPresupuestos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_presupuesto,parent,false);

        val elementoActual = listaPresupuestos[position];

        layout.tvEmpleadoPre.text = elementoActual.empleado.nombre + " " + elementoActual.empleado.apellidos;
        layout.tvFechaPre.text = elementoActual.fecha;
        layout.tvIntalacion.text = elementoActual.instalacion;


        if (elementoActual.estado.toBoolean()){

            layout.btnEstadoPre.text = "CERRADO";
            layout.btnEstadoPre.setTextColor(Color.WHITE);
            layout.btnEstadoPre.setBackgroundColor(Color.GREEN);

        }else{

            layout.btnEstadoPre.text = "EN TRAMITE";
            layout.btnEstadoPre.setTextColor(Color.WHITE);
            layout.btnEstadoPre.setBackgroundColor(Color.RED);
        }


        return layout;
    }
}