package blotto.service.app

import blotto.aop.inprogress.DisableIfBattleInProgress
import blotto.domain.Player
import blotto.domain.User
import blotto.errors.player.EmailAlreadyExists
import blotto.errors.player.UsernameAlreadyExists
import blotto.mail.app.Mailer
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
public class PlayerService {

    @Autowired
    Mailer mailer

    @Transactional
    public Player createPlayer(String username, String password, String email, String fullName) {
        if (User.findByUsername(username)) {
            throw new UsernameAlreadyExists(username)
        }
        if (User.findByEmail(email)) {
            throw new EmailAlreadyExists(email)
        }

        User user = new User(username: username, email: email, password: password, fullName: fullName)
        user.save(flush: true, failOnError: true)

        Player player = new Player(user: user)
        player.save(flush: true, failOnError: true)

        return player
    }

    @Transactional
    public Player signUpPlayer(String username, String password, String email, String fullName) {
        def player = createPlayer(username, password, email, fullName)
        mailer.onSignUp(email, username, password, fullName)
        return player
    }

    @Transactional
    @DisableIfBattleInProgress
    public User updateUser(User user, String password, String email, String fullName) {
        if (!user) {
            throw new IllegalArgumentException("wrong-args")
        }
        user.fullName = fullName
        user.email = email
        user.password = password
        user.save(flush: true, failOnError: true)
        return user
    }

    @Transactional
    public List listPlayers(Player forPlayer) {
        return Player.findAll("from Player p left join fetch p.user order by p.score desc, p.position asc, p.strategyLastUpdated desc", [:])
    }

    public User getCurrentLoggedInUser() {
        def principal = SecurityContextHolder.getContext()?.getAuthentication()?.getPrincipal()
        def username = ""
        if (principal instanceof String) {
            username = principal
        } else {
            username = principal?.username
        }
        if (username) {
            return User.findByUsername(username)
        }
        return null
    }

}
