package com.example.fixit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.fixit.databinding.ActivityMainBinding;
import com.example.fixit.fragments.ComposeFragment;
import com.example.fixit.fragments.EditProfileFragment;
import com.example.fixit.fragments.LogoutFragment;
import com.example.fixit.fragments.PostsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    //Testing
    public static final String TAG = "MainActivity";

    private ActivityMainBinding activityMainBinding;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        //fabCompose = activityMainBinding.fabCompose;

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bottomNavigationView = activityMainBinding.bvBottomNavigation;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new PostsFragment();
                        break;
                    case R.id.action_profile:
                        fragment = new EditProfileFragment();
                        break;
                    case R.id.action_logout:
                        fragment = new LogoutFragment();
                        ParseUser.logOut();
                        ParseUser currentLoggedUser = ParseUser.getCurrentUser();
                        goLoginActivity();
                        break;

                    default:
                        fragment = new EditProfileFragment();// placeholder
                        // log out here
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        //queryPost();              //FOR TESTING PURPOSES


    }

    //Logs off current User
    private void goLoginActivity(){
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }


    //FOR TESTING PURPOSES
    /*
    private void queryPost(){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        ParseQuery<User> queryUser = ParseQuery.getQuery(User.class);


        query.include(Post.KEY_AUTHOR);
        query.include(String.valueOf(Post.KEY_LIKES_COUNT));
        query.findInBackground(new FindCallback<Post>(){

            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issues with posts, e");
                    return;
                }

                for (Post post : posts){
                    Log.i(TAG, "post: username " + post.getAuthor().getUsername());
                    Log.i(TAG, "post: question " + post.getQuestion());
                    Log.i(TAG, "post: category " + post.getCategory());
                    Log.i(TAG, "post: likes " + post.getLikesCount());
                    Log.i(TAG, "post: comment " + post.getCommentsCount());
                    Log.i(TAG, "post: solved " + post.getSolved());

                    post.setQuestion("GoodBye!");
                    post.setKeyCategory("Heating");
                    post.setLikesCount(20);
                    post.setSolved(true);

                    Log.i(TAG, "post: username " + post.getAuthor().getUsername());
                    Log.i(TAG, "post: question " + post.getQuestion());
                    Log.i(TAG, "post: category " + post.getCategory());
                    Log.i(TAG, "post: likes " + post.getLikesCount());
                    Log.i(TAG, "post: comment " + post.getCommentsCount());
                    Log.i(TAG, "post: solved " + post.getSolved());
                }
            }
        });


        queryUser.include(User.KEY_LAST_NAME);
        queryUser.include(User.KEY_FIRST_NAME);
        queryUser.include(User.KEY_WHO);

        queryUser.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> users, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issues with users, e");
                    return;
                }

                for (User user : users){
                    Log.i(TAG, "user: first " + user.getFirstName() );
                    Log.i(TAG, "user: last " + user.getLastName() );
                    Log.i(TAG, "user: email " + user.getEmail() );
                    Log.i(TAG, "user: " + user.getWho().getUsername());
                }
            }
        });

    } */




}