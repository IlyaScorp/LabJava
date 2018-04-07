package ru.spbstu.telematics.java;


import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.BeforeSuite;
import utils.TestConfig;

public class PutConfig {
    TestConfig config;

    @BeforeSuite
    public void beforesuite(){
        config = ConfigFactory.create(TestConfig.class);

    }

}
