package com.ounce.javi.cvapi.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ounce.javi.cvapi.R;
import com.ounce.javi.cvapi.models.Pokemon;


import java.util.ArrayList;
import java.util.List;

public class PokemonsRecyclerAdapter extends RecyclerView.Adapter<PokemonsRecyclerAdapter.PokemonViewHolder>{

    List<Pokemon> list;
    private ArrayList<Pokemon> dataset;
    private Context context;

    public PokemonsRecyclerAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemPokemon = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(itemPokemon);
    }

    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {
        final Pokemon p = dataset.get(position);
        holder.nombreTextView.setText(p.getName());
        holder.tipo.setText(p.getType());

//https://www.serebii.net/pokemongo/pokemon/001.png
        //"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/
        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber() + ".png")
               // .centerCrop()
            //    .crossFade()
             //   .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public void setList(List<Pokemon> pokemons){
        this.list = pokemons;
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoImageView;
        private TextView nombreTextView;
        private TextView tipo;

        public PokemonViewHolder(View itemView) {
            super(itemView);

            fotoImageView = (ImageView) itemView.findViewById(R.id.fotoImageView);
            nombreTextView = (TextView) itemView.findViewById(R.id.nombreTextView);
            tipo = (TextView) itemView.findViewById(R.id.tipo);


        }
    }

}
