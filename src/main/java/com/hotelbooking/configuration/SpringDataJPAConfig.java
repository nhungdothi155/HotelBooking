package com.hotelbooking.configuration;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configurable
@EnableJpaRepositories(basePackages = "com.hotelbooking.dao")
@EntityScan(basePackages = "com.hotelbooking.entity")
public class SpringDataJPAConfig {

}
