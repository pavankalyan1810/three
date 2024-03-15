package com.project.Repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Model.Table;


@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

	List<Table> findByBookedFalse();

	Optional<Table> findBytableId(Long tableId);

}
