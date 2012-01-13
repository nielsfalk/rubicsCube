package de.n.cube.language.solution;

import de.n.cube.Cube;
import de.n.cube.language.MovesTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: niles
 * Date: 13.01.12
 * Time: 19:31
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
public class F2lSolutionTest {
    private F2lSolution solution;


    @Test
    public void solve() {
        assertThat(new F2lSolution(new Cube()).solved, is(true));
    }

    @Test
    public void solve2() {
        assertThat(new F2lSolution(MovesTest.SUPER_FLIP.apply(new Cube())).solved, is(false));
    }
}
