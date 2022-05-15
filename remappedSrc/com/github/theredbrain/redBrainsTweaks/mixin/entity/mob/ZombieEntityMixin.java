package com.github.theredbrain.dirtyThings.mixin.entity.mob;

import com.github.theredbrain.dirtyThings.entity.ai.goal.GroundMeatEatGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin extends HostileEntity {

    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "initGoals")
    protected void killAnimalsGoal(CallbackInfo ci) {
        this.goalSelector.add(4, new ActiveTargetGoal(this, CowEntity.class, true));
        this.goalSelector.add(4, new ActiveTargetGoal(this, PigEntity.class, true));
        this.goalSelector.add(4, new ActiveTargetGoal(this, SheepEntity.class, true));
        this.goalSelector.add(5, new GroundMeatEatGoal((HostileEntity) (Object) this));
    }
}
