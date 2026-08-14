package com.backend.MyBackend.common.configurations;

import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DynamicConfigManager{

    private static final String CONFIG_FILE = "/app/config/background.properties";
    private final ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration> builder;

    public DynamicConfigManager(){

        Parameters params = new Parameters();

        this.builder = new ReloadingFileBasedConfigurationBuilder<>(
                PropertiesConfiguration.class).configure(
                        params.fileBased()
                                .setFile(new File(CONFIG_FILE)));

        log.info("Dynamic configuration loaded from: {}",CONFIG_FILE);
    }

    public String getProperty(String key){
        try{
            builder.getReloadingController().checkForReloading(null);
            return builder
                    .getConfiguration()
                    .getString(key);

        } catch (org.apache.commons.configuration2.ex.ConfigurationException e){
            log.error(
                    "Failed to reload dynamic configuration. Key: {}",
                    key,
                    e);
            return null;
        }
    }
}
