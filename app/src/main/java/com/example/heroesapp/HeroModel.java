package com.example.heroesapp;



public class HeroModel {

    private String heroImage,heroTitle,heroAbilities;
    private boolean isFavoriteHero;
    private int currentPosition;




    HeroModel(String image, String title, String abilities, boolean bool, int position) {
        heroImage = image;
        heroTitle = title;
        heroAbilities = abilities;
        isFavoriteHero = bool;
        currentPosition = position;
    }

    int getCurrentPosition() {
        return currentPosition;
    }

    String getHeroImage() {
        //Return path to image resource and not the actual image.
        return heroImage;
    }

    String getHeroTitle() {
        return heroTitle;
    }

    String getHeroAbilities() {
        //heroAbilities is coming as ["ability","ability"] etc... , remove []
        return heroAbilities.substring(1, heroAbilities.length()-1);
    }

    boolean getIfFavoriteHero() {
        return isFavoriteHero;
    }

    void setFavoriteHero(boolean favoriteHero) {
        isFavoriteHero = favoriteHero;
    }
}
