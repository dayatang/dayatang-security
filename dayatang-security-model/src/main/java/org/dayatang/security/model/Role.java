package org.dayatang.security.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


/**
 * 角色。角色是权限的集合。
 * @author yyang
 *
 */
@Entity
@DiscriminatorValue("R")
public class Role extends Authority {

	private static final long serialVersionUID = 6108585828740676974L;
	
	private Set<Permission> permissions = new HashSet<Permission>();

	protected Role() {
	}

	public Role(String name) {
		super(name);
	}

	@ManyToMany
	@JoinTable(name = "ROLE_PERMISSION_MAP", 
		joinColumns = @JoinColumn(name = "ROLE_ID"), 
		inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID"))
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

}
