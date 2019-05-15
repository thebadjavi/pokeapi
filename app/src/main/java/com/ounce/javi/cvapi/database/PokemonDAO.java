package com.ounce.javi.cvapi.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ounce.javi.cvapi.models.Pokemon;

import java.util.List;

@Dao
public interface PokemonDAO {

    @Insert
    void insert(Pokemon pokemon);

    @Query("SELECT * FROM pokemon WHERE id = :id")
    LiveData<Pokemon> getPokemon(int id);


    @Query("SELECT * FROM pokemon")
    LiveData<List<Pokemon>> getAllPokemons();


    @Query("DELETE FROM pokemon")
    abstract void deleteAllPokemons();

    @Query("SELECT * FROM pokemon ORDER BY rating DESC")
    abstract LiveData<List<Pokemon>> getAllPokemonsOrderedByRating();

    @Query("SELECT * FROM pokemon ORDER BY name")
    abstract LiveData<List<Pokemon>> getAllPokemonsOrderedByName();


    @Query("SELECT * FROM pokemon ORDER BY type DESC")
    abstract LiveData<List<Pokemon>> getAllPokemonsOrderedByDate();

    @Query("UPDATE pokemon SET rating=:rating WHERE id=:id")
    void setRating(int id, float rating);
}
