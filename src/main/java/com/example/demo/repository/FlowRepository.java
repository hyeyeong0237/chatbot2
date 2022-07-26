package com.example.demo.repository;

import com.example.demo.entity.Flow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FlowRepository extends JpaRepository<Flow, Long> {

    @Query("select  f from Flow f join fetch f.steps where f.id = : id")
    Optional<Flow> findByWithStep(@Param(value = "id") Long id);

}
