package homework.querydsl.contents24.entity;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 사원 복합키 클래스
 */
@Getter @Setter
public class EmployeeKey implements Serializable {
    private Long employeeNo;
    private Long deptNo;
}
