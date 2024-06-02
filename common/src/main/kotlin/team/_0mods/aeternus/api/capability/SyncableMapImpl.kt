/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.capability

import com.google.common.reflect.TypeToken
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer
import net.minecraft.nbt.Tag
import team._0mods.aeternus.api.util.nbt.NBTFormat

class SyncableMapImpl<K : Any, V : Any>(
    val map: MutableMap<K, V>,
    keyType: Class<K>? = null,
    valueType: Class<V>? = null,
    val syncMethod: () -> Unit = {},
) : MutableMap<K, V> {
    companion object {
        inline fun <reified K : Any, reified V : Any> create(
            map: MutableMap<K, V> = HashMap(),
            noinline syncMethod: () -> Unit = {},
        ) = SyncableMapImpl(map, K::class.java, V::class.java, syncMethod)
    }

    val serializer = SyncableMapSerializer(
        keyType ?: throw IllegalStateException("Key type must be not null"),
        valueType ?: throw IllegalStateException("Value type must be not null"),
    )

    override val entries get() = map.entries
    override val keys get() = map.keys
    override val size get() = map.size
    override val values get() = map.values


    override fun clear() {
        map.clear()
        syncMethod()
    }

    override fun isEmpty() = map.isEmpty()

    override fun remove(key: K) = map.remove(key).apply { syncMethod() }

    override fun putAll(from: Map<out K, V>) {
        map.putAll(from)
        syncMethod()
    }

    override fun put(key: K, value: V): V? {
        return map.put(key, value).apply {
            syncMethod()
        }
    }

    override fun get(key: K) = map[key]

    override fun containsValue(value: V) = map.containsValue(value)

    override fun containsKey(key: K) = map.containsKey(key)
    fun serializeNBT(): Tag {
        return NBTFormat.serialize(serializer, this)
    }

    fun deserializeNBT(nbt: Tag) {
        val map = NBTFormat.deserialize(serializer, nbt)
        this.map.clear()
        this.map.putAll(map)
    }

    override fun toString(): String {
        return map.toString()
    }
}

class SyncableMapSerializer<K : Any, V : Any>(val keyType: Class<K>, val valueType: Class<V>) :
    KSerializer<SyncableMapImpl<K, V>> {
    val keySerializer = NBTFormat.serializersModule.serializer(TypeToken.of(keyType).type) as KSerializer<K>
    val valueSerializer = NBTFormat.serializersModule.serializer(TypeToken.of(valueType).type) as KSerializer<V>

    private val delegatedSerializer = MapSerializer(keySerializer, valueSerializer)

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor = SerialDescriptor("map_serializer", delegatedSerializer.descriptor)

    override fun serialize(encoder: Encoder, value: SyncableMapImpl<K, V>) {
        val l = value.toMap()
        encoder.encodeSerializableValue(delegatedSerializer, l)
    }

    override fun deserialize(decoder: Decoder): SyncableMapImpl<K, V> {
        val l = decoder.decodeSerializableValue(delegatedSerializer)
        return SyncableMapImpl(l.toMutableMap(), keyType, valueType)
    }
}

fun main() {
    val map = SyncableMapImpl.create<String, Long>(HashMap()) { println("Update") }
    map["1"] = 1
    map["2"] = 20L
    println(map)

    val tag = map.serializeNBT()

    println(tag)
    val map2 = SyncableMapImpl.create<String, Long>()
    map2.deserializeNBT(tag)

    println(map2)
}