package com.example.whatsnew;

import android.nfc.Tag;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private final static String LOGGING_TAG="";

    private QueryUtils(){};

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url){
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            Log.e(LOGGING_TAG, "Error of making HTTP request: ",e);
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
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

    /**
     * Get News with http request to given url
     * extract the news list from the response
     */
    public static ArrayList<News> getNewsFromUrl(String url){
        if(url==null) {
            return null;
        };

        String response = makeHttpRequest(createUrl(url));
        ArrayList<News> newsList = extractNewsFromJson(response);
        return newsList;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOGGING_TAG, "Error with creating URL", e);
        }
        return url;
    }

    /**
     * Extract News List from response
     */
    public static ArrayList<News> extractNewsFromJson(String jsonResponse){
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<News> newsList = new ArrayList<>();

        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // build up a list of News objects with the corresponding data.
            JSONObject object = new JSONObject(jsonResponse);
            JSONObject response = object.getJSONObject("response");
            JSONArray results  = response.getJSONArray("results");
            for(int i=0;i<results.length();i++){
                JSONObject newsJson = results.getJSONObject(i);
                String category = newsJson.getString("sectionName");
                String title = newsJson.getString("webTitle");
                String date = newsJson.getString("webPublicationDate");
                String url  = newsJson.getString("webUrl");
                newsList.add(new News(title, category, date,url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOGGING_TAG, "Problem parsing the news JSON response", e);
        }

        // Return the list of earthquakes
        return newsList;
    }
}
