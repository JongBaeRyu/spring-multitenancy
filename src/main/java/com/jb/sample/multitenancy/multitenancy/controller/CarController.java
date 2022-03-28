package com.jb.sample.multitenancy.multitenancy.controller;

import com.jb.sample.multitenancy.multitenancy.entity.Car;
import com.jb.sample.multitenancy.multitenancy.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@RequestMapping(path = "/cars")
@Slf4j
public class CarController {

    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Car insert(@RequestBody Car car){
//        return carRepository.save(car);

        log.debug("insert Car..");

        // 로그 확인을 위해서 플러시를 날렸다.
        Car saveCar = carRepository.save(car);
        carRepository.flush();
        return saveCar;
    }

    @GetMapping
    public List<Car> posts(){
        log.debug("find Car..");
        return carRepository.findAll();
    }
}
