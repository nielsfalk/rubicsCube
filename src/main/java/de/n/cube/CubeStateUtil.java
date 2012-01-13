package de.n.cube;

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
}
