package de.n.cube.language.solution;

import de.n.cube.Cube;
import de.n.cube.language.Moves;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: niles
 * Date: 13.01.12
 * Time: 19:33
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
public class F2lSolution {
    private Cube cube;
    private static Logger log = Logger.getLogger(F2lSolution.class.getName());
    private List<Moves> moves = new ArrayList<Moves>();
    boolean solved;

    public F2lSolution(Cube cube) {
        this.cube = cube;
        this.cube.resetCounts();
        log.info("start solving \n" + cube.getCubeState());

        Moves step;
        while ((step = solveStep(cube)) != null) {
            this.moves.add(step);
            log.info("solve step: " + step);
        }
        Moves concatenated = Moves.concatenate(this.moves.toArray(new Moves[this.moves.size()]));
        if (cube.isSolved()) {
            log.info("solved *G " + concatenated);
            this.solved = true;
        } else {
            log.severe("can't solve cube\n" + cube.getCubeState() + "\n so far: " + concatenated);
            this.solved = false;
        }

    }

    private Moves solveStep(Cube cube) {
        if (SolveState.orientationLastLayer.isReached(cube)) {
            return null;
        }
        if (SolveState.layer2.isReached(cube)) {
            return null;
        }
        if (SolveState.cross.isReached(cube)) {
            return null;
        }
        if (SolveState.orientationWhiteMiddle.isReached(cube)) {
            return null;
        }

        return new OrientationWhiteMiddle(cube).getSolution();
    }


    private class OrientationWhiteMiddle extends AbstractSolver {
        public OrientationWhiteMiddle(Cube cube) {
            super(cube, SolveState.orientationWhiteMiddle);
        }


        @Override
        public Moves getSolution() {
            Moves t2t2 = Moves.moves("white was up", "t2t2");
            t2t2.apply(cube);
            if (SolveState.orientationWhiteMiddle.isReached(cube)) {
                log.info(SolveState.orientationWhiteMiddle + " done");
                return t2t2;
            } else return null;
        }
    }

    private abstract class AbstractSolver {
        Cube cube;
        SolveState orientationWhiteMiddle;

        public AbstractSolver(Cube cube, SolveState orientationWhiteMiddle) {
            this.cube = cube;
            this.orientationWhiteMiddle = orientationWhiteMiddle;
        }

        public abstract Moves getSolution();
    }
}
