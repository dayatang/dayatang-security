package org.dayatang.security.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 角色。角色是权限的集合。
 * @author yyang
 *
 */
public class Role extends Grantable {

	private static final long serialVersionUID = 6108585828740676974L;

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
