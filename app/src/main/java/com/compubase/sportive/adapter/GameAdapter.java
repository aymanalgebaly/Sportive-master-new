package com.compubase.sportive.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.compubase.sportive.R;
import com.compubase.sportive.model.GameActivityResponse;
import com.compubase.sportive.model.GameModel;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolderGame> {

    private List<GameModel>gameModelsList;

    Context context;

    public GameAdapter(List<GameModel> gameModelsList) {
        this.gameModelsList = gameModelsList;
    }

    @NonNull
    @Override
    public ViewHolderGame onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rcv_design_center_home, viewGroup, false);
        return new ViewHolderGame(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGame viewHolderGame, int i) {

        GameModel gameModel = gameModelsList.get(i);

        viewHolderGame.game.setText(gameModel.getGame());
        viewHolderGame.name.setText(gameModel.getName());
    }

    @Override
    public int getItemCount() {
        return gameModelsList.size();
    }


    class ViewHolderGame extends RecyclerView.ViewHolder {

        TextView name,game;

        ViewHolderGame(@NonNull View itemView) {
            super(itemView);

            game = itemView.findViewById(R.id.nameOfGame);
            name = itemView.findViewById(R.id.nameOfTrainer);
        }
    }
}
