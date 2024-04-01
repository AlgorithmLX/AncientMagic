/*
 * MIT License
 *
 * Copyright (c) 2024. AlgorithmLX & 0mods.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package team._0mods.multilib.config

import kotlin.reflect.KProperty

class ConfigSettings {
    var fileName: String = "config"
}

abstract class DelegatedConfig<T: BaseValue<*>>(private var value: (ConfigSettings) -> T) {
    private val config by lazy { ConfigSettings() }

    operator fun getValue(thisRef: String, property: KProperty<*>) = value(config)

    operator fun setValue(thisRef: String, property: KProperty<*>, value: T) {
        this.value = { value }
    }
}

class ConfigDelegate<T: BaseValue<*>>(val cfg: (ConfigSettings) -> DelegatedConfig<T>) : (ConfigSettings) -> DelegatedConfig<T> by cfg
