package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class task2 {

    public static void main(String[] args) {
        try {
            exportCommentsForLastPostOfUser(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportCommentsForLastPostOfUser(int userId) throws IOException {
        URL postsUrl = new URL("https://jsonplaceholder.typicode.com/users/" + userId + "/posts");
        HttpURLConnection postsConnection = (HttpURLConnection) postsUrl.openConnection();
        postsConnection.setRequestMethod("GET");

        if (postsConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream postsStream = postsConnection.getInputStream();
            JSONArray postsArray = new JSONArray(new String(postsStream.readAllBytes()));

            JSONObject lastPost = postsArray.getJSONObject(postsArray.length() - 1);
            int lastPostId = lastPost.getInt("id");

            URL commentsUrl = new URL("https://jsonplaceholder.typicode.com/posts/" + lastPostId + "/comments");
            HttpURLConnection commentsConnection = (HttpURLConnection) commentsUrl.openConnection();
            commentsConnection.setRequestMethod("GET");

            if (commentsConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream commentsStream = commentsConnection.getInputStream();
                JSONArray commentsArray = new JSONArray(new String(commentsStream.readAllBytes()));

                writeCommentsToFile(userId, lastPostId, commentsArray);
            } else {
                System.out.println("Failed to fetch comments. Response code: " + commentsConnection.getResponseCode());
            }
            commentsConnection.disconnect();
        } else {
            System.out.println("Failed to fetch posts. Response code: " + postsConnection.getResponseCode());
        }
        postsConnection.disconnect();
    }

    public static void writeCommentsToFile(int userId, int postId, JSONArray commentsArray) throws IOException {
        String fileName = "user-" + userId + "-post-" + postId + "-comments.json";
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(commentsArray.toString(4));
        fileWriter.close();
        System.out.println("Comments exported to file: " + fileName);
    }
}
