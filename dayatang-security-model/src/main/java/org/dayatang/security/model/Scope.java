package org.dayatang.security.model;

import javax.persistence.*;

import com.dayatang.domain.AbstractEntity;

import java.util.Collections;
import java.util.HashSet;
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

    @ManyToOne
    @JoinTable(name = "SCOPE_RELATION",
            joinColumns = @JoinColumn(name = "PARENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID"))
    private Scope parent;

    @OneToMany(mappedBy = "parent")
    private Set<Scope> children = new HashSet<Scope>();

    public Scope getParent() {
        return parent;
    }

    public final void setParent(Scope parent) {
        if (contains(parent)) {
            throw new IllegalArgumentException("Cannot set parent to it self or offspring!");
        }
        this.parent = parent;
    }

    public Set<Scope> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    public boolean contains(Scope scope) {
        if (scope == null) {
            return false;
        }
        if (equals(scope)) {
            return true;
        }
        if (children.contains(scope)) {
            return true;
        }
        return contains(scope.getParent());
    }
}
