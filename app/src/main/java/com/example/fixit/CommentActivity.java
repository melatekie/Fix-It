package com.example.fixit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fixit.databinding.ActivityCommentBinding;
import com.example.fixit.databinding.FragmentPostsBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    public static final String TAG = "CommentActivity";

    private ActivityCommentBinding activityCommentBinding;

    private List<Comment> AllComments;
    private CommentAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_comment);

        activityCommentBinding = DataBindingUtil.setContentView(this,R.layout.activity_comment);

        AllComments = new ArrayList<>();
        adapter = new CommentAdapter(this,AllComments);
        //String postId = getIntent().getStringExtra("postId");

        activityCommentBinding.rvComment.setAdapter(adapter);
        activityCommentBinding.rvComment.setLayoutManager((new LinearLayoutManager(this)));
        queryPosts();

        /*
        activityCommentBinding.ivPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment();
                String user_input_comment = activityCommentBinding.etComment.getText().toString();
                if (user_input_comment.isEmpty()) {
                    Log.i(TAG, "Empty comment");
                    Toast.makeText(CommentActivity.this, "comment cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                comment.setComment(user_input_comment);
                comment.setUserId(ParseUser.getCurrentUser());

                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null){
                            Log.e(TAG, "Error while saving", e);
                            Log.i(TAG, e.getMessage());
                        }
                        Log.i(TAG, "Comment save was successful");
                        activityCommentBinding.etComment.setText("");
                    }
                });

            }
        });

        */

    }


    private void queryPosts() {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.include(Comment.KEY_COMMENT);
        query.setLimit(10);
        //query.addDescendingOrder(Post.KEY_CREATED_AT);

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
}
