package de.n.cube.language;

import de.n.cube.Cube;
import de.n.cube.Face;
import de.n.cube.SpinOperations;
import de.n.cube.TurnOperations;

import java.util.ArrayList;
import java.util.List;

public class Moves {

    static final char[] FACE_CHARS = "UFRBLD".toCharArray();
    static final char[] DIRECTION_CHARS = "-2+".toCharArray();
    static final char[] SEPERATOR_CHARS = " ,".toCharArray();
    static final char[] TURN_DIRECTION_CHARS = "2468".toCharArray();
    String desc = "";
    List<Move> moves = new ArrayList<Move>();

    private Moves(String desc) {
        this.desc = desc;
    }

    public static Moves moves(String cube20String) {
        return moves("", cube20String);
    }

    public static Moves moves(String desc, String cube20String) {
        cube20String = cube20String.toUpperCase();
        Moves result = new Moves(desc);
        //cutDesc
        int descSeperatorIndex = cube20String.indexOf(":");
        if (descSeperatorIndex != -1) {
            cube20String = cube20String.substring(descSeperatorIndex + 1);
        }
        while (!cube20String.isEmpty()) {
            Character face = cube20String.charAt(0);
            if (charInArray(face, SEPERATOR_CHARS)) {
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
                Character potentionDirection = cube20String.charAt(0);
                if (charInArray(potentionDirection, DIRECTION_CHARS)) {
                    cube20String = cube20String.substring(1);
                    direction = potentionDirection;
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

    /**
     * Created by IntelliJ IDEA.
     * User: nielsfalk
     * Date: 12.01.12
     * Time: 14:13
     * To change this template use File | Settings | File Templates.
     */
    public static class Move {

        private char face;
        char direction;


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
            return new Move(face, getOppositDirection(direction));
        }

        private char getOppositDirection(char direction) {
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
            return new Turn(getOppositDirection(direction));
        }

        private char getOppositDirection(char direction) {
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


