package de.n.cube.mechanics;

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

    @Test(expected = IllegalArgumentException.class)
    public void checkStateWithVariablesErr() {
        char notInVariablesInState = 'c';
        CubeStateUtil.checkStateWithVariables(new Cube().cubeState, "...", notInVariablesInState);
    }

    @Test
    public void checkStateWithVariables() {
        String variablesInState = "...\n"//
                + ".a.\n"//
                + "...\n"//
                + "bb..........\n"//
                + "...........c\n"//
                + "..........a.\n"//
                + "c..\n"//
                + "...\n"//
                + "...\n";

        String matchingState = "...\n"//
                + ".r.\n"//
                + "...\n"//
                + "gg..........\n"//
                + "...........y\n"//
                + "..........r.\n"//
                + "y..\n"//
                + "...\n"//
                + "...\n";

        String cNotMatchingState = "...\n"//
                + ".r.\n"//
                + "...\n"//
                + "gg..........\n"//
                + "...........b\n"//
                + "..........r.\n"//
                + "!..\n"//
                + "...\n"//
                + "...\n";


        assertThat(CubeStateUtil.checkStateWithVariables(new Cube().cubeState, variablesInState, 'a', 'b', 'c'), is(false));
        assertThat(CubeStateUtil.checkStateWithVariables(matchingState, variablesInState, 'a', 'b', 'c'), is(true));
        assertThat(CubeStateUtil.checkStateWithVariables(cNotMatchingState, variablesInState, 'a', 'b', 'c'), is(false));
        CubeStateUtil.checkStateWithVariables("oyy\n" +
                "oyr\n" +
                "bbr\n" +
                "yygyyobowbbr\n" +
                "rrwgggyowbbw\n" +
                "rrwgggyowbbw\n" +
                "ggo\n" +
                "rwo\n" +
                "rwo\n", "...\n" +
                "...\n" +
                "...\n" +
                "............\n" +
                ".a..........\n" +
                ".a..........\n" +
                ".w.\n" +
                ".w.\n" +
                "...\n", 'a', 'w');
    }

    @Test
    public void movesOnPattern() {
        String pattern = "...\n"//
                + "...\n"//
                + "...\n"//
                + "............\n"//
                + ".a..........\n"//
                + ".a..........\n"//
                + ".w.\n"//
                + ".w.\n"//
                + "...\n";
        assertPatternMoved(pattern, "t4t6", pattern);

        String t4Pattern = "...\n" +
                "...\n" +
                "...\n" +
                "............\n" +
                "..........a.\n" +
                "..........a.\n" +
                "...\n" +
                "ww.\n" +
                "...\n";
        assertPatternMoved(pattern, "t4", t4Pattern);

        String t4t4Pattern = "...\n" +
                "...\n" +
                "...\n" +
                "............\n" +
                ".......a....\n" +
                ".......a....\n" +
                "...\n" +
                ".w.\n" +
                ".w.\n";
        assertPatternMoved(pattern, "t4t4", t4t4Pattern);

    }

    private void assertPatternMoved(String pattern, String moves, String expected) {
        assertThat(CubeStateUtil.movesOnPattern(pattern, moves), is(expected));
    }

}
