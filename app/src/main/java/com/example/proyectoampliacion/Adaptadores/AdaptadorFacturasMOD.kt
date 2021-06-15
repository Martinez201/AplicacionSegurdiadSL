package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.proyectoampliacion.Classes_Auxiliares.Factura
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_facturas.view.*
import kotlinx.android.synthetic.main.vista_mod_factura.view.*

class AdaptadorFacturasMOD (private val mContext: Context, private val listaFacturas: List<Factura>): ArrayAdapter<Factura>(mContext,0,listaFacturas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_mod_factura, parent, false);

        val elementoActual = listaFacturas[position];

        layout.tvClienteFACMOD.text = elementoActual.cliente.toString();
        layout.tvEmpleadoFACMOD.text = elementoActual.empleado.toString();
        layout.tvEmisionFACMOD.text = elementoActual.fecha
        layout.tvConceptoFACMOD.text = elementoActual.concepto;

        layout.btnEditarFACMOD.setOnClickListener(){


        }


        return layout;
    }

}