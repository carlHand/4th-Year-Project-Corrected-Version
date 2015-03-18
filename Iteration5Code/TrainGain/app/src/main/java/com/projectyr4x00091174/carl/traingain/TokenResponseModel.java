package com.projectyr4x00091174.carl.traingain;

/**
 * Created by carl on 08/02/2015.
 */
public class TokenResponseModel {
    ///[JsonProperty("access_token")]
    public String AccessToken; //{ get; set; }

    //[JsonProperty(".expires")]
    public String ExpiresAt;// { get; set; }

   // [JsonProperty("expires_in")]
    public int ExpiresIn; //{ get; set; }

   // [JsonProperty(".issued")]
    public String IssuedAt; //{ get; set; }

   // [JsonProperty("token_type")]
    public String TokenType; //{ get; set; }

   // [JsonProperty("userName")]
    public String Username; //{ get; set; }
}
