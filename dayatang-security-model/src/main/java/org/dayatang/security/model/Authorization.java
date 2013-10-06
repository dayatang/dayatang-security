package org.dayatang.security.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.QuerySettings;

/**
 * 授权，在指定范围内将某种权限（Permission）或权限集合（Role）授予Actor
 * @author yyang
 *
 */
@Entity
@Table(name = "AUTHORIZATIONS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CATEGORY", discriminatorType = DiscriminatorType.CHAR)
public class Authorization extends AbstractEntity {

	private static final long serialVersionUID = -3829499122651729475L;

	@ManyToOne
	@JoinColumn(name = "ACTOR_ID")
	private Actor actor;
	
	@ManyToOne
	@JoinColumn(name = "GRANTABLE_ID")
	private Grantable grantable;
	
	@ManyToOne
	@JoinColumn(name = "SCOPE_ID")
	private Scope scope;

	protected Authorization() {
	}

	public Authorization(Actor actor, Grantable grantable) {
		this.actor = actor;
		this.grantable = grantable;
	}

	public Authorization(Actor actor, Grantable grantable, Scope scope) {
		this.actor = actor;
		this.grantable = grantable;
		this.scope = scope;
	}

	public Actor getActor() {
		return actor;
	}

	public Grantable getGrantable() {
		return grantable;
	}

	public Scope getScope() {
		return scope;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Authorization)) {
			return false;
		}
		Authorization that = (Authorization) other;
		return new EqualsBuilder()
			.append(this.getActor(), that.getActor())
			.append(this.getGrantable(), that.getGrantable())
			.append(this.getScope(), that.getScope())
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 47).append(getActor()).append(getGrantable()).append(getScope()).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(getActor()).append(getGrantable()).append(getScope()).build();
	}

	public static boolean exists(Actor actor, Grantable grantable, Scope scope) {
		QuerySettings<Authorization> querySettings = QuerySettings.create(Authorization.class)
				.eq("actor", actor).eq("grantable", grantable).eq("scope", scope);
		Authorization authorization = getRepository().getSingleResult(querySettings);
		return authorization == null ? false : true;
	}

	public static Set<Grantable> getGrantablesOfActorInScope(Actor actor, Scope scope) {
		QuerySettings<Authorization> querySettings = QuerySettings.create(Authorization.class)
				.eq("actor", actor).eq("scope", scope);
		List<Authorization> authorizations = getRepository().find(querySettings);
		Set<Grantable> results = new HashSet<Grantable>();
		for (Authorization authorization : authorizations) {
			results.add(authorization.getGrantable());
		}
		return results;
	}
}
