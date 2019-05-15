package com.ounce.javi.cvapi.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ounce.javi.cvapi.R;
import com.ounce.javi.cvapi.models.Pokemon;
import com.ounce.javi.cvapi.models.PokemonViewModel;

import java.util.List;


public abstract class PokemonListFragment extends Fragment {
    PokemonViewModel pokemonViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.pokeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);

        getPokemons().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(@Nullable List<Pokemon> pokemons) {

            }
        });

        return view;
    }

    abstract LiveData<List<Pokemon>> getPokemons();
}
