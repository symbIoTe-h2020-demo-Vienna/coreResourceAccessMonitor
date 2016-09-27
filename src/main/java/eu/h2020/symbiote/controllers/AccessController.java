package eu.h2020.symbiote;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.HashMap;

@RestController
public class AccessController {

    @RequestMapping(value="/access/{resourceIdList}", method=RequestMethod.GET)
    @ResponseBody
    public Map<String, String> accessResources(@PathVariable String[] resourceIdList) {

        RestTemplate restTemplate = new RestTemplate();

    	Map<String, String> resourceIdMap = new HashMap(); 
    	for(String id : resourceIdList)
    		resourceIdMap.put(id, "");

        Map<String, String> ids = restTemplate.getForObject(
                "http://localhost:8080/urls/", Map.class);

        for(Map.Entry<String, String> id : ids.entrySet())
            ids.put(id.getKey(), String.valueOf(Integer.parseInt(id.getValue()) + 9));

        return ids;
    }

    @RequestMapping(value="/access", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> accessResources(@RequestBody Map<String, String> resourceIdMap) {

        RestTemplate restTemplate = new RestTemplate();

        for(Map.Entry<String, String> id : resourceIdMap.entrySet())
            resourceIdMap.put(id.getKey(), "");

        Map<String, String> ids = restTemplate.getForObject(
                "http://localhost:8080/urls/", Map.class);
        

        for(Map.Entry<String, String> id : ids.entrySet())
    		ids.put(id.getKey(), String.valueOf(Integer.parseInt(id.getValue()) + 9));

        return ids;
    }

}