package com.jb.sample.multitenancy.multitenancy.repository;

import com.jb.sample.multitenancy.multitenancy.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
