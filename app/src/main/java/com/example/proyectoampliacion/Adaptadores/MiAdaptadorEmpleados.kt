package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.proyectoampliacion.Classes_Auxiliares.Empleado
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_empleados.view.*

class MiAdaptadorEmpleados  (private val mContext: Context, private val listaEmpleados: List<Empleado>): ArrayAdapter<Empleado>(mContext,0,listaEmpleados) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_empleados, parent, false);

        val elementoActual = listaEmpleados[position];

        layout.tvNombreE.text = elementoActual.nombre.toString();
        layout.tvApellidosE.text = elementoActual.apellidos.toString();
        layout.tvTelefonoE.text = elementoActual.telefono
        layout.tvDireccionE.text = elementoActual.direccion;

        if (elementoActual.avatar.isEmpty()){

            layout.avatar.setImageResource(R.drawable.pixlr_bg_result)

        }
        else{


        }


        return layout;
    }

}