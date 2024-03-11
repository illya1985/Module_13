package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



        public class task1 {
            private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

            public static void main(String[] args) throws IOException {
                String newUserJson = "{\"name\": \"Illya Zaharevich\", \"username\": \"Illuxa\", \"email\": \"I_lluxsa@i.ua\"}";
                String createdUserJson = createUser(newUserJson);
                System.out.println("Created user: " + createdUserJson);

                String updatedUserJson = updateUser(1, "{\"name\": \"New Name\", \"username\": \"newUsername\", \"email\": \"updatedemail@gmail.com\"}");
                System.out.println("Updated user: " + updatedUserJson);

                int deletedUserId = 1;
                int responseCode = deleteUser(deletedUserId);
                System.out.println("Delete user response code: " + responseCode);

                String allUsersInfo = getAllUsers();
                System.out.println("All users: " + allUsersInfo);

                int userId = 1;
                String userInfoById = getUserById(userId);
                System.out.println("User info by id " + userId + ": " + userInfoById);


                String username = "Bill";
                String userInfoByUsername = getUserByUsername(username);
                System.out.println("User info by username " + username + ": " + userInfoByUsername);
            }

            private static String createUser(String newUserJson) throws IOException {
                return sendPostRequest(BASE_URL + "/users", newUserJson);
            }

            private static String updateUser(int userId, String updatedUserInfoJson) throws IOException {
                return sendPutRequest(BASE_URL + "/users/" + userId, updatedUserInfoJson);
            }

            private static int deleteUser(int userId) throws IOException {
                return sendDeleteRequest(BASE_URL + "/users/" + userId);
            }

            private static String getAllUsers() throws IOException {
                return sendGetRequest(BASE_URL + "/users");
            }

            private static String getUserById(int userId) throws IOException {
                return sendGetRequest(BASE_URL + "/users/" + userId);
            }

            private static String getUserByUsername(String username) throws IOException {
                return sendGetRequest(BASE_URL + "/users?username=" + username);
            }

            private static String sendPostRequest(String urlString, String requestBody) throws IOException {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                connection.getOutputStream().write(requestBody.getBytes());

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }
                        return response.toString();
                    }
                } else {
                    return "Error occurred. Response code: " + responseCode;
                }
            }

            private static String sendPutRequest(String urlString, String requestBody) throws IOException {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                connection.getOutputStream().write(requestBody.getBytes());

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }
                        return response.toString();
                    }
                } else {
                    return "Error occurred. Response code: " + responseCode;
                }
            }

            private static int sendDeleteRequest(String urlString) throws IOException {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");

                return connection.getResponseCode();
            }

            private static String sendGetRequest(String urlString) throws IOException {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }
                        return response.toString();
                    }
                } else {
                    return "Error occurred. Response code: " + responseCode;
                }
            }

        }
