package com.vinifillos.tests;

import com.vinifillos.tests.domain.Planet;

import static com.vinifillos.tests.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/remove_planets.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/import_planets.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PlanetIT {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void createPlanet_ReturnsCreated() {
        Planet sut = webTestClient.post().uri("/planets").bodyValue(PLANET)
                .exchange().expectStatus().isCreated().expectBody(Planet.class)
                .returnResult().getResponseBody();

        assertThat(sut.getId()).isNotNull();
        assertThat(sut.getName()).isEqualTo(PLANET.getName());
    }

    @Test
    void getPlanet_ById_ReturnPlanet() {
        Planet sut = webTestClient.get().uri("/planets/1").exchange()
                .expectStatus().isOk().expectBody(Planet.class)
                .returnResult().getResponseBody();

        assertThat(sut).isEqualTo(TATOOINE);
    }

    @Test
    void getPlanet_ByName_ReturnPlanet() {
        Planet sut = webTestClient.get().uri("/planets/name/"+TATOOINE.getName()).exchange()
                .expectStatus().isOk().expectBody(Planet.class)
                .returnResult().getResponseBody();

        assertThat(sut).isEqualTo(TATOOINE);

        sut = webTestClient.get().uri("/planets/name/"+ALDERAAN.getName()).exchange()
                .expectStatus().isOk().expectBody(Planet.class)
                .returnResult().getResponseBody();

        assertThat(sut).isEqualTo(ALDERAAN);

        sut = webTestClient.get().uri("/planets/name/"+YAVINIV.getName()).exchange()
                .expectStatus().isOk().expectBody(Planet.class)
                .returnResult().getResponseBody();

        assertThat(sut).isEqualTo(YAVINIV);
    }

    @Test
    void listPlanets_ReturnsAllPlanets() {
        webTestClient.get()
                .uri("/planets")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Planet[].class)
                .value(planets -> assertThat(planets).hasSize(3))
                .value(planets -> assertThat(planets[0]).isEqualTo(TATOOINE))
                .value(planets -> assertThat(planets[1]).isEqualTo(ALDERAAN))
                .value(planets -> assertThat(planets[2]).isEqualTo(YAVINIV));
    }


    @Test
    void listPlanets_ByClimate_ReturnsPlanets() {
        webTestClient.get()
                .uri("/planets?climate="+ALDERAAN.getClimate())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Planet[].class)
                .value(planets -> assertThat(planets).hasSize(1))
                .value(planets -> assertThat(planets[0]).isEqualTo(ALDERAAN));
    }

    @Test
    void listPlanets_ByTerrain_ReturnsPlanets() {
        webTestClient.get()
                .uri("/planets?terrain="+YAVINIV.getTerrain())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Planet[].class)
                .value(planets -> assertThat(planets).hasSize(1))
                .value(planets -> assertThat(planets[0]).isEqualTo(YAVINIV));
    }

    @Test
    void removePlanet_ReturnsNoContent() {
        webTestClient.delete()
                .uri("/planets/1")
                .exchange()
                .expectStatus().isNoContent();
    }
}
