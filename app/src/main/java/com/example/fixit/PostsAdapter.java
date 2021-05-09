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

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fixit.databinding.ItemPostBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import org.parceler.Parcels;

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
        //        private TextView tvQuestion;
//        private TextView tvUsername;
//        private ImageView ivProfile;
//        private ImageView ivPicture;
        ItemPostBinding itemPostBinding;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            tvQuestion = itemView.findViewById(R.id.tvQuestion);
//            tvUsername = itemView.findViewById(R.id.tvUsername);
//            ivProfile = itemView.findViewById(R.id.ivProfileImage);
//            ivPicture = itemView.findViewById(R.id.ivPicture);
//        }

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

//        public void itemPostBinding(Post post) {
//
//            ParseFile image = post.getImage();
//
////            tvQuestion.setText(post.getQuestion());
////            tvUsername.setText(post.getAuthor().getUsername());
//            itemPostBinding.tvQuestion.setText(post.getQuestion());
//            itemPostBinding.tvUsername.setText(post.getAuthor().getUsername());
//
//            if (image != null)
//                Glide.with(context).load(post.getImage().getUrl()).into(itemPostBinding.ivPicture);
//
//        }
    }


}
