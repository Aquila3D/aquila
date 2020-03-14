package org.aquila3d.core.math

import kotlin.math.PI

/**
 * Represents an angle in units of Radians.
 */
inline class Radians(val angle: Double) {

    fun toDegrees(): Degrees {
        return Degrees(angle * 180.0 / PI)
    }
}

/**
 * Represents an angle in units of Degrees.
 */
inline class Degrees(val angle: Double) {

    fun toRadians(): Radians {
        return Radians(angle * PI / 180.0)
    }
}