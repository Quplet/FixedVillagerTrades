package qu.fixedVillagerTrades.mixin;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.fixedVillagerTrades.FixedVillagerTrades;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntityMixin {

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeNbt(NbtCompound nbt, CallbackInfo ci) {
        if (FixedVillagerTrades.areTradesFixed()) {
            nbt.copyFrom(this.offersNbt);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readNbt(NbtCompound nbt, CallbackInfo ci) {
        if (FixedVillagerTrades.areTradesFixed()) {
            for (VillagerProfession profession1 : Registry.VILLAGER_PROFESSION) {
                String key = profession1.id() + "Offers";
                if (nbt.contains(key)) {
                    this.offersNbt.put(key, nbt.get(key));
                }
            }
        }
    }
}
