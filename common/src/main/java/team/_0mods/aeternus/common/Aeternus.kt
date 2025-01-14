/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

@file:JvmName("Aeternus")

package team._0mods.aeternus.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import team._0mods.aeternus.api.config.ConfigInstance
import team._0mods.aeternus.api.config.loadConfig
import team._0mods.aeternus.api.config.prefix
import team._0mods.aeternus.api.util.debugIfEnabled
import team._0mods.aeternus.common.init.config.AeternusClientConfig
import team._0mods.aeternus.common.init.config.AeternusCommonConfig
import team._0mods.aeternus.common.init.registry.AeternusRegistry

const val ModId = "aeternus"
const val ModName = "Aeternus"

@JvmField val LOGGER: Logger = LoggerFactory.getLogger(ModName) //const

lateinit var commonConfig: ConfigInstance<AeternusCommonConfig> private set
lateinit var clientConfig: ConfigInstance<AeternusClientConfig> private set

fun commonInit() {
    commonConfig = loadConfig(AeternusCommonConfig.defaultConfig, prefix("common"))

    LOGGER.debugIfEnabled("DEBUG MODE IS ACTIVATED")

    AeternusRegistry.init()
}

fun clientInit() {
    clientConfig = loadConfig(AeternusClientConfig.defaultConfig, prefix("client"))
}
