package com.example.fixit.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fixit.MainActivity;
import com.example.fixit.Post;
import com.example.fixit.R;
import com.example.fixit.databinding.FragmentComposeBinding;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class ComposeFragment extends DialogFragment {

    public static final String TAG = "ComposeFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 142;

    private File photoFile;
    public String photoFileName = "photo.jpg";
    private FragmentComposeBinding fragmentComposeBinding;

    public ComposeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentComposeBinding = FragmentComposeBinding.bind(view);

        fragmentComposeBinding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        //Launch the camera on click
        fragmentComposeBinding.ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Launching Camera!", Toast.LENGTH_SHORT).show();
                launchCamera();
            }
        });

        /*
            Function to check whether the field is empty or not. I chose to put this in the class
            rather than the XML because it is easier to test and add in functionality.
         */

        fragmentComposeBinding.btnCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //String composeText = fragmentComposeBinding.etProblem.getText().toString();
                //fragmentComposeBinding.btnCompose.setEnabled(!composeText.isEmpty());
                fragmentComposeBinding.btnCompose.setEnabled(false);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String composeText = fragmentComposeBinding.etProblem.getText().toString();
                fragmentComposeBinding.btnCompose.setEnabled(!composeText.isEmpty() && composeText.length() <= 200);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Save and upload the question/picture
        fragmentComposeBinding.btnCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description =  fragmentComposeBinding.etProblem.getText().toString();
                if (description.isEmpty()){
                    Toast.makeText(getContext(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Since posts must have photos, this is a checker to prevent crashes


                /*
                photoFile = getContext().getFileStreamPath(photoFileName);
                if (!photoFile.exists()){
                    Toast.makeText(getContext(), "Must take a photo!", Toast.LENGTH_SHORT).show();
                    return;
                } */

                /*if (photoFile == null || fragmentComposeBinding.ivPicture.getDrawable() == null){
                    Toast.makeText(getContext(), "No Image", Toast.LENGTH_SHORT).show();
                    return;
                } */

                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description, currentUser, photoFile);
            }
        });
    }



    /*
        Launch camera function supplied by Codepath.
        Note: make sure capture_image_activity_request_code is something unique and identifiable like 42 or 234243.
        Anything besides single digit numbers which would  confused people. This number is just used to identify
     */
    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    /*
        Save the actual picture upon taken
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview

                fragmentComposeBinding.ivPicture.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
        Getter for photofileuri supplied by Codepath.
     */
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory, check parse please!");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);

    }


    /*
        Create a post object which will store the necessary information into the variable 'post' and then use .saveInBackground() to actually save it.
        Feel free to add more if we want to scale it with either categories or other stretch stories.
     */
    private void savePost(String description, ParseUser currentUser, File photoFile) {
        Post post = new Post();
        post.setQuestion(description);
        post.setAuthor(currentUser);
        if(photoFile!=null) post.setImage(new ParseFile(photoFile));
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving in savePost", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful");
                fragmentComposeBinding.etProblem.setText("");
                fragmentComposeBinding.ivPicture.setImageResource(0);
                //pbProgress.setVisibility(ProgressBar.INVISIBLE);                                      //PROGRESS BAR IN PROGRESS
            }
        });
    }
}