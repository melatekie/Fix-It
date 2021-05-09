package com.example.fixit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fixit.EndlessRecyclerViewScrollListener;
import com.example.fixit.Post;
import com.example.fixit.PostsAdapter;
import com.example.fixit.R;
import com.example.fixit.databinding.FragmentPostsBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

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
        //fragmentPostsBinding.rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

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

        queryPosts();
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