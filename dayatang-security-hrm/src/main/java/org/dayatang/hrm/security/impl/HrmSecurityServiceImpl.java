package org.dayatang.hrm.security.impl;

import org.dayatang.hrm.organisation.domain.Organization;
import org.dayatang.security.model.Authorization;
import org.dayatang.hrm.security.HrmSecurityService;
import org.dayatang.hrm.security.OrganizationScope;
import org.dayatang.security.model.*;

/**
 * Created with IntelliJ IDEA.
 * User: yyang
 * Date: 13-10-22
 * Time: 下午4:43
 * To change this template use File | Settings | File Templates.
 */
public class HrmSecurityServiceImpl implements HrmSecurityService {
    @Override
    public void grantPermission(String username, String permission, Organization organization) {
        Scope scope = OrganizationScope.getByOrganization(organization);
        if (scope == null) {
            scope = new OrganizationScope(organization);
        }
        new Authorization(User.getByName(username), Permission.getByName(permission), scope).save();
    }
}
