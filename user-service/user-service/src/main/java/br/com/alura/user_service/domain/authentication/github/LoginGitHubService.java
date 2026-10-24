package br.com.alura.user_service.domain.authentication.github;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service 
public class LoginGitHubService {
    @Value("${oauth.client-id}")
    private String clientId;
    @Value("${oauth.client-secret}")
    private String clientSecret;
    private String redirectUri = "http://localhost:8082/user-service/login/github/authorized";
    private final RestClient restClient;

    public LoginGitHubService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
        .requestFactory(new JdkClientHttpRequestFactory())
        .build();
    }

    public String generateUrl(){
        return "https://github.com/login/oauth/authorize"+
                "?client_id="+clientId +
                "&redirect_uri="+redirectUri +
                "&scope=read:user,user:email";
    }

    private String getToken(String code) {
        var response = restClient.post()
                .uri("https://github.com/login/oauth/access_token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("code", code, "client_id", clientId,
                        "client_secret", clientSecret, "redirect_uri", redirectUri))
                .retrieve()
                .body(Map.class);
        return response.get("access_token").toString();
    }

    public String getEmail(String code){
        var token = getToken(code);

        var headers = new HttpHeaders();
        headers.setBearerAuth(token);

        var response = restClient.get()
                .uri("https://api.github.com/user/emails")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(DataEmailDto[].class);

        for(DataEmailDto d: response){
            if(d.primary() && d.verified())
                return d.email();
        }
        return null;
    }


}
