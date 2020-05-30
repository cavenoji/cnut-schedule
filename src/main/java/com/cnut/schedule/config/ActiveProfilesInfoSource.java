package com.cnut.schedule.config;

import io.micronaut.context.env.Environment;
import io.micronaut.context.env.PropertySource;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.management.endpoint.info.InfoSource;
import javax.inject.Inject;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class ActiveProfilesInfoSource implements InfoSource {

    private final Environment environment;

    @Inject
    public ActiveProfilesInfoSource(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Publisher<PropertySource> getSource() {
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("activeProfiles", environment.getActiveNames());
        return Publishers.just(PropertySource.of("active-profiles", map));
    }
}
