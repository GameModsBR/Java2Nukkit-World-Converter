package br.com.gamemods.j2nwc.internal

import br.com.gamemods.j2nwc.WorldConverter
import br.com.gamemods.nbtmanipulator.*
import br.com.gamemods.regionmanipulator.ChunkPos

internal data class NukkitChunkSection(
    var yPos: Int,
    var blockLight: ByteArray,
    var blocks: ByteArray,
    var blocksData: ByteArray,
    var skyLight: ByteArray,
    var targetType: WorldConverter.TargetType,
    var version: Int = 0,
    var blocksExtra: ByteArray? = null,
    var blocksDataExtra: ByteArray? = null,
    var waterlogged: BooleanArray? = null
) {
    fun toNbt(): NbtCompound {
        val nbt = NbtCompound(
            "Y" to NbtByte(yPos.toByte()),
            "BlockLight" to NbtByteArray(blockLight),
            "SkyLight" to NbtByteArray(skyLight)
        )

        if (version == 0 || version == 1) {
            nbt["Blocks"] = blocks
            nbt["Data"] = blocksData
            if (version == 1 && targetType == WorldConverter.TargetType.POWER_NUKKIT) {
                blocksExtra?.let { nbt["BlocksExtra"] = it }
                blocksDataExtra?.let { nbt["DataExtra"] = it }
            }
        } else if (version == 7 && targetType == WorldConverter.TargetType.POWER_NUKKIT) {
            val layer0 = NbtCompound(
                "Blocks" to NbtByteArray(blocks),
                "Data" to NbtByteArray(blocksData)
            )
            blocksExtra?.let { layer0["BlocksExtra"] = it }
            blocksDataExtra?.let { layer0["DataExtra"] = it }
            val storage = NbtList(layer0)
            waterlogged?.let {  waterlogged ->
                val layer1 = NbtCompound(
                    "Blocks" to NbtByteArray(ByteArray(waterlogged.size) { if(waterlogged[it]) 8 else 0 })
                )
                storage += layer1
            }
            nbt["Storage"] = storage
        } else {
            error("Unexpected chunk section version $version with target type $targetType")
        }

        if (version != 0) {
            nbt["Version"] = version
        }

        return nbt
    }

    @Suppress("DuplicatedCode")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NukkitChunkSection

        if (yPos != other.yPos) return false
        if (!blockLight.contentEquals(other.blockLight)) return false
        if (!blocks.contentEquals(other.blocks)) return false
        if (!blocksData.contentEquals(other.blocksData)) return false
        if (!skyLight.contentEquals(other.skyLight)) return false
        if (targetType != other.targetType) return false
        if (version != other.version) return false
        val blocksExtra1 = blocksExtra
        val blocksExtra2 = other.blocksExtra
        if (blocksExtra1 != null) {
            if (blocksExtra2 == null) return false
            if (!blocksExtra1.contentEquals(blocksExtra2)) return false
        } else if (blocksExtra2 != null) return false
        val blocksDataExtra1 = blocksDataExtra
        val blocksDataExtra2 = other.blocksDataExtra
        if (blocksDataExtra1 != null) {
            if (blocksDataExtra2 == null) return false
            if (!blocksDataExtra1.contentEquals(blocksDataExtra2)) return false
        } else if (blocksDataExtra2 != null) return false
        val waterlogged1 = waterlogged
        val waterlogged2 = other.waterlogged
        if (waterlogged1 != null) {
            if (waterlogged2 == null) return false
            if (!waterlogged1.contentEquals(waterlogged2)) return false
        } else if (waterlogged2 != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = yPos
        result = 31 * result + blockLight.contentHashCode()
        result = 31 * result + blocks.contentHashCode()
        result = 31 * result + blocksData.contentHashCode()
        result = 31 * result + skyLight.contentHashCode()
        result = 31 * result + targetType.hashCode()
        result = 31 * result + version
        result = 31 * result + (blocksExtra?.contentHashCode() ?: 0)
        result = 31 * result + (blocksDataExtra?.contentHashCode() ?: 0)
        result = 31 * result + (waterlogged?.contentHashCode() ?: 0)
        return result
    }


}

internal data class NukkitChunk(
    var entities: NbtList<NbtCompound>,
    var sections: Map<Int, NukkitChunkSection>,
    var tileEntities: NbtList<NbtCompound>,
    var inhabitedTime: Long,
    var lightPopulated: Boolean,
    var terrainGenerated: Boolean,
    var terrainPopulated: Boolean,
    var v: Byte,
    var position: ChunkPos,
    var biomes: ByteArray,
    var extraData: ByteArray,
    var heightMap: IntArray
) {
    fun toNbt(): NbtFile {
        val level = NbtCompound()
        level["Entities"] = entities
        level["Sections"] = NbtList(sections.values.map { it.toNbt() })
        level["TileEntities"] = tileEntities
        level["InhabitedTime"] = inhabitedTime
        level["LightPopulated"] = lightPopulated
        level["TerrainPopulated"] = terrainPopulated
        level["V"] = v
        level["xPos"] = position.xPos
        level["zPos"] = position.zPos
        level["Biomes"] = biomes
        level["ExtraData"] = extraData
        level["HeightMap"] = heightMap

        return NbtFile("", NbtCompound("Level" to level))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NukkitChunk

        if (entities != other.entities) return false
        if (sections != other.sections) return false
        if (tileEntities != other.tileEntities) return false
        if (inhabitedTime != other.inhabitedTime) return false
        if (lightPopulated != other.lightPopulated) return false
        if (terrainGenerated != other.terrainGenerated) return false
        if (terrainPopulated != other.terrainPopulated) return false
        if (v != other.v) return false
        if (position != other.position) return false
        if (!biomes.contentEquals(other.biomes)) return false
        if (!extraData.contentEquals(other.extraData)) return false
        if (!heightMap.contentEquals(other.heightMap)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = entities.hashCode()
        result = 31 * result + sections.hashCode()
        result = 31 * result + tileEntities.hashCode()
        result = 31 * result + inhabitedTime.hashCode()
        result = 31 * result + lightPopulated.hashCode()
        result = 31 * result + terrainGenerated.hashCode()
        result = 31 * result + terrainPopulated.hashCode()
        result = 31 * result + v
        result = 31 * result + position.hashCode()
        result = 31 * result + biomes.contentHashCode()
        result = 31 * result + extraData.contentHashCode()
        result = 31 * result + heightMap.contentHashCode()
        return result
    }
}
