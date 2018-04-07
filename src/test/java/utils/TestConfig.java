package utils;

import org.aeonbits.owner.Config;

import static org.aeonbits.owner.Config.Sources;


@Sources({
        "classpath:test.properties"
})
public interface TestConfig extends Config {

    @Key("test.user.name")
    String name();

    @Key("test.user.password")
    String password();

    @Key("webdriver.chrome.driver")
    String pathToDriver();
}
