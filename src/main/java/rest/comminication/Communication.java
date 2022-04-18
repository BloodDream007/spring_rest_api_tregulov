package rest.comminication;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Communication {

    private final String URL = "http://94.198.50.185:7081/api/users";

    @Autowired
    private RestTemplate restTemplate;

    List<String> cookies;
    HttpHeaders headers = new HttpHeaders();
    String result = "";


    public void doAllRequests(){
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<User>>() {
                });
        List<User> allUsers = responseEntity.getBody();

        cookies = responseEntity.getHeaders().get("Set-Cookie");
//        headers.set("Cookie");

        headers.set("Cookie",cookies.stream().collect(Collectors.joining(";")));

        System.out.println("cookies= " + cookies);
        System.out.println("____");
        System.out.println("headers= " + headers);
        saveUser();
        updateUser();
        deleteUser(3L);
        System.out.println("result: " + result);


    }

    public User getUser(Long id){
        User user = restTemplate.getForObject(URL + "/" + id, User.class);
        return user;
    }

    public void saveUser(){
        User user = new User(3L,"James", "Brown", (byte) 22);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<String> firstPart = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        System.out.println("first part = " + firstPart.getBody());
        result = result + firstPart.getBody();
    }

    public void updateUser() {
        User user = new User(3L,"Thomas", "Shelby", (byte) 22);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> secondPart = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        result = result + secondPart.getBody();

    }

    public void deleteUser(Long id) {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> lastPart = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class);
        result = result + lastPart.getBody();

    }
}
