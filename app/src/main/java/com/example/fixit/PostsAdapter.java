package com.example.fixit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fixit.databinding.ItemPostBinding;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.parceler.Parcels;


import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {


    public static final String TAG = "PostAdapter";


    private Context context;
    private List<Post> posts;
    private ItemPostBinding itemPostBinding;
    private String postQuestion;

    public PostsAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        itemPostBinding = ItemPostBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemPostBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.itemPostBinding.setPost(post);


        User user= new User();
//        user.setEmail(String.valueOf(post.getAuthor().getString("email")));
        user.setUsername(post.getAuthor().getUsername());
        user.setFirstName(post.getAuthor().getString("firstName"));
        user.setLastName(post.getAuthor().getString("lastName"));
        user.setIsProfessional(post.getAuthor().getBoolean("isProfessional"));
        if(post.getAuthor().getParseFile("profileImage")!=null){
            user.setImage(post.getAuthor().getParseFile("profileImage"));
        }
        holder.itemPostBinding.setUser(user);
        holder.itemPostBinding.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UserProfileActivity.class);
                i.putExtra("user", Parcels.wrap(user));
                i.putExtra("ParseUser", Parcels.wrap(post.getAuthor()));
                context.startActivity(i);
            }
        });


        //Clicking on Question will pop out detail view
        holder.itemPostBinding.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailPost.class);
                i.putExtra("user",Parcels.wrap(user));
                i.putExtra("post", Parcels.wrap(post));
                context.startActivity(i);
            }
        });

        /*
        holder.itemPostBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postQuestion = post.getQuestion().toString();
                Toast.makeText(context, "postQuestion: " + postQuestion.toString(), Toast.LENGTH_SHORT).show();
                deletePost(postQuestion);
            }
        }); */


        //set background color change for posts
        if(position % 2 == 0) {
            holder.itemPostBinding.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_light));
        }else{
            holder.itemPostBinding.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    //Something weird is going on with databinding, it is not working so I will be using boilerplate for this one
    class ViewHolder extends RecyclerView.ViewHolder{
        ItemPostBinding itemPostBinding;


        public ViewHolder(ItemPostBinding itemPostBinding) {
            super(itemPostBinding.getRoot());
            this.itemPostBinding = itemPostBinding;
        }
        public void clear() {
            posts.clear();
            notifyDataSetChanged();
        }

        // Add a list of items -- change to type used
        public void addAll(List<Post> list) {
            posts.addAll(list);
            notifyDataSetChanged();
        }

    }


/*
    private void deletePost(String postQuestion){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("question");


        query.whereEqualTo("question", postQuestion);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    objects.get(0).deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                Toast.makeText(context, "Post Deleted", Toast.LENGTH_SHORT).show();

                            } else{
                                Toast.makeText(context, "Post failed to delete", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else{
                    Toast.makeText(context, "Failed to get object", Toast.LENGTH_SHORT).show();
                }
            }
        });
    } */


}
