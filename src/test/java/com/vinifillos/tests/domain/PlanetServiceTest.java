package com.vinifillos.tests.domain;

import static com.vinifillos.common.PlanetConstants.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

//@SpringBootTest(classes = PlanetService.class)
@ExtendWith(MockitoExtension.class)
class PlanetServiceTest {
    //@Autowired
    @InjectMocks
    private PlanetService planetService;

    //@MockBean
    @Mock
    private PlanetRepository planetRepository;

    @Test
    void createPlant_WithValidData_ReturnPlanet() {
        when(planetRepository.save(PLANET)).thenReturn(PLANET);
        Planet sut = planetService.create(PLANET);
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    void createPlanet_WithInvalidData_ThrowsException() {
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        when(planetRepository.findById(1L)).thenReturn(Optional.of(PLANET));
        Optional<Planet> sut = planetService.get(1L);
        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    void getPlanet_ByUnexistingId_ReturnsEmpity() {
        when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Planet> sut = planetService.get(0L);
        assertThat(sut).isEmpty();
    }

    @Test
    void getPlanet_ByExistingName_ReturnsPlanet() {
        when(planetRepository.findByName(PLANET.getName())).thenReturn(Optional.of(PLANET));
        Optional<Planet> sut = planetService.getByName(PLANET.getName());
        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    void getPlanet_ByUnexistingName_ReturnsEmpity() {
        when(planetRepository.findByName("INVALID_NAME")).thenReturn(Optional.empty());
        Optional<Planet> sut = planetService.getByName("INVALID_NAME");
        assertThat(sut).isEmpty();
    }

    @Test
    void listPlanets_ReturnsAllPlanets() {
        List<Planet> planets = new ArrayList<>() {
            {
                add(PLANET);
            }
        };
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));
        when(planetRepository.findAll(query)).thenReturn(planets);
        List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());
        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET);
    }

    @Test
    void listPlanets_ReturnsNoPlanets() {
        when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());
        List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());
        assertThat(sut).isEmpty();
    }

    @Test
    void deletePlanet_ByExistingId_DoesNotThrowAnyException() {
        assertThatCode(() -> planetService.remove(1L)).doesNotThrowAnyException();
    }

    @Test
    void deletePlanet_ByUnexistingId_ThrowsException() {
        doThrow(new RuntimeException()).when(planetRepository).deleteById(0L);
        assertThatThrownBy(() -> planetService.remove(0L)).isInstanceOf(RuntimeException.class);
    }
}
