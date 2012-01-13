package de.n.cube;

/**
 * User: niles
 * Date: 13.01.12
 * Time: 01:39
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
public enum SpinOperations {
    //ordinal matters !
    clockwise, twoTimes, anticlockwise;

    public static SpinOperations forLang(char direction) {
        switch (direction) {
            case '+':
                return clockwise;
            case '2':
                return twoTimes;
            case '-':
                return anticlockwise;
        }
        throw new IllegalArgumentException("cant convert " + direction);
    }
}
