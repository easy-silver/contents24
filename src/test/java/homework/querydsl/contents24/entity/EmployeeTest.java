package homework.querydsl.contents24.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class EmployeeTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void 복합키_확인() {
        Employee employee = Employee.builder()
                .employeeNo(111L)
                .deptNo(999L)
                .name("leejieun")
                .build();

        em.persist(employee);

        em.flush();
        em.clear();

        EmployeeKey pk = new EmployeeKey();
        pk.setEmployeeNo(111L);
        pk.setDeptNo(999L);

        Employee findEmployee = em.find(Employee.class, pk);
        System.out.println(">>>\t\tfindEmployee = " + findEmployee.getName());

        assertThat(findEmployee.getName()).isEqualTo("leejieun");
    }
}