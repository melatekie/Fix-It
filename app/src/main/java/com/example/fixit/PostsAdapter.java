package com.example.fixit;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fixit.databinding.ItemPostBinding;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {


    public static final String TAG = "PostAdapter";

    //private ItemPostBinding binding;

    private Context context;
    private List<Post> posts;


    public PostsAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Post post = posts.get(position);
       holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    //Something weird is going on with databinding, it is not working so I will be using boilerplate for this one
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvQuestion;
        private TextView tvUsername;
        private ImageView ivProfile;
        private ImageView ivPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivProfile = itemView.findViewById(R.id.ivProfileImage);
            ivPicture = itemView.findViewById(R.id.ivPicture);
        }

        public void bind(Post post) {

            ParseFile image = post.getImage();

            tvQuestion.setText(post.getQuestion());
            tvUsername.setText(post.getAuthor().getUsername());
            //binding.tvQuestion.setText(post.getQuestion());
            //binding.tvUsername.setText(post.getAuthor().getUsername());

            if (image != null)
                Glide.with(context).load(post.getImage().getUrl()).into(ivPicture);

        }
    }


}
