package de.n.cube;

import org.apache.commons.lang.StringUtils;

/**
* User: niles
* Date: 13.01.12
* Time: 01:38
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
public enum Face {
    top(0, 0, 'w') {
        @Override
        protected void spinOtherFaces(SpinOperations spinOperation, Cube cube) {
            String orgCubeState = cube.cubeState;
            cube.cubeState = orgCubeState.substring(0, 12);

            String lineToSpin = orgCubeState.substring(12, 24);
            int shiftPositions = ((spinOperation.ordinal() + 1) * 3) % 24;

            String spinnedLine = lineToSpin.substring(shiftPositions);
            spinnedLine += lineToSpin.substring(0, shiftPositions);


            cube.cubeState += spinnedLine;
            cube.cubeState += orgCubeState.substring(24);
        }
    }, //
    front(0, 3, 'o') {
        @Override
        protected void spinOtherFaces(SpinOperations spinOperation, Cube cube) {
            for (int o = 0; o < (spinOperation.ordinal() + 1); o++) {
                String orgCubeState = cube.cubeState;
                String[] orgCubeStateLines = StringUtils.split(orgCubeState, '\n');
                cube.cubeState = orgCubeState.substring(0, 8);
                String buttonFirstLine = "";
                for (int i = 5; i >= 3; i--) {
                    cube.cubeState += orgCubeStateLines[i].charAt(11);  //topLastLine
                    buttonFirstLine += orgCubeStateLines[i].charAt(3);
                }
                cube.cubeState += '\n';

                String orgButtonFirstLine = orgCubeState.substring(8, 11); //123
                String orgTopLastLine = orgCubeState.substring(51, 54);//987
                for (int i = 0; i < 3; i++) {
                    String orgLine = orgCubeStateLines[i + 3];

                    cube.cubeState += "" + orgLine.substring(0, 3);
                    cube.cubeState += orgButtonFirstLine.charAt(i);
                    cube.cubeState += orgLine.substring(4, 11);
                    cube.cubeState += orgTopLastLine.charAt(i);
                    cube.cubeState += '\n';
                }
                cube.cubeState += buttonFirstLine; //654
                cube.cubeState += orgCubeState.substring(54);
                //cube.cubeState = orgCubeState;
            }
        }
    }, //
    left(9, 3, 'b') {
        @Override
        protected void spinOtherFaces(SpinOperations spinOperation, Cube cube) {
            cube.turnInternal(TurnOperations.right);
            front.spinOtherFaces(spinOperation, cube);
            cube.turnInternal(TurnOperations.left);
        }
    }, //
    back(6, 3, 'r') {
        @Override
        protected void spinOtherFaces(SpinOperations spinOperation, Cube cube) {
            cube.turnInternal(TurnOperations.left);
            cube.turnInternal(TurnOperations.left);
            front.spinOtherFaces(spinOperation, cube);
            cube.turnInternal(TurnOperations.left);
            cube.turnInternal(TurnOperations.left);

        }
    }, //
    right(3, 3, 'g') {
        @Override
        protected void spinOtherFaces(SpinOperations spinOperation, Cube cube) {
            cube.turnInternal(TurnOperations.left);
            front.spinOtherFaces(spinOperation, cube);
            cube.turnInternal(TurnOperations.right);
        }
    }, //
    button(0, 6, 'y') {
        @Override
        protected void spinOtherFaces(SpinOperations spinOperation, Cube cube) {
            String orgCubeState = cube.cubeState;
            cube.cubeState = orgCubeState.substring(0, 38);

            String lineToSpin = orgCubeState.substring(38, 50);
            int shiftPositions = ((SpinOperations.values().length - spinOperation.ordinal()) * 3);

            String spinnedLine = lineToSpin.substring(shiftPositions);
            spinnedLine += lineToSpin.substring(0, shiftPositions);


            cube.cubeState += spinnedLine;
            cube.cubeState += orgCubeState.substring(50);
        }
    };

    private final int hPaperOffset;

    private final int vPaperOffset;

    final char initialColor;

    Face(int hPaperOffset, int vPaperOffset, char initialColor) {
        this.hPaperOffset = hPaperOffset;
        this.vPaperOffset = vPaperOffset;
        this.initialColor = initialColor;
    }

    public static boolean isSolved(String faceState) {
        char middle = middlePointColor(faceState);
        for (char c : faceState.toCharArray()) {
            if (c != middle && c != '\n') {
                return false;
            }
        }
        return true;
    }

    static char middlePointColor(String faceState) {
        return StringUtils.split(faceState, '\n')[1].charAt(1);
    }

    public String faceState(String cubeState) {
        String[] cubeStateLines = StringUtils.split(cubeState, '\n');
        String result = "";
        for (int i = 0; i < 3; i++) {
            String line = cubeStateLines[i + vPaperOffset];
            result += line.substring(hPaperOffset, hPaperOffset + 3);
            result += '\n';
        }
        return result;
    }

    public void spinFaceOnCube(SpinOperations spinOperation, Cube cube) {
        spinCurrentFace(spinOperation, cube);
        spinOtherFaces(spinOperation, cube);
    }

    void spinCurrentFace(SpinOperations spinOperation, Cube cube) {
        String faceState = faceState(cube.cubeState);
        faceState = spinFace(faceState, spinOperation);
        setFaceState(cube, faceState);
    }

    protected abstract void spinOtherFaces(SpinOperations spinOperation, Cube cube);

    void setFaceState(Cube cube, String faceState) {
        String[] faceStateLines = StringUtils.split(faceState, '\n');
        String[] cubeStateLines = StringUtils.split(cube.cubeState, '\n');

        for (int i = 0; i < faceStateLines.length; i++) {
            String lineToSet = faceStateLines[i];
            String orgLine = cubeStateLines[i + vPaperOffset];
            String newLine = orgLine.substring(0, hPaperOffset)
                    + lineToSet
                    + orgLine.substring(hPaperOffset + 3);
            cubeStateLines[i + vPaperOffset] = newLine;

        }
        cube.cubeState = StringUtils.join(cubeStateLines, '\n') + '\n';
    }

    static String spinFace(String faceState, SpinOperations spinOperation) {
        //TODO
        for (int i = 0; i < spinOperation.ordinal() + 1; i++) {
            String[] faceStateLines = StringUtils.split(faceState, '\n');
            String result = "";
            for (int j = 0; j < 3; j++) {
                for (int k = 2; k >= 0; k--) {
                    result += faceStateLines[k].charAt(j);
                }
                result += '\n';
            }
            faceState = result;
        }
        return faceState;
    }

    public static Face forLang(char face) {
        switch (face) {
            case 'U':
                return top;
            case 'F':
                return front;
            case 'R':
                return right;
            case 'B':
                return back;
            case 'L':
                return left;
            case 'D':
                return button;
        }
        throw new IllegalArgumentException("cant convert "+face);
    }
}
