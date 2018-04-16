package me.dmillerw.upsidedown.item;

import me.dmillerw.upsidedown.network.GuiHandler;
import me.dmillerw.upsidedown.util.StateHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by dmillerw
 */
public class ItemDebug extends Item {

    public ItemDebug() {
        super();

        setMaxStackSize(1);

        setUnlocalizedName("debug");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote) {
            if (playerIn.isSneaking()) {
                StateHelper.swapState(playerIn);
            }
        } else {
            if (!playerIn.isSneaking()) {
                GuiHandler.GuiType.DEBUG.open(playerIn);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
