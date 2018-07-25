package com.nytimes.articles.data.remote;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.google.gson.stream.MalformedJsonException;
import com.nytimes.articles.NYTimesApp;
import com.nytimes.articles.R;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * This class act as the decider to cache the response/ fetch from the service always
 * Author: Lajesh D
 * Email: lajeshds2007@gmail.com
 * Created: 7/24/2018
 * Modified: 7/24/2018
 */
public abstract class NetworkBoundResource<T, V> {

    private final MediatorLiveData<Resource<T>> result = new MediatorLiveData<>();

    @MainThread
    protected NetworkBoundResource() {
        result.setValue(Resource.loading(null));

        // Always load the data from DB intially so that we have
        LiveData<T> dbSource = loadFromDb();

        // Fetch the data from network and add it to the resource
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch()) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> {
                    if(null != newData)
                        result.setValue(Resource.success(newData)) ;
                });
            }
        });
    }

    /**
     * This method fetches the data from remoted service and save it to local db
     * @param dbSource - Database source
     */
    private void fetchFromNetwork(final LiveData<T> dbSource) {
        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));
        createCall().enqueue(new Callback<V>() {
            @Override
            public void onResponse(@NonNull Call<V> call, @NonNull Response<V> response) {
                result.removeSource(dbSource);
                saveResultAndReInit(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<V> call, @NonNull Throwable t) {
                result.removeSource(dbSource);
                result.addSource(dbSource, newData -> result.setValue(Resource.error(getCustomErrorMessage(t), newData)));
            }
        });
    }

    private String getCustomErrorMessage(Throwable error){

        if (error instanceof SocketTimeoutException) {
            return NYTimesApp.getAppContext().getString(R.string.requestTimeOutError);
        } else if (error instanceof MalformedJsonException) {
            return  NYTimesApp.getAppContext().getString(R.string.responseMalformedJson);
        } else if (error instanceof IOException) {
             return  NYTimesApp.getAppContext().getString(R.string.networkError);
        } else if (error instanceof HttpException) {
            return (((HttpException) error).response().message());
        } else {
            return NYTimesApp.getAppContext().getString(R.string.unknownError);
        }

    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    private void saveResultAndReInit(V response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(), newData -> {
                    if (null != newData)
                        result.setValue(Resource.success(newData));
                });
            }
        }.execute();
    }

    @WorkerThread
    protected abstract void saveCallResult(V item);

    @MainThread
    private boolean shouldFetch() {
        return true;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<T> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Call<V> createCall();

    public final LiveData<Resource<T>> getAsLiveData() {
        return result;
    }
}
