package com.learning.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "hibernate_sequence")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HibernateSequence {
    @Id
    long next_val;
}
