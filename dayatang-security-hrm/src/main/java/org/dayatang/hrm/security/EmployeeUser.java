package org.dayatang.hrm.security;

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

    private Employee employee;

    protected EmployeeUser() {
        super();
    }

    public EmployeeUser(String name) {
        super(name);
    }

    public EmployeeUser(Employee employee) {
        super(employee.getSn());
        this.employee = employee;
    }

    @ManyToOne
    @JoinColumn(name = "employee_id", unique = true)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public static EmployeeUser getByEmployee(Employee employee) {
        return getRepository().createCriteriaQuery(EmployeeUser.class).eq("employee", employee).singleResult();
    }
}
