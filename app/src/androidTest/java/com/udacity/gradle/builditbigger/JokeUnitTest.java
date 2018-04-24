package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Pair;
import android.test.ApplicationTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by ByteTonight on 23.04.2018.
 */

@RunWith(AndroidJUnit4.class)
public class JokeUnitTest extends ApplicationTestCase<Application>
        implements EndpointsAsyncTask.AsyncTaskObserver {

    private static final String DATA_IS_NULL = "Received NULL";
    private static final String DATA_IS_EMPTY = "Received EMPTY String";
    private static final String DATA_IS_API_ERROR = "Received error message from API.\nCheck HOST_IP in EndPointsAsyncTask";
    private CountDownLatch signal;
    private String joke;

    public JokeUnitTest() {
        super(Application.class);
    }


    @SuppressWarnings("unchecked")
    @Test
    public void testJoke() {
        try {
            Context context = InstrumentationRegistry.getTargetContext();
            signal = new CountDownLatch(1);
            new EndpointsAsyncTask(this).execute(new Pair<Context, String>(context, "nothing"));
            signal.await(10, TimeUnit.SECONDS);
            assertNotNull(DATA_IS_NULL, joke);
            assertFalse(DATA_IS_EMPTY, joke.isEmpty());
            assertFalse(DATA_IS_API_ERROR, joke.equals(context.getString(R.string.api_service_error)));
        } catch (Exception e) {
            fail();
        }
    }


    @Override
    public void onTaskCompleted(String data) {
        this.joke = data;
        signal.countDown();
    }
}
