package org.example;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;

public class task3 {

    public static void main(String[] args) {
        try {
            fetchOpenTasksForUser(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fetchOpenTasksForUser(int userId) throws IOException {
        URL tasksUrl = new URL("https://jsonplaceholder.typicode.com/users/" + userId + "/todos");
        HttpURLConnection tasksConnection = (HttpURLConnection) tasksUrl.openConnection();
        tasksConnection.setRequestMethod("GET");

        if (tasksConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream tasksStream = tasksConnection.getInputStream();
            JSONArray tasksArray = new JSONArray(new String(tasksStream.readAllBytes()));

            JSONArray openTasksArray = new JSONArray();
            for (int i = 0; i < tasksArray.length(); i++) {
                if (!tasksArray.getJSONObject(i).getBoolean("completed")) {
                    openTasksArray.put(tasksArray.getJSONObject(i));
                }
            }

            writeOpenTasksToFile(userId, openTasksArray);
        } else {
            System.out.println("Failed to fetch tasks. Response code: " + tasksConnection.getResponseCode());
        }
        tasksConnection.disconnect();
    }

    public static void writeOpenTasksToFile(int userId, JSONArray openTasksArray) throws IOException {
        String fileName = "user-" + userId + "-open-tasks.json";
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(openTasksArray.toString(4));
        fileWriter.close();
        System.out.println("Open tasks exported to file: " + fileName);
    }
}
