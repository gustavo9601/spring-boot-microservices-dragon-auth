package springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.models.dto;

import java.io.Serializable;

public class TokenDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String token;

    public TokenDto() {
    }

    public TokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
