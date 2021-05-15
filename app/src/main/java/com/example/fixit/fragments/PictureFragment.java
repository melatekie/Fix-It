package com.example.fixit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.fixit.Post;
import com.example.fixit.R;
import com.example.fixit.databinding.FragmentPictureBinding;
import com.parse.ParseFile;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PictureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PictureFragment extends DialogFragment{

    private FragmentPictureBinding fragmentPictureBinding;

    Post param1;

    public PictureFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)

        param1 = getArguments().getParcelable("post");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentPictureBinding = FragmentPictureBinding.bind(view);


        ParseFile image = param1.getImage();

        if (image != null){
            Glide.with(this).load(image.getUrl()).into(fragmentPictureBinding.imageView);
        }

    }
}