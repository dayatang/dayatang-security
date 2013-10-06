package org.dayatang.security.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dayatang.domain.AbstractEntity;

/**
 * 可授权实体，代表某种权限（Permission）或权限集合（Role），可被授予Actor
 * @author yyang
 *
 */
@Entity
@Table(name = "AUTHORIZATIONS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CATEGORY", discriminatorType = DiscriminatorType.CHAR)
public abstract class Authorization extends AbstractEntity {

	private static final long serialVersionUID = -3829499122651729475L;

	@ManyToOne
	@JoinColumn(name = "ACTOR_ID")
	private Actor actor;
	
	@ManyToOne
	@JoinColumn(name = "GRANTABLE_ID")
	private Grantable grantable;

	protected Authorization() {
	}

	public Authorization(Actor actor, Grantable grantable) {
		this.actor = actor;
		this.grantable = grantable;
	}

	public Actor getActor() {
		return actor;
	}

	public Grantable getGrantable() {
		return grantable;
	}


}
