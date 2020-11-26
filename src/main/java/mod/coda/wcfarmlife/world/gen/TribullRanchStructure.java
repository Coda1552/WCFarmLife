package mod.coda.wcfarmlife.world.gen;

import mod.coda.wcfarmlife.WCFarmLife;
import net.minecraft.block.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class TribullRanchStructure extends Structure<NoFeatureConfig> {
    public TribullRanchStructure() {
        super(NoFeatureConfig.field_236558_a_);
    }

    public boolean canBeGenerated(ISeedReader reader, ChunkGenerator generator, Random rand, int chunkX, int chunkZ, Biome biome) {
        ChunkPos pos = this.getStartPositionForPosition(reader, generator, rand, chunkX, chunkZ, 0, 0);
        if (chunkX == pos.x && chunkZ == pos.z) {
            if (reader.getBlockState(pos.asBlockPos().down()).getBlock() == Blocks.GRASS_BLOCK); {
                return true;
            }
        }
        return false;
    }

    protected ChunkPos getStartPositionForPosition(ISeedReader reader, ChunkGenerator generator, Random rand, int x, int z, int offsetX, int offsetZ) {
        int maxDistance = 15;
        int minDistance = 4;

        int xTemp = x + maxDistance * offsetX;
        int ztemp = z + maxDistance * offsetZ;
        int xTemp2 = xTemp < 0 ? xTemp - maxDistance + 1 : xTemp;
        int zTemp2 = ztemp < 0 ? ztemp - maxDistance + 1 : ztemp;
        int validChunkX = xTemp2 / maxDistance;
        int validChunkZ = zTemp2 / maxDistance;

        ((SharedSeedRandom) rand).setLargeFeatureSeedWithSalt(reader.getSeed(), validChunkX, validChunkZ, this.getSeedModifier());
        validChunkX = validChunkX * maxDistance;
        validChunkZ = validChunkZ * maxDistance;
        validChunkX = validChunkX + rand.nextInt(maxDistance - minDistance);
        validChunkZ = validChunkZ + rand.nextInt(maxDistance - minDistance);

        return new ChunkPos(validChunkX, validChunkZ);
    }

    protected int getSeedModifier() {
        return 1235732158;
    }

    @Override
    public IStartFactory getStartFactory() {
        return Start::new;
    }

    @Override
    public String getStructureName() {
        return WCFarmLife.MOD_ID + ":tribull_ranch";
    }

    public static class Start extends StructureStart {
        public Start(Structure<?> structure, int chunkX, int cunkZ, MutableBoundingBox boundingBox, int reference, long seed) {
            super(structure, chunkX, cunkZ, boundingBox, reference, seed);
        }

        @Override
        public void func_230364_a_(DynamicRegistries p_230364_1_, ChunkGenerator generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome p_230364_6_, IFeatureConfig p_230364_7_) {
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];

            int x = (chunkX << 4) + 7;
            int z = (chunkX << 4) + 7;
            int y = generator.getHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
            BlockPos pos = new BlockPos(x, y, z);

            TribullRanchPieces.start(templateManagerIn, pos, rotation, this.components, this.rand);

            this.recalculateStructureSize();
        }
    }
}
