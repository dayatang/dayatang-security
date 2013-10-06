package org.dayatang.security.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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

	private Set<Actor> actors = new HashSet<Actor>();
	
	UserGroup() {
	}

	public UserGroup(String name) {
		super(name);
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

	public void addActor(Actor... actor) {
		actors.addAll(Arrays.asList(actor));
	}

	public void removeActor(Actor... actor) {
		actors.removeAll(Arrays.asList(actor));
	}
	
	public boolean containsActor(Actor actor) {
		if (actors.contains(actor)) {
			return true;
		}
		for (UserGroup each : getGroups()) {
			if (each.containsActor(actor)) {
				return true;
			}
		}
		return false;
	}

	public Set<UserGroup> getGroups() {
		Set<UserGroup> results = new HashSet<UserGroup>();
		for (Actor actor : actors) {
			if (actor instanceof UserGroup) {
				results.add((UserGroup) actor);
			}
		}
		return results;
	}

	public Set<User> getUsers() {
		Set<User> results = new HashSet<User>();
		for (Actor actor : actors) {
			if (actor instanceof User) {
				results.add((User) actor);
			}
		}
		return results;
	}

}
