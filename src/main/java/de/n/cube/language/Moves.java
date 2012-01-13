package de.n.cube.language;

import de.n.cube.Cube;
import de.n.cube.Face;
import de.n.cube.SpinOperations;
import de.n.cube.TurnOperations;

import java.util.ArrayList;
import java.util.List;

public class Moves {

    static final char[] FACE_CHARS = ("UFR" + "BLD").toCharArray();
    static final char[] DIRECTION_CHARS = "-2+".toCharArray();
    private static final char[] SEPARATOR_CHARS = " ,".toCharArray();
    static final char[] TURN_DIRECTION_CHARS = "2468".toCharArray();
    String desc = "";
    final List<Move> moves = new ArrayList<Move>();

    private Moves(String desc) {
        this.desc = desc;
    }

    public static Moves moves(String desc, String cube20String) {
        cube20String = cube20String.toUpperCase();
        Moves result = new Moves(desc);
        //cutDesc
        int descSeparatorIndex = cube20String.indexOf(":");
        if (descSeparatorIndex != -1) {
            cube20String = cube20String.substring(descSeparatorIndex + 1);
        }
        while (!cube20String.isEmpty()) {
            Character face = cube20String.charAt(0);
            if (charInArray(face, SEPARATOR_CHARS)) {
                cube20String = cube20String.substring(1);
                continue;
            }
            if (face.equals('T')) {
                char direction = cube20String.charAt(1);
                if (!charInArray(direction, TURN_DIRECTION_CHARS)) {
                    throw new IllegalArgumentException(direction + " is no valid Direction");
                }
                result.moves.add(new Turn(direction));
                cube20String = cube20String.substring(2);
                continue;
            }
            char direction = '+';
            if (!charInArray(face, FACE_CHARS)) {
                throw new IllegalArgumentException("" + face + " is no Face");
            }
            cube20String = cube20String.substring(1);
            if (!cube20String.isEmpty()) {
                Character potentialDirection = cube20String.charAt(0);
                if (charInArray(potentialDirection, DIRECTION_CHARS)) {
                    cube20String = cube20String.substring(1);
                    direction = potentialDirection;
                }
            }
            result.moves.add(new Move(face, direction));
        }
        return result;
    }

    private static boolean charInArray(char c, char[] chars) {
        for (char character : chars) {
            if (character == c) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String result = desc + ':';
        for (Move move : moves) {
            result += move;
        }
        return result;
    }

    public Cube apply(Cube cube) {
        cube.move(convert());
        return cube;
    }

    private de.n.cube.Move[] convert() {
        List<de.n.cube.Move> result = new ArrayList<de.n.cube.Move>();
        for (Move move : moves) {
            result.add(move.convert());
        }
        return result.toArray(new de.n.cube.Move[result.size()]);
    }

    public Moves undo() {
        String result = "";
        for (int i = moves.size() - 1; i >= 0; i--) {
            result += moves.get(i).undo();
        }
        return moves("undo " + desc, result);
    }

    public static Moves concatenate(Moves... parts) {
        Moves result = new Moves("combined(");
        for (Moves part : parts) {
            result.desc += part.desc + ",";
            result.moves.addAll(part.moves);
        }
        result.desc = result.desc.substring(0, result.desc.length() - 1);
        result.desc += ")";
        return result;
    }

    public static class Move {

        final char face;
        final char direction;


        Move(char face, char direction) {
            this.face = face;
            this.direction = direction;
        }

        @Override
        public String toString() {
            String string = "" + face;
            if (direction != '+') {
                string += "" + direction;
            }
            return string;
        }

        public de.n.cube.Move convert() {
            return de.n.cube.Move.spin(Face.forLang(face), SpinOperations.forLang(direction));
        }

        public Move undo() {
            return new Move(face, getOppositeDirection(direction));
        }

        private char getOppositeDirection(char direction) {
            switch (direction) {
                case '+':
                    return '-';
                case '-':
                    return '+';
                case '2':
                    return direction;
            }
            return 0;
        }
    }

    static class Turn extends Move {
        Turn(char direction) {
            super(' ', direction);
        }

        @Override
        public String toString() {
            return "T" + direction;
        }

        @Override
        public de.n.cube.Move convert() {
            return de.n.cube.Move.turn(TurnOperations.forLang(direction));
        }

        @Override
        public Move undo() {
            return new Turn(getOppositeDirection(direction));
        }

        private char getOppositeDirection(char direction) {
            switch (direction) {
                case '2':
                    return '8';
                case '8':
                    return '2';
                case '4':
                    return '6';
                case '6':
                    return '4';
            }
            return 0;
        }
    }
}


