@file:JsModule("nvk")
@file:JsNonModule
package nvk

import kotlin.js.Json

external class VulkanWindow(config: Json) {

    var width: Int
    var height: Int
    var title: String

}
