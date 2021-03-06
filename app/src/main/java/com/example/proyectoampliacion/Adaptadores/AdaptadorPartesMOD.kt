package com.example.proyectoampliacion.Adaptadores

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
import kotlinx.android.synthetic.main.vista_mod_partes.view.*
import kotlinx.android.synthetic.main.vista_partes.view.*

class AdaptadorPartesMOD(private val mContext: Context, private val listaPartes: List<Parte>): ArrayAdapter<Parte>(mContext,0,listaPartes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_mod_partes,parent,false);

        val elementoActual = listaPartes[position];

        layout.tvClientePARMOD.text = elementoActual.cliente.nombre + " " + elementoActual.cliente.apellidos;
        layout.tvEmpleadoPARMOD.text = elementoActual.empleado.nombre + " " +elementoActual.empleado.apellidos;
        layout.tvTipoPARMOD.text = elementoActual.tipo;
        layout.tvDetallePARMOD.text = elementoActual.observaciones;
        layout.tvFechaPARMOD.text = elementoActual.fecha

        if (elementoActual.estado.equals("true")){

            layout.btnEstadoPARMOD.text = "ABIERTO";
            layout.btnEstadoPARMOD.setTextColor(Color.WHITE);
            layout.btnEstadoPARMOD.setBackgroundColor(Color.parseColor("#c67f16"));

        }else{

            layout.btnEstadoPARMOD.text = "CERRADO";
            layout.btnEstadoPARMOD.setTextColor(Color.WHITE);
            layout.btnEstadoPARMOD.setBackgroundColor(Color.parseColor("#28a745"));
        }

        layout.btnEditarPARMOD.setBackgroundColor(Color.parseColor("#343a40"));

        layout.btnEditarPARMOD.setOnClickListener(){

            var bundle = Bundle();

            bundle.putInt("elemento",elementoActual.id)
            bundle.putInt("tipo_formulario",6)

            Navigation.findNavController(it).navigate(R.id.modificarFragment,bundle)

        }


        return layout;
    }
}