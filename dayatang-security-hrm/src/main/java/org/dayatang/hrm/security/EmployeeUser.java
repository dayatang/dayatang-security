package org.dayatang.hrm.security;

import com.dayatang.domain.QuerySettings;
import org.dayatang.hrm.organisation.domain.Employee;
import org.dayatang.security.model.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created with IntelliJ IDEA.
 * User: yyang
 * Date: 13-10-22
 * Time: 下午4:28
 * To change this template use File | Settings | File Templates.
 */
@Entity
@DiscriminatorValue("EMP")
public class EmployeeUser extends User {

    @ManyToOne
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    EmployeeUser() {
        super();
    }

    public EmployeeUser(String name) {
        super(name);
    }

    public EmployeeUser(Employee employee) {
        super(employee.getSn());
        this.employee = employee;
    }

    public static EmployeeUser getByEmployee(Employee employee) {
        return getRepository().getSingleResult(QuerySettings.create(EmployeeUser.class).eq("employee", employee));
    }
}
