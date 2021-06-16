package com.example.proyectoampliacion.Adaptadores

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
import kotlinx.android.synthetic.main.vista_mod_presupuesto.view.*


class AdaptadorPresupuestoMOD(private val mContext: Context, private val listaPresupuestos: List<Presupuesto>): ArrayAdapter<Presupuesto>(mContext,0,listaPresupuestos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_mod_presupuesto,parent,false);

        val elementoActual = listaPresupuestos[position];

        layout.tvEmpleadoPREMOD.text = elementoActual.empleado.nombre + " " + elementoActual.empleado.apellidos;
        layout.tvFechaPREMOD.text = elementoActual.fecha;
        layout.tvIntalacionPREMOD.text = elementoActual.instalacion;


        if (elementoActual.estado.toBoolean()){

            layout.btnEstadoPREMOD.text = "EN TRAMITE";
            layout.btnEstadoPREMOD.setTextColor(Color.WHITE);
            layout.btnEstadoPREMOD.setBackgroundColor(Color.RED);

        }else{

            layout.btnEstadoPREMOD.text = "CERRADO";
            layout.btnEstadoPREMOD.setTextColor(Color.WHITE);
            layout.btnEstadoPREMOD.setBackgroundColor(Color.GREEN);
        }

        layout.btnEditarPREMOD.setOnClickListener(){

            var bundle = Bundle();

            bundle.putInt("elemento",elementoActual.id)
            bundle.putInt("tipo_formulario",7)

            Navigation.findNavController(it).navigate(R.id.modificarFragment,bundle)
        }


        return layout;
    }
}