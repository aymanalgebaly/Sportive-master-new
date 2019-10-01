package com.compubase.sportive.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.compubase.sportive.R;
import com.compubase.sportive.model.CommentsListActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolderComment> {
    private Context context;
    private ArrayList<CommentsListActivity> commentsListActivityArrayList;

    public CommentAdapter(ArrayList<CommentsListActivity> commentsListActivityArrayList) {
        this.commentsListActivityArrayList = commentsListActivityArrayList;
    }

    public CommentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderComment onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.comment_design, viewGroup, false);

        return new ViewHolderComment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderComment viewHolderComment, int i) {

        CommentsListActivity commentsListActivity = commentsListActivityArrayList.get(i);

        viewHolderComment.comment.setText(commentsListActivity.getComment());
        viewHolderComment.rate_num.setText(commentsListActivity.getRate());
        viewHolderComment.ratingBar.setRating(Float.parseFloat(commentsListActivity.getRate()));
        viewHolderComment.user_name.setText(commentsListActivity.getName());

        Glide.with(context).load(commentsListActivity.getImages()).placeholder(R.drawable.user_defualt_img).into(viewHolderComment.img);
    }

    @Override
    public int getItemCount() {
        return commentsListActivityArrayList != null ? commentsListActivityArrayList.size():0;
    }

    public class ViewHolderComment extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView rate_num,comment,user_name;
        MaterialRatingBar ratingBar;

        public ViewHolderComment(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_comment);
            rate_num = itemView.findViewById(R.id.rate_num_txt_comment);
            comment = itemView.findViewById(R.id.edit_comment);
            ratingBar = itemView.findViewById(R.id.rate_comment_design);
            user_name = itemView.findViewById(R.id.user_name);
        }
    }
}
