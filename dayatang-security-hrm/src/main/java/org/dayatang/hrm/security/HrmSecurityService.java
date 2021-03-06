package org.dayatang.hrm.security;

import org.dayatang.hrm.organisation.domain.*;

/**
 * Created with IntelliJ IDEA.
 * User: yyang
 * Date: 13-10-22
 * Time: 下午4:21
 * To change this template use File | Settings | File Templates.
 */
public interface HrmSecurityService {


    void grantPermission(String username, String permission, Organization organization);

    void grantPermission(Employee employee, String permission, Organization organization);

    boolean hasPermission(String username, String permission, Organization organization);
}
