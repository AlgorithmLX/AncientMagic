/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.client.models.animations.interpol

import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector4f
import team._0mods.aeternus.api.client.models.GltfTree
import java.util.*
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sin
import kotlin.math.sqrt

abstract class Interpolator<T>(val keys: FloatArray, val values: Array<T>) {
    abstract fun compute(time: Float): T

    val maxTime = keys.last()

    val Float.animIndex: Int
        get() {
            val index = Arrays.binarySearch(keys, this)

            return if (index >= 0) index
            else 0.coerceAtLeast(-index - 2)
        }

    lateinit var node: GltfTree.Node
}

class Vec3Step(keys: FloatArray, values: Array<Vector3f>) : Interpolator<Vector3f>(keys, values) {
    override fun compute(time: Float): Vector3f = values[time.animIndex]
}

class QuatStep(keys: FloatArray, values: Array<Quaternionf>) : Interpolator<Quaternionf>(keys, values) {
    override fun compute(time: Float): Quaternionf = values[time.animIndex]
}

class LinearSingle(keys: FloatArray, values: Array<FloatArray>) : Interpolator<FloatArray>(keys, values) {
    override fun compute(time: Float): FloatArray {
        if (time <= keys.first() || keys.size == 1) return values.first()
        else if (time >= keys.last()) return values.last()
        else {
            val previousIndex = time.animIndex
            val nextIndex = previousIndex + 1
            val local = time - keys[previousIndex]
            val delta = keys[nextIndex] - keys[previousIndex]
            val alpha = local / delta
            val previousValue = values[previousIndex]
            val nextValue = values[nextIndex]
            val interpolatedValue =
                FloatArray(previousValue.size) { i -> previousValue[i] + (nextValue[i] - previousValue[i]) * alpha }
            return interpolatedValue
        }
    }

}

class Linear(keys: FloatArray, values: Array<Vector3f>) : Interpolator<Vector3f>(keys, values) {
    override fun compute(time: Float): Vector3f {
        if (time <= keys.first() || keys.size == 1) return values.first()
        else if (time >= keys.last()) return values.last()
        else {
            val previousIndex = time.animIndex
            val nextIndex = previousIndex + 1
            val local = time - keys[previousIndex]
            val delta = keys[nextIndex] - keys[previousIndex]
            val alpha = local / delta
            val previousPoint = Vector3f(values[previousIndex])
            val nextPoint = values[nextIndex]
            return previousPoint.lerp(nextPoint, alpha)
        }
    }
}

class SphericalLinear(keys: FloatArray, values: Array<Quaternionf>) : Interpolator<Quaternionf>(keys, values) {
    override fun compute(time: Float): Quaternionf {
        if (time <= keys.first() || keys.size == 1) return values.first()
        else if (time >= keys.last()) return values.last()
        else {
            val previousIndex = time.animIndex
            val nextIndex = previousIndex + 1

            val local = time - keys[previousIndex]
            val delta = keys[nextIndex] - keys[previousIndex]
            val alpha = local / delta

            val previousPoint = Quaternionf(values[previousIndex])
            val nextPoint = values[nextIndex]

            return previousPoint.slerp(nextPoint, alpha)
        }
    }

}

fun Quaternionf.sphericalLerp(target: Quaternionf, alpha: Float) {
    val cosom = Math.fma(x(), target.x(), Math.fma(y(), target.y(), Math.fma(z(), target.z(), w() * target.w())))
    val absCosom = abs(cosom)
    val scale0: Float
    var scale1: Float
    if (1.0f - absCosom > 1E-6f) {
        val sinSqr = 1.0f - absCosom * absCosom
        val sinom = 1.0f / sqrt(sinSqr)
        val omega = atan2(sinSqr * sinom, absCosom)
        scale0 = (sin((1.0f - alpha) * omega) * sinom)
        scale1 = (sin(alpha * omega) * sinom)
    } else {
        scale0 = 1.0f - alpha
        scale1 = alpha
    }
    scale1 = if (cosom >= 0.0f) scale1 else -scale1
    set(
        Math.fma(scale0, x(), scale1 * target.w()),
        Math.fma(scale0, y(), scale1 * target.z()),
        Math.fma(scale0, z(), scale1 * target.y()),
        Math.fma(scale0, w(), scale1 * target.x())
    )
}