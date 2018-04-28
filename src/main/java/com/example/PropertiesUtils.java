package com.quark.cobra.posp.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.PropertySourcesLoader;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtils {
    public static ResourceLoader resourceLoader = null;
    public static Properties props_posp = null;

    public static void load() throws IOException{
        resourceLoader = resourceLoader == null ? new DefaultResourceLoader() : resourceLoader;
        Resource resource = resourceLoader.getResource("classpath:/application.properties");
        PropertiesLoaderUtils.loadProperties(new EncodedResource(resource));
        Properties props = new Properties();
        PropertiesLoaderUtils.fillProperties(props, resource);
        props_posp = props;
    }

    public static String getProperty(String key, String defaultValue){
        return props_posp == null? "" :props_posp.getProperty(key,defaultValue);
    }

    public static String getProperty(String key){
        return props_posp == null? "" :props_posp.getProperty(key,null);
    }

    //配置文件可允许路径
    private static final String DEFAULT_SEARCH_LOCATIONS = "classpath:/,classpath:/config/,file:./,file:./config/";
    private Set<String> getSearchLocations() {
        List<String> list = Arrays.asList(StringUtils.trimArrayElements(
            StringUtils.commaDelimitedListToStringArray(DEFAULT_SEARCH_LOCATIONS)));
        Collections.reverse(list);
        Set<String> locations = new LinkedHashSet<String>();
        locations.addAll(new LinkedHashSet<String>(list));
        return locations;
    }

    //配置文件可允许后缀
    private Set<String> getAllFileExtensions() {
        PropertySourcesLoader propertiesLoader = new PropertySourcesLoader();
        return propertiesLoader.getAllFileExtensions();
    }
}
