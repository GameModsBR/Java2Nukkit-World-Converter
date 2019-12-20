package br.com.gamemods.j2nwc.internal

import br.com.gamemods.nbtmanipulator.NbtByteArray
import br.com.gamemods.nbtmanipulator.NbtCompound
import br.com.gamemods.nbtmanipulator.NbtIntArray
import br.com.gamemods.nbtmanipulator.NbtList
import br.com.gamemods.regionmanipulator.Chunk
import br.com.gamemods.regionmanipulator.ChunkPos
import java.util.*

internal data class JavaPalette(
    var blockName: String,
    var properties: NbtCompound?
) {
    constructor(compound: NbtCompound): this (
        compound.getString("Name"),
        compound.getNullableCompound("Properties")
    )
}

internal data class JavaChunkSection(
    var chunkPos: ChunkPos,
    var yPos: Int,
    var blockStates: LongArray?,
    var palette: List<JavaPalette>?,
    var skyLight: ByteArray?
) {
    constructor(compound: NbtCompound, chunkPos: ChunkPos): this (
        chunkPos,
        compound.getByte("Y").toInt(),
        compound.getNullableLongArray("BlockStates"),
        compound.getNullableCompoundList("Palette")?.map { JavaPalette(it) },
        compound.getNullableByteArray("SkyLight")
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JavaChunkSection

        if (yPos != other.yPos) return false
        if (blockStates != null) {
            if (other.blockStates == null) return false
            if (!blockStates!!.contentEquals(other.blockStates!!)) return false
        } else if (other.blockStates != null) return false
        if (palette != other.palette) return false
        if (skyLight != null) {
            if (other.skyLight == null) return false
            if (!skyLight!!.contentEquals(other.skyLight!!)) return false
        } else if (other.skyLight != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = yPos
        result = 31 * result + (blockStates?.contentHashCode() ?: 0)
        result = 31 * result + palette.hashCode()
        result = 31 * result + (skyLight?.contentHashCode() ?: 0)
        return result
    }
}

internal data class JavaBiomeArea(
    val biome: Int,
    val x: Int,
    val y: Int,
    val z: Int,
    val lastX: Int,
    val lastY: Int,
    val lastZ: Int
)

internal data class JavaChunk(
    var lastModified: Date,
    var heightMap: NbtCompound?,
    var structures: NbtCompound,
    var entities: NbtList<NbtCompound>,
    var liquidsToBeTicked: NbtList<NbtList<*>>?,
    var liquidTicks: NbtList<*>?,
    var postProcessing: NbtList<NbtList<*>>?,
    var sections: Map<Int, JavaChunkSection>,
    var tileEntities: NbtList<NbtCompound>,
    var tileTicks: NbtList<*>?,
    var toBeTicked: NbtList<NbtList<*>>?,
    var inhabitedTime: Long,
    var isLightOn: Boolean,
    var lastUpdate: Date,
    var status: String,
    var position: ChunkPos,
    var biomes: List<JavaBiomeArea>?
) {
    constructor(chunk: Chunk): this(
        chunk.lastModified,
        chunk.level.getNullableCompound("Heightmaps"),
        chunk.level.getCompound("Structures"),
        chunk.level.getNullableCompoundList("Entities") ?: NbtList(),
        chunk.level.getNullableListOfList("LiquidsToBeTicked"),
        chunk.level.getNullableList("LiquidTicks"),
        chunk.level.getNullableListOfList("PostProcessing"),
        (chunk.level.getNullableCompoundList("Sections") ?: NbtList()).associate {
            it.getByte("Y").toInt() to JavaChunkSection(
                it,
                ChunkPos(chunk.level.getInt("xPos"), chunk.level.getInt("zPos"))
            )
        },
        chunk.level.getNullableCompoundList("TileEntities") ?: NbtList(),
        chunk.level.getNullableList("TileTicks"),
        chunk.level.getNullableListOfList("ToBeTicked"),
        chunk.level.getNullableLong("InhabitedTime") ?: 0,
        chunk.level.getNullableBooleanByte("isLightOn"),
        Date(chunk.level.getLong("LastUpdate") * 1000L),
        chunk.level.getString("Status"),
        ChunkPos(chunk.level.getInt("xPos"), chunk.level.getInt("zPos")),
        chunk.level["Biomes"].let { tag ->
            when (tag) {
                null -> null
                is NbtIntArray -> {
                    if (tag.value.size == 256) {
                        tag.value.mapIndexed { index, biome ->
                            val z = index and 0xF
                            val x = (index shr 4) and 0xF
                            JavaBiomeArea(biome and 0xFF, x, 0,z, x, 256, z)
                        }
                    } else {
                        val biomes = tag.value.asSequence().chunked(64).chunked(4).toList()
                        val areas = mutableListOf<JavaBiomeArea>()
                        biomes.forEachIndexed { x, xBiomes ->
                            xBiomes.forEachIndexed { z, zBiomes ->
                                zBiomes.forEachIndexed { y, biome ->
                                    areas += JavaBiomeArea(biome,
                                        x * 4, y * 4, z * 4,
                                        ((x+1) * 4) -1,
                                        ((y+1) * 4) -1,
                                        ((z+1) * 4) -1
                                    )
                                }
                            }
                        }
                        areas
                    }
                }
                is NbtByteArray -> {
                    tag.value.mapIndexed { index, biome ->
                        val z = index and 0xF
                        val x = (index shr 4) and 0xF
                        JavaBiomeArea(biome.toInt() and 0xFF, x, 0,z, x, 256, z)
                    }
                }
                else -> error("Unexpected type for Biomes tag: ${tag::class.java}")
            }
        }
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JavaChunk

        if (lastModified != other.lastModified) return false
        if (heightMap != other.heightMap) return false
        if (structures != other.structures) return false
        if (entities != other.entities) return false
        if (liquidsToBeTicked != other.liquidsToBeTicked) return false
        if (liquidTicks != other.liquidTicks) return false
        if (postProcessing != other.postProcessing) return false
        if (sections != other.sections) return false
        if (tileEntities != other.tileEntities) return false
        if (tileTicks != other.tileTicks) return false
        if (toBeTicked != other.toBeTicked) return false
        if (inhabitedTime != other.inhabitedTime) return false
        if (isLightOn != other.isLightOn) return false
        if (lastUpdate != other.lastUpdate) return false
        if (status != other.status) return false
        if (position != other.position) return false
        if (biomes != other.biomes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = lastModified.hashCode()
        result = 31 * result + heightMap.hashCode()
        result = 31 * result + structures.hashCode()
        result = 31 * result + entities.hashCode()
        result = 31 * result + liquidsToBeTicked.hashCode()
        result = 31 * result + liquidTicks.hashCode()
        result = 31 * result + postProcessing.hashCode()
        result = 31 * result + sections.hashCode()
        result = 31 * result + tileEntities.hashCode()
        result = 31 * result + tileTicks.hashCode()
        result = 31 * result + toBeTicked.hashCode()
        result = 31 * result + inhabitedTime.hashCode()
        result = 31 * result + isLightOn.hashCode()
        result = 31 * result + lastUpdate.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + position.hashCode()
        result = 31 * result + biomes.hashCode()
        return result
    }
}
