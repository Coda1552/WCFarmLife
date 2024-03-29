package mod.coda.farmlife.entities;

import mod.coda.farmlife.init.FlLifeEntities;
import mod.coda.farmlife.init.FLItems;
import mod.coda.farmlife.init.FLSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class DomesticTribullEntity extends AnimalEntity {

    public DomesticTribullEntity(EntityType<? extends DomesticTribullEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.2F);
    }

    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.BEETROOT;
    }

    protected SoundEvent getAmbientSound() {
        return FLSounds.DOMESTIC_TRIBULL_AMBIENT.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FLSounds.DOMESTIC_TRIBULL_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return FLSounds.DOMESTIC_TRIBULL_DEATH.get();
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    @Nullable
    @Override
    public DomesticTribullEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
        return FlLifeEntities.DOMESTIC_TRIBULL.get().create(this.level);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(FLItems.DOMESTIC_TRIBULL_SPAWN_EGG.get());
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isBaby() ? 0.5F : 1.0F;
    }
}
