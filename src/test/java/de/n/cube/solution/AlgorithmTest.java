package de.n.cube.solution;

import de.n.cube.language.Moves;
import de.n.cube.language.MovesTest;
import de.n.cube.mechanics.Cube;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static de.n.cube.language.Moves.moves;
import static de.n.cube.solution.Algorithm.forSolveState;
import static de.n.cube.solution.Algorithm.whiteIsUp;
import static de.n.cube.solution.SolveState.orientationWhiteMiddle;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * User: niles
 * Date: 13.01.12
 * Time: 21:43
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
public class AlgorithmTest {
    @Test
    public void whiteIsNotUp() {
        Cube superFlip = moves("turn", "t2t2").apply(new Cube());
        assertThat(whiteIsUp.isApplyHelpFull(superFlip).value, CoreMatchers.is(false));
    }

    @Test
    public void whiteIsUp() {
        Cube cube = MovesTest.SUPER_FLIP.apply(new Cube());
        testIt(cube, whiteIsUp);
    }

    @Test
    public void whiteIsFront() {
        Cube cube = movedSuperFlip("t8");
        testIt(cube, Algorithm.whiteIsFront);
    }

    @Test
    public void whiteIsRight() {
        Cube cube = movedSuperFlip("t8t6");
        testIt(cube, Algorithm.whiteIsRight);
    }

    @Test
    public void whiteIsBack() {
        Cube cube = movedSuperFlip("t8t6t6");
        testIt(cube, Algorithm.whiteIsBack);
    }

    @Test
    public void whiteIsLeft() {
        Cube cube = movedSuperFlip("t8t4");
        testIt(cube, Algorithm.whiteIsLeft);
    }

    @Test
    public void noAlgorithmHelpFull() {
        Cube orientationWhiteMiddleCube = orientationWhiteMiddleCube();
        for (Algorithm algorithm : forSolveState(orientationWhiteMiddle)) {
            assertThat(algorithm.isApplyHelpFull(orientationWhiteMiddleCube).value, is(false));
        }
    }

    @Test
    public void spinFirstLayer() {
        Cube orientationWhiteMiddleCube = orientationWhiteMiddleCube();
        Algorithm algorithm = Algorithm.spinFirstLayer;

        assertThat(algorithm.isApplyHelpFull(orientationWhiteMiddleCube).value, is(true));
        System.out.println(orientationWhiteMiddleCube.getCubeState());
        algorithm.baseMoves.apply(orientationWhiteMiddleCube);

        assertThat(algorithm.isApplyHelpFull(orientationWhiteMiddleCube).value, is(true));
        System.out.println(orientationWhiteMiddleCube.getCubeState());
        algorithm.baseMoves.apply(orientationWhiteMiddleCube);

        assertThat(algorithm.isApplyHelpFull(orientationWhiteMiddleCube).value, is(true));
        System.out.println(orientationWhiteMiddleCube.getCubeState());
        algorithm.baseMoves.apply(orientationWhiteMiddleCube);

        assertThat(algorithm.isApplyHelpFull(orientationWhiteMiddleCube).value, is(false));

        System.out.println(orientationWhiteMiddleCube.getCubeState());
    }

    @Test
    public void hWaa2vWaa() {
        Cube cube = cube("rog\n"//
                + "b..\n"//
                + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "rr." + "..." + "..." + "..w\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        Algorithm algorithm = Algorithm.hWaa2vAaw;
        Algorithm.HelpFullResult helpFullResult = algorithm.isApplyHelpFull(cube);
        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("hWaa2vAaw:F-"));
        helpFullResult.moves.apply(cube);
        assertThat(cube.getCubeState(), is("rog\n" +
                "b..\n" +
                "...\n" +
                "............\n" +
                ".r..........\n" +
                ".r..........\n" +
                ".w.\n" +
                ".w.\n" +
                "...\n"));
    }

    @Test
    public void hWaa2vWaaOtherHorizontalFronts() {
        Cube cube = cube("rog\n"//
                + "b..\n"//
                + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "rr." + "..." + "..." + "..w\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        Algorithm algorithm = Algorithm.hWaa2vAaw;


        testHWaa2vWaa(cube, algorithm, "L-");
        testHWaa2vWaa(cube, algorithm, "B-");
        testHWaa2vWaa(cube, algorithm, "R-");
        testHWaa2vWaa(cube, algorithm, "F-");
    }

    private void testHWaa2vWaa(Cube cube, Algorithm algorithm, String expectedMove) {
        moves("t4").apply(cube);
        Algorithm.HelpFullResult helpFullResult = algorithm.isApplyHelpFull(cube);
        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("hWaa2vAaw:" + expectedMove));
    }

    private Cube cube(String state) {
        return new SettableCube().setCubeState(state);
    }

    private void testIt(Cube cube, Algorithm algorithm) {
        assertThat(algorithm.isApplyHelpFull(cube).value, is(true));
        algorithm.baseMoves.apply(cube);
        assertThat(algorithm.solveStateToReach.isReached(cube), is(true));
    }

    private Cube movedSuperFlip(String turns) {
        return Moves.concatenate(MovesTest.SUPER_FLIP, moves("turn up", turns)).apply(new Cube());
    }

    private Cube orientationWhiteMiddleCube() {
        Cube orientationWhiteMiddleCube = Moves.moves("turned", "t2t2lfr" + "ud-").apply(new Cube());
        assertThat(orientationWhiteMiddle.isReached(orientationWhiteMiddleCube), is(true));
        return orientationWhiteMiddleCube;
    }

    public static class SettableCube extends Cube {
        public SettableCube setCubeState(String state) {
            this.cubeState = state;
            return this;
        }
    }

}
