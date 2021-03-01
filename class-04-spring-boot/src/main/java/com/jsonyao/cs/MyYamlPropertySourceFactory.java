package com.jsonyao.cs;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.List;

/**
 * 提供YamlPropertySourceLoader来加载yml属性
 */
public class MyYamlPropertySourceFactory extends DefaultPropertySourceFactory {

    @Override
    public org.springframework.core.env.PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        if(resource == null){
            return super.createPropertySource(name, resource);
        }

        List<PropertySource<?>> sources = new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource());
        System.err.println(sources.toString());
        return sources.get(0);
    }

}
