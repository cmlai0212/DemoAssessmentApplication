package com.example.DemoAssessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.DemoAssessment.model.Client;

@Transactional
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
