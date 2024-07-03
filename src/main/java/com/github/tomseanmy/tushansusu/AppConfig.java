package com.github.tomseanmy.tushansusu;

import com.github.tomseanmy.tushansusu.property.NoticeProperty;
import com.github.tomseanmy.tushansusu.wxwork.WxworkSdk;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tomsean
 */
@Configuration
@EnableConfigurationProperties({NoticeProperty.class})
public class AppConfig {

    private final NoticeProperty noticeProperty;
    public AppConfig(NoticeProperty noticeProperty) {
        this.noticeProperty = noticeProperty;
    }

    @Bean
    public WxworkSdk wxworkSdk() {
        return new WxworkSdk(noticeProperty.getWxwork());
    }
}
