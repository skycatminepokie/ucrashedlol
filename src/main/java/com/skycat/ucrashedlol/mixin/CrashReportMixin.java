package com.skycat.ucrashedlol.mixin;

import com.skycat.ucrashedlol.FileHandler;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CrashReport.class)
public class CrashReportMixin {
    @ModifyVariable(method = "generateWittyComment", at = @At(value = "STORE", ordinal = 0))
    private static String[] ucrashedlol_addCustomComments(String[] original) {
        return FileHandler.getCommentStrings(original);
    }
}
