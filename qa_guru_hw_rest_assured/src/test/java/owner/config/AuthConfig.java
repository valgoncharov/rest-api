package owner.config;

import io.restassured.config.Config;
@Config.Sources({
        "classpath:auth.properties"
})

public interface AuthConfig extends Config {

    @org.aeonbits.owner.Config.Key("username")
    String email();

    @org.aeonbits.owner.Config.Key("password")
    String password();
}
