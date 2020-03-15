package org.aquila3d.core.math

import kotlin.math.PI

/**
 * Represents an angle in units of Radians.
 */
inline class Radians(val angle: Double) {

    fun toDegrees(): Degrees {
        return Degrees(angle * 180.0 / PI)
    }

    operator fun plus(rhs: Radians): Radians {
        return Radians(angle + rhs.angle)
    }

    operator fun minus(rhs: Radians): Radians {
        return Radians(angle - rhs.angle)
    }
}