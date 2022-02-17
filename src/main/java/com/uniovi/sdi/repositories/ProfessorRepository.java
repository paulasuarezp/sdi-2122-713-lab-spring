package com.uniovi.sdi.repositories;

import com.uniovi.sdi.entities.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor, Long> {
}