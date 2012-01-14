package de.n.cube.language.solution;

import de.n.cube.Cube;
import de.n.cube.language.Moves;
import de.n.cube.language.MovesTest;
import org.junit.Ignore;
import org.junit.Test;

import static de.n.cube.language.Moves.moves;
import static de.n.cube.language.solution.Algorithm.forSolveState;
import static de.n.cube.language.solution.Algorithm.whiteIsUp;
import static de.n.cube.language.solution.SolveState.orientationWhiteMiddle;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
        assertThat(whiteIsUp.isApplyHelpFull(superFlip), is(false));
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
            assertThat(algorithm.isApplyHelpFull(orientationWhiteMiddleCube), is(false));
        }
    }

    @Test
    @Ignore("not finished")
    public void spinFirstLayer() {
        Cube orientationWhiteMiddleCube = orientationWhiteMiddleCube();
        System.out.println(orientationWhiteMiddleCube.getCubeState());
        Algorithm algorithm = Algorithm.spinFirstLayer;
        assertThat(algorithm.isApplyHelpFull(orientationWhiteMiddleCube), is(true));
        algorithm.moves.apply(orientationWhiteMiddleCube);
    }

    private void testIt(Cube cube, Algorithm algorithm) {
        assertThat(algorithm.isApplyHelpFull(cube), is(true));
        algorithm.moves.apply(cube);
        assertThat(algorithm.solveStateToReach.isReached(cube), is(true));
    }

    private Cube movedSuperFlip(String turns) {
        return Moves.concatenate(MovesTest.SUPER_FLIP, moves("turn up", turns)).apply(new Cube());
    }

    private Cube orientationWhiteMiddleCube() {
        Cube orientationWhiteMiddleCube = Moves.moves("turned", "t2t2lfr").apply(new Cube());
        assertThat(orientationWhiteMiddle.isReached(orientationWhiteMiddleCube), is(true));
        return orientationWhiteMiddleCube;
    }

}
