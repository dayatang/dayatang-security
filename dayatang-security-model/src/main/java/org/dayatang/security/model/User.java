package org.dayatang.security.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.dayatang.domain.QuerySettings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 用户。用于登录系统的主体。
 * @author yyang
 *
 */
@Entity
@DiscriminatorValue("U")
public class User extends Actor {
	
	private static final long serialVersionUID = 7316666894482866864L;

	
	@ManyToMany(mappedBy = "users")
	private Set<UserGroup> groups = new HashSet<UserGroup>();
	
	
	protected User() {
	}

	public User(String name) {
		super(name);
	}

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

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof User)) {
			return false;
		}
		User that = (User) other;
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

    public static User getByName(String username) {
        return getRepository().getSingleResult(QuerySettings.create(User.class).eq("name", username));
    }
}
