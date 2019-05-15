package com.ounce.javi.cvapi.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.ounce.javi.cvapi.models.Pokemon;


@Database(entities = {Pokemon.class}, version = 1)
public abstract class PokemonRoomDatabase extends RoomDatabase {

    public abstract PokemonDAO pokemonDAO();

    private static volatile PokemonRoomDatabase INSTANCE;

    static PokemonRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PokemonRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PokemonRoomDatabase.class, "pokemon_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
