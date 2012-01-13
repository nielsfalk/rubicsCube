package de.n.cube;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * User: niles
 * Date: 12.01.12
 * Time: 22:53
 * <p/>
 * Copyright (C) 2012 Niels Falk
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * @author niles
 */
public class CubeStateUtilTest {
    @Test
    public void maskState() {
        String mask = "...\n"//
                + ".?.\n"//
                + "...\n"//
                + "............\n"//
                + "............\n"//
                + "............\n"//
                + "...\n"//
                + "...\n"//
                + "...\n";
        String maskedState = CubeStateUtil.maskState(Cube.INITIAL_CUBE_STATE, mask);
        assertThat(maskedState, is("...\n"//
                + ".w.\n"//
                + "...\n"//
                + "............\n"//
                + "............\n"//
                + "............\n"//
                + "...\n"//
                + "...\n"//
                + "...\n"));


        try {
            CubeStateUtil.maskState(Cube.INITIAL_CUBE_STATE, "");
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

}
