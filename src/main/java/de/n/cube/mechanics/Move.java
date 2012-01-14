package de.n.cube.mechanics;

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

public class Move {
    TurnOperations turnOperation;

    Face faceToSpin;

    SpinOperations spinOperation;

    private Move() {

    }

    public static Move turn(TurnOperations turnOperation) {
        Move move = new Move();
        move.turnOperation = turnOperation;
        return move;
    }

    public static Move spin(Face face, SpinOperations spinOperation) {
        Move move = new Move();
        move.faceToSpin = face;
        move.spinOperation = spinOperation;
        return move;
    }

    @Override
    public String toString() {
        return "Face:" + faceToSpin + " SpinOperation:" + spinOperation;
    }
}
