package de.n.cube;

import org.apache.commons.lang.StringUtils;

/**
* User: niles
* Date: 13.01.12
* Time: 01:39
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
public enum TurnOperations {
    up {
        @Override
        protected void turn(Cube cube) {
            String[] cubeStateLines = StringUtils.split(cube.cubeState, '\n');
            String result = "";
            for (int i = 0; i < 3; i++) {
                result += cubeStateLines[3 + i].substring(0, 3) + '\n';
            }
            for (int i = 0; i < 3; i++) {
                result += cubeStateLines[6 + i].substring(0, 3);//y
                result += cubeStateLines[3 + i].substring(3, 6);//g //TODO strange flip on Back
                result += cubeStateLines[i];//w
                result += cubeStateLines[3 + i].substring(9, 12);//b
                result += '\n';
            }
            for (int i = 0; i < 3; i++) {
                result += cubeStateLines[3 + i].substring(6, 9) + '\n';
            }
            cube.cubeState = result;

            Face.back.spinCurrentFace(SpinOperations.clockwise, cube);
            Face.back.spinCurrentFace(SpinOperations.clockwise, cube);

            Face.button.spinCurrentFace(SpinOperations.clockwise, cube);
            Face.button.spinCurrentFace(SpinOperations.clockwise, cube);

            Face.right.spinCurrentFace(SpinOperations.clockwise, cube);

            Face.left.spinCurrentFace(SpinOperations.clockwise, cube);
        }
    },
    down {
        @Override
        protected void turn(Cube cube) {
            up.turn(cube);
            up.turn(cube);
            up.turn(cube);
        }
    },
    left {
        @Override
        protected void turn(Cube cube) {
            String[] cubeStateLines = StringUtils.split(cube.cubeState, '\n');
            String result = "";
            for (int i = 0; i < 3; i++) {
                result += cubeStateLines[i] + '\n';
            }
            for (int i = 0; i < 3; i++) {
                result += cubeStateLines[3 + i].substring(3, 12);
                result += cubeStateLines[3 + i].substring(0, 3);
                result += '\n';
            }
            for (int i = 0; i < 3; i++) {
                result += cubeStateLines[i + 6] + '\n';
            }
            cube.cubeState = result;

            Face.top.spinCurrentFace(SpinOperations.clockwise, cube);
            Face.button.spinCurrentFace(SpinOperations.anticlockwise, cube);
        }
    },
    right {
        @Override
        protected void turn(Cube cube) {
            left.turn(cube);
            left.turn(cube);
            left.turn(cube);
        }
    };

    protected abstract void turn(Cube cube);

    public static TurnOperations forLang(char direction) {
        switch (direction){
            case '2': return up;
            case '8': return down;
            case '4': return left;
            case '6': return right;
        }
        throw new IllegalArgumentException("direction "+direction+" is strange");
    }
}
