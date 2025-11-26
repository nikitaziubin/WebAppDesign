package com.example.WebApplicationDesign.Descriptions;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.Data;
import lombok.ToString;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Data
class Jwiki {
    final String BASE_URL="https://en.wikipedia.org/api/rest_v1/page/summary/";
    String subject=null;
    String displayTitle="";
    String imageURL="";
    @ToString.Exclude
    String extractText="";

    Jwiki(String subject)
    {
        this.subject=subject;
        getData();
    }

    private void getData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL+subject)
                .get()
                .header("User-Agent", "MyFilmApp/1.0 (your-email@example.com)")
                .header("Accept", "application/json")
                .build();
        try {
            Response response=client.newCall(request).execute();
            String data = response.body().string();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(data);
            //get text
            extractText = (String)jsonObject.get("extract");

        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}