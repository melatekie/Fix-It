package com.example.fixit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fixit.databinding.ItemCommentBinding;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    public static final String TAG = "CommentAdapter";
    private ItemCommentBinding itemCommentBinding;

    Context context;
    List<Comment> comments;


    public CommentAdapter(Context context, List<Comment> comments){
        this.context = context;
        this.comments = comments;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        itemCommentBinding = ItemCommentBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemCommentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Comment comment = comments.get(position);
        holder.itemCommentBinding.tvComment.setText(comment.getComment());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemCommentBinding itemCommentBinding;

        public ViewHolder(@NonNull ItemCommentBinding itemCommentBinding) {
            super(itemCommentBinding.getRoot());
            this.itemCommentBinding = itemCommentBinding;
        }
        public void clear() {
            comments.clear();
            notifyDataSetChanged();
        }

        // Add a list of items -- change to type used
        public void addAll(List<Comment> list) {
            comments.addAll(list);
            notifyDataSetChanged();
        }

    }

}
