package com.targetcoders.sowing.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class GoogleUserInfoDTO {

    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "verified_email")
    private String verifiedEmail;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "given_name")
    private String givenName;
    @JsonProperty(value = "family_name")
    private String familyName;
    @JsonProperty(value = "picture")
    private String picture;
    @JsonProperty(value = "locale")
    private String locale;

}
