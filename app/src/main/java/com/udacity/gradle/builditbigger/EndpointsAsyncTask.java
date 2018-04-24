package com.udacity.gradle.builditbigger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by ByteTonight on 19.04.2018.
 */


class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    private static final String TAG = EndpointsAsyncTask.class.getSimpleName();
    private static final String GENYMOTION = "http://10.0.3.2:8080/_ah/api/";
    private static final String AVD = "http://10.0.2.2:8080/_ah/api/";

    private static final String HOST_IP = GENYMOTION;
    private AsyncTaskObserver asyncTaskObserver;
    private static MyApi myApiService = null;

    @SuppressLint("StaticFieldLeak")
    private Context context;

    EndpointsAsyncTask(AsyncTaskObserver asyncTaskObserver) {
        this.asyncTaskObserver = asyncTaskObserver;
    }

    public interface AsyncTaskObserver {
        void onTaskCompleted(String data);
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(HOST_IP)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            builder.setApplicationName("BuildItBigger");
            myApiService = builder.build();
        }

        context = params[0].first;
        String name = params[0].second;

        try {
            //return myApiService.sayHi("mooh").execute().getData();
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            myApiService = null;
            return context.getString(R.string.api_service_error);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (asyncTaskObserver != null)
            asyncTaskObserver.onTaskCompleted(result);

        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
