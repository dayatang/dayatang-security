package org.dayatang.security.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 用户组。用于聚合一群用户。
 * @author yyang
 *
 */
public class UserGroup extends Actor {

	private static final long serialVersionUID = -8375222067841000014L;

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
