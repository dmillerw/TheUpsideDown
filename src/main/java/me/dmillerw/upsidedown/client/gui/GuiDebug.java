package me.dmillerw.upsidedown.client.gui;

import me.dmillerw.upsidedown.lib.ModInfo;
import me.dmillerw.upsidedown.proxy.ClientProxy;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

/**
 * Created by dmillerw
 */
public class GuiDebug extends GuiScreen implements GuiPageButtonList.GuiResponder {

    private static final GuiSlider.FormatHelper FLOAT = (id, name, value) -> String.format("%.2f", value);;

    protected int xSize = 256;
    protected int ySize = 166;
    protected int guiLeft;
    protected int guiTop;

    @Override
    public void initGui() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        final int fourth = xSize / 4;

        this.buttonList.add(new GuiSlider(this, 0, guiLeft + fourth - 40, guiTop + 25, "FOG_DENSITY", 0F, 0.3F, ClientProxy.atmosphericState.fogDensity, FLOAT));
        this.buttonList.add(new GuiSlider(this, 1, guiLeft + fourth - 40, guiTop + 55, "FOG_RED", 0F, 1F, ClientProxy.atmosphericState.fogRed, FLOAT));
        this.buttonList.add(new GuiSlider(this, 2, guiLeft + fourth - 40, guiTop + 85, "FOG_GREEN", 0F, 1F, ClientProxy.atmosphericState.fogGreen, FLOAT));
        this.buttonList.add(new GuiSlider(this, 3, guiLeft + fourth - 40, guiTop + 115, "FOG_BLUE", 0F, 1F, ClientProxy.atmosphericState.fogBlue, FLOAT));

        this.buttonList.add(new GuiSlider(this, 4, guiLeft + fourth * 3 - 40, guiTop + 25, "LIGHT_INTENSITY", 0F, 1F, ClientProxy.atmosphericState.lightingIntensity, FLOAT));
        this.buttonList.add(new GuiSlider(this, 5, guiLeft + fourth * 3 - 40, guiTop + 55, "LIGHT_RED", 0F, 1F, ClientProxy.atmosphericState.lightingRed, FLOAT));
        this.buttonList.add(new GuiSlider(this, 6, guiLeft + fourth * 3 - 40, guiTop + 85, "LIGHT_GREEN", 0F, 1F, ClientProxy.atmosphericState.lightingGreen, FLOAT));
        this.buttonList.add(new GuiSlider(this, 7, guiLeft + fourth * 3 - 40, guiTop + 115, "LIGHT_BLUE", 0F, 1F, ClientProxy.atmosphericState.lightingBlue, FLOAT));

        for (GuiButton button : buttonList) {
            if (button instanceof GuiSlider) {
                ((GuiSlider)button).setWidth(80);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.ID, "textures/gui/debug.png"));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        super.drawScreen(mouseX, mouseY, partialTicks);

        final int fontColor = 0x0000000;

        final String headerFog = "FOG";
        final int sizeFog = fontRendererObj.getStringWidth(headerFog);
        fontRendererObj.drawString(headerFog, guiLeft + (xSize / 4) - (sizeFog / 2), guiTop + 8, fontColor);

        final String headerLighting = "LIGHTING";
        final int sizeLighting = fontRendererObj.getStringWidth(headerLighting);
        fontRendererObj.drawString(headerLighting, guiLeft + (xSize / 2) +(xSize / 4) - (sizeLighting / 2), guiTop + 8, fontColor);

        for (GuiButton button : buttonList) {
            if (button instanceof GuiSlider && button.isMouseOver()) {
                String text = "";
                switch (button.id) {
                    case 0: text = "Density"; break;
                    case 1: text = "Red"; break;
                    case 2: text = "Green"; break;
                    case 3: text = "Blue"; break;
                    case 4: text = "Intensity"; break;
                    case 5: text = "Red"; break;
                    case 6: text = "Green"; break;
                    case 7: text = "Blue"; break;
                }
                if (!text.isEmpty())
                    drawHoveringText(Arrays.asList(text), mouseX, mouseY);
            }
        }
    }

    /* GUI_RESPONDER */
    @Override
    public void setEntryValue(int id, boolean value) {

    }

    @Override
    public void setEntryValue(int id, float value) {
        switch (id) {
            case 0:
                ClientProxy.atmosphericState.fogDensity = value;
                break;
            case 1:
                ClientProxy.atmosphericState.fogRed = value;
                break;
            case 2:
                ClientProxy.atmosphericState.fogGreen = value;
                break;
            case 3:
                ClientProxy.atmosphericState.fogBlue = value;
                break;
            case 4:
                ClientProxy.atmosphericState.lightingIntensity = value;
                break;
            case 5:
                ClientProxy.atmosphericState.lightingRed = value;
                break;
            case 6:
                ClientProxy.atmosphericState.lightingGreen = value;
                break;
            case 7:
                ClientProxy.atmosphericState.lightingBlue = value;
                break;
        }
    }

    @Override
    public void setEntryValue(int id, String value) {

    }
}
