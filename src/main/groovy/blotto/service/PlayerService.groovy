package blotto.service

import blotto.domain.Player
import blotto.errors.ServiceException
import groovy.util.logging.Log4j
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
public class PlayerService {

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

    public Player getLoggedInUser() {
        def username = SecurityContextHolder.getContext()?.getAuthentication()?.getPrincipal()?.username
        if (username) {
            return Player.findByUsername(username)
        }
        return null
    }

}
