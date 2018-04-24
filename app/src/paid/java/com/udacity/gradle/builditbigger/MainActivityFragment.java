package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.example.com.libjokedisplay.JokeDisplayActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private static final String TAG = MainActivityFragment.class.getSimpleName();

    private ProgressBar progressBar;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        progressBar = root.findViewById(R.id.progressBar);
        Button tellJoke = root.findViewById(R.id.jokesButton);
        tellJoke.setOnClickListener(onClickListener);

        return root;
    }


    public EndpointsAsyncTask.AsyncTaskObserver asyncTaskObserver = new EndpointsAsyncTask.AsyncTaskObserver() {
        @Override
        public void onTaskCompleted(String data) {

            Intent intent = new Intent(getContext(), JokeDisplayActivity.class);
            intent.putExtra("joke", data);
            startActivity(intent);
            progressBar.setVisibility(View.INVISIBLE);
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            displayJoke();
        }
    };


    @SuppressWarnings("unchecked")
    private void displayJoke() {
        progressBar.setVisibility(View.VISIBLE);
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(asyncTaskObserver);
        endpointsAsyncTask.execute(new Pair<Context, String>(getContext(), null));
        //new EndpointsAsyncTask(this).execute(new Pair<Context, String>(context, null));
    }


}
