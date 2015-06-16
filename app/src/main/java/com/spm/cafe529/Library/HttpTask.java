package com.spm.cafe529.Library;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by park on 2015. 6. 15..
 */
public class HttpTask extends AsyncTask<Void, Void, JSONObject> {

    String urlPath;
    JSONObject json;
    TaskListener taskListener;

    public HttpTask(String urlPath, JSONObject json, TaskListener tl) {
        this.urlPath = urlPath;
        this.json = json;
        taskListener = tl;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {



        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(urlPath);

            List<BasicNameValuePair> nameValue = new ArrayList<BasicNameValuePair>(2);

            Log.v("JSON",json.toString());
            nameValue.add(new BasicNameValuePair("data", json.toString()));
            //웹 접속 - utf-8 방식으로
            httpPost.setEntity(new UrlEncodedFormEntity(nameValue));


            HttpResponse response = httpClient.execute(httpPost);
            String responceString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);


            Log.d("RESULT MESSAGE", responceString);

            JSONObject result_json = new JSONObject(responceString);

            return result_json;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {

        if(jsonObject == null) taskListener.onCancelled();
        else {
            taskListener.onReceived(jsonObject);
        }
        super.onPostExecute(jsonObject);

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
