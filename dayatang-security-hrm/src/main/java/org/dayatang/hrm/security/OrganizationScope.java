package org.dayatang.hrm.security;

import com.dayatang.domain.QuerySettings;
import org.dayatang.hrm.organisation.domain.OrgLineMgmt;
import org.dayatang.hrm.organisation.domain.Organization;
import org.dayatang.security.model.Scope;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: yyang
 * Date: 13-10-22
 * Time: 下午4:23
 * To change this template use File | Settings | File Templates.
 */
@Entity
@DiscriminatorValue("ORG")
public class OrganizationScope extends Scope {

    OrganizationScope() {
    }

    public OrganizationScope(Organization organization) {
        this.organization = organization;
    }

    @ManyToOne
    @JoinColumn(name = "organization_id", unique = true)
    private Organization organization;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public int hashCode() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object other) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static OrganizationScope getByOrganization(Organization organization) {
        return getRepository().getSingleResult(QuerySettings.create(OrganizationScope.class).eq("organization", organization));
    }

    @Override
    public Scope getParent() {
        Organization parent = OrgLineMgmt.getParentOfOrganization(organization, new Date());
        return OrganizationScope.getByOrganization(parent);
    }

    @Override
    public Set<Scope> getChildren() {
        List<Organization> orgs = OrgLineMgmt.findChildrenOfOrganization(organization, new Date());
        Set<Scope> results = new HashSet<Scope>();
        for (Organization org: orgs) {
            results.add(OrganizationScope.getByOrganization(org));
        }
        return results;
    }
}
