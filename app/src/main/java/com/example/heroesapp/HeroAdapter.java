package com.example.heroesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.heroesapp.Utills.FullSizeImageDialog;
import com.example.heroesapp.Utills.ManageFavoriteHero;
import com.example.heroesapp.Utills.OnItemClickListener;


import java.util.List;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.HeroesViewHolder> {
    private List<HeroModel> heroesData;
    private Context adapterContext;

    //To handle the adapter clicks inside the activity
    private final OnItemClickListener listener;


    HeroAdapter(List<HeroModel> heroes, Context context, OnItemClickListener listener) {
        heroesData = heroes;
        adapterContext = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public HeroesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View heroRow = LayoutInflater.from(adapterContext).inflate(R.layout.hero_recyclerview_row, parent, false);
        ViewGroup.LayoutParams layoutParams = heroRow.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.25); // control heroRow height
        heroRow.setLayoutParams(layoutParams);

        return new HeroesViewHolder(heroRow);
    }


    //To handle the adapter clicks inside the activity
    @Override
    public void onBindViewHolder(@NonNull HeroesViewHolder holder, int position) {
        holder.bind(heroesData.get(position), listener, position, holder);
        holder.heroName.setText(heroesData.get(position).getHeroTitle());
        holder.heroAbilities.setText(heroesData.get(position).getHeroAbilities());
        //  heroesData.get(position).getIfFavoriteHero();
        Glide.with(adapterContext).load(heroesData.get(position).getHeroImage()).apply(RequestOptions.circleCropTransform()).into(holder.heroImage);
        ManageFavoriteHero manageFavoriteHero = new ManageFavoriteHero(adapterContext);

        if (heroesData.get(position).getHeroTitle().equals(manageFavoriteHero.getSpTitle())) { //only color the single favorite hero (if exist)
            holder.makeFavoriteBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
        } else {
            holder.makeFavoriteBtn.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
        }
        imageClickListener(holder, position);
    }


    @Override
    public int getItemCount() {
        return heroesData.size();
    }

    public class HeroesViewHolder extends RecyclerView.ViewHolder {
        ImageView heroImage;
        TextView heroName, heroAbilities;
        Button makeFavoriteBtn;
        boolean isFavoriteHero;

        HeroesViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews();
        }

        private void initViews() {
            heroImage = itemView.findViewById(R.id.heroImage);
            heroName = itemView.findViewById(R.id.heroName);
            heroAbilities = itemView.findViewById(R.id.heroAbilities);
            makeFavoriteBtn = itemView.findViewById(R.id.makeFavoriteBtn);
            isFavoriteHero = false;
        }

        //To handle the adapter clicks inside the activity
        void bind(final HeroModel model, final OnItemClickListener listener, final int position, final HeroesViewHolder holder) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectLikedHero(holder, position);
                    listener.onItemClick(model, holder, position);
                }
            });
        }
    }

    private void imageClickListener(final HeroesViewHolder holder, final int position) {
        holder.heroImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullSizeImageDialog fullSizeImageDialog = new FullSizeImageDialog(adapterContext);
                Window window = fullSizeImageDialog.getWindow();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(window.getAttributes());
                //This makes the dialog take up the full width
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(lp);
                fullSizeImageDialog.setImageFullSize(heroesData.get(position).getHeroImage());
                fullSizeImageDialog.show();
            }
        });
    }


    private void selectLikedHero(HeroesViewHolder holder, int position) {

        ManageFavoriteHero manageFavoriteHero = new ManageFavoriteHero(adapterContext);
        if (heroesData.get(position).getIfFavoriteHero()) {
            holder.makeFavoriteBtn.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
            heroesData.get(position).setFavoriteHero(false);
            manageFavoriteHero.saveSp("Choose hero"
                    , ("no image"));
        } else {
            holder.makeFavoriteBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
            heroesData.get(position).setFavoriteHero(true);
            manageFavoriteHero.saveSp(heroesData.get(position).getHeroTitle()
                    , heroesData.get(position).getHeroImage());
        }
        notifyDataSetChanged();
    }
}
