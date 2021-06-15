package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.proyectoampliacion.Classes_Auxiliares.Cliente
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.vista_clientes.view.*
import kotlinx.android.synthetic.main.vista_clientes.view.btnEstado
import kotlinx.android.synthetic.main.vista_mod_clientes.view.*

class AdaptadorClientesMOD(private val mContext: Context, private val listaClientes: List<Cliente>):
    ArrayAdapter<Cliente>(mContext,0,listaClientes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vista_mod_clientes,parent,false);

        val elementoActual = listaClientes[position];

        layout.tvNombreMODCLI.text = elementoActual.nombre;
        layout.tvApellidosMODCLI.text = elementoActual.apellidos;
        layout.tvDireccionMODCLI.text = elementoActual.direccion + " "+ elementoActual.ciudad + " " +elementoActual.provincia;
        layout.tvTelefonoMODCLI.text = elementoActual.telefono;

        if (elementoActual.estado.equals("true")){

            layout.btnEstado.text = "ALTA";
            layout.btnEstado.setTextColor(Color.WHITE);
            layout.btnEstado.setBackgroundColor(Color.GREEN);

        }else{

            layout.btnEstado.text = "BAJA";
            layout.btnEstado.setTextColor(Color.WHITE);
            layout.btnEstado.setBackgroundColor(Color.RED);
        }

        layout.btnEditar.setOnClickListener(){

            var bundle = Bundle();

            bundle.putInt("elemento",elementoActual.id)
            bundle.putInt("tipo_formulario",2)

            Navigation.findNavController(it).navigate(R.id.modificarFragment,bundle)

        }


        return layout;
    }
}