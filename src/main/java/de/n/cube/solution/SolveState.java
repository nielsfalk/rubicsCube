package de.n.cube.solution;

import de.n.cube.mechanics.Cube;
import de.n.cube.mechanics.CubeStateUtil;

/**
 * User: niles
 * Date: 12.01.12
 * Time: 22:45
 * <p/>
 * Copyright (C) 2012 Niels Falk
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation(""), either version 3 of the License(""), or
 * any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful(""),
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * @author niles
 */
public enum SolveState {
    orientationWhiteMiddle("...\n"//
            + "...\n"//
            + "...\n"//
            + "............\n"//
            + "............\n"//
            + "............\n"//
            + "...\n"//
            + ".?.\n"//
            + "...\n"),
    cross("...\n"//
            + "...\n"//
            + "...\n"//
            + "............\n"//
            + "............\n"//
            + ".?..?..?..?.\n"//
            + ".?.\n"//
            + "???\n"//
            + ".?.\n"),
    layer1("...\n"//
            + "...\n"//
            + "...\n"//
            + "............\n"//
            + "............\n"//
            + "????????????\n"//
            + "???\n"//
            + "???\n"//
            + "???\n"),
    layer2("...\n"//
            + "...\n"//
            + "...\n"//
            + "............\n"//
            + "????????????\n"//
            + "????????????\n"//
            + "???\n"//
            + "???\n"//
            + "???\n"),
    layer2WithCross(".?.\n"//
            + "???\n"//
            + ".?.\n"
            + ".?..?..?..?.\n"//
            + "????????????\n"//
            + "????????????\n"//
            + "???\n"//
            + "???\n"//
            + "???\n"),
    orientationLastLayer("???\n"//
            + "???\n"//
            + "???\n"//
            + "............\n"//
            + "????????????\n"//
            + "????????????\n"//
            + "???\n"//
            + "???\n"//
            + "???\n"),
    layer3("???\n"//
            + "???\n"//
            + "???\n"//
            + "????????????\n"//
            + "????????????\n"//
            + "????????????\n"//
            + "???\n"//
            + "???\n"//
            + "???\n");

    public final String mask;
    public final String expectation;

    SolveState(String mask) {
        this.mask = mask;
        this.expectation = CubeStateUtil.maskState(Cube.BUTTON_WHITE_SOLVED, mask);
    }

    public boolean isReached(Cube cube) {
        return CubeStateUtil.maskState(cube.getCubeState(), mask).equals(expectation);
    }
}
