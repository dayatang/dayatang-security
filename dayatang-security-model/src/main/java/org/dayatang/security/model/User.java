package org.dayatang.security.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;


/**
 * 用户。用于登录系统的主体。
 *
 * @author yyang
 *
 */
@Entity
@DiscriminatorValue("U")
public class User extends Actor {

    private static final long serialVersionUID = 7316666894482866864L;

    private Set<UserGroup> groups = new HashSet<UserGroup>();

    protected User() {
    }

    public User(String name) {
        super(name);
    }

    @ManyToMany(mappedBy = "users")
    public Set<UserGroup> getGroups() {
        return Collections.unmodifiableSet(groups);
    }

    public boolean hasPermission(Permission permission, Scope scope) {
        if (getPermissions(scope).contains(permission)) {
            return true;
        }
        for (UserGroup group : getGroups()) {
            if (group.hasPermission(permission, scope)) {
                return true;
            }
        }
        return false;
    }

    public static User getByName(String username) {
        return getRepository().createCriteriaQuery(User.class).eq("name", username).singleResult();
    }
}
