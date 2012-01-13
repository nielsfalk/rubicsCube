package de.n.cube.language.solution;

import de.n.cube.Cube;
import de.n.cube.language.Moves;
import de.n.cube.language.MovesTest;
import org.junit.Test;

import static de.n.cube.language.Moves.moves;
import static de.n.cube.language.solution.Algorithm.whiteIsUp;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: niles
 * Date: 13.01.12
 * Time: 21:43
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
public class AlgorithmTest {


    @Test
    public void whiteIsUp() {
        Cube cube = MovesTest.SUPER_FLIP.apply(new Cube());

        testIt(cube, whiteIsUp);
    }

    @Test
    public void whiteIsFront() {
        Cube cube = movedSuperFlip('8');
        System.out.println(cube.getCubeState());
        testIt(cube, Algorithm.whiteIsFront);
    }

    @Test
    public void whiteIsNotUp() {
        Cube superFlip = Moves.concatenate(MovesTest.SUPER_FLIP, moves("turn", "t2")).apply(new Cube());
        assertThat(whiteIsUp.isApplyHelpFull(superFlip), is(false));
    }


    private void testIt(Cube cube, Algorithm algorithm) {
        assertThat(algorithm.isApplyHelpFull(cube), is(true));
        algorithm.moves.apply(cube);
        assertThat(algorithm.solveStateToReach.isReached(cube), is(true));
    }

    private Cube movedSuperFlip(char c) {
        return Moves.concatenate(MovesTest.SUPER_FLIP, moves("turn up", "t" + c)).apply(new Cube());
    }

}
