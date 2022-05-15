package com.github.theredbrain.dirtyThings.mixin;

import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Supplier;

@Mixin(ToolMaterials.class)
abstract class ToolMaterialsMixin {

    // https://github.com/magistermaks/fabric/blob/master/wiki/mixins/enums.md
/*
    @Invoker("<init>")
    private static ToolMaterials init(String name, int id, int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier repairIngredient) {
        throw new AssertionError(); // unreachable state
    }

    @Redirect(
            method = "<clinit>()V",
            at = @At(
                    value = "NEW",
                    ordinal = 0,
//                    target = "Lnet/minecraft/item/ToolMaterials;<init>(Ljava/lang/String;IIIFFILnet/minecraft/util/Lazy;)Lnet/minecraft/item/ToolMaterials;"
                    target = "Lnet/minecraft/item/ToolMaterials;<init>(Ljava/lang/String;IIIFFILjava/util/function/Supplier;)Lnet/minecraft/item/ToolMaterials;"
            )
    )
    private static ToolMaterials clinit(String name, int id, int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier repairIngredient) {
        return init("WOOD", 0, 0, 3, 2.0F, 0.0F, 15, () -> {
            return Ingredient.fromTag(ItemTags.PLANKS);
        });
    }
 */
}
