package com.github.theredbrain.dirtyThings.entity.ai.goal;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.GameRules;

import java.util.EnumSet;
import java.util.List;

public class GroundMeatEatGoal extends Goal {

    private final MobEntity mob;
    private ItemEntity meatEntity;
    private static final Ingredient MEAT;

    public GroundMeatEatGoal(MobEntity mob) {
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        if (this.mob.getEntityWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            this.meatEntity = this.findMeat();
        }

        return this.meatEntity != null;
    }

    public boolean shouldContinue() {
        return this.meatEntity != null && this.meatEntity.getStack().getCount() > 0;
    }

    public void stop() {
        this.meatEntity = null;
    }

    public void tick() {
        if (this.meatEntity != null) {
            this.mob.getLookControl().lookAt(this.meatEntity, 10.0F, (float) this.mob.getMaxLookPitchChange()); // TODO testing
            this.mob.getNavigation().startMovingTo(this.meatEntity, 1.0f);
            if (this.mob.squaredDistanceTo(this.meatEntity) < 4.0D) {
                this.feed();
            }
        }
    }

    private ItemEntity findMeat() {
        List<ItemEntity> list = mob.world.getEntitiesByClass(ItemEntity.class, this.mob.getBoundingBox().expand(8.0D), (entity) -> entity != null);
        double d = Double.MAX_VALUE;

        ItemEntity result = null;
        for (ItemEntity itemEntity: list) {
            if (MEAT.test(itemEntity.getStack()) && this.mob.squaredDistanceTo(itemEntity) < d) {
                result = itemEntity;
                d = this.mob.squaredDistanceTo(itemEntity);
            }
        }

        return result;
    }

    private void feed() {
        if (this.meatEntity.getStack().getCount() > 0) {
            meatEntity.getStack().decrement(1);
        }

        stop();
    }

    static {
        MEAT = Ingredient.ofItems(new ItemConvertible[]{Items.PORKCHOP, Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.RABBIT});
    }
}
