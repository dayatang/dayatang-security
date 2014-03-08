package org.dayatang.security.model;

import javax.persistence.*;

import org.dayatang.domain.AbstractEntity;

import java.util.Set;

/**
 * 授权范围。子类分别代表机构范围等。
 * @author yyang
 *
 */
@Entity
@Table(name = "SCOPES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CATEGORY", discriminatorType = DiscriminatorType.STRING)
public abstract class Scope extends AbstractEntity {

    public abstract Scope getParent();

    public abstract Set<Scope> getChildren();

    public boolean contains(Scope scope) {
        if (scope == null) {
            return false;
        }
        if (equals(scope)) {
            return true;
        }
        if (getChildren().contains(scope)) {
            return true;
        }
        return contains(scope.getParent());
    }
}
