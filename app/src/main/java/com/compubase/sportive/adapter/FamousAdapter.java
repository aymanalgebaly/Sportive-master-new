//package com.compubase.sportive.adapter;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.compubase.sportive.R;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
//public class FamousAdapter extends RecyclerView.Adapter<FamousAdapter.ViewHolderFamous>{
//
//    private Context context;
////    private List<FamousModel> famousModelList;
//
//    public FamousAdapter(Context context) {
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolderFamous onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(context).inflate(R.layout.famous_design, viewGroup, false);
//        return new ViewHolderFamous(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolderFamous viewHolderFamous, int i) {
//
////        FamousModel famousModel = famousModelList.get(i);
////
////        viewHolderFamous.type.setText(famousModel.getType());
////        viewHolderFamous.name.setText(famousModel.getName());
////
////        Picasso.get().load(famousModel.getImg()).into(viewHolderFamous.imageView);
//    }
//
////    @Override
////    public int getItemCount() {
////        return famousModelList != null ? famousModelList.size():0;
////    }
//
////    public void setData(List<FamousModel> famousModels) {
////        this.famousModelList = famousModels;
////    }
//
//    public class ViewHolderFamous extends RecyclerView.ViewHolder {
//
//        ImageView imageView;
//        TextView name,type;
//
//        public ViewHolderFamous(@NonNull View itemView) {
//            super(itemView);
//
//            imageView = itemView.findViewById(R.id.img_famous);
//            name = itemView.findViewById(R.id.txt_center_name_famous);
//            type = itemView.findViewById(R.id.type_center_famous);
//        }
//    }
//}
