package org.dayatang.hrm.security.impl;

import org.dayatang.hrm.organisation.domain.Employee;
import org.dayatang.hrm.organisation.domain.Organization;
import org.dayatang.hrm.security.EmployeeUser;
import org.dayatang.security.application.SecurityApplication;
import org.dayatang.security.model.Authorization;
import org.dayatang.hrm.security.HrmSecurityService;
import org.dayatang.hrm.security.OrganizationScope;
import org.dayatang.security.model.*;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: yyang
 * Date: 13-10-22
 * Time: 下午4:43
 * To change this template use File | Settings | File Templates.
 */
public class HrmSecurityServiceImpl implements HrmSecurityService {

    @Inject
    private SecurityApplication securityApplication;

    @Override
    public void grantPermission(String username, String permission, Organization organization) {
        OrganizationScope scope = OrganizationScope.getByOrganization(organization);
        if (scope == null) {
            scope = new OrganizationScope(organization);
        }
        securityApplication.createAuthorization(User.getByName(username), Permission.getByName(permission), scope);
    }

    @Override
    public void grantPermission(Employee employee, String permission, Organization organization) {
        Scope scope = OrganizationScope.getByOrganization(organization);
        if (scope == null) {
            scope = new OrganizationScope(organization);
        }
        EmployeeUser user = EmployeeUser.getByEmployee(employee);
        if (user == null) {
            user = new EmployeeUser(employee);
        }
        securityApplication.createAuthorization(user, Permission.getByName(permission), scope);
    }

    @Override
    public boolean hasPermission(String username, String permission, Organization organization) {
        User user = User.getByName(username);
        Permission permission1 = Permission.getByName(permission);

        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
