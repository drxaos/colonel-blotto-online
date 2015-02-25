package blotto.service.app

import blotto.aop.inprogress.DisableIfBattleInProgress
import blotto.domain.Player
import blotto.errors.UserAlreadyExists
import blotto.mail.app.MailHelper
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
public class PlayerService {

    @Autowired
    MailHelper mailHelper

    @Transactional
    public Player createPlayer(String username, String password, String email, String fullName) {
        if (!Player.findByUsername(username)) {
            Player player = new Player(username: username, email: email, password: password, fullName: fullName)
            player.save(flush: true, failOnError: true)
            return player
        } else {
            throw new UserAlreadyExists(username)
        }
    }

    @Transactional
    public Player signUpPlayer(String username, String password, String email, String fullName) {
        def player = createPlayer(username, password, email, fullName)
        mailHelper.onSignUp(email, username, password, fullName)
        return player
    }

    @Transactional
    @DisableIfBattleInProgress
    public Player updatePlayer(Player player, String password, String email, String fullName) {
        if (!player) {
            throw new IllegalArgumentException("wrong-args")
        }
        player.fullName = fullName
        player.email = email
        player.password = password
        player.save(flush: true, failOnError: true)
        return player
    }

    @Transactional
    public List listPlayers(Player forPlayer) {
        Player.findAll("from Player p order by p.score desc, p.position asc, p.strategyLastUpdated desc", [:], [max: 1000])
    }

    public Player getCurrentLoggedInUser() {
        def principal = SecurityContextHolder.getContext()?.getAuthentication()?.getPrincipal()
        def username = ""
        if (principal instanceof String) {
            username = principal
        } else {
            username = principal?.username
        }
        if (username) {
            return Player.findByUsername(username)
        }
        return null
    }

}
