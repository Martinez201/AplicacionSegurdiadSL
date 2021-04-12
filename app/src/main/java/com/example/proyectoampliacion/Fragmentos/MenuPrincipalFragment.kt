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
import androidx.navigation.Navigation
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

        val bundle = Bundle();

        for (i in 0..referencia.childCount - 1){
            var cardView: CardView = referencia.getChildAt(i) as CardView;
            cardView.setOnClickListener { it

               when(i){

                   0->{
                       bundle.putInt("tipo",0);
                       Navigation.findNavController(view).navigate(R.id.menuOpcionesCategoriaFragment,bundle);

                   }
                   1->{
                       bundle.putInt("tipo",1);
                       Navigation.findNavController(view).navigate(R.id.menuOpcionesCategoriaFragment,bundle);

                   }
                   2->{
                       bundle.putInt("tipo",2);
                       Navigation.findNavController(view).navigate(R.id.menuOpcionesCategoriaFragment,bundle);

                   }
                   3->{
                       bundle.putInt("tipo",3);
                       Navigation.findNavController(view).navigate(R.id.menuOpcionesCategoriaFragment,bundle);

                   }
                   4->{
                       bundle.putInt("tipo",4);
                       Navigation.findNavController(view).navigate(R.id.menuOpcionesCategoriaFragment,bundle);

                   }
                   5->{
                       bundle.putInt("tipo",5);
                       Navigation.findNavController(view).navigate(R.id.menuOpcionesCategoriaFragment,bundle);

                   }
                   6->{
                       bundle.putInt("tipo",6);
                       Navigation.findNavController(view).navigate(R.id.menuOpcionesCategoriaFragment,bundle);

                   }
                   7->{
                       bundle.putInt("tipo",7);
                       Navigation.findNavController(view).navigate(R.id.menuOpcionesCategoriaFragment,bundle);

                   }
               }

            }
        }

    }

}