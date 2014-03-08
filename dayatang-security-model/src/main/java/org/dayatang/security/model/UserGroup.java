package org.dayatang.security.model;

import java.util.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


/**
 * 用户组。用于聚合一群用户。
 *
 * @author yyang
 *
 */
@Entity
@DiscriminatorValue("G")
public class UserGroup extends Actor {

    private static final long serialVersionUID = -8375222067841000014L;

    private UserGroup parent;

    private Set<UserGroup> children = new HashSet<UserGroup>();

    private Set<User> users = new HashSet<User>();

    protected UserGroup() {
    }

    public UserGroup(String name) {
        super(name);
    }

    public UserGroup(String name, UserGroup parent) {
        super(name);
        setParent(parent);
    }

    public void addUser(User... users) {
        this.users.addAll(Arrays.asList(users));
    }

    public void removeUser(User... users) {
        this.users.removeAll(Arrays.asList(users));
    }

    @ManyToOne
    @JoinTable(name = "USER_GROUP_RELATION",
            joinColumns = @JoinColumn(name = "PARENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID"))
    public UserGroup getParent() {
        return parent;
    }

    public final void setParent(UserGroup parent) {
        if (contains(parent)) {
            throw new IllegalArgumentException("Cannot set parent to it self or offspring!");
        }
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent")
    public Set<UserGroup> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    public void setChildren(Set<UserGroup> children) {
        this.children = children;
    }

    @ManyToMany
    @JoinTable(name = "USER_GROUP_MAP",
            joinColumns = @JoinColumn(name = "GROUP_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean hasPermission(Permission permission, Scope scope) {
        if (getPermissions(scope).contains(permission)) {
            return true;
        }
        if (parent == null) {
            return false;
        }
        return parent.hasPermission(permission, scope);
    }

    /**
     * 获取根用户组的集合，即没有父用户组的用户组
     *
     * @return 根用户组的集合
     */
    @Transient
    public List<UserGroup> getRootGroups() {
        return getRepository().createCriteriaQuery(UserGroup.class).isNull("parent").list();
    }

    public boolean contains(User user) {
        if (users.contains(user)) {
            return true;
        }
        for (UserGroup each : getChildren()) {
            if (each.contains(user)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否直接或间接包含用户组group。 一个用户组同时包含它自身。
     *
     * @param group 一个用户组
     * @return 如果当前用户组包含group，则返回true，否则返回false。
     */
    public boolean contains(UserGroup group) {
        if (group == null) {
            return false;
        }
        if (equals(group)) {
            return true;
        }
        if (children.contains(group)) {
            return true;
        }
        return contains(group.getParent());
    }

}
