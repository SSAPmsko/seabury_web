package com.seabury.web.vo.alfresco;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("alfresco")
@Getter
@Setter
public class AlfrescoPropertiesVO {
    private String id;

    private String password;

    private String url;

    private String port;

    private String site;

    private String connection;
}
