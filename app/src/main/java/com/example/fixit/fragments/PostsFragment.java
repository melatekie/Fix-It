package com.example.fixit.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import androidx.appcompat.widget.SearchView;

import com.example.fixit.Comment;
import com.example.fixit.EndlessRecyclerViewScrollListener;
import com.example.fixit.MainActivity;
import com.example.fixit.Post;
import com.example.fixit.PostsAdapter;
import com.example.fixit.R;
import com.example.fixit.User;
import com.example.fixit.databinding.FragmentPostsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.parse.Parse.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostsFragment extends Fragment {
    public static final String TAG = "PostsFragment";
    private FragmentPostsBinding fragmentPostsBinding;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    EndlessRecyclerViewScrollListener scrollListener;


    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentPostsBinding = FragmentPostsBinding.bind(view);

        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);


        fragmentPostsBinding.rvPosts.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentPostsBinding.rvPosts.setLayoutManager(layoutManager);


        //shrinks compose button when scrolled
        fragmentPostsBinding.rvPosts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    fragmentPostsBinding.fabCompose.shrink();
                }else{
                    fragmentPostsBinding.fabCompose.extend();
                }
            }
        });

        fragmentPostsBinding.fabCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComposeFragment composefragment = new ComposeFragment();
                composefragment.show(getFragmentManager(),"Testing Dialog Fragment!");
            }
        });

        fragmentPostsBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                allPosts.clear();
                scrollListener.resetState();
                queryPosts();
                fragmentPostsBinding.swipeContainer.setRefreshing(false);
            }
        });

        fragmentPostsBinding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i(TAG, "onLoadMore: " + page);
                loadMoreData();
            }
        };
        fragmentPostsBinding.rvPosts.addOnScrollListener(scrollListener);

        fragmentPostsBinding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


        queryPosts();
    }

    private void filter(String text){
        ArrayList<Post> filteredList = new ArrayList<>();

        for(Post post : allPosts) {
            if (post.getQuestion().toLowerCase().contains(text.toLowerCase()) ||
                post.getCategory().toLowerCase().contains(text.toLowerCase()) ||
                post.getAuthor().getUsername().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(post);
            }
        }
        adapter.filterList(filteredList);
    }

    private void loadMoreData() {
        queryPosts();
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.setSkip(allPosts.size()); //infinite scrolling
        query.include(Post.KEY_AUTHOR);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);

        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue getting posts", e);
                    return;
                }

                for (Post post : posts){
                    Log.i(TAG, "Post: " + post.getQuestion() + ", username: " + post.getAuthor().getUsername());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });

    }


}