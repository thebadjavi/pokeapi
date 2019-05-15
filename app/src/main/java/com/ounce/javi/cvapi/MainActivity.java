package com.ounce.javi.cvapi;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ounce.javi.cvapi.Interface.ItemClickListener;
import com.ounce.javi.cvapi.fragments.AZPokemonListFragment;
import com.ounce.javi.cvapi.fragments.NewestPokemonListFragment;
import com.ounce.javi.cvapi.models.Pokemon;
import com.ounce.javi.cvapi.models.PokemonRespuesta;
import com.ounce.javi.cvapi.models.PokemonViewModel;
import com.ounce.javi.cvapi.pokeapi.PokkeapiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        private SectionsPagerAdapter mSectionsPagerAdapter;
        private ViewPager mViewPager;

        private static final String TAG = "POKEDEX";
        List<Pokemon> pokemonList = new ArrayList<>();

        private Retrofit retrofit;

        private RecyclerView recyclerView;
        private ListaPokemonAdapter listaPokemonAdapter;

        private int offset;

        private boolean aptoParaCargar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PokemonViewModel pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);

        pokemonList = pokemonViewModel.getPokemonList();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listaPokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " Llegamos al final.");

                            aptoParaCargar = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aptoParaCargar = true;
        offset = 0;
        obtenerDatos(offset);
    }


    private void obtenerDatos(int offset) {
        PokkeapiService service = retrofit.create(PokkeapiService.class);
         Call<PokemonRespuesta> pokemonRespuestaCall1 = service.obtenerListaPokemon( 900,offset );


        pokemonRespuestaCall1.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()) {

                    PokemonRespuesta pokemonRespuesta = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonRespuesta.getResults();

                    listaPokemonAdapter.adicionarListaPokemon(listaPokemon);

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                aptoParaCargar = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });

    }
//tabs
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new NewestPokemonListFragment();
                case 1: return new AZPokemonListFragment();
               // case 2: return new UserPostsFragment();
             //   case 3: return new FriendPostFragment();
                //case 4: return new NuevoFragment();
                default: return new Fragment();
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder> {

        private ArrayList<Pokemon> dataset;
        private Context context;

        List<String> favoritos = new ArrayList<String>();
        PokemonViewModel pokemonViewModel = ViewModelProviders.of(MainActivity.this).get(PokemonViewModel.class);

        public ListaPokemonAdapter(Context context) {
            this.context = context;
            dataset = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Pokemon p = dataset.get(position);

            holder.nombreTextView.setText(p.getName());
            holder.tipo.setText(p.getType());

            String[] urlPartes = p.getUrl().split("/");

            //https://www.serebii.net/pokemongo/pokemon/001.png
            //"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/
            Glide.with(context)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber()+ ".png")
                    .into(holder.fotoImageView);

             holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    int tamnyo=favoritos.size();
                    String nombre=p.getName();

                    if (favoritos.contains(nombre)) {
                        Toast.makeText(context, "ya esta en la llista=" + favoritos + tamnyo, Toast.LENGTH_SHORT).show();

                    }else {
                        favoritos.add(p.getName());
                        Toast.makeText(context,"la lista es="+favoritos+tamnyo,Toast.LENGTH_SHORT).show();

                        PokemonViewModel.insert(p);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {

            return dataset.size();
        }

        public void adicionarListaPokemon(ArrayList<Pokemon> listaPokemon) {
            dataset.addAll(listaPokemon);
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private ImageView fotoImageView;
            private TextView nombreTextView;
            private TextView tipo;

            ItemClickListener itemClickListener;

            public void setItemClickListener(ItemClickListener itemClickListener) {
                this.itemClickListener = itemClickListener;
            }

            public ViewHolder(View itemView) {
                super(itemView);

                fotoImageView = (ImageView) itemView.findViewById(R.id.fotoImageView);
                nombreTextView = (TextView) itemView.findViewById(R.id.nombreTextView);
                tipo = (TextView) itemView.findViewById(R.id.tipo);

                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                itemClickListener.onClick(v,getAdapterPosition());
            }
        }

    }
}
