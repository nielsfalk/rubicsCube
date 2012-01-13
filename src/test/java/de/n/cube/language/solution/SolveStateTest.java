package de.n.cube.language.solution;

import de.n.cube.Cube;
import de.n.cube.CubeStateUtil;
import de.n.cube.language.MovesTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: niles
 * Date: 12.01.12
 * Time: 22:33
 * <p/>
 * Copyright (C) 2011 Niels Falk
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
public class SolveStateTest {
    @Test
    public void basicTest() {
        for (SolveState solveState : SolveState.values()) {
            assertThat(solveState.isReached(Cube.WHITE_DOWN), is(true));
            assertThat(solveState.isReached(new Cube()), is(false));
            assertThat(solveState.isReached(MovesTest.SUPER_FLIP.apply(new Cube())), is(false));
        }
    }


    @Test
    public void orientationWhiteMiddle() {

        String matchingState = "www\n"//
                + "www\n"//
                + "www\n"//
                + "ooogggrrrbbb\n"//
                + "ooogggrrrbbb\n"//
                + "ooogggrrrbbb\n"//
                + "yyy\n"//
                + "ywy\n"//
                + "yyy\n";
        assertReached(matchingState, SolveState.orientationWhiteMiddle);
    }


    @Test
    public void cross() {
        String matchingState = "www\n"//
                + "www\n"//
                + "www\n"//
                + "ooogggrrrbbb\n"//
                + "ooogggrrrbbb\n"//
                + "orogggrorbbb\n"//
                + "ywy\n"//
                + "www\n"//
                + "ywy\n";
        assertReached(matchingState, SolveState.cross);

    }

    @Test
    public void layer1() {
        String matchingState = "www\n"//
                + "www\n"//
                + "www\n"//
                + "ooogggrrrbbb\n"//
                + "ooogggrrrbbb\n"//
                + "rrrgggooobbb\n"//
                + "www\n"//
                + "www\n"//
                + "www\n";
        assertReached(matchingState, SolveState.layer1);
    }

    @Test
    public void layer2() {
        String matchingState = "www\n"//
                + "www\n"//
                + "www\n"//
                + "ooogggrrrbbb\n"//
                + "rrrgggooobbb\n"//
                + "rrrgggooobbb\n"//
                + "www\n"//
                + "www\n"//
                + "www\n";
        assertReached(matchingState, SolveState.layer2);
    }

    @Test
    public void layer2WithCross() {
        String matchingState = "wyw\n"//
                + "yyy\n"//
                + "wyw\n"//
                + "orogggrorbbb\n"//
                + "rrrgggooobbb\n"//
                + "rrrgggooobbb\n"//
                + "www\n"//
                + "www\n"//
                + "www\n";
        assertReached(matchingState, SolveState.layer2WithCross);
    }

    @Test
    public void orientationLastLayer() {
        String matchingState = "yyy\n"//
                + "yyy\n"//
                + "yyy\n"//
                + "öööööööööööö\n"//
                + "rrrgggooobbb\n"//
                + "rrrgggooobbb\n"//
                + "www\n"//
                + "www\n"//
                + "www\n";
        assertReached(matchingState, SolveState.orientationLastLayer);
    }
    @Test
    public void layer3() {
        String matchingState = "yyy\n"//
                + "yyy\n"//
                + "yyy\n"//
                + "rrrgggooobbb\n"//
                + "rrrgggooobbb\n"//
                + "rrrgggooobbb\n"//
                + "www\n"//
                + "www\n"//
                + "www\n";
        assertReached(matchingState, SolveState.layer3);
    }


    private void assertReached(final String matchingState, SolveState solveState) {
        Cube faked = new Cube() {
            @Override
            public String getCubeState() {
                return matchingState;
            }
        };

        boolean reached = solveState.isReached(faked);
        if (!reached) {
            System.out.println("expected:\n"+solveState.expectation);
            System.out.println("\n\ngot:\n" + CubeStateUtil.maskState(faked.getCubeState(), solveState.mask));
        }
        assertThat(reached, is(true));

    }
}
