package com.example.heroesapp.Utills;



import com.example.heroesapp.HeroAdapter;
import com.example.heroesapp.HeroModel;

public interface OnItemClickListener { //interface to handle click events for the favorite hero
    void onItemClick(HeroModel hero, HeroAdapter.HeroesViewHolder holder, int position);
}
