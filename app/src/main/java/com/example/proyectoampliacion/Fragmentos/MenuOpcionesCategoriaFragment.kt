package com.example.proyectoampliacion.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import com.example.proyectoampliacion.R

class MenuOpcionesCategoriaFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_opciones_categoria, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var menu = view.findViewById<GridLayout>(R.id.menuSubGrid);

        arguments?.let {

            setSingleEvent(menu,view,it?.getInt("tipo"))
        }



    }


    fun setSingleEvent(referencia: GridLayout, view: View, tipo:Int){

        val bundle = Bundle();

        for (i in 0..referencia.childCount - 1){

            var cardView: CardView = referencia.getChildAt(i) as CardView;
            cardView.setOnClickListener { it

                when(i){

                    0->{
                        bundle.putInt("tipo",tipo);
                        Navigation.findNavController(view).navigate(R.id.altasFragment,bundle);

                    }
                    1->{
                        bundle.putInt("tipo",tipo);
                        Navigation.findNavController(view).navigate(R.id.modificarFragment,bundle);

                    }
                    2->{
                        bundle.putInt("tipo",tipo);
                        Navigation.findNavController(view).navigate(R.id.bajasFragment,bundle);

                    }
                    3->{

                        Navigation.findNavController(view).navigate(R.id.menuPrincipalFragment);

                    }

                }

            }
        }

    }

}