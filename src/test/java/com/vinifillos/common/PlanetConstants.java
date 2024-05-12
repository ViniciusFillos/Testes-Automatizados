package com.vinifillos.common;

import com.vinifillos.tests.domain.Planet;

public class PlanetConstants {
    public static final Planet PLANET = new Planet("name", "climate", "terrain");
    public static final Planet INVALID_PLANET = new Planet("", "", "");
    public static final Planet EMPITY_PLANET = new Planet();
}
