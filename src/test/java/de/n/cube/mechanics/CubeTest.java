package de.n.cube.mechanics;

import org.junit.Before;
import org.junit.Test;

import static de.n.cube.mechanics.Cube.INITIAL_CUBE_STATE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
public class CubeTest {


    private Cube cube;

    private static final String TURNED_UP_CUBE_STATE = "ooo\n"//
            + "ooo\n"//
            + "ooo\n"//
            + "yyy" + "ggg" + "www" + "bbb\n"//
            + "yyy" + "ggg" + "www" + "bbb\n"//
            + "yyy" + "ggg" + "www" + "bbb\n"//
            + "rrr\n"//
            + "rrr\n"//
            + "rrr\n";

    private static final String TURNED_DOWN_CUBE_STATE = "rrr\n"//
            + "rrr\n"//
            + "rrr\n"//
            + "www" + "ggg" + "yyy" + "bbb\n"//
            + "www" + "ggg" + "yyy" + "bbb\n"//
            + "www" + "ggg" + "yyy" + "bbb\n"//
            + "ooo\n"//
            + "ooo\n"//
            + "ooo\n";

    private static final String TURNED_LEFT_CUBE_STATE = "www\n"//
            + "www\n"//
            + "www\n"//
            + "ggg" + "rrr" + "bbb" + "ooo\n"//
            + "ggg" + "rrr" + "bbb" + "ooo\n"//
            + "ggg" + "rrr" + "bbb" + "ooo\n"//
            + "yyy\n"//
            + "yyy\n"//
            + "yyy\n";

    private static final String TURNED_RIGHT_CUBE_STATE = "www\n"//
            + "www\n"//
            + "www\n"//
            + "bbb" + "ooo" + "ggg" + "rrr\n"//
            + "bbb" + "ooo" + "ggg" + "rrr\n"//
            + "bbb" + "ooo" + "ggg" + "rrr\n"//
            + "yyy\n"//
            + "yyy\n"//
            + "yyy\n";

    private static final String WATCHABLE_FOR_SPIN_TOP_CUBE_STATE = "123\n"//
            + "456\n"//
            + "789\n"//
            + "123" + "456" + "789" + "abc\n"//
            + "ooo" + "ggg" + "rrr" + "bbb\n"//
            + "ooo" + "ggg" + "rrr" + "bbb\n"//
            + "yyy\n"//
            + "yyy\n"//
            + "yyy\n";

    private static final String TOP_CLOCKWISE_CUBE_STATE = "741\n"//
            + "852\n"//
            + "963\n"//
            + "456" + "789" + "abc" + "123\n"//
            + "ooo" + "ggg" + "rrr" + "bbb\n"//
            + "ooo" + "ggg" + "rrr" + "bbb\n"//
            + "yyy\n"//
            + "yyy\n"//
            + "yyy\n";

    private static final String WATCHABLE_FOR_SPIN_BUTTON_CUBE_STATE = "www\n"//
            + "www\n"//
            + "www\n"//
            + "ooo" + "ggg" + "rrr" + "bbb\n"//
            + "ooo" + "ggg" + "rrr" + "bbb\n"//
            + "123" + "456" + "789" + "abc\n"//
            + "123\n"//
            + "456\n"//
            + "789\n";

    private static final String BUTTON_CLOCKWISE_CUBE_STATE = "www\n"//
            + "www\n"//
            + "www\n"//
            + "ooo" + "ggg" + "rrr" + "bbb\n"//
            + "ooo" + "ggg" + "rrr" + "bbb\n"//
            + "abc" + "123" + "456" + "789\n"//
            + "741\n"//
            + "852\n"//
            + "963\n";

    private static final String WATCHABLE_FOR_SPIN_FRONT_CUBE_STATE = "www\n"//
            + "www\n"//
            + "123\n"//
            + "123" + "4gg" + "rrr" + "bbc\n"//
            + "456" + "5gg" + "rrr" + "bbb\n"//
            + "789" + "6gg" + "rrr" + "bba\n"//
            + "987\n"//
            + "yyy\n"//
            + "yyy\n";

    private static final String FRONT_CLOCKWISE_CUBE_STATE = "www\n"//
            + "www\n"//
            + "abc\n"//
            + "741" + "1gg" + "rrr" + "bb9\n"//
            + "852" + "2gg" + "rrr" + "bb8\n"//
            + "963" + "3gg" + "rrr" + "bb7\n"//
            + "654\n"//
            + "yyy\n"//
            + "yyy\n";
    private static final String WATCHABLE_FOR_SPIN_RIGHT_CUBE_STATE = "ww3\n"//
            + "ww2\n"//
            + "ww1\n"//
            + "ooc1234rr" + "bbb\n"//
            + "oob4565rr" + "bbb\n"//
            + "ooa7896rr" + "bbb\n"//
            + "yy9\n"//
            + "yy8\n"//
            + "yy7\n";
    private static final String RIGHT_CLOCKWISE_CUBE_STATE = "wwc\n"//
            + "wwb\n"//
            + "wwa\n"//
            + "oo97411rr" + "bbb\n"//
            + "oo88522rr" + "bbb\n"//
            + "oo79633rr" + "bbb\n"//
            + "yy6\n"//
            + "yy5\n"//
            + "yy4\n";

    private static final String TURN_SPECIAL_STATE = "123\n"//
            + "456\n"//
            + "789\n"//
            + "123" + "ggg" + "rrr" + "bbb\n"//
            + "456" + "ggg" + "rrr" + "bbb\n"//
            + "789" + "ggg" + "rrr" + "bbb\n"//
            + "123\n"//
            + "456\n"//
            + "789\n";
    private String faked;
    private static final String WATCHABLE_FOR_SPIN_BACK_CUBE_STATE = "321\n"//
            + "www\n"//
            + "www\n"//
            + "ooo" + "ggc" + "123" + "4bb\n"//
            + "ooo" + "ggb" + "456" + "5bb\n"//
            + "ooo" + "gga" + "789" + "6bb\n"//
            + "yyy\n"//
            + "yyy\n"//
            + "789\n";
    private static final String BACK_CLOCKWISE_CUBE_STATE = "cba\n" +
            "www\n" +
            "www\n" +
            "ooo" + "gg97411bb\n" +
            "ooo" + "gg88522bb\n" +
            "ooo" + "gg79633bb\n" +
            "yyy\n" +
            "yyy\n" +
            "456\n";
    private static final String WATCHABLE_FOR_SPIN_LEFT_CUBE_STATE = "1ww\n"//
            + "2ww\n"//
            + "3ww\n"//
            + "4oo" + "ggg" + "rrc" + "123\n"//
            + "5oo" + "ggg" + "rrb" + "456\n"//
            + "6oo" + "ggg" + "rra" + "789\n"//
            + "7yy\n"//
            + "8yy\n"//
            + "9yy\n";
    private static final String LEFT_CLOCKWISE_CUBE_STATE = "aww\n" +
            "bww\n" +
            "cww\n" +
            "1oo" + "ggg" + "rr9" + "741\n" +
            "2oo" + "ggg" + "rr8" + "852\n" +
            "3oo" + "ggg" + "rr7" + "963\n" +
            "4yy\n" +
            "5yy\n" +
            "6yy\n";

    @Before
    public void setup() {
        cube = new Cube();
    }


    @Test
    public void turnUp() throws Exception {
        cube.move(Move.turn(TurnOperations.up));

        assertCubeStateSolved(TURNED_UP_CUBE_STATE, 1);
    }


    @Test
    public void turnUp3x() throws Exception {
        cube.move(Move.turn(TurnOperations.up), Move.turn(TurnOperations.up), Move.turn(TurnOperations.up));

        assertCubeStateSolved(TURNED_DOWN_CUBE_STATE, 3);
    }

    @Test
    public void turnDown() throws Exception {
        cube.move(Move.turn(TurnOperations.down));
        assertCubeStateSolved(TURNED_DOWN_CUBE_STATE, 1);
    }

    @Test
    public void turnUpDown() throws Exception {
        cube.move(Move.turn(TurnOperations.down), Move.turn(TurnOperations.up));

        assertCubeStateSolved(INITIAL_CUBE_STATE, 2);
    }

    @Test
    public void turnLeft() throws Exception {
        cube.move(Move.turn(TurnOperations.left));

        assertCubeStateSolved(TURNED_LEFT_CUBE_STATE, 1);
    }

    @Test
    public void turnLeft3x() throws Exception {
        cube.move(Move.turn(TurnOperations.left), Move.turn(TurnOperations.left), Move.turn(TurnOperations.left));

        assertCubeStateSolved(TURNED_RIGHT_CUBE_STATE, 3);
    }

    @Test
    public void turnRight() throws Exception {
        cube.move(Move.turn(TurnOperations.right));

        assertCubeStateSolved(TURNED_RIGHT_CUBE_STATE, 1);
    }

    @Test
    public void turnRightLeft() throws Exception {
        cube.move(Move.turn(TurnOperations.right), Move.turn(TurnOperations.left));

        assertCubeStateSolved(INITIAL_CUBE_STATE, 2);
    }

    @Test
    public void turnSpecialHorizontal() throws Exception {
        fakeCubeState(TURN_SPECIAL_STATE);

        cube.move(Move.turn(TurnOperations.left));
        assertCubeState("741\n"//
                + "852\n"//
                + "963\n"//
                + "ggg" + "rrr" + "bbb" + "123\n"//
                + "ggg" + "rrr" + "bbb" + "456\n"//
                + "ggg" + "rrr" + "bbb" + "789\n"//
                + "369\n"//
                + "258\n"//
                + "147\n", 1, 0, false);
        cube.move(Move.turn(TurnOperations.right));
        assertCubeState(TURN_SPECIAL_STATE, 2, 0, false);
        cube.move(Move.turn(TurnOperations.left), Move.turn(TurnOperations.left), Move.turn(TurnOperations.left), Move.turn(TurnOperations.left));
        assertCubeState(TURN_SPECIAL_STATE, 6, 0, false);
    }

    @Test
    public void turnSpecialVertical() throws Exception {
        String specialState = "123\n"//
                + "456\n"//
                + "789\n"//
                + "ooo" + "ggg123bbb\n"//
                + "ooo" + "ggg456bbb\n"//
                + "ooo" + "ggg789bbb\n"//
                + "123\n"//
                + "456\n"//
                + "789\n";
        fakeCubeState(specialState);
        cube.move(Move.turn(TurnOperations.up));

        assertCubeState("ooo\n"//
                + "ooo\n"//
                + "ooo\n"//
                + "123ggg987bbb\n"//
                + "456ggg654bbb\n"//
                + "789ggg321bbb\n"//
                + "987\n"//
                + "654\n"//
                + "321\n", 1, 0, false);
        cube.move(Move.turn(TurnOperations.down));

        assertCubeState(specialState, 2, 0, false);
        cube.move(Move.turn(TurnOperations.down), Move.turn(TurnOperations.down), Move.turn(TurnOperations.down), Move.turn(TurnOperations.down));

        assertCubeState(specialState, 6, 0, false);
    }

    @Test
    public void turnSpecialVertical2() throws Exception {
        String specialState = "www\n"//
                + "www\n"//
                + "www\n"//
                + "ooo123rrr123\n"//
                + "ooo456rrr456\n"//
                + "ooo789rrr789\n"//
                + "yyy\n"//
                + "yyy\n"//
                + "yyy\n";
        fakeCubeState(specialState);
        cube.move(Move.turn(TurnOperations.up));

        assertCubeState("ooo\n"//
                + "ooo\n"//
                + "ooo\n"//
                + "yyy741www741\n"//
                + "yyy852www852\n"//
                + "yyy963www963\n"//
                + "rrr\n"//
                + "rrr\n"//
                + "rrr\n", 1, 0, false);
        cube.move(Move.turn(TurnOperations.down));

        assertCubeState(specialState, 2, 0, false);
        cube.move(Move.turn(TurnOperations.down), Move.turn(TurnOperations.down), Move.turn(TurnOperations.down), Move.turn(TurnOperations.down));

        assertCubeState(specialState, 6, 0, false);
    }

    @Test
    public void spinTopFace() throws Exception {
        testSpin(WATCHABLE_FOR_SPIN_TOP_CUBE_STATE, Face.top, TOP_CLOCKWISE_CUBE_STATE);
    }


    @Test
    public void spinButtonFace() throws Exception {
        testSpin(WATCHABLE_FOR_SPIN_BUTTON_CUBE_STATE, Face.button, BUTTON_CLOCKWISE_CUBE_STATE);
    }

    @Test
    public void spinFrontFace() throws Exception {
        testSpin(WATCHABLE_FOR_SPIN_FRONT_CUBE_STATE, Face.front, FRONT_CLOCKWISE_CUBE_STATE);
    }

    @Test
    public void spinRightFace() throws Exception {
        testSpin(WATCHABLE_FOR_SPIN_RIGHT_CUBE_STATE, Face.right, RIGHT_CLOCKWISE_CUBE_STATE);
    }

    @Test
    public void spinBackFace() throws Exception {
        testSpin(WATCHABLE_FOR_SPIN_BACK_CUBE_STATE, Face.back, BACK_CLOCKWISE_CUBE_STATE);
    }

    @Test
    public void spinLeftFace() throws Exception {
        testSpin(WATCHABLE_FOR_SPIN_LEFT_CUBE_STATE, Face.left, LEFT_CLOCKWISE_CUBE_STATE);
    }

    @Test
    public void simplePattern() throws Exception {
        cube.move(Move.spin(Face.left, SpinOperations.clockwise), Move.spin(Face.right, SpinOperations.anticlockwise));
        cube.move(Move.spin(Face.left, SpinOperations.clockwise), Move.spin(Face.right, SpinOperations.anticlockwise));
        cube.move(Move.turn(TurnOperations.right));
        cube.move(Move.spin(Face.left, SpinOperations.clockwise), Move.spin(Face.right, SpinOperations.anticlockwise));
        cube.move(Move.spin(Face.left, SpinOperations.clockwise), Move.spin(Face.right, SpinOperations.anticlockwise));

        cube.move(Move.spin(Face.top, SpinOperations.clockwise), Move.spin(Face.button, SpinOperations.anticlockwise));
        cube.move(Move.spin(Face.top, SpinOperations.clockwise), Move.spin(Face.button, SpinOperations.anticlockwise));
        assertThat(cube.cubeState, is("wyw\n" +
                "ywy\n" +
                "wyw\n" +
                "bgb" + "oro" + "gbg" + "ror\n" +
                "gbg" + "ror" + "bgb" + "oro\n" +
                "bgb" + "oro" + "gbg" + "ror\n" +
                "ywy\n" +
                "wyw\n" +
                "ywy\n"));
    }

    @Test
    public void faceTest() throws Exception {
        String testFaceState = "123\n456\n789\n";
        assertThat(Face.middlePointColor(testFaceState), is('5'));
        assertThat(Face.top.faceState(testFaceState), is(testFaceState));
        assertThat(Face.isSolved(testFaceState), is(false));
        assertThat(Face.spinFace(testFaceState, SpinOperations.clockwise), is("741\n"//
                + "852\n"//
                + "963\n"));
        assertThat(Face.spinFace(testFaceState, SpinOperations.twoTimes), is("987\n"//
                + "654\n"//
                + "321\n"));
        assertThat(Face.spinFace(testFaceState, SpinOperations.anticlockwise), is("369\n"//
                + "258\n"//
                + "147\n"));

        for (Face face : Face.values()) {
            char c = face.initialColor;
            String expectedFace = "" + c + c + c + '\n';
            expectedFace += expectedFace + expectedFace;
            assertThat(face.faceState(cube.cubeState), is(expectedFace));
            String faceState = face.faceState(cube.cubeState);
            assertThat(Face.middlePointColor(faceState), is(c));
            assertThat(Face.isSolved(faceState), is(true));
            face.setFaceState(cube, testFaceState);
            assertThat(face.faceState(cube.cubeState), is(testFaceState));

        }
    }


    private void testSpin(String watchableCubeState, Face faceToSpin, String expectedStateAfterClockwiseSpin) {
        fakeCubeState(watchableCubeState);
        assertThat(cube.isSolved(), is(false));
        cube.move(Move.spin(faceToSpin, SpinOperations.clockwise));
        assertCubeSpinedUnsolved(expectedStateAfterClockwiseSpin, 1);

        cube.move(Move.spin(faceToSpin, SpinOperations.anticlockwise));
        assertCubeSpinedUnsolved(watchableCubeState, 2);

        cube.move(Move.spin(faceToSpin, SpinOperations.clockwise), Move.spin(faceToSpin, SpinOperations.clockwise), Move.spin(faceToSpin, SpinOperations.twoTimes));
        assertCubeSpinedUnsolved(watchableCubeState, 5);
    }

    private void assertCubeSpinedUnsolved(String expected, int spinCount) {
        assertCubeState(expected, 0, spinCount, false);
    }

    private void assertCubeStateSolved(String expected, int expectedTurnCount) {
        assertCubeState(expected, expectedTurnCount, 0, true);
    }

    private void assertCubeState(String expected, int expectedTurnCount, int expectedSpinCount, boolean solved) {
        if (!expected.equals(cube.cubeState)) {
            if (faked == null) {
                System.out.println("initial:\n" + INITIAL_CUBE_STATE);
            } else {
                System.out.println("faked:\n" + faked);
            }
            System.out.println("\n\n" + "expected:\n" + expected);
            System.out.println("\n\n" + "got:\n" + cube.cubeState);
        }
        assertThat(cube.turnCount, is(expectedTurnCount));
        assertThat(cube.spinCount, is(expectedSpinCount));
        assertThat(cube.isSolved(), is(solved));
        assertThat(cube.cubeState, is(expected));
    }

    private void fakeCubeState(String fakedState) {
        cube.cubeState = fakedState;
        faked = fakedState;
    }
}
