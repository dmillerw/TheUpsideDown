package me.dmillerw.upsidedown.client.gui;

import me.dmillerw.upsidedown.proxy.ClientProxy;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;

/**
 * Created by dmillerw
 */
public class GuiDebug extends GuiScreen implements GuiPageButtonList.GuiResponder {

    private static final GuiSlider.FormatHelper FLOAT = (id, name, value) -> Float.toString(value);

    protected int xSize;
    protected int ySize;
    protected int guiLeft;
    protected int guiTop;

    public GuiSlider testSlider;

    @Override
    public void initGui() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        this.buttonList.add(new GuiSlider(this, 0, guiLeft - 200, guiTop - 60, "FOG_DENSITY", 0F, 0.3F, ClientProxy.atmosphericState.fogDensity, FLOAT));
        this.buttonList.add(new GuiSlider(this, 1, guiLeft - 200, guiTop - 30, "FOG_RED", 0F, 1F, ClientProxy.atmosphericState.fogRed, FLOAT));
        this.buttonList.add(new GuiSlider(this, 2, guiLeft - 200, guiTop +  0, "FOG_GREEN", 0F, 1F, ClientProxy.atmosphericState.fogGreen, FLOAT));
        this.buttonList.add(new GuiSlider(this, 3, guiLeft - 200, guiTop + 30, "FOG_BLUE", 0F, 1F, ClientProxy.atmosphericState.fogBlue, FLOAT));

        this.buttonList.add(new GuiSlider(this, 4, guiLeft + 50, guiTop - 60, "LIGHT_INTENSITY", 0F, 1F, ClientProxy.atmosphericState.lightingIntensity, FLOAT));
        this.buttonList.add(new GuiSlider(this, 5, guiLeft + 50, guiTop - 30, "LIGHT_RED", 0F, 1F, ClientProxy.atmosphericState.lightingRed, FLOAT));
        this.buttonList.add(new GuiSlider(this, 6, guiLeft + 50, guiTop +  0, "LIGHT_GREEN", 0F, 1F, ClientProxy.atmosphericState.lightingGreen, FLOAT));
        this.buttonList.add(new GuiSlider(this, 7, guiLeft + 50, guiTop + 30, "LIGHT_BLUE", 0F, 1F, ClientProxy.atmosphericState.lightingBlue, FLOAT));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
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
