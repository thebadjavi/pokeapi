package com.ounce.javi.cvapi.fragments;

import android.arch.lifecycle.LiveData;

import com.ounce.javi.cvapi.models.Pokemon;
import com.ounce.javi.cvapi.models.PokemonViewModel;

import java.util.List;

public class TopRatedPokemonListFragment extends PokemonListFragment {


    @Override
    LiveData<List<Pokemon>> getPokemons() { return PokemonViewModel.getAllPokemonsOrderedByRating();
    }
}
