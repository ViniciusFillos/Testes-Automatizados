package com.vinifillos.common;

import com.vinifillos.tests.domain.Planet;

import java.util.ArrayList;
import java.util.List;

public class PlanetConstants {
    public static final Planet PLANET = new Planet("name", "climate", "terrain");
    public static final Planet INVALID_PLANET = new Planet("", "", "");
    public static final Planet EMPITY_PLANET = new Planet();

    public static final Planet TATOOINE = new Planet("Tatooine", "arid", "desert");
    public static final Planet ALDERAN = new Planet("Alderan", "temperate", "grassland, mountains");
    public static final Planet YAVINIV = new Planet("Yavin IV", "temperate, tropical", "jungle, rainforests");
    public static final List<Planet> PLANETS = new ArrayList<>() {{
        add(TATOOINE);
        add(ALDERAN);
        add(YAVINIV);
    }};
}
