package com.example.richpanel;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LoadTest {

    private static final int CONNECTIONS = 100;
    private static final int DURATION = 10; // in seconds
    private static final int PIPELINING = 10;

    public static void main(String[] args) throws Exception {
        runAllTests();
    }

    private static CompletableFuture<Void> runLoadTest(String endpoint, String method, String payload) {
        ExecutorService executor = Executors.newFixedThreadPool(CONNECTIONS);
        HttpClient client = HttpClient.newBuilder().executor(executor).build();

        long startTime = System.nanoTime();
        CompletableFuture<Void>[] futures = new CompletableFuture[CONNECTIONS * PIPELINING];

        for (int i = 0; i < CONNECTIONS * PIPELINING; i++) {
            Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080" + endpoint))
                    .header("Content-Type", "application/json")
                    .header("X-API-KEY", "richpanelsecurity");

            if ("POST".equalsIgnoreCase(method)) {
                requestBuilder = requestBuilder.POST(BodyPublishers.ofString(payload));
            } else if ("GET".equalsIgnoreCase(method)) {
                requestBuilder = requestBuilder.GET();
            } else if ("PUT".equalsIgnoreCase(method)) {
                requestBuilder = requestBuilder.PUT(BodyPublishers.ofString(payload));
            }

            HttpRequest request = requestBuilder.build();

            futures[i] = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        long latency = (System.nanoTime() - startTime) / 1_000_000;
                        System.out.println("Received response: " + response.statusCode() + " in " + latency + " ms");
                        return null;
                    });
        }

        return CompletableFuture.allOf(futures).thenRun(() -> {
            long elapsedTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime);
            System.out.println("Load test for " + endpoint + "  Method  " + method + " completed in " + elapsedTime + " seconds.");
            executor.shutdown();
        });
    }

    private static void runAllTests() throws Exception {
        String jsonPayload = "{\"id\": 2, \"title\": \"Updated Post Title\", \"content\": \"Updated content for the post.\"}";
        runLoadTest("/posts", "POST", "{}").join();
//        Thread.sleep(2000);
        runLoadTest("/posts/1", "GET", "").join();
//        Thread.sleep(2000);
        runLoadTest("/posts/1", "PUT", jsonPayload).join();
//        Thread.sleep(2000);
        // Add more endpoints and methods as needed
    }
}
