package mod.coda.wcfarmlife.world.features;

import com.mojang.serialization.Codec;
import mod.coda.wcfarmlife.WCFarmLife;
import mod.coda.wcfarmlife.entities.GalliraptorEntity;
import mod.coda.wcfarmlife.init.WCFarmLifeBlocks;
import mod.coda.wcfarmlife.init.WCFarmLifeEntities;
import mod.coda.wcfarmlife.init.WCFarmLifeStructures;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class GreenhouseStructure extends Structure<NoFeatureConfig> {

    public GreenhouseStructure(Codec<NoFeatureConfig> p_i231977_1_) {
        super(p_i231977_1_);
    }

    @Override
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return GreenhouseStructure.Start::new;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGen, BiomeProvider biomeSource, long seed, SharedSeedRandom rand, int chunkPosX, int chunkPosZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        return chunkPosX > 4 && chunkPosZ > 4;
    }

    public static class Start extends StructureStart<NoFeatureConfig>  {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config) {
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;
            int surfaceY = Math.max(generator.getNoiseHeightMinusOne(x + 12, z + 12, Heightmap.Type.WORLD_SURFACE_WG), generator.getGroundHeight());
            BlockPos blockpos = new BlockPos(x, surfaceY, z);
            Piece.start(templateManagerIn, blockpos, rotation, this.components, this.rand);
            this.recalculateStructureSize();
        }
    }

    public static class Piece extends TemplateStructurePiece {
        private ResourceLocation resourceLocation;
        private Rotation rotation;

        public Piece(TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
            super(WCFarmLifeStructures.GREENHOUSE_PIECE, 0);
            this.resourceLocation = resourceLocationIn;
            this.templatePosition = pos;
            this.rotation = rotationIn;
            this.setupPiece(templateManagerIn);
        }

        public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
            super(WCFarmLifeStructures.GREENHOUSE_PIECE, tagCompound);
            this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.setupPiece(templateManagerIn);
        }

        public static void start(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
            int x = pos.getX();
            int z = pos.getZ();
            BlockPos rotationOffSet = new BlockPos(0, 2, 0).rotate(rotation);
            BlockPos blockpos = rotationOffSet.add(x, pos.getY(), z);
            pieceList.add(new Piece(templateManager, new ResourceLocation(WCFarmLife.MOD_ID, "greenhouse"), blockpos, rotation));
        }

        private void setupPiece(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
            this.setup(template, this.templatePosition, placementsettings);
        }

        @Override
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Template", this.resourceLocation.toString());
            tagCompound.putString("Rot", this.rotation.name());
        }

        @Override
        public boolean func_230383_a_(ISeedReader seedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPos, BlockPos pos) {
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
            BlockPos blockpos = BlockPos.ZERO;
            this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(-blockpos.getX(), 0, -blockpos.getZ())));
            return super.func_230383_a_(seedReader, structureManager, chunkGenerator, randomIn, structureBoundingBoxIn, chunkPos, pos);
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
            if ("galliraptor".equals(function)) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                GalliraptorEntity entity = WCFarmLifeEntities.GALLIRAPTOR.get().create(worldIn.getWorld());
                if (entity != null) {
                    if (rand.nextInt(5) == 0) entity.setGrowingAge(-24000);
                    entity.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    entity.setVariant(rand.nextInt(5));
                    entity.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(pos), SpawnReason.STRUCTURE, null, null);
                    worldIn.addEntity(entity);
                }
            }
            if ("flower".equals(function)) {
                if (rand.nextFloat() < 0.2F) worldIn.setBlockState(pos, WCFarmLifeBlocks.PEACOCK_BURST_POPPY.get().getDefaultState(), 2);
                else if (rand.nextFloat() < 0.2F) worldIn.setBlockState(pos, WCFarmLifeBlocks.ELECTRIC_BURST_POPPY.get().getDefaultState(), 2);
                else if (rand.nextFloat() < 0.2F) worldIn.setBlockState(pos, WCFarmLifeBlocks.FANCY_BURST_POPPY.get().getDefaultState(), 2);
                else if (rand.nextFloat() < 0.2F) worldIn.setBlockState(pos, WCFarmLifeBlocks.OLIVE_BURST_POPPY.get().getDefaultState(), 2);
                else if (rand.nextFloat() < 0.2F) worldIn.setBlockState(pos, WCFarmLifeBlocks.RUSTY_BURST_POPPY.get().getDefaultState(), 2);
                else if (rand.nextFloat() < 0.2F) worldIn.setBlockState(pos, WCFarmLifeBlocks.SUNSTREAK_BURST_POPPY.get().getDefaultState(), 2);
                else worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            }
        }
    }
}