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

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolderGame> {

    private List<GameActivityResponse>gameActivityResponseList;
    private Context context;

    public GameAdapter(Context context) {
        this.context = context;
    }

    public GameAdapter(List<GameActivityResponse> gameActivityResponseList) {
        this.gameActivityResponseList = gameActivityResponseList;
    }

    @NonNull
    @Override
    public ViewHolderGame onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rcv_design_center_home, viewGroup, false);
        return new ViewHolderGame(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGame viewHolderGame, int i) {

        GameActivityResponse gameActivityResponse = gameActivityResponseList.get(i);

        viewHolderGame.game.setText(gameActivityResponse.getNameGame());
        viewHolderGame.name.setText(gameActivityResponse.getCoach());
    }

    @Override
    public int getItemCount() {
        return gameActivityResponseList != null ? gameActivityResponseList.size():0;
    }

    public class ViewHolderGame extends RecyclerView.ViewHolder {

        TextView name,game;
        public ViewHolderGame(@NonNull View itemView) {
            super(itemView);

            game = itemView.findViewById(R.id.nameOfGame);
            name = itemView.findViewById(R.id.nameOfTrainer);
        }
    }
}
