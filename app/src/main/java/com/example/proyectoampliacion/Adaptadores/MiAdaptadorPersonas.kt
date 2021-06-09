package com.example.proyectoampliacion.Adaptadores

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.proyectoampliacion.Classes_Auxiliares.Persona
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.persona_item.view.*

class MiAdaptadorPersonas(private val mContext: Context, private val listaClientes: List<Persona>): ArrayAdapter<Persona>(mContext,0,listaClientes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //return super.getView(position, convertView, parent)

        val layout = LayoutInflater.from(mContext).inflate(R.layout.persona_item,parent,false);

        val elementoActual = listaClientes[position];

        layout.tvNombre.text = elementoActual.nombre;
        layout.tvApellidos.text = elementoActual.apellidos;
        layout.tvId.text = elementoActual.id


        var bundle:Bundle = Bundle();

        layout.btnAnnadirP.setOnClickListener(){ vista ->

            var elegido = Persona(listaClientes[position].nombre, listaClientes[position].apellidos, listaClientes[position].id)

            bundle.putString("nombre",elegido.nombre);
            bundle.putString("apellidos",elegido.apellidos);
            bundle.putString("id",elegido.id);
            bundle.putInt("tipo",6)

            Navigation.findNavController(vista).navigate(R.id.altasFragment,bundle);


        }

        return layout;
    }
}