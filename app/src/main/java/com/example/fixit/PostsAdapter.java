package com.example.fixit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;


import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {


    public static final String TAG = "PostAdapter";


    private Context context;
    private List<Post> posts;
    private ItemPostBinding itemPostBinding;

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
        Comment comment = new Comment();


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

        //Post creator can delete their own posts
        ParseUser currentUser = ParseUser.getCurrentUser();
        itemPostBinding.dropDownMenu.setVisibility(View.GONE);
        if(post.getAuthor().getObjectId().equals(currentUser.getObjectId())) {
            itemPostBinding.dropDownMenu.setVisibility(View.VISIBLE);

            //dropdown menu for Deleting posts
            holder.itemPostBinding.dropDownMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context, view);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_post, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_delete:
                                    deletePostComments(post);
                                    notifyDataSetChanged();
                                    goMainActivity();
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popupMenu.show();
                }
            });
        }

        //set background color change for posts
        if(position % 2 == 0) {
            holder.itemPostBinding.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_light));
        }else{
            holder.itemPostBinding.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

    }

    private void goMainActivity() {
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
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

    private void deletePostComments(Post post) {
        post.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.i(TAG, e.getMessage());
                    return;
                }
                Log.i(TAG, "Delete Post Successful");

            }
        });
        //Gets all comments from post and delete them
        ParseQuery<Comment> postComment = ParseQuery.getQuery("Comment");
        postComment.whereEqualTo("postId", post);
        postComment.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                if (e != null){
                    Log.e(TAG, e.getMessage());
                    return;
                }

                for (Comment comment: comments){
                    Log.i(TAG, "Comment: " + comment.getComment());
                    comment.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                Log.i(TAG, e.getMessage());
                                return;
                            }
                            Log.i(TAG, "Delete Comments Successful");

                        }
                    });
                }
            }
        });
    }

}
