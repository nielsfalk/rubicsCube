package de.n.cube;


public class Cube {
    public static final String INITIAL_CUBE_STATE = "www\n"//
            + "www\n"//
            + "www\n"//
            + "ooogggrrrbbb\n"//
            + "ooogggrrrbbb\n"//
            + "ooogggrrrbbb\n"//
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

}
