package me.dmillerw.upsidedown.client;

import me.dmillerw.upsidedown.client.event.RenderEventHandler;
import me.dmillerw.upsidedown.lib.ModInfo;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by dmillerw
 */
public class LightmapReloader implements IResourceManagerReloadListener {

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        RenderEventHandler.lightmapOverride = false;
        try {
            BufferedImage image = ImageIO.read(resourceManager.getResource(new ResourceLocation(ModInfo.ID, "textures/lightmap.png")).getInputStream());
            for (int i=0; i<16; i++) {
                for (int j=0; j<16; j++) {
                    RenderEventHandler.lightmap[(j * 16) + i] = image.getRGB(i, j);
                }
            }
            RenderEventHandler.lightmapOverride = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
