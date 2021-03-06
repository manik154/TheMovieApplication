package com.example.a13877.themovieapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a13877.themovieapplication.Activity.MovieGenreList;
import com.example.a13877.themovieapplication.R;

import org.w3c.dom.Text;

public class GenresFragment extends Fragment {

    private TextView textViewmoviesList;
    private TextView textViewTvShowList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_menu_3, container, false);
textViewmoviesList=view.findViewById(R.id.textview1);
        textViewTvShowList=view.findViewById(R.id.textview2);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Genres");

        textViewmoviesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MovieGenreList.class);
                intent.putExtra("isMovie",true);
                startActivity(intent);
            }
        });
        textViewTvShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MovieGenreList.class);
                intent.putExtra("isMovie",false);
                startActivity(intent);
            }
        });
    }


}
