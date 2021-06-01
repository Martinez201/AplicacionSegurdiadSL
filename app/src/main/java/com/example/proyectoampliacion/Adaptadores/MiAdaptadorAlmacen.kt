package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.proyectoampliacion.Classes_Auxiliares.Almacen
import com.example.proyectoampliacion.Classes_Auxiliares.Cliente
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_clientes.view.*
import kotlinx.android.synthetic.main.vista_productos.view.*

open class MiAdaptadorAlmacen(private val mContext:Context,private val listaProductos: List<Almacen>):ArrayAdapter<Almacen>(mContext,0,listaProductos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_productos,parent,false);

        val elementoActual = listaProductos[position];

        layout.tvNombreP.text = elementoActual.nombre;
        layout.tvPrecio.text = elementoActual.precio.toString();
        layout.tvCantidad.text = elementoActual.cantidad.toString();
        layout.tvTipo.text = elementoActual.tipo;


        return layout;
    }
}