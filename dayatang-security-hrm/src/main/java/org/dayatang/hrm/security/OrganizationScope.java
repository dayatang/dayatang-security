package org.dayatang.hrm.security;

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

    protected OrganizationScope() {
    }

    public OrganizationScope(Organization organization) {
        this.organization = organization;
    }

    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "organization_id", unique = true)
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }


    public static OrganizationScope getByOrganization(Organization organization) {
        return getRepository().createCriteriaQuery(OrganizationScope.class).eq("organization", organization).singleResult();
    }

    @Transient
    @Override
    public Scope getParent() {
        Organization parent = OrgLineMgmt.getParentOfOrganization(organization, new Date());
        return OrganizationScope.getByOrganization(parent);
    }

    @Transient
    @Override
    public Set<Scope> getChildren() {
        List<Organization> orgs = OrgLineMgmt.findChildrenOfOrganization(organization, new Date());
        Set<Scope> results = new HashSet<Scope>();
        for (Organization org: orgs) {
            results.add(OrganizationScope.getByOrganization(org));
        }
        return results;
    }

    @Override
    public String[] businessKeys() {
        return new String[] {"organization"};
    }
}
