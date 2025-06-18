package com.meddle.Hatsu.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IgdbService {

   private RestTemplate _template;

   public IgdbService() {
      _template = new RestTemplate();
   }

}
