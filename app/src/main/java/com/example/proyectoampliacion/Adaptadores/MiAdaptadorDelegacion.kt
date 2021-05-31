package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.proyectoampliacion.Classes_Auxiliares.Delegacion
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_clientes.view.*
import kotlinx.android.synthetic.main.vista_delegacion.view.*
import kotlinx.android.synthetic.main.vista_productos.view.*

open class MiAdaptadorDelegacion(private val mContext:Context,private val listaDelegaciones: List<Delegacion>):ArrayAdapter<Delegacion>(mContext,0,listaDelegaciones) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_clientes,parent,false);

        val elementoActual = listaDelegaciones[position];

        layout.tvNombreD.text = elementoActual.nombre;
        layout.tvDireccionD.text = elementoActual.direccion;
        layout.tvEmailD.text = elementoActual.email;
        layout.tvTelefonoD.text = elementoActual.telefono;


        return layout;
    }
}