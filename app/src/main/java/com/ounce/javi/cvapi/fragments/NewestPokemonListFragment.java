package com.ounce.javi.cvapi.fragments;

import android.arch.lifecycle.LiveData;

import com.ounce.javi.cvapi.models.Pokemon;

import java.util.List;

public class NewestPokemonListFragment extends PokemonListFragment {

    @Override
    LiveData<List<Pokemon>> getPokemons() {
        return pokemonViewModel.getAllPokemonsOrderedByDate();
    }
}
