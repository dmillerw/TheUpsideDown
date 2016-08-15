package me.dmillerw.upsidedown.item;

import me.dmillerw.upsidedown.util.StateHelper;
import me.dmillerw.upsidedown.proxy.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by dmillerw
 */
public class ItemDebug extends Item {

    public ItemDebug() {
        super();

        setMaxStackSize(1);

        setRegistryName("debug");
        setUnlocalizedName("debug");

        GameRegistry.register(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if (!worldIn.isRemote) {
            if (playerIn.isSneaking()) {
                StateHelper.swapState(playerIn);
            }
        } else {
            if (!playerIn.isSneaking()) {
                playerIn.addChatComponentMessage(new TextComponentString(Boolean.toString(ClientProxy.inUpsideDown)));
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }
}
