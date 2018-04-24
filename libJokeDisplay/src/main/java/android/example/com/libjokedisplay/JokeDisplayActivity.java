package android.example.com.libjokedisplay;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class JokeDisplayActivity extends AppCompatActivity
        implements JokeFragment.OnFragmentInteractionListener {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        if (getIntent() != null && getIntent().getExtras() != null) {

            Bundle bundle = getIntent().getExtras();

            JokeFragment fragment = JokeFragment.newInstance(bundle.getString("joke"));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentFrame, fragment)
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
