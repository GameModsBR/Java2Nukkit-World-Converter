# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
Click the link above to see the future.

## [3.0.0] - 2020-01-25
### Added
- [#84] Mappings for Minecraft Java Edition 1.15 items and blocks

### Changed
- `WorldConverter.convert()` now throws `IOException` in java **(breaking change)**
- Updated [Region-Manipulator to `2.0.0`][Region 2.0.0] from `1.1.0` **(breaking change)**
- [#84] The source world must be optimized by Minecraft 1.15.2 now
- If the converter finds an unmapped block 
  it will now be converted as 248:0 (minecraft:info_update) instead of 1:15 (stone with invalid data)
- sweet_berries and shield are no longer replaced, they are obtainable in Nukkit 1.X now
- Honey bottle and all banner patterns are no longer replaced, they aren't obtainable in Nukkit 1.X but 
the server won't break if you have these items in your inventory anymore. 

### Fixes
- [#78] Error parsing sign text: IllegalStateException: component must not be null
- [#79] Region files with axis number (X or Z) higher then 9 being ignored
- [#87] Double chests facing north and east have the contents swapped
- Internal mappings for barrel, grindstone, lectern, stonecutter, bell, campfire, bee_nest and beehive.
  Does not affects the output because they aren't supported by Nukkit 1.X, so they were all replaced by other blocks.

## [2.0.1] - 2019-06-18
### Fixes
- [#72] Some redstone wire states gets converted to other block improperly
- [#73] Fixes the conversion for petrified oak slabs
- [#74] Fixes `large_fern` becoming `sunflower` in inventories
- [#75] Fixes dragon heads becomes skeleton skulls

## [2.0.0] - 2019-06-02
### Added
- `WorldConveter.regions` to filter regions using the Region-Manipulator's `RegionPos`.
- Type alias `RegionPosition` to help the conversion from the deprecated `RegionPos` to Region-Manipulator's `RegionPos`
- `RegionPos.toRegionManipulator()` to convert the object to the equivalent's Region-Manipulator object.
- `--keep-custom-heads` argument to keep converting the player heads with custom skins as regular player heads.
- `WorldConverter.skipSkinHeads` if player heads with custom skins as regular player heads should be skipped.

### Changed
- Updated [Region-Manipulator to `1.1.0`][Region 1.1.0] from `1.0.0`.
- Deprecated `RegionPos`. Users should use the one provided by Region-Manipulator.
- Deprecated `WorldConverter.regionFilter`. Users should use `WorldConverter.regions` instead.
- Unmapped block states will now log a warning
- Colored signs will be colored using text color instead of dye color. Some colors will be a little different and all
them will be very bright. 
- [#54] Player heads with custom skins will now be skipped by default. This can be changed using `--keep-custom-heads` or `WorldConverter.skipSkinHeads` 
- [#59] `smooth_red_sandstone_slab` is now replaced with `red_sandstone_slab` instead of `acacia_slab`.  
- [#60] `red_nether_brick_stairs` is now replaced with `nether_brick_stairs` instead of `brick_stairs`.  
- [#63] `dark_prismarine_stairs` is now replaced with `stone_brick_stairs` instead of `cobblestone_stairs`.  

### Fixed
- [#38] The entire path is shown in Usage at --help
- [#39] HeightMap is not converted properly
- [#40] Biomes are not converted properly
- Exceptions when converting optimized 1.8.8 to Nukkit. ([#41], [#42], [#43], [#44], [#45], [#46], [#47], [#48], [#66], [#67])
- [#49] Nukkit crash due to an illegal conversion of generatorOption settings
- [#50] waterloggable block states migrated from optimized 1.8.8 world becomes stone.
- [#51] noteblocks migrated from optimized 1.8.8 world becomes stone.
- [#52] generatorOptions conversion for flat worlds
- [#53] The trapdoor placement and open/close state changes after conversion.
- [#55] Signs are empty after the conversion.
- [#56] The buttons placement and pressed state changes after conversion.
- [#57] Stained glasses are becoming invisible bedrock.
- [#58] `prismarine_brick_slab` and `dark_prismarine_slab` are swapped.
- [#61], [#62] `nether_brick_wall` and `end_stone_brick_wall` are swapped.
- [#64] Empty chunks being recreated.
- [#68] Banners are not being completely converted
- [#69], [#71] Incorrect block data manipulation by the item frame conversion causes nearby blocks to change.
- [#70] Unmapped biome with id -107 (and other negative values)

## [1.0.0] - 2019-05-25
### Added
- Support for Minecraft 1.14.1 to Nukkit [ccd5d78](https://github.com/NukkitX/Nukkit/tree/ccd5d78aee06d6097327dc825e32d10482c79043)
- Conversion for block states to block/data
- Conversion for items in inventories
- Conversion for paintings
- Conversion for item frames
- Conversion for dropped items
- Conversion for experience orbs
- Conversion for falling blocks
- Conversion for primed TNT
- Small API for usage as library
- Option specify the region files that will be converted

[Unreleased]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/compare/v3.0.0...HEAD
[3.0.0]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/compare/v2.0.1..v3.0.0
[2.0.1]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/compare/v2.0.0..v2.0.1
[2.0.0]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/compare/v1.0.0..v2.0.0
[1.0.0]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/compare/a8f41900b32740648752ff214581eb8da0f928f6...v1.0.0

[#38]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/38
[#39]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/39
[#40]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/40
[#41]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/41
[#42]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/42
[#43]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/43
[#44]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/44
[#45]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/45
[#46]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/46
[#47]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/47
[#48]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/48
[#49]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/49
[#50]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/50
[#51]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/51
[#52]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/52
[#53]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/53
[#54]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/54
[#55]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/55
[#56]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/56
[#57]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/57
[#58]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/58
[#59]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/59
[#60]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/60
[#61]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/61
[#62]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/62
[#63]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/63
[#64]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/64
[#66]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/66
[#67]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/67
[#68]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/68
[#69]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/69
[#70]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/70
[#71]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/71

[#72]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/72
[#73]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/73
[#74]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/74
[#75]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/75

[#78]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/78
[#79]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/79
[#84]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/84
[#87]: https://github.com/GameModsBR/Java2Nukkit-World-Converter/issues/87


[Region 2.0.0]: https://gamemodsbr.github.io/Region-Manipulator/CHANGELOG.html#200---2020-01-24
[Region 1.1.0]: https://gamemodsbr.github.io/Region-Manipulator/CHANGELOG.html#110---2019-06-02
