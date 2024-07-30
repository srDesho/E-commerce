package com.cristianml.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// This class is used to configure the addResourceHandlers method to specify custom paths
// for the static resources we'll be working with (e.g., uploading files like images).
// It allows us to add external paths and store them in a folder that we specify.
@org.springframework.context.annotation.Configuration
public class Configuration implements WebMvcConfigurer {

    // We create a variable to receive the path of the selected folder on the computer,
    // retrieved from the application.properties using the @Value annotation.
    @Value("${cristian.values.path_upload}")
    private String path_upload;

    // We inject our cartInterceptor
    @Autowired
    CartInterceptor cartInterceptor;

    // We overwrite addResourceHandlers(ResourceHandlerRegistry registry) method.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // We add the folder name and double ** to specify that it will use everything inside
        registry.addResourceHandler("/upload/**")
                // We add the path
                .addResourceLocations("file: " + this.path_upload);
    }

    // Registers custom interceptors to the application context
    // The addInterceptor method adds the specified interceptor to the registry,
    // allowing it to intercept HTTP requests and responses according to its defined logic.
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cartInterceptor);
    }

}
