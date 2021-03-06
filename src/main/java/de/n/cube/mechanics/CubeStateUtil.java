package de.n.cube.mechanics;

import de.n.cube.language.Moves;

import java.util.ArrayList;
import java.util.List;

/**
 * User: niles
 * Date: 12.01.12
 * Time: 22:54
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
public class CubeStateUtil {
    public static String maskState(String cubeState, String mask) {
        if (cubeState.length() != mask.length()) {
            throw new IllegalArgumentException();
        }
        String result = "";
        for (int i = 0; i < cubeState.length(); i++) {
            char maskChar = mask.charAt(i);
            if (maskChar == '?') {
                result += cubeState.charAt(i);
                continue;
            }
            result += maskChar;
        }
        return result;
    }

    public static boolean isFaceMiddleColorWhite(Cube cube, Face face) {
        String faceState = face.faceState(cube.getCubeState());
        return Face.middlePointColor(faceState) == 'w';
    }

    public static boolean checkStateWithVariables(String cubeState, String variablesInState, char... variables) {
        char[] variablesInStateArray = variablesInState.toCharArray();
        List<Character> foundValues = new ArrayList<Character>();
        for (char variable : variables) {
            int firstPosition = variablesInState.indexOf(variable);
            if (firstPosition == -1) {
                throw new IllegalArgumentException("variable " + variable + " not found in \n" + variablesInState);
            }
            char expected = cubeState.charAt(firstPosition);
            if (foundValues.contains(expected)) {
                return false;
            }
            foundValues.add(expected);
            for (int i = 0; i < variablesInStateArray.length; i++) {
                if (variablesInStateArray[i] == variable) {
                    if (cubeState.charAt(i) != expected) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static String movesOnPattern(String pattern, String moves) {
        Cube cubeToMove = new Cube();
        cubeToMove.cubeState = pattern;
        Moves.moves("movesOnPattern", moves).apply(cubeToMove);
        return cubeToMove.getCubeState();
    }

}
