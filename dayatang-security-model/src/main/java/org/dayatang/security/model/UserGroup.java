package org.dayatang.security.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 用户组。用于聚合一群用户。
 * @author yyang
 *
 */
@Entity
@DiscriminatorValue("G")
public class UserGroup extends Actor {

	private static final long serialVersionUID = -8375222067841000014L;

	@ManyToOne
    @JoinTable(name = "USER_GROUP_RELATION",
            joinColumns = @JoinColumn(name = "PARENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID"))
	private UserGroup parent;
	
	@OneToMany(mappedBy = "parent")
	private Set<UserGroup> children = new HashSet<UserGroup>();
	
	@ManyToMany
	@JoinTable(name = "USER_GROUP_MAP",
			joinColumns = @JoinColumn(name = "GROUP_ID"),
			inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private Set<User> users = new HashSet<User>();
	
	UserGroup() {
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

	public void addChildGroup(UserGroup... userGroups) {
		for (UserGroup each : userGroups) {
			this.children.add(each);
			each.setParent(this);
		}
	}

	public void removeChildGroup(UserGroup... userGroups) {
		for (UserGroup each : userGroups) {
			this.children.remove(each);
			each.setParent(null);
		}
	}

	public final void setParent(UserGroup parent) {
        if (contains(parent)) {
            throw new IllegalArgumentException("Cannot set parent to it self or offspring!");
        }
		this.parent = parent;
	}

	public UserGroup getParent() {
		return parent;
	}

	public boolean containsUser(User user) {
		if (users.contains(user)) {
			return true;
		}
		for (UserGroup each : getChildren()) {
			if (each.containsUser(user)) {
				return true;
			}
		}
		return false;
	}

	public Set<UserGroup> getChildren() {
		return Collections.unmodifiableSet(children);
	}

    /**
     * 判断是否直接或间接包含用户组group。
     * 一个用户组同时包含它自身。
     * @param group
     * @return
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

	public Set<User> getUsers() {
		return Collections.unmodifiableSet(users);
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

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserGroup)) {
			return false;
		}
		UserGroup that = (UserGroup) other;
		return new EqualsBuilder().append(this.getName(), that.getName()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 47).append(getName()).toHashCode();
	}

	@Override
	public String toString() {
		return getName();
	}

}
