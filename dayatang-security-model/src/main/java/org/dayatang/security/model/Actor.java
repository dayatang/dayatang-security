package org.dayatang.security.model;

import com.dayatang.domain.AbstractEntity;

/**
 * 参与者，是User和UserGroup的共同基类。可以对Actor授予角色或权限。
 */
public abstract class Actor extends AbstractEntity {

	private static final long serialVersionUID = -4659049959430484022L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
