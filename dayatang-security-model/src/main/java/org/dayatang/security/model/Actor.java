package org.dayatang.security.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.dayatang.domain.AbstractEntity;

/**
 * 参与者，是User和UserGroup的共同基类。可以对Actor授予角色或权限。
 */
@Entity
@Table(name = "ACTORS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CATEGORY", discriminatorType = DiscriminatorType.CHAR)
public abstract class Actor extends AbstractEntity {

    private static final long serialVersionUID = -4659049959430484022L;

    private String name;

    protected Actor() {
    }

    public Actor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void remove() {
        for (Authorization authorization : Authorization.findByActor(this)) {
            authorization.remove();
        }
        super.remove();
    }

    /**
     * 在指定范围内授予权限或角色
     *
     * @param authority
     * @param scope
     */
    public void grant(Authority authority, Scope scope) {
        if (Authorization.exists(this, authority, scope)) {
            return;
        }
        new Authorization(this, authority, scope).save();
    }

    /**
     * 获取权限
     *
     * @param scope
     * @return
     */
    public Set<Permission> getPermissions(Scope scope) {
        Set<Permission> results = new HashSet<Permission>();
        for (Authority authority : getAuthorities(scope)) {
            if (authority instanceof Permission) {
                results.add((Permission) authority);
            } else {
                Role role = (Role) authority;
                results.addAll(role.getPermissions());
            }
        }
        return results;
    }

    private Set<Authority> getAuthorities(Scope scope) {
        return Authorization.getAuthoritiesOfActorInScope(this, scope);
    }

    @Override
    public String[] businessKeys() {
        return new String[]{"name"};
    }

    @Override
    public String toString() {
        return getName();
    }
}
