package de.n.cube.solution;

import de.n.cube.language.Moves;
import de.n.cube.mechanics.Cube;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static de.n.cube.solution.SolveState.*;

/**
 * User: niles
 * Date: 13.01.12
 * Time: 19:33
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
class F2lSolution {
    private static final Logger log = Logger.getLogger(F2lSolution.class.getName());
    private final List<Moves> moves = new ArrayList<Moves>();
    boolean solved;

    public F2lSolution(Cube cube) {
        solve(cube);
    }

    private void solve(Cube cube) {
        cube.resetCounts();
        log.info("start solving \n" + cube.getCubeState());

        Moves step;
        while ((step = solveStep(cube)) != null) {
            this.moves.add(step);
            log.info("solve step: " + step);
        }
        Moves concatenated = Moves.concatenate(this.moves.toArray(new Moves[this.moves.size()]));
        if (cube.isSolved()) {
            log.info("solve *G " + concatenated);
            this.solved = true;
        } else {
            log.severe("can't solve cube\n" + cube.getCubeState() + "\n so far: " + concatenated);
            this.solved = false;
        }
    }

    private Moves solveStep(Cube cube) {
        if (orientationLastLayer.isReached(cube)) {
            return null;
        }
        if (layer2.isReached(cube)) {
            return null;
        }
        if (cross.isReached(cube)) {
            return null;
        }
        if (orientationWhiteMiddle.isReached(cube)) {
            return solve(cross, cube, false);
        }
        return solve(orientationWhiteMiddle, cube, false);
    }

    private Moves solve(SolveState solveState, Cube cube, boolean sortAlgorithmByLength) {
        List<Algorithm> algorithms = Algorithm.forSolveState(solveState);
        for (Algorithm algorithm : algorithms) {
            Algorithm.HelpFullResult helpFullResult = algorithm.isApplyHelpFull(cube);
            if (helpFullResult.value) {
                helpFullResult.moves.apply(cube);
                if (solveState.isReached(cube)) {
                    log.info(solveState.name() + " done");
                }
                return algorithm.baseMoves;
            }
        }
        log.severe("can't solve to state " + solveState.name());
        return null;
    }
}
