package com.uniovi.sdi.repositories;


import com.uniovi.sdi.entities.Mark;
import org.springframework.data.repository.CrudRepository;

public interface MarksRepository extends CrudRepository<Mark, Long> {
}
