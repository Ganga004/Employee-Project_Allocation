package com.employee.data.repository;

import com.employee.data.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface EmpRepository  extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM employeedata.employee ORDER BY over_all_experience DESC limit 1 offset 1", nativeQuery=true)
    Employee findSecondMostExperiencedEmployee();

    @Query("SELECT e FROM Employee e WHERE " +
            "(:skill1 IS NULL OR e.primarySkill NOT LIKE %:skill1%) AND " +
            "(:skill2 IS NULL OR e.primarySkill NOT LIKE %:skill2%) AND " +
            "(:skill3 IS NULL OR e.primarySkill NOT LIKE %:skill3%) AND " +
            "(:skill4 IS NULL OR e.primarySkill NOT LIKE %:skill4%)")
    List<Employee> findEmployeesByNotAllocatedSkills(
            @Param("skill1") String skill1,
            @Param("skill2") String skill2,
            @Param("skill3") String skill3,
            @Param("skill4") String skill4);


    @Query("SELECT e FROM Employee e WHERE " +
            "(:primarySkill1 IS NULL OR e.primarySkill LIKE %:primarySkill1%) AND " +
            "(:primarySkill2 IS NULL OR e.primarySkill LIKE %:primarySkill2%) AND " +
            "(:secondarySkill1 IS NULL OR e.secondarySkill LIKE %:secondarySkill1%) AND " +
            "(:secondarySkill2 IS NULL OR e.secondarySkill LIKE %:secondarySkill2%)")
    List<Employee> findEmployeesListBasedOnPrimaryAndSecondarySkills(
            @Param("primarySkill1") String primarySkill1,
            @Param("primarySkill2") String primarySkill2,
            @Param("secondarySkill1") String secondarySkill1,
            @Param("secondarySkill2") String secondarySkill2);
}
