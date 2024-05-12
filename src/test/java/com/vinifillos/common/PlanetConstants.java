package com.vinifillos.common;

import com.vinifillos.tests.domain.Planet;

public class PlanetConstants {
    public static final Planet PLANET = new Planet("name", "climate", "terrain");
    public static final Planet PLANET_WITH_ID = new Planet(1L,"name", "climate", "terrain");
    public static final Planet INVALID_PLANET = new Planet("", "", "");
    public static final Long VALID_ID = 1L;
    public static final Long INVALID_ID = 0L;
}
