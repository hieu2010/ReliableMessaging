package main;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import data.Weather;

public class CommunicationClient {

	private HttpClient client;
	
	public CommunicationClient() {
		this.client = HttpClient.newBuilder()
			      .version(Version.HTTP_2)
			      //.followRedirects(Redirect.NORMAL)
			      //.proxy(ProxySelector.of(new InetSocketAddress("www-proxy.com", 8080)))
			      //.authenticator(Authenticator.getDefault())
			      .build();
	}
	
	
	// Sends a simple GET request to mydealz.
	// TODO: Error-Handling (repeated requests until complete
	public void sendReliableRequest(Weather w) {
		HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create("https://www.mydealz.de/"))
			      .timeout(Duration.ofMinutes(1))
			      //.header("Content-Type", "application/json")
			      .GET()
			      .build();
		HttpResponse<String> response = null;
		try {
			response = client.send(request, BodyHandlers.ofString());
		} catch (IOException e) {
			// TODO: Handle IO Exception
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO: Handle InterruptedException
			e.printStackTrace();
		}
			System.out.println("Server responded - Status-Code: " + response.statusCode());
			//System.out.println(response.body());
	}
}
