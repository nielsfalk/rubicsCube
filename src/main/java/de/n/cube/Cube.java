package de.n.cube;

/**
 * User: niles
 * Date: 13.01.12
 * Time: 01:38
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

public class Cube {
    public static final String INITIAL_CUBE_STATE = "www\n"//
            + "www\n"//
            + "www\n"//
            + "ooo" + "ggg" + "rrr" + "bbb\n"//
            + "ooo" + "ggg" + "rrr" + "bbb\n"//
            + "ooo" + "ggg" + "rrr" + "bbb\n"//
            + "yyy\n"//
            + "yyy\n"//
            + "yyy\n";
    public static final Cube WHITE_DOWN = new Cube().move(Move.turn(TurnOperations.up), Move.turn(TurnOperations.up));
    public static final String BUTTON_WHITE_SOLVED = WHITE_DOWN.getCubeState();


    String cubeState;

    int turnCount = 0;

    int spinCount = 0;

    public Cube() {
        cubeState = INITIAL_CUBE_STATE;
    }

    public String getCubeState() {
        return cubeState;
    }

    public int getSpinCount() {
        return spinCount;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public Cube move(Move... moves) {
        for (Move move : moves) {
            if (move.turnOperation != null) {
                turnInternal(move.turnOperation);
                turnCount++;
            }
            if (move.spinOperation != null) {
                move.faceToSpin.spinFaceOnCube(move.spinOperation, this);
                spinCount++;
            }
        }
        return this;
    }

    void turnInternal(TurnOperations turnOperation) {
        turnOperation.turn(this);
    }

    public boolean isSolved() {
        for (Face face : Face.values()) {
            if (!Face.isSolved(face.faceState(cubeState))) {
                return false;
            }
        }
        return true;
    }

    public void resetCounts() {
        spinCount = 0;
        turnCount = 0;
    }
}
