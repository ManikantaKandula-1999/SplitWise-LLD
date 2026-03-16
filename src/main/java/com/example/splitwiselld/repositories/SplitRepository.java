package com.example.splitwiselld.repositories;

import com.example.splitwiselld.models.Split;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SplitRepository extends JpaRepository<Split, Long> {
}
