package org.aquila3d.core.input

enum class EventSource {
    KEY, CLICK, TOUCH
}

enum class Event {
    DOWN, UP, REPEAT, MOVE
}

data class InputEvent(val source: EventSource, val event: Event, val eventData: Any) {}