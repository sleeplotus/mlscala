/*
 *  Copyright 2016 NAVER Corp.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.vrv.pinpoint.example.web.vo.scatter;

/**
 * @author Taejin Koo
 */
public class Coordinates {

    private final long x;
    private final long y;

    public Coordinates(long x, long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        long result = x;
        result = 31 * result + y;
        return (int)(result^(result>>>32));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Coordinates that = (Coordinates) o;
        return x == that.getX() && y == that.getY();
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" + "x=" + x + ", y=" + y + '}';
    }
}