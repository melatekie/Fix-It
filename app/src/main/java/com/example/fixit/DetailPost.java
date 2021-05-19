package com.example.fixit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.fixit.databinding.PostDetailBinding;
import com.example.fixit.fragments.PictureFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class DetailPost extends AppCompatActivity {

    private PostDetailBinding postDetailBinding;
    public static final String TAG = "DetailPost";

    private boolean likeClick;

    private List<Comment> AllComments;
    private CommentAdapter adapter;
    private Post currentPost ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);
        postDetailBinding = DataBindingUtil.setContentView(this,R.layout.post_detail);

        AllComments = new ArrayList<>();
        adapter = new CommentAdapter(this,AllComments);
        postDetailBinding.rvComment.setAdapter(adapter);
        postDetailBinding.rvComment.setLayoutManager((new LinearLayoutManager(this)));

        //Back button
        postDetailBinding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        User user= Parcels.unwrap(getIntent().getParcelableExtra("user"));
        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
        currentPost = post;
        Comment comment = Parcels.unwrap(getIntent().getParcelableExtra("comment"));
        ParseUser currentUser = ParseUser.getCurrentUser();

        //On Click to pop out image
        postDetailBinding.ivEnlarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putParcelable("post", post);
                PictureFragment pictureFragment = new PictureFragment();
                pictureFragment.setArguments(bundle);
                pictureFragment.show(getSupportFragmentManager(), "it works!");
            }
        });



        postDetailBinding.setUser(user);

        postDetailBinding.setPost(post);
        postDetailBinding.tvQuestion.setText(post.getQuestion());

        if(post.getLikesCount() != null)
            postDetailBinding.tvLikesCount.setText(post.getLikesCount().toString());

        String temp = post.getCategory();
        if ( temp != null )
            postDetailBinding.tvCategory.setText(post.getCategory());

        //Image with conditional to prevent crash  if  null

        postDetailBinding.tvName.setText(user.getFirstName() + " " + user.getLastName());

        /*
        ParseFile image = post.getImage();
        if (image != null){
            Glide.with(this).load(image.getUrl()).into(postDetailBinding.ivProblem);
        }

         */

        //get current user image
        User user1 = new User();
        user1.setObjectID(currentUser.getObjectId());
        user1.setIsProfessional(currentUser.getBoolean("isProfessional"));
        if(currentUser.getParseFile("profileImage") != null) {
            user1.setImage(currentUser.getParseFile("profileImage"));
        }
        User.loadImage(postDetailBinding.ivProfileSelf, user1);

        /*Professional currentUserProf = new Professional();
        currentUserProf.setUser(currentUser);


        //able to see self profile TODO not working for professional
        postDetailBinding.ivProfileSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailPost.this, UserProfileActivity.class);
                i.putExtra("user", Parcels.wrap(currentUser));
                i.putExtra("ParseUser", Parcels.wrap(user1));
                i.putExtra("Professional", Parcels.wrap(currentUserProf));
                DetailPost.this.startActivity(i);
            }
        });*/

        //postDetailBinding.ivSolve.setVisibility(View.INVISIBLE);
        //if(currentPost.getAuthor() == currentUser){
            //postDetailBinding.ivSolve.setVisibility(View.VISIBLE);
            //checks if problem has been solved
            if(!post.getSolved()){
                postDetailBinding.ivSolve.setChecked(false);
                postDetailBinding.ivSolve.setText("UNSOLVED");
                Log.i(TAG,  "solve is false: " + post.getSolved());
            }
            if(post.getSolved()){
                postDetailBinding.ivSolve.setChecked(true);
                postDetailBinding.ivSolve.setText("SOLVED");
                Log.i(TAG,  "solve is true");
            }

            //button to set solved or unsolved
        postDetailBinding.ivSolve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked){
                    postDetailBinding.ivSolve.setChecked(true);
                    postDetailBinding.ivSolve.setText("SOLVED");
                    post.setSolved(true);
                }
                if(!isChecked){
                    postDetailBinding.ivSolve.setChecked(false);
                    postDetailBinding.ivSolve.setText("UNSOLVED");
                    post.setSolved(false);
                }
                post.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null){
                            Log.i(TAG,  e.getMessage());
                        }else{
                            Log.i(TAG,  "Set Solved Field");
                        }
                    }
                });
            }
        });

        //}


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
                comment.setUserId(ParseUser.getCurrentUser());
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
                        if(post.getCommentsCount() != null){
                            post.setCommentsCount(post.getCommentsCount().intValue()+1);
                        }else {
                            post.setCommentsCount(1);
                        }

                        post.saveInBackground();
                        postDetailBinding.tvCommentCount.setText(post.getCommentsCount().toString());
                        postDetailBinding.etComment.setText("");
                        adapter.clear();
                        queryPosts();

                        //fragmentComposeBinding.ivPicture.setImageResource(0);
                        //pbProgress.setVisibility(ProgressBar.INVISIBLE);                                      //PROGRESS BAR IN PROGRESS

                    }
                });

            }
        });



        postDetailBinding.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailPost.this, UserProfileActivity.class);
                i.putExtra("user", Parcels.wrap(user));
                i.putExtra("ParseUser", Parcels.wrap(post.getAuthor()));
                DetailPost.this.startActivity(i);
            }
        });


        queryPosts();
        setLikeListener(post);

    }



    private void queryPosts() {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.whereEqualTo(Comment.KEY_POSTID, currentPost);
        query.setLimit(10);
        query.include(Comment.KEY_USERID);
        query.addDescendingOrder(Comment.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue getting posts", e);
                    return;
                }

                for (Comment comment: comments){
                    Log.i(TAG, "Post: " + comment.getComment());
                }
                AllComments.addAll(comments);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /*
        Comment click counter
        Start as false  to prevent multi-click likes
        When clicked, set the icon to be bright red and increment the count by converting Number to Int then back to String as setText()
        If clicked again with the condition of clicking beforehand, decrement the count and change the icon back to grey.

        Then call saveInBackground to actually save it (don't be like me and think it was just incrementing the count and refreshing)
     */
    private void setLikeListener(final Post post){
        likeClick = false;
        postDetailBinding.ivLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!likeClick){
                    postDetailBinding.ivLikes.setColorFilter(DetailPost.this.getResources().getColor(R.color.red));
                    postDetailBinding.ivLikes.setImageDrawable(ContextCompat.getDrawable(DetailPost.this, R.drawable.heart));

                    if (post.getLikesCount() == null || post.getLikesCount().intValue() == 0){
                        post.setLikesCount(1);
                    } else{
                        post.setLikesCount(post.getLikesCount().intValue() + 1);
                    }
                } else {
                    postDetailBinding.ivLikes.setColorFilter(Color.DKGRAY);
                    postDetailBinding.ivLikes.setImageDrawable(ContextCompat.getDrawable(DetailPost.this, R.drawable.heartblank));
                    post.setLikesCount(post.getLikesCount().intValue() - 1);
                }

                if (post.getLikesCount().intValue() == 0) {
                    postDetailBinding.tvLikesCount.setText("0");
                } else{
                    postDetailBinding.tvLikesCount.setText( String.valueOf(post.getLikesCount().intValue()) );
                }

                post.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d("SaveLikes", "Save successful");
                        } else {
                            Log.d("SaveLikes", "Save failed");
                            e.printStackTrace();
                        }
                    }
                });
                likeClick = !likeClick;

                Toast.makeText(DetailPost.this,"Liked!", Toast.LENGTH_SHORT).show();
            }
        });
    }







}