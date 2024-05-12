package com.vinifillos.tests.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PlanetService {
    private PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    @Transactional
    public Planet create(Planet planet) {
        return planetRepository.save(planet);
    }

    @Transactional(readOnly = true)
    public Optional<Planet> getById(Long id) {
        return planetRepository.findById(id);
    }
}
