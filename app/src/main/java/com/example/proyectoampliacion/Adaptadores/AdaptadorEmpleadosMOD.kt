package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.proyectoampliacion.Classes_Auxiliares.Empleado
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_empleados.view.*
import kotlinx.android.synthetic.main.vista_mod_empleado.view.*

class AdaptadorEmpleadosMOD   (private val mContext: Context, private val listaEmpleados: List<Empleado>): ArrayAdapter<Empleado>(mContext,0,listaEmpleados) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_mod_empleado, parent, false);

        val elementoActual = listaEmpleados[position];

        layout.tvNombreEMMOD.text = elementoActual.nombre.toString();
        layout.tvApellidosEMMOD.text = elementoActual.apellidos.toString();
        layout.tvTelefonoEMMOD.text = elementoActual.telefono
        layout.tvDireccionEMMOD.text = elementoActual.direccion;

        if (elementoActual.avatar.isEmpty()){

            layout.avatar.setImageResource(R.drawable.pixlr_bg_result)

        }
        else{


        }

        layout.btnEditarEMMOD.setBackgroundColor(Color.parseColor("#343a40"));

        layout.btnEditarEMMOD.setOnClickListener(){

            var bundle = Bundle();

            bundle.putInt("elemento",elementoActual.id)
            bundle.putInt("tipo_formulario",4)

            Navigation.findNavController(it).navigate(R.id.modificarFragment,bundle)

        }


        return layout;
    }

}