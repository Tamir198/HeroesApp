package com.example.heroesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.heroesapp.Utills.ManageFavoriteHero;
import com.example.heroesapp.Utills.OnItemClickListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView heroesRecyclerView = null;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView toolbarImage;
    HeroAdapter heroAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadToolbarUi();
        handleHeroesInfo();
    }



    public void handleHeroesInfo() {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        final String url = MainActivity.this.getResources().getString(R.string.heroes_json);
        // prepare the Request
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            creteJson(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Response", error.toString());
                    }
                }
        );
        queue.add(request);
    }

    void creteJson(JSONArray response) throws JSONException{
        JSONArray data = new JSONArray();
        JSONObject row;
        List<HeroModel> heroModels = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {//iterate over the response and convert it to
            row = response.getJSONObject(i);
            data.put(row);
            heroModels.add(new HeroModel(row.get("image").toString()
                    ,row.get("title").toString()
                    ,row.get("abilities").toString()
                    ,checkIfHeroIsLiked(row.get("title").toString())
                    ,i));
        }
        configRecyclerView(heroModels);
    }

    boolean checkIfHeroIsLiked(String title){
        ManageFavoriteHero manageFavoriteHero = new ManageFavoriteHero(MainActivity.this);
        System.out.println("whyyyyy" +  manageFavoriteHero.getSpTitle().equals(title));
        return (manageFavoriteHero.getSpTitle().equals(title));
    }

    void configRecyclerView(final List<HeroModel> heroModels){
        heroesRecyclerView = findViewById(R.id.recyclerview);
        heroesRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

         //getting the click events on the adapter and manage toolbar
         heroAdapter = new HeroAdapter(heroModels, MainActivity.this, new OnItemClickListener() {
            @Override
            public void onItemClick(HeroModel hero, HeroAdapter.HeroesViewHolder holder, int position) {
                Glide.with(MainActivity.this).load(hero.getHeroImage()).into(toolbarImage);
                collapsingToolbarLayout.setTitle(hero.getHeroTitle());

            }
        });
        heroesRecyclerView.setAdapter(heroAdapter);
    }


    private void initViews() {
        toolbarImage = findViewById(R.id.toolbar_image);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
    }

    private void loadToolbarUi() { // loads the image of the saved favorite hero (if exist)
        ManageFavoriteHero manageFavoriteHero = new ManageFavoriteHero(this);
        collapsingToolbarLayout.setTitle(manageFavoriteHero.getSpTitle());
        if(!(manageFavoriteHero.getSpImage().equals("no image")))
            Glide.with(this).load(manageFavoriteHero.getSpImage()).into(toolbarImage);
    }

}
