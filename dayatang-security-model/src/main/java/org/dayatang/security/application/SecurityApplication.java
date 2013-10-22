package org.dayatang.security.application;

import org.dayatang.security.model.*;

/**
 * Created with IntelliJ IDEA.
 * User: yyang
 * Date: 13-10-18
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 */
public interface SecurityApplication {
    void createActor(Actor actor);
    void createAuthority(Authority authority);
    void createScope(Scope scope);
    void createAuthorization(Actor actor, Authority authority, Scope scope);
    void addUserToGroup(User user, UserGroup group);
    void removeUserFromGroup(User user, UserGroup group);
}
