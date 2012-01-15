package de.n.cube.solution;

import de.n.cube.language.Moves;
import de.n.cube.mechanics.Cube;
import de.n.cube.mechanics.CubeStateUtil;

import static de.n.cube.language.Moves.moves;
import static de.n.cube.solution.Algorithm.HelpFullResult;

/**
 * User: niles
 * Date: 15.01.12
 * Time: 03:51
 * <p/>
 * Copyright (C) 2012 Niels Falk
 * <p/>vvv
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
class AlgorithmUtil {

    public static HelpFullResult crossOneMoveHelpFull(Cube cube, Moves baseMoves, Algorithm algorithm, String patternWithVariables, String spinDirection) {
        String variableInState = patternWithVariables;
        for (char front : new char[]{'f', 'l', 'b', 'r'}) {
            if (front != 'f') {
                variableInState = CubeStateUtil.movesOnPattern(variableInState, "t4");
            }
            if (CubeStateUtil.checkStateWithVariables(cube.getCubeState(), variableInState, 'w', 'a')) {
                return new HelpFullResult(true, moves(algorithm.name(), "" + front + spinDirection));
            }
        }
        return Algorithm.NOT_HELP_FULL;
    }
}
