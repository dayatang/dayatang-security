package org.dayatang.security.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


/**
 * 权限。代表系统的一项操作或功能。
 *
 * @author yyang
 *
 */
@Entity
@DiscriminatorValue("P")
public class Permission extends Authority {

    private static final long serialVersionUID = 7316666894482866864L;

    protected Permission() {
    }

    public Permission(String name) {
        super(name);
    }

    public static Permission getByName(String name) {
        return getRepository().createCriteriaQuery(Permission.class).eq("name", name).singleResult();
    }

}
