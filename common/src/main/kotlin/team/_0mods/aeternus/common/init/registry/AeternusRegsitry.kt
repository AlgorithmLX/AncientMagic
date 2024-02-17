/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.init.registry

import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.dimension.DimensionType
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.util.resloc
import team._0mods.aeternus.common.ModId
import java.util.function.Function
import java.util.function.Supplier

/* REGISTRY PRE INIT */
private val items = mutableMapOf<String, Function<Item.Properties, out Item>>()
private val blocks = mutableMapOf<String, Function<BlockBehaviour.Properties, out Block>>()

fun <T: Item> registerItem(
    id: String,
    obj: (Item.Properties) -> T,
    props: Item.Properties = Item.Properties()
): Supplier<T> {
    if (items.putIfAbsent(id, obj) != null)
        throw IllegalArgumentException("Some bad news... Duplicated id: ${resloc(ModId, id)}")
    return Supplier { obj(props) }
}

fun <T: Block> registerBlock(
    id: String,
    obj: (BlockBehaviour.Properties) -> T,
    props: BlockBehaviour.Properties = BlockBehaviour.Properties.of()
): Supplier<T> {
    if (blocks.putIfAbsent(id, obj) != null)
        throw IllegalArgumentException("Some bad news... Duplicated id: ${resloc(ModId, id)}")
    return Supplier { obj(props) }
}

object AeternusRegsitry {
    /* RESOURCE KEYS */
    val tab: ResourceKey<CreativeModeTab> = ResourceKey.create(Registries.CREATIVE_MODE_TAB, resloc(ModId, "aeternus"))
    /* RESOURCE REGISTRY KEYS */
    val researchRK: ResourceKey<Registry<Research>> = ResourceKey.createRegistryKey(resloc(ModId, "research"))

    /* ITEMS */
    val knowledgeBook = registerItem("knowledge_book", ::Item)

    /* BLOCKS */

    /* DIMENSION TYPES */
    val alTakeDim: ResourceKey<DimensionType> = ResourceKey.create(Registries.DIMENSION_TYPE, resloc(ModId, "altake"))

    @JvmStatic fun getBlocksForRegistry() = blocks
    @JvmStatic fun getItemsForRegistry() = items
}