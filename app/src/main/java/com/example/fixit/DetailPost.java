package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.fixit.databinding.PostDetailBinding;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

public class DetailPost extends AppCompatActivity {

    private PostDetailBinding postDetailBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);
        postDetailBinding = DataBindingUtil.setContentView(this,R.layout.post_detail);

        //Back button
        postDetailBinding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        User user= Parcels.unwrap(getIntent().getParcelableExtra("user"));
        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
        postDetailBinding.setUser(user);

        postDetailBinding.setPost(post);
        postDetailBinding.tvQuestion.setText(post.getQuestion());

        //Image with conditional to prevent crash  if  null

        postDetailBinding.tvName.setText(user.getFirstName() + " " + user.getLastName());
        ParseFile image = post.getImage();
        if (image != null){
            Glide.with(this).load(image.getUrl()).into(postDetailBinding.ivProblem);
        }
    }
}
