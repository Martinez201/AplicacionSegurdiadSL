package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.proyectoampliacion.Classes_Auxiliares.Cliente
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_clientes.view.*

open class MiAdaptadorClientes(private val mContext:Context,private val listaClientes: List<Cliente>):ArrayAdapter<Cliente>(mContext,0,listaClientes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_clientes,parent,false);

        val elementoActual = listaClientes[position];

        layout.tvNombre.text = elementoActual.nombre;
        layout.tvApellidos.text = elementoActual.apellidos;
        layout.tvDireccion.text = elementoActual.direccion + " "+ elementoActual.ciudad + " " +elementoActual.provincia;
        layout.tvTelefono.text = elementoActual.telefono;

        if (elementoActual.estado.equals("TRUE")){

            layout.btnEstado.text = "ALTA";
            layout.btnEstado.setTextColor(Color.WHITE);
            layout.btnEstado.setBackgroundColor(Color.GREEN);

        }else{

            layout.btnEstado.text = "BAJA";
            layout.btnEstado.setTextColor(Color.WHITE);
            layout.btnEstado.setBackgroundColor(Color.RED);
        }


        return layout;
    }
}