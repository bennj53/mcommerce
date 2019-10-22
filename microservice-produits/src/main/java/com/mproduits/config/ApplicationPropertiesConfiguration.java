package com.mproduits.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("mes.configs")
@RefreshScope
public class ApplicationPropertiesConfiguration {
    private int limitDeProduit;

}
