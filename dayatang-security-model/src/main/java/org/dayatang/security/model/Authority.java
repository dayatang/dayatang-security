package org.dayatang.security.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.dayatang.domain.AbstractEntity;

/**
 * 可授权实体，代表某种权限（Permission）或权限集合（Role），可被授予Actor
 * @author yyang
 *
 */
@Entity
@Table(name = "AUTHORITIES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CATEGORY", discriminatorType = DiscriminatorType.CHAR)
public abstract class Authority extends AbstractEntity {

	private static final long serialVersionUID = -262442090925650546L;

	private String name;

	protected Authority() {
	}

	public Authority(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Override
    public void remove() {
        for (Authorization authorization : Authorization.findByAuthority(this)) {
            authorization.remove();
        }
        super.remove();
    }
}
