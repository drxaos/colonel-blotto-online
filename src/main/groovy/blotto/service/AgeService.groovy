package blotto.service

import groovy.time.TimeCategory
import groovy.util.logging.Log4j
import org.springframework.stereotype.Service

@Log4j
@Service
public class AgeService {

    public int calcBirthdayYear(int age) {
        return use(TimeCategory) { (new Date() - age.years)[Calendar.YEAR] }
    }

}
