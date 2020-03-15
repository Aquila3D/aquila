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

/**
 * Represents an angle in units of Degrees.
 */
inline class Degrees(val angle: Double) {

    fun toRadians(): Radians {
        return Radians(angle * PI / 180.0)
    }

    operator fun plus(rhs: Degrees): Degrees {
        return Degrees(angle + rhs.angle)
    }

    operator fun minus(rhs: Degrees): Degrees {
        return Degrees(angle - rhs.angle)
    }
}