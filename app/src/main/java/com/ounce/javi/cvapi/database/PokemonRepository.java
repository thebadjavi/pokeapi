package com.ounce.javi.cvapi.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ounce.javi.cvapi.models.Pokemon;

import java.util.List;

public class PokemonRepository {

    private PokemonDAO mPokemonDao;

    public PokemonRepository(Application application) {
        mPokemonDao = PokemonRoomDatabase.getDatabase(application). pokemonDAO();
    }

    public LiveData<List<Pokemon>> getAllPokemons() {
        return mPokemonDao.getAllPokemons();
    }

    public LiveData<Pokemon> getPokemon(int id){ return mPokemonDao.getPokemon(id); }

    public void insert(Pokemon pokemon) {
        new insertAsyncTask(mPokemonDao).execute(pokemon);
    }

    private static class insertAsyncTask extends AsyncTask<Pokemon, Void, Void> {

        private PokemonDAO mAsyncTaskDao;

        insertAsyncTask(PokemonDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Pokemon... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void setRating(Pokemon pokemon){
        new setRatingAsyncTask(mPokemonDao).execute(pokemon);
    }

    private static class setRatingAsyncTask extends AsyncTask<Pokemon, Void, Void> {

        private PokemonDAO mAsyncTaskDao;

        setRatingAsyncTask(PokemonDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Pokemon... params) {
            mAsyncTaskDao.setRating(params[0].id, params[0].rating);
            return null;
        }
    }

    public void deleteAllPokemons(){
        new DeleteAsyncTask(mPokemonDao).execute();
    }

    private static class DeleteAsyncTask extends AsyncTask<Pokemon, Void, Void> {

        private PokemonDAO mAsyncTaskDao;

        DeleteAsyncTask(PokemonDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Pokemon... pokemons) {
            mAsyncTaskDao.deleteAllPokemons();
            return null;
        }
    }

    public LiveData<List<Pokemon>> getAllPokemonsOrderedByName() {
        return mPokemonDao.getAllPokemonsOrderedByName();
    }

    public LiveData<List<Pokemon>> getAllPokemonsOrderedByDate() {
        return mPokemonDao.getAllPokemonsOrderedByDate();
    }

    public LiveData<List<Pokemon>> getAllPokemonsOrderedByRating() {
        return mPokemonDao.getAllPokemonsOrderedByRating();
    }
}
