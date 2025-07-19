package net.dairycultist.dss.mixin;

import net.dairycultist.dss.DairyStructureSaver;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.GrassBlock;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Block.class, GrassBlock.class})
public class MBlock {

    @Inject(method = "getColorMultiplier", at = @At(value = "HEAD"), cancellable = true)
    @Environment(EnvType.CLIENT)
    public void getColorMultiplier(BlockView blockView, int x, int y, int z, CallbackInfoReturnable<Integer> cir) {

        if (
                x >= DairyStructureSaver.x1 && x < DairyStructureSaver.x2 &&
                y >= DairyStructureSaver.y1 && x < DairyStructureSaver.y2 &&
                z >= DairyStructureSaver.z1 && x < DairyStructureSaver.z2
        )
            cir.setReturnValue(16776960);
    }
}
