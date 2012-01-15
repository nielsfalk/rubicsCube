package de.n.cube.solution;

import de.n.cube.language.Moves;
import de.n.cube.language.MovesTest;
import de.n.cube.mechanics.Cube;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static de.n.cube.language.Moves.moves;
import static de.n.cube.solution.Algorithm.*;
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
        algorithm.baseMoves.apply(orientationWhiteMiddleCube);

        assertThat(algorithm.isApplyHelpFull(orientationWhiteMiddleCube).value, is(true));
        algorithm.baseMoves.apply(orientationWhiteMiddleCube);

        assertThat(algorithm.isApplyHelpFull(orientationWhiteMiddleCube).value, is(true));
        algorithm.baseMoves.apply(orientationWhiteMiddleCube);

        assertThat(algorithm.isApplyHelpFull(orientationWhiteMiddleCube).value, is(false));
    }

    @Test
    public void hWaa2vAaw() {
        Cube cube = cube("rog\n"//
                + "b..\n"//
                + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "rr." + "..." + "..." + "..w\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        Algorithm.HelpFullResult helpFullResult = crossOneMove1.isApplyHelpFull(cube);
        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("crossOneMove1:F-"));
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
    public void hWaa2vAawOtherHorizontalFronts() {
        Cube cube = cube("rog\n"//
                + "b..\n"//
                + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "rr." + "..." + "..." + "..w\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        testHWaa2vWaa(cube, "L-");
        testHWaa2vWaa(cube, "B-");
        testHWaa2vWaa(cube, "R-");
        testHWaa2vWaa(cube, "F-");
    }

    @Test
    public void hAaw2vAaw() {
        Cube cube = cube("rog\n"//
                + "b..\n"//
                + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + ".rr" + "w.." + "..." + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        Algorithm.HelpFullResult helpFullResult = Algorithm.crossOneMove2.isApplyHelpFull(cube);
        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("crossOneMove2:F"));
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
    public void whiteFromYellowFace1() {
        Cube cube = cube("rog\n"//
                + "b..\n"//
                + ".w.\n"//
                + ".r." + "..." + "..." + "...\n"//
                + ".r." + "..." + "..." + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        Algorithm.HelpFullResult helpFullResult = crossOneMove3.isApplyHelpFull(cube);
        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("crossOneMove3:F2"));
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
    public void whiteFromYellowFaceHorizontalFronts() {
        Cube cube = cube("rog\n"//
                + "b..\n"//
                + ".w.\n"//
                + ".r." + "..." + "..." + "...\n"//
                + ".r." + "..." + "..." + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        testWhiteFromYellow(cube, "L2");
        testWhiteFromYellow(cube, "B2");
        testWhiteFromYellow(cube, "R2");
        testWhiteFromYellow(cube, "F2");
    }

    private void testHWaa2vWaa(Cube cube, String expectedMove) {
        Algorithm algorithm = crossOneMove1;
        moves("t4").apply(cube);
        Algorithm.HelpFullResult helpFullResult = algorithm.isApplyHelpFull(cube);
        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("crossOneMove1:" + expectedMove));
    }

    private void testWhiteFromYellow(Cube cube, String expectedMove) {
        Algorithm algorithm = crossOneMove3;
        moves("t4").apply(cube);
        Algorithm.HelpFullResult helpFullResult = algorithm.isApplyHelpFull(cube);
        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("crossOneMove3:" + expectedMove));
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
