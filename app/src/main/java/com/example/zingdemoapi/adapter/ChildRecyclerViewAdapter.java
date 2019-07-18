//package com.example.zingdemoapi.adapter;
//
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.zingdemoapi.R;
//import com.example.zingdemoapi.datamodel.BoxObject;
//
//public class ChildRecyclerViewAdapter extends RecyclerView.Adapter<ChildRecyclerViewAdapter.ChildRecyclerViewHolder> {
//    protected BoxObject mboxObject;
//
//    public ChildRecyclerViewAdapter(BoxObject boxObject) {
//        mboxObject = boxObject;
//    }
//
//    @NonNull
//    @Override
//    public ChildRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.program_recycler_item, viewGroup, false);
//        return new ChildRecyclerViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ChildRecyclerViewHolder viewHolder, int i) {
//        viewHolder.childRecyclerTitle.setText(mboxObject.getTitle());
//        //Log.d("LOAD", mboxObject.getPageList().get(i))
////        Glide.with(viewHolder.childRecyclerImage.getContext())
////                .load(mboxObject.getPageList().get(i).getThumbnail())
////                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
////                .into(viewHolder.childRecyclerImage);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mboxObject.getPageList().size();
//    }
//
//    public class ChildRecyclerViewHolder extends RecyclerView.ViewHolder {
//        TextView childRecyclerTitle;
//        ImageView childRecyclerImage;
//
//        public ChildRecyclerViewHolder(@NonNull View itemView) {
//            super(itemView);
//            childRecyclerTitle = itemView.findViewById(R.id.child_recycler_title);
//            childRecyclerImage = itemView.findViewById(R.id.child_recycler_image);
//        }
//    }
//}
