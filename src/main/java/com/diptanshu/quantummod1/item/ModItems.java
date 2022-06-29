package com.diptanshu.quantummod1.item;

import com.diptanshu.quantummod1.QuantumMod1;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    // List of items created under "newtrymod" as known by Forge
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, QuantumMod1.MOD_ID);

    /**
    public static final RegistryObject<MeasurementItem> MEASUREMENT = ITEMS.register("measurement",
            () -> new MeasurementItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
     */

    // create a register
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
