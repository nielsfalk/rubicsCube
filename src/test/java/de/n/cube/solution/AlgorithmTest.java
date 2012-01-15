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
    public void crossOneMove1() {
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
    public void crossOneMove1OtherHorizontalFronts() {
        Cube cube = cube("rog\n"//
                + "b..\n"//
                + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "rr." + "..." + "..." + "..w\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        testCrossOneMove1(cube, "L-");
        testCrossOneMove1(cube, "B-");
        testCrossOneMove1(cube, "R-");
        testCrossOneMove1(cube, "F-");
    }

    @Test
    public void crossOneMove2() {
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
    public void crossOneMove3() {
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
    public void crossOneMove3HorizontalFronts() {
        Cube cube = cube("rog\n"//
                + "b..\n"//
                + ".w.\n"//
                + ".r." + "..." + "..." + "...\n"//
                + ".r." + "..." + "..." + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        testCrossOneMove3(cube, "L2");
        testCrossOneMove3(cube, "B2");
        testCrossOneMove3(cube, "R2");
        testCrossOneMove3(cube, "F2");
    }

    @Test
    public void prepareCrossOneMove() {
        Cube cube1 = cube("...\n"//
                + "...\n"//
                + ".w.\n"//
                + ".r." + "..." + "..." + "...\n"//
                + "..." + ".r." + "..." + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        Cube cube2 = cube("...\n"//
                + "...\n"//
                + ".w.\n"//
                + ".r." + "..." + "..." + "...\n"//
                + "..." + "..." + ".r." + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        Cube cube3 = cube("...\n"//
                + "...\n"//
                + ".w.\n"//
                + ".r." + "..." + "..." + "...\n"//
                + "..." + "..." + "..." + ".r.\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        testPrepareCrossOneMove(cube1, "U-");
        testPrepareCrossOneMove(cube2, "U2");
        testPrepareCrossOneMove(cube3, "U");
    }

    @Test
    public void crossTwoMoves1() {
        Cube cube = cube("...\n"//
                + "..b\n"//
                + "...\n"//
                + "..." + ".w." + "..." + "...\n"//
                + "..." + "..." + ".b." + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        HelpFullResult helpFullResult = Algorithm.crossTwoMoves1.isApplyHelpFull(cube);

        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("crossTwoMoves1:RB-"));
        helpFullResult.moves.apply(cube);
        assertThat(cube.getCubeState(), is("...\n" +
                "...\n" +
                "...\n" +
                "............\n" +
                ".......b....\n" +
                ".......b....\n" +
                "...\n" +
                ".w.\n" +
                ".w.\n"));
    }

    @Test
    public void crossTwoMoves2() {
        Cube cube = cube("...\n"//
                + "..b\n"//
                + "...\n"//
                + "..." + ".w." + "..." + "...\n"//
                + ".b." + "..." + "..." + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        HelpFullResult helpFullResult = crossTwoMoves2.isApplyHelpFull(cube);

        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("crossTwoMoves2:R-F"));
        helpFullResult.moves.apply(cube);
        assertThat(cube.getCubeState(), is("...\n" +
                "...\n" +
                "...\n" +
                "............\n" +
                ".b..........\n" +
                ".b..........\n" +
                ".w.\n" +
                ".w.\n" +
                "...\n"));
    }

    @Test
    public void prepareCrossTwoMoves() {
        Cube cube = cube("...\n"//
                + "...\n"//
                + ".o.\n"//
                + ".w." + "..." + "..." + "...\n"//
                + ".o." + ".b." + ".r." + ".g.\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");
        assertThat(crossTwoMoves2.isApplyHelpFull(cube).value, is(false));
        assertThat(crossTwoMoves1.isApplyHelpFull(cube).value, is(false));
        HelpFullResult helpFullResult = Algorithm.prepareCrossTwoMoves.isApplyHelpFull(cube);
        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("prepareCrossTwoMoves:U"));
        helpFullResult.moves.apply(cube);

        assertThat(crossTwoMoves1.isApplyHelpFull(cube).value, is(true));
    }

    @Test
    public void moveOutWrongInSecondLayer1() {
        Cube cube = cube("...\n"//
                + "...\n"//
                + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + ".bg" + "w.." + "..." + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");

        HelpFullResult helpFullResult = Algorithm.moveOutWrongInSecondLayer1.isApplyHelpFull(cube);
        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("moveOutWrongInSecondLayer1:F-U"));
        helpFullResult.moves.apply(cube);
        assertThat(cube.getCubeState(), is("...\n" +
                "w..\n" +
                "...\n" +
                "..........g.\n" +
                ".b..........\n" +
                "............\n" +
                "...\n" +
                ".w.\n" +
                "...\n"));
    }

    @Test
    public void moveOutWrongInSecondLayer2() {
        Cube cube = cube("...\n"//
                + "...\n"//
                + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "gb." + ",.." + "..." + "..w\n"//
                + "..." + "..." + "..." + "...\n"//
                + "...\n"//
                + ".w.\n"//
                + "...\n");

        HelpFullResult helpFullResult = Algorithm.moveOutWrongInSecondLayer2.isApplyHelpFull(cube);
        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("moveOutWrongInSecondLayer2:FU"));
        helpFullResult.moves.apply(cube);
        assertThat(cube.getCubeState(), is("...\n" +
                "w..\n" +
                "...\n" +
                "..........g.\n" +
                ".b..........\n" +
                "............\n" +
                ".,.\n" +
                ".w.\n" +
                "...\n"));
    }

    @Test
    public void moveOutWrongInFirstLayer1() {
        Cube cube = cube("...\n"//
                + "...\n"//
                + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + "..." + ",.." + "..." + "...\n"//
                + ".w." + "..." + "..." + "...\n"//
                + ".g.\n"//
                + ".w.\n"//
                + "...\n");
        HelpFullResult helpFullResult = Algorithm.moveOutWrongInFirstLayer1.isApplyHelpFull(cube);
        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("moveOutWrongInFirstLayer1:F2"));
        helpFullResult.moves.apply(cube);
        assertThat(cube.getCubeState(), is("...\n" +
                "...\n" +
                ".g.\n" +
                ".w..........\n" +
                "...........,\n" +
                "............\n" +
                "...\n" +
                ".w.\n" +
                "...\n"));
    }

    @Test
    public void moveOutWrongInFirstLayer2() {
        Cube cube = cube("...\n"//
                + "...\n"//
                + "...\n"//
                + "..." + "..." + "..." + "...\n"//
                + ".b." + "..." + "..." + "...\n"//
                + ".g." + "..." + "..." + "...\n"//
                + ".w.\n"//
                + ".w.\n"//
                + "...\n");
        HelpFullResult helpFullResult = Algorithm.moveOutWrongInFirstLayer2.isApplyHelpFull(cube);
        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("moveOutWrongInFirstLayer2:F2"));
        helpFullResult.moves.apply(cube);
        assertThat(cube.getCubeState(), is("...\n" +
                "...\n" +
                ".w.\n" +
                ".g..........\n" +
                ".b..........\n" +
                "............\n" +
                "...\n" +
                ".w.\n" +
                "...\n"));
    }

    private void testPrepareCrossOneMove(Cube cube1, String expectedMove) {
        assertThat(crossOneMove3.isApplyHelpFull(cube1).value, is(false));

        HelpFullResult helpFull = Algorithm.prepareCrossOneMove.isApplyHelpFull(cube1);
        assertThat(helpFull.value, is(true));
        assertThat(helpFull.moves.toString(), is("prepareCrossOneMove:" + expectedMove));
        helpFull.moves.apply(cube1);

        //now cOM 3 is helpFull
        assertThat(crossOneMove3.isApplyHelpFull(cube1).value, is(true));
    }

    private void testCrossOneMove1(Cube cube, String expectedMove) {
        Algorithm algorithm = crossOneMove1;
        moves("t4").apply(cube);
        Algorithm.HelpFullResult helpFullResult = algorithm.isApplyHelpFull(cube);
        assertThat(helpFullResult.value, is(true));
        assertThat(helpFullResult.moves.toString(), is("crossOneMove1:" + expectedMove));
    }

    private void testCrossOneMove3(Cube cube, String expectedMove) {
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
