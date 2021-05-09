package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.fixit.databinding.PostDetailBinding;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

public class DetailPost extends AppCompatActivity {

    private PostDetailBinding postDetailBinding;
    public static final String TAG = "DetailPost";

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
        Comment comment = Parcels.unwrap(getIntent().getParcelableExtra("comment"));

        postDetailBinding.setUser(user);

        postDetailBinding.setPost(post);
        postDetailBinding.tvQuestion.setText(post.getQuestion());

        String temp = post.getCategory();
        if ( temp != null )
            postDetailBinding.tvCategory.setText(post.getCategory());

        //Image with conditional to prevent crash  if  null

        postDetailBinding.tvName.setText(user.getFirstName() + " " + user.getLastName());
        ParseFile image = post.getImage();
        if (image != null){
            Glide.with(this).load(image.getUrl()).into(postDetailBinding.ivProblem);
        }



        postDetailBinding.ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailPost.this, CommentActivity.class);
                i.putExtra("user",Parcels.wrap(user));
                i.putExtra("post",Parcels.wrap(post));
                i.putExtra("comment", Parcels.wrap(comment));

                DetailPost.this.startActivity(i);

            }
        });



        // WILL BE MOVED TO DETAILED ACTVITITY
        postDetailBinding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment();
                String user_input_comment = postDetailBinding.etComment.getText().toString();
                if (user_input_comment.isEmpty()){
                    Log.i(TAG, "Empty comment");
                    Toast.makeText(DetailPost.this, "comment cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                comment.setUserId(post.getAuthor());
                comment.setComment(user_input_comment);
                comment.setPostId(post);

                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null){
                            Log.e(TAG, "Error while saving", e);
                            Log.i(TAG,  e.getMessage());
                        }
                        Log.i(TAG, "Comment save was successful");
                        postDetailBinding.etComment.setText("");
                        //fragmentComposeBinding.ivPicture.setImageResource(0);
                        //pbProgress.setVisibility(ProgressBar.INVISIBLE);                                      //PROGRESS BAR IN PROGRESS

                    }
                });
            }
        });
        // WILL BE MOVED TO DETAILED ACTVITITY

    }




}