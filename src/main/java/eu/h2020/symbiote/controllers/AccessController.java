package eu.h2020.symbiote;

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

@RestController
public class AccessController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value="/access/{resourceIdList}", method=RequestMethod.GET)
    @ResponseBody
    public Map<String, String> accessResources(@PathVariable String[] resourceIdList) {

    	Map<String, String> resourceIdMap = new HashMap(); 
    	for(String id : resourceIdList)
    		resourceIdMap.put(id, "");

        System.out.println("Before Rest request!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Map<String, String> ids = restTemplate.getForObject(
                "http://symbIoTe.com/urls", Map.class);
        System.out.println("After Rest request!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        for(Map.Entry<String, String> id : ids.entrySet())
            ids.put(id.getKey(), String.valueOf(Integer.parseInt(id.getValue()) + 9));
        // Map<String, String> ids = new HashMap();
        return ids;
    }

    @RequestMapping(value="/access", method=RequestMethod.POST)
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