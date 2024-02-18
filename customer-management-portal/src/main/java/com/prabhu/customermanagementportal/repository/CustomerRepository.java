package com.prabhu.customermanagementportal.repository;

import com.prabhu.customermanagementportal.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByEmail(String email);
    @Query(value="select * from customer where first_name like %:pattern%",nativeQuery = true)
    Page<Customer> findByFirstNameContains(String pattern, Pageable pageable);
     @Query(value="select * from customer where phone like %:pattern%",nativeQuery = true)
    Page<Customer> findByPhoneContains(String pattern, Pageable pageable);
    @Query(value="select * from customer where email like %:pattern%",nativeQuery = true)
    Page<Customer> findByEmailContains(String pattern, Pageable pageable);

    @Query(value="select * from customer where city like %:pattern%",nativeQuery = true)
    Page<Customer> findByCityContains(String pattern, Pageable pageable);



    @Query(value="select * from customer where uuid=:id",nativeQuery = true)
    Optional<Customer> findByUuid(String id);
}
