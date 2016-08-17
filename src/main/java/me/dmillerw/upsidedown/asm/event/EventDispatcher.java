package me.dmillerw.upsidedown.asm.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by dmillerw
 */
public class EventDispatcher {

    public static float getSunBrightness(World world, float partialTicks, float brightness) {
        SunBrightnessEvent event = new SunBrightnessEvent(world, partialTicks, brightness);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getBrightness();
    }

    public static UpdateLightmapEvent updateLightmap(int position, int red, int green, int blue) {
        UpdateLightmapEvent event = new UpdateLightmapEvent(position, red, green, blue);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }

    public static IBakedModel getBlockModel(BlockModelShapes blockModelShapes, IBlockState state, IBakedModel model) {
        GetBlockModelEvent event = new GetBlockModelEvent(blockModelShapes, state, model);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getBlockModel();
    }
}
