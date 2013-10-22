package org.dayatang.security.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.dayatang.domain.QuerySettings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 权限。代表系统的一项操作或功能。
 * @author yyang
 *
 */
@Entity
@DiscriminatorValue("P")
public class Permission extends Authority {
	
	private static final long serialVersionUID = 7316666894482866864L;

	Permission() {
	}

	public Permission(String name) {
		super(name);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Permission)) {
			return false;
		}
		Permission that = (Permission) other;
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

    public static Permission getByName(String name) {
        return getRepository().getSingleResult(QuerySettings.create(Permission.class).eq("name", name));
    }

}

