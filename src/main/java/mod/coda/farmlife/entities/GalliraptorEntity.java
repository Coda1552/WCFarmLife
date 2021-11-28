package mod.coda.farmlife.entities;

import mod.coda.farmlife.init.FlLifeEntities;
import mod.coda.farmlife.init.FLSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class GalliraptorEntity extends AnimalEntity {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.defineId(GalliraptorEntity.class, DataSerializers.INT);
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.MELON, Items.MELON_SEEDS);
    public int timeUntilNextEgg = this.random.nextInt(8000) + 8000;


    public GalliraptorEntity(EntityType<? extends GalliraptorEntity> type, World worldIn) {
        super(type, worldIn);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, FoxEntity.class, false));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, OcelotEntity.class, false));
    }

    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();

            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.hurt(source, amount);
        }
    }

    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
            if (flag) {
                this.doEnchantDamageEffects(this, entityIn);
            }

            return flag;
    }


    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.85F : 0.72F;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.3F)
                .add(Attributes.ATTACK_DAMAGE, 1.5f);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && --this.timeUntilNextEgg <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(Items.EGG);
            this.spawnAtLocation(Items.EGG);
            this.timeUntilNextEgg = this.random.nextInt(8000) + 8000;
        }
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return FLSounds.GALLIRAPTOR_AMBIENT.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FLSounds.GALLIRAPTOR_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return FLSounds.GALLIRAPTOR_DEATH.get();
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }

    public GalliraptorEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
        GalliraptorEntity entity = FlLifeEntities.GALLIRAPTOR.get().create(this.level);
        int i = this.getVariant();
        if (this.random.nextInt(5) != 0) {
            if (ageable instanceof GalliraptorEntity && this.random.nextBoolean()) {
                i = ((GalliraptorEntity)ageable).getVariant();
            }
        }
        entity.setVariant(i);
        return entity;
    }

    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.MELON_SLICE || stack.getItem() == Items.MELON_SEEDS;
    }

    protected int getExperienceReward(PlayerEntity player) {
        return super.getExperienceReward(player);
    }

    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("EggLayTime")) {
            this.timeUntilNextEgg = compound.getInt("EggLayTime");
        }
        setVariant(compound.getInt("Variant"));
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("EggLayTime", this.timeUntilNextEgg);
        compound.putInt("Variant", getVariant());
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        setVariant(random.nextInt(8));
        return spawnDataIn;
    }
}