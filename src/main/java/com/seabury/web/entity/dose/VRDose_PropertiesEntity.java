package com.seabury.web.entity.dose;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class VRDose_PropertiesEntity {
    private String id;
    private String password;
    private String url;
    private String port;
    public VRDose_PropertiesEntity(@Value("${vrdose.id}") String id,
                                   @Value("${vrdose.password}") String password,
                                   @Value("${vrdose.url}") String url,
                                   @Value("${vrdose.port}") String port){
        this.id = id;
        this.password = password;
        this.url = url;
        this.port = port;
    }
}
