package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.proyectoampliacion.Classes_Auxiliares.Factura
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_facturas.view.*

class MiAdaptadorFactura (private val mContext: Context, private val listaFacturas: List<Factura>): ArrayAdapter<Factura>(mContext,0,listaFacturas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_facturas, parent, false);

        val elementoActual = listaFacturas[position];

        layout.tvCliente.text = elementoActual.cliente.toString();
        layout.tvEmpleado.text = elementoActual.empleado.toString();
        layout.tvEmision.text = elementoActual.fecha
        layout.tvConcepto.text = elementoActual.concepto;



        return layout;
    }

}