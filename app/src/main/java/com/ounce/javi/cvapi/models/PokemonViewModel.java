package com.ounce.javi.cvapi.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ounce.javi.cvapi.database.PokemonDAO;
import com.ounce.javi.cvapi.database.PokemonRepository;

import java.util.ArrayList;
import java.util.List;

public class PokemonViewModel extends   AndroidViewModel {

    List<Pokemon> pokemonList = new ArrayList<>();

    private PokemonDAO mPokemonDao;

    public List<Pokemon>  getPokemonList(){
        return pokemonList;
    }


    //DAo
    private static PokemonRepository mRepository;

    public PokemonViewModel(Application application) {
        super(application);
        mRepository = new PokemonRepository(application);
    }

    LiveData<List<Pokemon>> getAllPokemons() { return mRepository.getAllPokemons(); }

    LiveData<Pokemon> getPokemon(int id){ return mRepository.getPokemon(id); }



    public static void insert(Pokemon pokemon) { mRepository.insert(pokemon); }

    public void setRating(Pokemon pokemon) { mRepository.setRating(pokemon); }

    public void deleteAllPoems() { mRepository.deleteAllPokemons(); }



    //fragm

    public static LiveData<List<Pokemon>> getAllPokemonsOrderedByName() { return mRepository.getAllPokemonsOrderedByName(); }
    public LiveData<List<Pokemon>> getAllPokemonsOrderedByDate() { return mRepository.getAllPokemonsOrderedByDate(); }
    public static LiveData<List<Pokemon>> getAllPokemonsOrderedByRating() { return mRepository.getAllPokemonsOrderedByRating(); }

}

