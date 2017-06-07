package com.vinit.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: bothravinit_old
 * Date: 2017/06/06
 * Time: 2:00
 */
@ConfigurationProperties(prefix = "gdma", ignoreUnknownFields = true)
@Getter
@Setter
@Configuration
public class GdmaConfig {

    private String key;
}
