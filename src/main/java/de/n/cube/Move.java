package de.n.cube;


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
        return "Face:"+faceToSpin+" SpinOperation:"+spinOperation;
    }
}
