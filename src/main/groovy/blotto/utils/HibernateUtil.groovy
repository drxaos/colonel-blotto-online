package blotto.utils

import org.hibernate.Hibernate
import org.hibernate.collection.spi.PersistentCollection
import org.hibernate.proxy.HibernateProxy

class HibernateUtil {

    def static initializeAndUnproxy(Collection vars) {
        vars.each { initializeAndUnproxy(it) }
    }

    def static initializeAndUnproxy(var) {
        if (var == null) return null

        Hibernate.initialize(var);
        if (var instanceof HibernateProxy || var instanceof PersistentCollection) {
            var = var.getHibernateLazyInitializer().getImplementation();
        }
        var
    }

}
