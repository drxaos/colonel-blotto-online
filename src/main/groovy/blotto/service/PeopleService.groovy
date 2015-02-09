package blotto.service

import blotto.domain.Player
import blotto.errors.ServiceException
import groovy.util.logging.Log4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
public class PeopleService {

    @Transactional
    public Player createPlayer(String username, String password, String email) {
        if (!Player.findByUsername(username)) {
            Player player = new Player(username: username, email: email, password: password)
            player.save(flush: true, failOnError: true)
            return player
        } else {
            throw new ServiceException("player exists", [username: username])
        }
    }

}
