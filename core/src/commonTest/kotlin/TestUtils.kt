import kotlin.math.abs
import kotlin.test.assertTrue

fun assertEqualsEpsilon(expected: Double, actual: Double, message: String) {
    assertEqualsEpsilon(expected, actual, null, message)
}

fun assertEqualsEpsilon(expected: Double, actual: Double, epsilon: Double?, message: String) {
    assertTrue(abs(expected - actual) <= (epsilon ?: 1e-12), "$message. Expected: $expected Actual: $actual")
}