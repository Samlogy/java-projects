package com.codesnippet.SpringBatchDemo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    void deleteAll();
}
