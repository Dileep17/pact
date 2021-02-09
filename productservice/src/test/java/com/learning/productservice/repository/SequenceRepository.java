package com.learning.productservice.repository;

import com.learning.productservice.model.HibernateSequence;
import org.springframework.data.repository.CrudRepository;

public interface SequenceRepository extends CrudRepository<HibernateSequence, Long> {

}
