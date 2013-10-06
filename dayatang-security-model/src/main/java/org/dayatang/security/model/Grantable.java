package org.dayatang.security.model;

import com.dayatang.domain.AbstractEntity;

/**
 * 可授权实体，代表某种权限（Permission）或权限集合（Role），可被授予Actor
 * @author yyang
 *
 */
public abstract class Grantable extends AbstractEntity {

	private static final long serialVersionUID = -262442090925650546L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
