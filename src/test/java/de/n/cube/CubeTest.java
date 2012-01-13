package de.n.cube;

import org.junit.Before;
import org.junit.Test;

import static de.n.cube.Cube.INITIAL_CUBE_STATE;
import static de.n.cube.Face.*;
import static de.n.cube.Move.spin;
import static de.n.cube.Move.turn;
import static de.n.cube.SpinOperations.*;
import static de.n.cube.TurnOperations.down;
import static de.n.cube.TurnOperations.up;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
public class CubeTest {


    private Cube cube;

    private static final String TURNED_UP_CUBESTATE = "ooo\n"//
            + "ooo\n"//
            + "ooo\n"//
            + "yyygggwwwbbb\n"//
            + "yyygggwwwbbb\n"//
            + "yyygggwwwbbb\n"//
            + "rrr\n"//
            + "rrr\n"//
            + "rrr\n";

    private static final String TURNED_DOWN_CUBESTATE = "rrr\n"//
            + "rrr\n"//
            + "rrr\n"//
            + "wwwgggyyybbb\n"//
            + "wwwgggyyybbb\n"//
            + "wwwgggyyybbb\n"//
            + "ooo\n"//
            + "ooo\n"//
            + "ooo\n";

    private static final String TURNED_LEFT_CUBESTATE = "www\n"//
            + "www\n"//
            + "www\n"//
            + "gggrrrbbbooo\n"//
            + "gggrrrbbbooo\n"//
            + "gggrrrbbbooo\n"//
            + "yyy\n"//
            + "yyy\n"//
            + "yyy\n";

    private static final String TURNED_RIGHT_CUBESTATE = "www\n"//
            + "www\n"//
            + "www\n"//
            + "bbbooogggrrr\n"//
            + "bbbooogggrrr\n"//
            + "bbbooogggrrr\n"//
            + "yyy\n"//
            + "yyy\n"//
            + "yyy\n";

    private static final String WATCHABLE_FOR_SPINTOP_CUBESTATE = "123\n"//
            + "456\n"//
            + "789\n"//
            + "123456789abc\n"//
            + "ooogggrrrbbb\n"//
            + "ooogggrrrbbb\n"//
            + "yyy\n"//
            + "yyy\n"//
            + "yyy\n";

    private static final String TOP_CLOCKWISE_CUBE_STATE = "741\n"//
            + "852\n"//
            + "963\n"//
            + "456789abc123\n"//
            + "ooogggrrrbbb\n"//
            + "ooogggrrrbbb\n"//
            + "yyy\n"//
            + "yyy\n"//
            + "yyy\n";

    private static final String WATCHABLE_FOR_SPINBUTTON_CUBESTATE = "www\n"//
            + "www\n"//
            + "www\n"//
            + "ooogggrrrbbb\n"//
            + "ooogggrrrbbb\n"//
            + "123456789abc\n"//
            + "123\n"//
            + "456\n"//
            + "789\n";

    private static final String BUTTON_CLOCKWISE_CUBE_STATE = "www\n"//
            + "www\n"//
            + "www\n"//
            + "ooogggrrrbbb\n"//
            + "ooogggrrrbbb\n"//
            + "abc123456789\n"//
            + "741\n"//
            + "852\n"//
            + "963\n";

    private static final String WATCHABLE_FOR_SPINFRONT_CUBESTATE = "www\n"//
            + "www\n"//
            + "123\n"//
            + "1234ggrrrbbc\n"//
            + "4565ggrrrbbb\n"//
            + "7896ggrrrbba\n"//
            + "987\n"//
            + "yyy\n"//
            + "yyy\n";

    private static final String FRONT_CLOCKWISE_CUBE_STATE = "www\n"//
            + "www\n"//
            + "abc\n"//
            + "7411ggrrrbb9\n"//
            + "8522ggrrrbb8\n"//
            + "9633ggrrrbb7\n"//
            + "654\n"//
            + "yyy\n"//
            + "yyy\n";
    private static final String WATCHABLE_FOR_SPINRIGHT_CUBESTATE = "ww3\n"//
            + "ww2\n"//
            + "ww1\n"//
            + "ooc1234rrbbb\n"//
            + "oob4565rrbbb\n"//
            + "ooa7896rrbbb\n"//
            + "yy9\n"//
            + "yy8\n"//
            + "yy7\n";
    private static final String RIGHT_CLOCKWISE_CUBE_STATE = "wwc\n"//
            + "wwb\n"//
            + "wwa\n"//
            + "oo97411rrbbb\n"//
            + "oo88522rrbbb\n"//
            + "oo79633rrbbb\n"//
            + "yy6\n"//
            + "yy5\n"//
            + "yy4\n";

    static final String TURN_SPECIAL_STATE = "123\n"//
            + "456\n"//
            + "789\n"//
            + "123gggrrrbbb\n"//
            + "456gggrrrbbb\n"//
            + "789gggrrrbbb\n"//
            + "123\n"//
            + "456\n"//
            + "789\n";
    private String faked;
    private static final String WATCHABLE_FOR_SPINBACK_CUBESTATE = "321\n"//
            + "www\n"//
            + "www\n"//
            + "oooggc1234bb\n"//
            + "oooggb4565bb\n"//
            + "ooogga7896bb\n"//
            + "yyy\n"//
            + "yyy\n"//
            + "789\n";
    private static final String BACK_CLOCKWISE_CUBE_STATE = "cba\n" +
            "www\n" +
            "www\n" +
            "ooogg97411bb\n" +
            "ooogg88522bb\n" +
            "ooogg79633bb\n" +
            "yyy\n" +
            "yyy\n" +
            "456\n";
    private static final String WATCHABLE_FOR_SPINLEFT_CUBESTATE = "1ww\n"//
            + "2ww\n"//
            + "3ww\n"//
            + "4oogggrrc123\n"//
            + "5oogggrrb456\n"//
            + "6oogggrra789\n"//
            + "7yy\n"//
            + "8yy\n"//
            + "9yy\n";
    private static final String LEFT_CLOCKWISE_CUBE_STATE = "aww\n" +
            "bww\n" +
            "cww\n" +
            "1oogggrr9741\n" +
            "2oogggrr8852\n" +
            "3oogggrr7963\n" +
            "4yy\n" +
            "5yy\n" +
            "6yy\n";

    @Before
    public void setup() {
        cube = new Cube();
    }


    @Test
    public void turnUp() throws Exception {
        cube.move(turn(up));

        assertCubeStateSolved(TURNED_UP_CUBESTATE, 1);
    }


    @Test
    public void turnup3x() throws Exception {
        cube.move(turn(up), turn(up), turn(up));

        assertCubeStateSolved(TURNED_DOWN_CUBESTATE, 3);
    }

    @Test
    public void turndown() throws Exception {
        cube.move(turn(down));
        assertCubeStateSolved(TURNED_DOWN_CUBESTATE, 1);
    }

    @Test
    public void turnupdown() throws Exception {
        cube.move(turn(down), turn(up));

        assertCubeStateSolved(INITIAL_CUBE_STATE, 2);
    }

    @Test
    public void turnleft() throws Exception {
        cube.move(turn(TurnOperations.left));

        assertCubeStateSolved(TURNED_LEFT_CUBESTATE, 1);
    }

    @Test
    public void turnleft3x() throws Exception {
        cube.move(turn(TurnOperations.left), turn(TurnOperations.left), turn(TurnOperations.left));

        assertCubeStateSolved(TURNED_RIGHT_CUBESTATE, 3);
    }

    @Test
    public void turnright() throws Exception {
        cube.move(turn(TurnOperations.right));

        assertCubeStateSolved(TURNED_RIGHT_CUBESTATE, 1);
    }

    @Test
    public void turnrightleft() throws Exception {
        cube.move(turn(TurnOperations.right), turn(TurnOperations.left));

        assertCubeStateSolved(INITIAL_CUBE_STATE, 2);
    }

    @Test
    public void turnSpecialhorizontal() throws Exception {
        fakeCubeState(TURN_SPECIAL_STATE);

        cube.move(turn(TurnOperations.left));
        assertCubeState("741\n"//
                + "852\n"//
                + "963\n"//
                + "gggrrrbbb123\n"//
                + "gggrrrbbb456\n"//
                + "gggrrrbbb789\n"//
                + "369\n"//
                + "258\n"//
                + "147\n", 1, 0, false);
        cube.move(turn(TurnOperations.right));
        assertCubeState(TURN_SPECIAL_STATE, 2, 0, false);
        cube.move(turn(TurnOperations.left), turn(TurnOperations.left), turn(TurnOperations.left), turn(TurnOperations.left));
        assertCubeState(TURN_SPECIAL_STATE, 6, 0, false);
    }

    @Test
    public void turnSpecialvertical() throws Exception {
        String specialState = "123\n"//
                + "456\n"//
                + "789\n"//
                + "oooggg123bbb\n"//
                + "oooggg456bbb\n"//
                + "oooggg789bbb\n"//
                + "123\n"//
                + "456\n"//
                + "789\n";
        fakeCubeState(specialState);
        cube.move(turn(up));

        assertCubeState("ooo\n"//
                + "ooo\n"//
                + "ooo\n"//
                + "123ggg987bbb\n"//
                + "456ggg654bbb\n"//
                + "789ggg321bbb\n"//
                + "987\n"//
                + "654\n"//
                + "321\n", 1, 0, false);
        cube.move(turn(down));

        assertCubeState(specialState, 2, 0, false);
        cube.move(turn(down), turn(down), turn(down), turn(down));

        assertCubeState(specialState, 6, 0, false);
    }

    @Test
    public void turnSpecialvertical2() throws Exception {
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
        cube.move(turn(up));

        assertCubeState("ooo\n"//
                + "ooo\n"//
                + "ooo\n"//
                + "yyy741www741\n"//
                + "yyy852www852\n"//
                + "yyy963www963\n"//
                + "rrr\n"//
                + "rrr\n"//
                + "rrr\n", 1, 0, false);
        cube.move(turn(down));

        assertCubeState(specialState, 2, 0, false);
        cube.move(turn(down), turn(down), turn(down), turn(down));

        assertCubeState(specialState, 6, 0, false);
    }

    @Test
    public void spinTopface() throws Exception {
        testSpin(WATCHABLE_FOR_SPINTOP_CUBESTATE, top, TOP_CLOCKWISE_CUBE_STATE);
    }


    @Test
    public void spinButtonface() throws Exception {
        testSpin(WATCHABLE_FOR_SPINBUTTON_CUBESTATE, button, BUTTON_CLOCKWISE_CUBE_STATE);
    }

    @Test
    public void spinFrontface() throws Exception {
        testSpin(WATCHABLE_FOR_SPINFRONT_CUBESTATE, front, FRONT_CLOCKWISE_CUBE_STATE);
    }

    @Test
    public void spinRightface() throws Exception {
        testSpin(WATCHABLE_FOR_SPINRIGHT_CUBESTATE, Face.right, RIGHT_CLOCKWISE_CUBE_STATE);
    }

    @Test
    public void spinBackface() throws Exception {
        testSpin(WATCHABLE_FOR_SPINBACK_CUBESTATE, back, BACK_CLOCKWISE_CUBE_STATE);
    }

    @Test
    public void spinLeftface() throws Exception {
        testSpin(WATCHABLE_FOR_SPINLEFT_CUBESTATE, Face.left, LEFT_CLOCKWISE_CUBE_STATE);
    }

    @Test
    public void simplePattern() throws Exception {
        cube.move(spin(Face.left, clockwise), spin(Face.right, anticlockwise));
        cube.move(spin(Face.left, clockwise), spin(Face.right, anticlockwise));
        cube.move(turn(TurnOperations.right));
        cube.move(spin(Face.left, clockwise), spin(Face.right, anticlockwise));
        cube.move(spin(Face.left, clockwise), spin(Face.right, anticlockwise));

        cube.move(spin(Face.top, clockwise), spin(Face.button, anticlockwise));
        cube.move(spin(Face.top, clockwise), spin(Face.button, anticlockwise));
        assertThat(cube.cubeState, is("wyw\n" +
                "ywy\n" +
                "wyw\n" +
                "bgborogbgror\n" +
                "gbgrorbgboro\n" +
                "bgborogbgror\n" +
                "ywy\n" +
                "wyw\n" +
                "ywy\n"));
    }

    @Test
    public void faceTest() throws Exception {
        String testFaceState = "123\n456\n789\n";
        assertThat(Face.middlePointColor(testFaceState), is('5'));
        assertThat(top.faceState(testFaceState), is(testFaceState));
        assertThat(Face.isSolved(testFaceState), is(false));
        assertThat(Face.spinFace(testFaceState, clockwise), is("741\n"//
                + "852\n"//
                + "963\n"));
        assertThat(Face.spinFace(testFaceState, twoTimes), is("987\n"//
                + "654\n"//
                + "321\n"));
        assertThat(Face.spinFace(testFaceState, anticlockwise), is("369\n"//
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


    private void testSpin(String watchableCubestate, Face faceToSpin, String expectedStateAfterClockwiseSpin) {
        fakeCubeState(watchableCubestate);
        assertThat(cube.isSolved(), is(false));
        cube.move(spin(faceToSpin, clockwise));
        assertCubeSpinnedUnsolved(expectedStateAfterClockwiseSpin, 1);

        cube.move(spin(faceToSpin, anticlockwise));
        assertCubeSpinnedUnsolved(watchableCubestate, 2);

        cube.move(spin(faceToSpin, clockwise), spin(faceToSpin, clockwise), spin(faceToSpin, twoTimes));
        assertCubeSpinnedUnsolved(watchableCubestate, 5);
    }

    private void assertCubeSpinnedUnsolved(String expected, int spinCount) {
        assertCubeState(expected, 0, spinCount, false);
    }

    private void assertCubeStateSolved(String expected, int expectedTurnCount) {
        assertCubeState(expected, expectedTurnCount, 0, true);
    }

    private void assertCubeState(String expected, int expectedTurnCount, int expectedSpincount, boolean solved) {
        if (!expected.equals(cube.cubeState)) {
            if (faked == null) {
                System.out.println("initial:\n" + INITIAL_CUBE_STATE);
            } else {
                System.out.println("faked:\n" + faked);
            }
            System.out.println("\n\nexpected:\n" + expected);
            System.out.println("\n\ngot:\n" + cube.cubeState);
        }
        assertThat(cube.turnCount, is(expectedTurnCount));
        assertThat(cube.spinCount, is(expectedSpincount));
        assertThat(cube.isSolved(), is(solved));
        assertThat(cube.cubeState, is(expected));
    }

    private void fakeCubeState(String fakedState) {
        cube.cubeState = fakedState;
        faked = fakedState;
    }
}
