package com.datadit.newsapplication;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private RecyclerView recyclerView;
    private TextView textViewEmpty;
    private ProgressBar progressBar;
    NewsAdapter newsAdapter;
    private static final String NEWS_URL = "https://newsapi.org/v2/top-headlines?country=us&category=technology&apiKey=2856b411a447490680d4b56bba405a28";
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    static String newsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.loading_spinner);
        textViewEmpty = findViewById(R.id.emptyView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        recyclerView.setAdapter(newsAdapter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                getLoaderManager().initLoader(1, null, this);
            } else {
                progressBar.setVisibility(View.GONE);
                textViewEmpty.setText(R.string.no_internet_connection);
            }
        }
    }

    public static List<News> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem in making the HTTP request", e);
        }
        return extractFeatureFromJson(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "HTTP RESPONSE =" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Exception = " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                Log.e(LOG_TAG, "Exception");
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJson(String newsJSON) {

        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        List<News> newsList = new ArrayList<>();
        try {
            JSONObject baseJSONObject = new JSONObject(newsJSON);

            if (baseJSONObject.has("articles")) {
                JSONArray jsonArrayArticles = baseJSONObject.optJSONArray("articles");
                {
                    for (int i = 0; i < jsonArrayArticles.length(); i++) {
                        JSONObject jsonObject = jsonArrayArticles.optJSONObject(i);
                        JSONObject jsonObjectSource = jsonObject.getJSONObject("source");
                        if (jsonObjectSource.has("name")) {
                            newsName = jsonObjectSource.getString("name");
                            if (newsName.equals("null"))
                                newsName = "Unknown";
                        }
                        String author = jsonObject.getString("author");
                        String title = jsonObject.getString("title");
                        String date = jsonObject.getString("publishedAt");
                        String url = jsonObject.getString("url");
                        String description = jsonObject.getString("description");
                        if (author.equals("null"))
                            author = "Unknown";
                        News news = new News(title, date, newsName, url, author, description);
                        newsList.add(news);
                    }

                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem in parsing JSON", e);
        }
        return newsList;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, NEWS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        progressBar.setVisibility(View.GONE);
        textViewEmpty.setText(R.string.no_news);
        newsAdapter.refreshNews(newsList);
        if (newsList != null && !newsList.isEmpty()) {
            newsAdapter.notifyDataSetChanged();
            textViewEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }
}
