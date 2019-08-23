package com.compubase.sportive.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.compubase.sportive.R;
import com.compubase.sportive.model.GameModel;

import java.util.List;

public class GameAdapterStatic extends RecyclerView.Adapter<GameAdapterStatic.ViewHolder> {

    private Context context;
    private List<GameModel> gameModelList;

    public GameAdapterStatic(List<GameModel> gameModelList) {
        this.gameModelList = gameModelList;
    }

    public GameAdapterStatic(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rcv_design_center_home, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GameModel gameModel = gameModelList.get(i);

        viewHolder.game.setText(gameModel.getGame());
        viewHolder.name.setText(gameModel.getName());
    }

    @Override
    public int getItemCount() {
        return gameModelList != null ? gameModelList.size():0;
    }

    public void setData(List<GameModel> gameModels) {
        this.gameModelList = gameModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,game;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            game = itemView.findViewById(R.id.nameOfGame);
            name = itemView.findViewById(R.id.nameOfTrainer);
        }
    }
}
