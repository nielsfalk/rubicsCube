package de.n.cube.language;

import de.n.cube.mechanics.Cube;
import de.n.cube.mechanics.Face;
import de.n.cube.mechanics.SpinOperations;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * User: niles
 * Date: 12.01.12
 * Time: 19:09
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
public class MovesTest {
    public static final Moves SUPER_FLIP = Moves.moves("superFlip", "RLU2FU-DF2R2B2LU2F-B-UR2DF2UR2U");
    private Cube cube;

    @Before
    public void setup() {
        cube = new Cube();
    }

    @Test
    public void convertLang() {
        for (char faceChar : Moves.FACE_CHARS) {
            for (char directionChar : Moves.DIRECTION_CHARS) {
                new Moves.Move(faceChar, directionChar).convert();
            }
        }

        try {
            new Moves.Move('ö', 'ü').convert();
            fail();
        } catch (IllegalArgumentException ignored) {
        }

        try {
            Face.forLang('ä');
            fail();
        } catch (IllegalArgumentException ignored) {
        }

        try {
            SpinOperations.forLang('ü');
            fail();
        } catch (IllegalArgumentException ignored) {
        }

        for (char directionChar : Moves.TURN_DIRECTION_CHARS) {
            new Moves.Turn(directionChar).convert();
        }
    }


    @Test
    public void convertSuperFlip() {
        Iterator<String> expectIterator = Arrays.asList("Face:right SpinOperation:clockwise",
                "Face:left SpinOperation:clockwise",
                "Face:top SpinOperation:twoTimes",
                "Face:front SpinOperation:clockwise",
                "Face:top SpinOperation:anticlockwise",
                "Face:button SpinOperation:clockwise",
                "Face:front SpinOperation:twoTimes",
                "Face:right SpinOperation:twoTimes",
                "Face:back SpinOperation:twoTimes",
                "Face:left SpinOperation:clockwise",
                "Face:top SpinOperation:twoTimes",
                "Face:front SpinOperation:anticlockwise",
                "Face:back SpinOperation:anticlockwise",
                "Face:top SpinOperation:clockwise",
                "Face:right SpinOperation:twoTimes",
                "Face:button SpinOperation:clockwise",
                "Face:front SpinOperation:twoTimes",
                "Face:top SpinOperation:clockwise",
                "Face:right SpinOperation:twoTimes",
                "Face:top SpinOperation:clockwise").iterator();
        for (Moves.Move move : SUPER_FLIP.moves) {
            assertThat(move.convert().toString(), is(expectIterator.next()));
        }
    }

    @Test
    public void patternX6() {
        Moves moves = Moves.moves("pattern", "l2r2u2d2f2b2");
        testPattern(moves, "wyw\n" +
                "ywy\n" +
                "wyw\n" +
                "oro" + "gbg" + "ror" + "bgb\n" +
                "ror" + "bgb" + "oro" + "gbg\n" +
                "oro" + "gbg" + "ror" + "bgb\n" +
                "ywy\n" +
                "wyw\n" +
                "ywy\n", 6, 0);
        testTwoTimesAppliedIsSolved(moves);
    }

    @Test
    public void patterMiddlePointsSpined() {
        testPattern(Moves.moves("pattern", "l-rt8u-dt4lr-t2"), "www\n" +
                "wgw\n" +
                "www\n" +
                "ooo" + "ggg" + "rrr" + "bbb\n" +
                "gog" + "ryr" + "brb" + "owo\n" +
                "ooo" + "ggg" + "rrr" + "bbb\n" +
                "yyy\n" +
                "yby\n" +
                "yyy\n", 5 + 1, 3);
    }

    private void testTwoTimesAppliedIsSolved(Moves movesEqualsUndoMoves) {
        assertThat(cube.isSolved(), is(true));
        movesEqualsUndoMoves.apply(cube);
        movesEqualsUndoMoves.apply(cube);
        assertThat(cube.isSolved(), is(true));
    }


    @Test
    public void undo() {
        Moves moves = randomMoves(3000);
        moves.apply(cube);
        assertThat(cube.isSolved(), is(false));
        moves.undo().apply(cube);
        assertThat(cube.isSolved(), is(true));
        randomMoves(1);
    }

    @Test
    public void concatenate() {
        Moves concatenate = Moves.concatenate(Moves.moves("to combine1", "f2"), Moves.moves("to combine2", "t2"));
        assertThat(concatenate.toString(), is("combined(to combine1,to combine2):F2T2"));


    }


    private void testPattern(Moves moves, String expectedCubeState, int expectedSpinCount, int expectedTurnCount) {
        assertThat(cube.getSpinCount(), is(0));
        assertThat(cube.getTurnCount(), is(0));

        assertThat(moves.undo().desc, is("undo pattern"));
        moves.apply(cube);

        assertThat(cube.getSpinCount(), is(expectedSpinCount));
        assertThat(cube.getTurnCount(), is(expectedTurnCount));

        assertThat(cube.getCubeState(), is(expectedCubeState));
        assertThat(cube.isSolved(), is(false));
        moves.undo().apply(cube);
        assertThat(cube.isSolved(), is(true));
    }


    private Moves randomMoves(int moveCount) {
        String random = "";
        for (int i = 0; i < moveCount; i++) {
            random += randomChar(Moves.FACE_CHARS);
            random += randomChar(Moves.DIRECTION_CHARS);
            if (new Random(3).nextInt() == 1) {
                random += "t" + randomChar(Moves.TURN_DIRECTION_CHARS);
            }
        }
        return Moves.moves("total random", random);
    }

    private char randomChar(char[] chars) {
        return chars[new Random().nextInt(chars.length)];
    }
}
