package eu.h2020.symbiote.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.HashMap;

import eu.h2020.symbiote.repository.PlatformRepository;
import eu.h2020.symbiote.repository.SensorRepository;
import eu.h2020.symbiote.model.Sensor;
import eu.h2020.symbiote.model.Platform;

@RestController
@RequestMapping("/cram_api")
public class AccessController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PlatformRepository platformRepo;

    @Autowired
    private SensorRepository sensorRepo;

    @RequestMapping(value="/resource_urls/{resourceIdList}", method=RequestMethod.GET)
    @ResponseBody
    public Map<String, String> accessResources(@PathVariable String[] resourceIdList) {

        Map<String, String> ids = new HashMap();

    	// Map<String, String> resourceIdMap = new HashMap(); 
    	// for(String id : resourceIdList)
    	// 	resourceIdMap.put(id, "");

     //    Map<String, String> ids = restTemplate.getForObject(
     //            "http://symbIoTe.com/urls", Map.class);

        System.out.println("Before Query!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


        for(String id : resourceIdList) {
            Sensor sensor = sensorRepo.findOne(id);
            if (sensor != null)
                ids.put(sensor.getId(), sensor.getPlatform().getResourceAccessProxyUrl().toString() + '/' 
                        + sensor.getPlatform().getId() + '/' + sensor.getId());
        }
        
        System.out.println("After Query!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        // for(Map.Entry<String, String> id : ids.entrySet())
        //     ids.put(id.getKey(), String.valueOf(Integer.parseInt(id.getValue()) + 9));

        

        return ids;
    }

    @RequestMapping(value="/resource_urls", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> accessResources(@RequestBody Map<String, String> resourceIdMap) {

        for(Map.Entry<String, String> id : resourceIdMap.entrySet())
            resourceIdMap.put(id.getKey(), "");

        Map<String, String> ids = restTemplate.getForObject(
                "http://symbIoTe.com/urls", Map.class);
        

        for(Map.Entry<String, String> id : ids.entrySet())
    		ids.put(id.getKey(), String.valueOf(Integer.parseInt(id.getValue()) + 9));

        return ids;
    }

}