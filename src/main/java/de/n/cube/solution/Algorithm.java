package de.n.cube.solution;

import de.n.cube.language.Moves;
import de.n.cube.mechanics.Cube;
import de.n.cube.mechanics.CubeStateUtil;
import de.n.cube.mechanics.Face;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static de.n.cube.language.Moves.moves;
import static de.n.cube.mechanics.CubeStateUtil.*;
import static de.n.cube.solution.SolveState.cross;
import static de.n.cube.solution.SolveState.orientationWhiteMiddle;

/**
 * User: niles
 * Date: 13.01.12
 * Time: 21:41
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
public enum Algorithm {
    whiteIsUp(orientationWhiteMiddle, moves("", "t2t2")) {
        @Override
        public HelpFullResult isApplyHelpFull(Cube cube) {
            return new HelpFullResult(isFaceMiddleColorWhite(cube, Face.top), baseMoves);
        }
    }, whiteIsFront(orientationWhiteMiddle, moves("", "t8")) {
        @Override
        public HelpFullResult isApplyHelpFull(Cube cube) {
            return new HelpFullResult(isFaceMiddleColorWhite(cube, Face.front), baseMoves);
        }
    }, whiteIsRight(orientationWhiteMiddle, moves("", "t4t8")) {
        @Override
        public HelpFullResult isApplyHelpFull(Cube cube) {
            return new HelpFullResult(isFaceMiddleColorWhite(cube, Face.right), baseMoves);
        }
    }, whiteIsBack(orientationWhiteMiddle, moves("", "t2")) {
        @Override
        public HelpFullResult isApplyHelpFull(Cube cube) {
            return new HelpFullResult(isFaceMiddleColorWhite(cube, Face.back), baseMoves);
        }
    }, whiteIsLeft(orientationWhiteMiddle, moves("", "t6t8")) {
        @Override
        public HelpFullResult isApplyHelpFull(Cube cube) {
            return new HelpFullResult(isFaceMiddleColorWhite(cube, Face.left), baseMoves);
        }
    }, spinFirstLayer(cross, moves("", "d-")) {
        @Override
        public HelpFullResult isApplyHelpFull(Cube cube) {
            String buttonState = Face.button.faceState(cube.getCubeState());
            String maskedButtonState = CubeStateUtil.maskState(buttonState, ".?.\n" + "???\n" + ".?.\n");
            if (StringUtils.countMatches(maskedButtonState, "w") == 1) {
                return NOT_HELP_FULL;
            }
            String variablesInState = "...\n"//
                    + "...\n"//
                    + "...\n"//
                    + "............\n"//
                    + ".a..........\n"//
                    + ".a..........\n"//
                    + ".w.\n"//
                    + ".w.\n"//
                    + "...\n";

            return new HelpFullResult(nothingSolvedOnCrossYet(cube, variablesInState), baseMoves);
        }
    };
    public static final HelpFullResult NOT_HELP_FULL = new HelpFullResult(false);

    private static boolean nothingSolvedOnCrossYet(Cube cube, String variablesInState) {
        String cubeState = cube.getCubeState();
        return !checkStateWithVariables(cubeState, variablesInState, 'a', 'w') &&
                !checkStateWithVariables(cubeState, movesOnPattern(variablesInState, "t4"), 'a', 'w') &&
                !checkStateWithVariables(cubeState, movesOnPattern(variablesInState, "t4t4"), 'a', 'w') &&
                !checkStateWithVariables(cubeState, movesOnPattern(variablesInState, "t6"), 'a', 'w');
    }

    final Moves baseMoves;
    final SolveState solveStateToReach;

    Algorithm(SolveState solveStateToReach, Moves baseMoves) {
        this.solveStateToReach = solveStateToReach;
        this.baseMoves = baseMoves;
        baseMoves.setDesc(this.name());
    }

    public abstract HelpFullResult isApplyHelpFull(Cube cube);

    public static List<Algorithm> forSolveState(SolveState solveState) {
        List<Algorithm> result = new ArrayList<Algorithm>();
        for (Algorithm algorithm : Algorithm.values()) {
            if (algorithm.solveStateToReach == solveState) {
                result.add(algorithm);
            }
        }
        return result;
    }

    public static class HelpFullResult {
        public final boolean value;
        public final Moves moves;

        public HelpFullResult(Moves moves) {
            this.moves = moves;
            this.value = true;
        }

        public HelpFullResult(boolean value) {
            this.moves = null;
            this.value = value;
        }

        public HelpFullResult(boolean value, Moves moves) {
            this.value = value;
            this.moves = value ? moves : null;
        }
    }
}
