package eu.h2020.symbiote.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import eu.h2020.symbiote.model.Platform;
import eu.h2020.symbiote.model.Sensor;
import eu.h2020.symbiote.repository.PlatformRepository;
import eu.h2020.symbiote.repository.SensorRepository;

/**
 * Created by tipech on 04/10/2016.
 */
@Component
public class RepositoryManager {

    @Autowired
    private static PlatformRepository platformRepository;

    @Autowired
    private static SensorRepository sensorRepository;

    @Autowired
    public RepositoryManager(PlatformRepository platformRepository, SensorRepository sensorRepository){
    	
    	Assert.notNull(platformRepository,"Platform repository can not be null!");
    	this.platformRepository = platformRepository;
    	
    	Assert.notNull(sensorRepository,"Sensor repository can not be null!");
    	this.sensorRepository = sensorRepository;
    }

    public static void savePlatform(Platform deliveredObject) {

		platformRepository.save(deliveredObject);
    }

    public static void saveSensor(Sensor deliveredObject) {

		sensorRepository.save(deliveredObject);
    }

}