package de.n.cube.language.solution;

import de.n.cube.Cube;
import de.n.cube.Face;
import de.n.cube.language.Moves;

import java.util.ArrayList;
import java.util.List;

import static de.n.cube.language.solution.SolveState.orientationWhiteMiddle;

/**
 * User: niles
 * Date: 13.01.12
 * Time: 21:41
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
public enum Algorithm {
    whiteIsUp(orientationWhiteMiddle, Moves.moves("", "t2t2")) {
        @Override
        public boolean isApplyHelpFull(Cube cube) {
            String faceState = Face.top.faceState(cube.getCubeState());
            return Face.middlePointColor(faceState) == 'w';
        }
    };

    Moves moves;
    SolveState solveStateToReach;

    Algorithm(SolveState solveStateToReach, Moves moves) {
        this.solveStateToReach = solveStateToReach;
        this.moves = moves;
        moves.setDesc(this.name());
    }

    public abstract boolean isApplyHelpFull(Cube cube);

    public static List<Algorithm> forSolveState(SolveState solveState) {
        List<Algorithm> result = new ArrayList<Algorithm>();
        for (Algorithm algorithm : Algorithm.values()) {
            if (algorithm.solveStateToReach == solveState) {
                result.add(algorithm);
            }
        }
        return result;
    }
}