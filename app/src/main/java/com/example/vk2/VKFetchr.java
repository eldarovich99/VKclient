package com.example.vk2;

import android.net.Uri;
import android.util.Log;

import com.example.vk2.ResponseClasses.AuthResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VKFetchr {
    private final static String TAG = "VKFetchr";
    private final static String GRANT_TYPE = "grant_type";
    private final static String PASSWORD = "password";
    private final static String CLIENT_ID = "client_id";
    private final static String CLIENT_SECRET = "client_secret";
    private final static String USERNAME = "username";
    public final static String ACCESS_TOKEN = "access_token";
    public final static String EXPIRES_IN = "expires_in";
    public final static String USER_ID = "user_id";
    private AuthResponse mAuthResponse;

    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                InputStream in = connection.getInputStream();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                    throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
                }
                int bytesRead = 0;
                byte[] buffer = new byte[1024];
                while ((bytesRead = in.read(buffer)) > 0){
                    out.write(buffer, 0, bytesRead);
                }
            }
            catch (IOException ioe){
                Log.e(TAG, "incorrect login or password", ioe);
            }
            out.close();
            return out.toByteArray();
        }
        finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    public AuthResponse auth(String username, String password){
        try{
            String url = Uri.parse("https://oauth.vk.com/token/")
                    .buildUpon()
                    .appendQueryParameter(GRANT_TYPE, PASSWORD)
                    .appendQueryParameter(CLIENT_ID, "2274003")
                    .appendQueryParameter(CLIENT_SECRET, "hHbZxrka2uZ6jB1inYsH")
                    .appendQueryParameter(USERNAME, username)
                    .appendQueryParameter(PASSWORD, password)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            mAuthResponse = new AuthResponse();
            parseAuthItems(mAuthResponse, jsonBody);
        }
        catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        catch (JSONException joe){
            Log.e(TAG, "Failed to parse JSON", joe);
        }
        return mAuthResponse;
    }

    private void parseAuthItems(AuthResponse authResponse, JSONObject jsonBody) throws IOException, JSONException{
        authResponse.setAccessToken(jsonBody.getString(ACCESS_TOKEN));
        authResponse.setExpiresIn(jsonBody.getInt(EXPIRES_IN));
        authResponse.setUserID(jsonBody.getInt(USER_ID));
    }

    /*
    private void parseItems(List<GalleryItem> items, JSONObject jsonBody)
 throws IOException, JSONException {
 JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
 JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");
 for (int i = 0; i < photoJsonArray.length(); i++) {
 JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);
 GalleryItem item = new GalleryItem();
 item.setId(photoJsonObject.getString("id"));
 item.setCaption(photoJsonObject.getString("title"));
 if (!photoJsonObject.has("url_s")) {
 continue;
 }
 item.setUrl(photoJsonObject.getString("url_s"));
 items.add(item);
 }
 }
     */
}
