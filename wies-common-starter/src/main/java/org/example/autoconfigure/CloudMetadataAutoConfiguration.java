package org.example.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
@ConditionalOnClass({Registration.class})
@ConditionalOnBean(Registration.class)
public class CloudMetadataAutoConfiguration {

  @Autowired
  private Registration registration;

  @PostConstruct
  public void init() {
    if (registration != null) {
      registration.getMetadata()
          .put("preserved.register.time", new DateFormatter("yyyy-MM-dd HH:mm:ss").print(new Date(),
              Locale.SIMPLIFIED_CHINESE));
    }
  }

}
