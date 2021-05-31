package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.proyectoampliacion.Classes_Auxiliares.Cliente
import com.example.proyectoampliacion.Classes_Auxiliares.Delegacion
import com.example.proyectoampliacion.Classes_Auxiliares.Empleado
import com.example.proyectoampliacion.Classes_Auxiliares.Parte
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_clientes.view.*
import kotlinx.android.synthetic.main.vista_partes.view.*

class MiAdaptadorPartes(private val mContext: Context, private val listaPartes: List<Parte>): ArrayAdapter<Parte>(mContext,0,listaPartes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_clientes,parent,false);

        val elementoActual = listaPartes[position];

        layout.tvClienteP.text = elementoActual.cliente.nombre + " " + elementoActual.cliente.apellidos;
        layout.tvEmpleadoP.text = elementoActual.empleado.nombre + " " +elementoActual.empleado.apellidos;
        layout.tvTipoP.text = elementoActual.tipo;
        layout.tvDetalleP.text = elementoActual.observaciones;

        if (elementoActual.estado.equals("true")){

            layout.btnEstadoP.text = "ABIERTO";
            layout.btnEstadoP.setTextColor(Color.WHITE);
            layout.btnEstadoP.setBackgroundColor(Color.GREEN);

        }else{

            layout.btnEstadoP.text = "CERRADO";
            layout.btnEstadoP.setTextColor(Color.WHITE);
            layout.btnEstadoP.setBackgroundColor(Color.YELLOW);
        }


        return layout;
    }
}