package ru.pgk.smartbudget.security.jwt.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt.secret")
public class JwtProperties {

    private String access;
    private String refresh;
}