package com.udacity.vehicles.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final MapsClient maps;
    private final PriceClient pricing;

    public CarService(CarRepository repository, WebClient maps, WebClient pricing) {
        
        this.repository = repository;
        this.maps = new MapsClient(maps, new ModelMapper());
        this.pricing = new PriceClient(pricing);
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll().stream().map((Car car) -> {
        	String price = pricing.getPrice(car.getId());
        	Location location = maps.getAddress(car.getLocation());
        	car.setPrice(price);
        	car.setLocation(location);
        	return car;
        }).toList();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {

        Optional<Car> carOptional = repository.findById(id);
        if (carOptional.isEmpty())
        	throw new CarNotFoundException();
        Car result = carOptional.get();
        
        /**
         * Set the price
         */
        String price = pricing.getPrice(id);
        result.setPrice(price);
        
        /**
         * Update location
         */
        Location location = maps.getAddress(result.getLocation());
        result.setLocation(location);

        return result;
    }

    /*
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        carToBeUpdated.setCondition(car.getCondition());
                        carToBeUpdated.setPrice(car.getPrice());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
    	
    	Optional<Car> carOptional = repository.findById(id);
        if (carOptional.isEmpty())
        	throw new CarNotFoundException();
        repository.deleteById(id);
    }
}
