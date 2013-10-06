package org.dayatang.security.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.dayatang.domain.AbstractEntity;

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
}
