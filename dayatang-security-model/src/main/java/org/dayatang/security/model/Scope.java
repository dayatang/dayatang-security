package org.dayatang.security.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.dayatang.domain.AbstractEntity;

/**
 * 授权范围。子类分别代表机构范围等。
 * @author yyang
 *
 */
@Entity
@Table(name = "SCOPES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CATEGORY", discriminatorType = DiscriminatorType.CHAR)
public abstract class Scope extends AbstractEntity {

}
