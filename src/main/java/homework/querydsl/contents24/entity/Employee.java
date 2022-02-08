package homework.querydsl.contents24.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@IdClass(EmployeeKey.class)
@Entity
public class Employee {

    // 사원번호
    @Id
    @Column(name = "employee_no")
    private Long employeeNo;

    // 부서 번호
    @Id
    @Column(name = "dept_no")
    private Long deptNo;

    // 사원 이름
    @Column(name = "employee_name", nullable = false)
    private String name;

    @Builder
    public Employee(Long employeeNo, Long deptNo, String name) {
        this.employeeNo = employeeNo;
        this.deptNo = deptNo;
        this.name = name;
    }
}
