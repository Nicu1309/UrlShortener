package urlshortener.aeternum.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import urlshortener.common.domain.Click;
import urlshortener.common.domain.Stats;
import urlshortener.common.repository.ClickRepository;
import urlshortener.common.repository.ShortURLRepository;
import urlshortener.common.repository.UserRepository;
import urlshortener.common.web.UrlShortenerController;

@RestController
public class ViewStats {
    private static final Logger LOG = LoggerFactory
        .getLogger(UrlShortenerController.class);

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected ClickRepository clickRepository;

    @Autowired
    protected UserRepository userRepository;

    @RequestMapping(value = "/viewStatistics", method = RequestMethod.GET)
    public ResponseEntity<Stats> shortener() {
        int upTime = 69;
        Long totalURL = shortURLRepository.count();
        Long totalUser = userRepository.count();
        Long averageAccessURL = new Long(0);
        if (!totalURL.equals(new Long(0))) {
            averageAccessURL = clickRepository.count() / totalURL ;
        }

        List<Click> topClicks = getTopUrl(new Long(10));
        LOG.info("Clicks que hay" + clickRepository.count());
        
        int responseTime = 69;
        int memoryUsed = 69;
        int memoryAvailable = 69;
        List<String> topURL = new ArrayList<String>();
        topURL.add("hola1");
        topURL.add("hola2");
        topURL.add("hola3");
        Stats statisticsSystem = new Stats(upTime, totalURL, totalUser, averageAccessURL,
            responseTime, memoryUsed, memoryAvailable, topURL);
        if (statisticsSystem != null) {
            LOG.info("System statistics");
            return new ResponseEntity<>(statisticsSystem, HttpStatus.CREATED);
        } else {
            LOG.info("Error to get the system statistics");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public List<Click> getTopUrl (Long limit) {
        return clickRepository.topURL(limit);
    }
}
