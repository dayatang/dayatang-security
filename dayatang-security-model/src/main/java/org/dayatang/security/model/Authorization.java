package org.dayatang.security.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.dayatang.domain.AbstractEntity;

/**
 * 授权，在指定范围内将某种权限（Permission）或权限集合（Role）授予Actor
 *
 * @author yyang
 *
 */
@Entity
@Table(name = "AUTHORIZATIONS", uniqueConstraints = @UniqueConstraint(columnNames = {"ACTOR_ID", "AUTHORITY_ID", "SCOPE_ID"}))
public class Authorization extends AbstractEntity {

    private static final long serialVersionUID = -3829499122651729475L;

    private Actor actor;

    private Authority authority;

    private Scope scope;

    protected Authorization() {
    }

    public Authorization(Actor actor, Authority authority) {
        this.actor = actor;
        this.authority = authority;
    }

    public Authorization(Actor actor, Authority authority, Scope scope) {
        this.actor = actor;
        this.authority = authority;
        this.scope = scope;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ACTOR_ID")
    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "AUTHORITY_ID")
    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "SCOPE_ID")
    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override
    public void save() {
        if (exists(actor, authority, scope)) {
            return;
        }
        super.save();
    }

    /**
     * 判断参与者actor是否已经被授予scope范围的authority权限
     *
     * @param actor
     * @param authority
     * @param scope
     * @return
     */
    public static boolean exists(Actor actor, Authority authority, Scope scope) {
        Authorization authorization = getRepository().createCriteriaQuery(Authorization.class)
                .eq("actor", actor).eq("authority", authority).eq("scope", scope).singleResult();
        return authorization == null;
    }

    /**
     * 找到参与者actor的所有授权信息。 如果actor是个用户组，则包含它的父用户组的授权信息；
     * 如果actor是个用户，则包含它所属的每个用户组的授权信息。
     *
     * @param actor
     * @return
     */
    public static Set<Authorization> getAuthorizationsOf(Actor actor) {
        Set<Authorization> results = new HashSet<Authorization>();
        List<Authorization> authorizations = getRepository().createCriteriaQuery(Authorization.class)
                .eq("actor", actor).list();
        results.addAll(authorizations);
        if (actor instanceof User) {
            for (UserGroup group : ((User) actor).getGroups()) {
                results.addAll(getAuthorizationsOf(group));
            }
        }
        if (actor instanceof UserGroup) {
            UserGroup group = (UserGroup) actor;
            if (group.getParent() != null) {
                results.addAll(getAuthorizationsOf(group.getParent()));
            }
        }
        return results;
    }

    public static Set<Authority> getAuthoritiesOfActorInScope(Actor actor, Scope scope) {
        Set<Authority> results = new HashSet<Authority>();
        for (Authorization authorization : getAuthorizationsOf(actor)) {
            if (authorization.getScope().contains(scope)) {
                results.add(authorization.getAuthority());
            }
        }
        return results;
    }

    static List<Authorization> findByActor(Actor actor) {
        return getRepository().createCriteriaQuery(Authorization.class).eq("actor", actor).list();
    }

    static List<Authorization> findByAuthority(Authority authority) {
        return getRepository().createCriteriaQuery(Authorization.class).eq("authority", authority).list();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append(getActor()).append(getAuthority()).append(getScope()).build();
    }

    @Override
    public String[] businessKeys() {
        return new String[] {"actor", "authority", "scope"};
    }
}
