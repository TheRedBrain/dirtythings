package com.github.theredbrain.dirtyThings.mixin.entity.mob;

import com.github.theredbrain.dirtyThings.entity.ai.goal.GroundFoodMateGoal;
import com.github.theredbrain.dirtyThings.entity.ai.goal.GroundMeatEatGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin extends HostileEntity {

    protected SpiderEntityMixin(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "initGoals")
    protected void killAnimalsGoal(CallbackInfo ci) {
        this.goalSelector.add(4, new ActiveTargetGoal(this, ChickenEntity.class, true));
        this.goalSelector.add(4, new ActiveTargetGoal(this, RabbitEntity.class, true));
        this.goalSelector.add(4, new GroundMeatEatGoal((HostileEntity) (Object) this));
    }
}
