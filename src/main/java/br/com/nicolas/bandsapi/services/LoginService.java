package br.com.nicolas.bandsapi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.nicolas.bandsapi.client.login.AuthSpotifyClient;
import br.com.nicolas.bandsapi.client.login.LoginRequest;
import br.com.nicolas.bandsapi.services.exceptions.SpotifyAuthException;

@Service
public class LoginService {

    private AuthSpotifyClient authSpotifyClient;

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    public LoginService(AuthSpotifyClient authSpotifyClient) {
        this.authSpotifyClient = authSpotifyClient;
    }

    public String loginSpotify() {
        try {
            var request = new LoginRequest("client_credentials", clientId, clientSecret);
            String requestBody = "grant_type=" + request.grantType() +
                    "&client_id=" + request.clientId() +
                    "&client_secret=" + request.clientSecret();
            return authSpotifyClient.login(requestBody).accessToken();
        } catch (SpotifyAuthException ex) {
            throw new SpotifyAuthException("Failed to authenticate with Spotify");
        }
    }
}
