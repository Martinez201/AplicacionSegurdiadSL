package com.example.proyectoampliacion.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.GridView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.proyectoampliacion.R
import kotlinx.android.synthetic.main.fragment_menu_principal.*

class MenuPrincipalFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_principal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        var menu = view.findViewById<GridLayout>(R.id.menuGrid);

        setSingleEvent(menu,view)

    }

    fun setSingleEvent(referencia: GridLayout, view: View){


        for (i in 0..referencia.childCount - 1){
            var cardView: CardView = referencia.getChildAt(i) as CardView;
            cardView.setOnClickListener { it

                Toast.makeText(it.context,"HOLA ==> "+i, Toast.LENGTH_SHORT).show();

            }
        }

    }

}