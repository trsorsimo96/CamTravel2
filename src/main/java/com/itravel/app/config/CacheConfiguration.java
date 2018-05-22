package com.itravel.app.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.itravel.app.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.itravel.app.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Company.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Company.class.getName() + ".cars", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Company.class.getName() + ".trains", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Company.class.getName() + ".bookings", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Car.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Car.class.getName() + ".voyages", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Train.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Train.class.getName() + ".wagons", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Train.class.getName() + ".voyages", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Wagon.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.ModelCar.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.ModelCar.class.getName() + ".cars", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.ModelCar.class.getName() + ".wagons", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.City.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.City.class.getName() + ".routes", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Routes.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.TypePassenger.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.TypePassenger.class.getName() + ".passengers", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Passenger.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Voyage.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Voyage.class.getName() + ".voyages", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.TypeVoyage.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.TypeVoyage.class.getName() + ".voyages", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Booking.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Booking.class.getName() + ".passengers", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Booking.class.getName() + ".bookings", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Agency.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Agency.class.getName() + ".bookings", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Agency.class.getName() + ".deposits", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Deposit.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Classe.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Classe.class.getName() + ".wagons", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.Classe.class.getName() + ".cars", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.ModePayment.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.ModePayment.class.getName() + ".bookings", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.ModePayment.class.getName() + ".deposits", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.StateVoyage.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.StateVoyage.class.getName() + ".voyages", jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.CompanyClasse.class.getName(), jcacheConfiguration);
            cm.createCache(com.itravel.app.domain.CompanyAgency.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
