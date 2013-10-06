package org.dayatang.security.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 用户。用于登录系统的主体。
 * @author yyang
 *
 */
public class User extends Actor {
	
	private static final long serialVersionUID = 7316666894482866864L;

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

}
