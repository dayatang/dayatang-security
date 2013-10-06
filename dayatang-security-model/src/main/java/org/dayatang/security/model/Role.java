package org.dayatang.security.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 角色。角色是权限的集合。
 * @author yyang
 *
 */
@Entity
@DiscriminatorValue("R")
public class Role extends Grantable {

	private static final long serialVersionUID = 6108585828740676974L;
	
	@ManyToMany
	@JoinTable(name = "ROLE_PERMISSION_MAP", 
		joinColumns = @JoinColumn(name = "ROLE_ID"), 
		inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID"))
	private Set<Permission> permissions = new HashSet<Permission>();

	Role() {
	}

	public Role(String name) {
		super(name);
	}

	public Set<Permission> getPermissions() {
		return Collections.unmodifiableSet(permissions);
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = new HashSet<Permission>(permissions);
	}
	
	public void addPermission(Permission permission) {
		permissions.add(permission);
	}
	
	public void removePermission(Permission permission) {
		permissions.remove(permission);
	}

	public boolean hasPermission(Permission permission) {
		return permissions.contains(permission);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Role)) {
			return false;
		}
		Role that = (Role) other;
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
