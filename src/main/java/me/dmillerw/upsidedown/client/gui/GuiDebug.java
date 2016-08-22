package me.dmillerw.upsidedown.client.gui;

import com.google.common.collect.Lists;
import me.dmillerw.upsidedown.client.particle.ParticleSpeck;
import me.dmillerw.upsidedown.lib.ModInfo;
import me.dmillerw.upsidedown.proxy.ClientProxy;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUnicodeGlyphButton;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dmillerw
 */
public class GuiDebug extends GuiScreen implements GuiPageButtonList.GuiResponder {

    private static final GuiSlider.FormatHelper FLOAT = (id, name, value) -> String.format("%.2f", value);
    private static final GuiSlider.FormatHelper INT = (id, name, value) -> Integer.toString(Math.round(value));

    private static enum DebugType {

        FOG,
        LIGHT,
        PARTICLE;

        private List<GuiButton> buttons = Lists.newArrayList();
        private void register(GuiDebug gui, GuiButton button) {
            gui.buttonList.add(button);
            buttons.add(button);
        }
        
        public void addGuiElements(GuiDebug gui, List<GuiButton> buttonList) {
            final int WIDTH = 150;
            final int CENTER = gui.guiLeft + (gui.xSize / 2) - (WIDTH / 2);
            final int DISTANCE = 30;

            switch (this) {
                case FOG: {
                    register(gui, new GuiSlider(
                            gui,
                            SLIDER_FOG_DENSITY,
                            CENTER,
                            gui.guiTop + 25,
                            "FOG_DENSITY",
                            0F,
                            0.3F,
                            ClientProxy.atmosphericState.fogDensity,
                            FLOAT));

                    register(gui, new GuiSlider(
                            gui,
                            SLIDER_FOG_RED,
                            CENTER,
                            gui.guiTop + 25 + DISTANCE,
                            "FOG_RED",
                            0F,
                            1F,
                            ClientProxy.atmosphericState.fogRed,
                            FLOAT));

                    register(gui, new GuiSlider(
                            gui,
                            SLIDER_FOG_GREEN,
                            CENTER,
                            gui.guiTop + 25 + DISTANCE * 2,
                            "FOG_GREEN",
                            0F,
                            1F,
                            ClientProxy.atmosphericState.fogGreen,
                            FLOAT));

                    register(gui, new GuiSlider(
                            gui,
                            SLIDER_FOG_BLUE,
                            CENTER,
                            gui.guiTop + 25 + DISTANCE * 3,
                            "FOG_BLUE",
                            0F,
                            1F,
                            ClientProxy.atmosphericState.fogBlue,
                            FLOAT));

                    break;
                }

                case LIGHT: {
                    register(gui, new GuiSlider(
                            gui,
                            SLIDER_LIGHT_INTENSITY,
                            CENTER,
                            gui.guiTop + 25,
                            "LIGHT_INTENSITY",
                            0F,
                            1F,
                            ClientProxy.atmosphericState.lightingIntensity,
                            FLOAT));

                    register(gui, new GuiSlider(
                            gui,
                            SLIDER_LIGHT_RED,
                            CENTER,
                            gui.guiTop + 25 + DISTANCE,
                            "LIGHT_RED",
                            0F,
                            1F,
                            ClientProxy.atmosphericState.lightingRed,
                            FLOAT));

                    register(gui, new GuiSlider(
                            gui,
                            SLIDER_LIGHT_GREEN,
                            CENTER,
                            gui.guiTop + 25 + DISTANCE * 2,
                            "LIGHT_GREEN",
                            0F,
                            1F,
                            ClientProxy.atmosphericState.lightingGreen,
                            FLOAT));

                    register(gui, new GuiSlider(
                            gui,
                            SLIDER_LIGHT_BLUE,
                            CENTER,
                            gui.guiTop + 25 + DISTANCE * 3,
                            "LIGHT_BLUE",
                            0F,
                            1F,
                            ClientProxy.atmosphericState.lightingBlue,
                            FLOAT));

                    break;
                }

                case PARTICLE: {
                    register(gui, new GuiSlider(
                            gui,
                            SLIDER_PARTICLE_SIZE,
                            CENTER,
                            gui.guiTop + 25,
                            "PARTICLE_SIZE",
                            0F,
                            1F,
                            ClientProxy.atmosphericState.particleSize,
                            FLOAT
                    ));

                    register(gui, new GuiSlider(
                            gui,
                            SLIDER_PARTICLE_LIFESPAN,
                            CENTER,
                            gui.guiTop + 25 + DISTANCE,
                            "PARTICLE_LIFESPAN",
                            1F,
                            128F,
                            ClientProxy.atmosphericState.particleMaxLifespan,
                            INT
                    ));

                    register(gui, new GuiSlider(
                            gui,
                            SLIDER_PARTICLE_DISTANCE,
                            CENTER,
                            gui.guiTop + 25 + DISTANCE * 2,
                            "PARTICLE_DISTANCE",
                            1F,
                            128F,
                            ClientProxy.atmosphericState.particleMaxDistance,
                            INT
                    ));

                    register(gui, new GuiSlider(
                            gui,
                            SLIDER_PARTICLE_AMOUNT,
                            CENTER,
                            gui.guiTop + 25 + DISTANCE * 3,
                            "PARTICLE_AMOUNT",
                            1F,
                            1000F,
                            ClientProxy.atmosphericState.particleMaxAmount,
                            INT
                    ));

                    register(gui, new GuiSlider(
                            gui,
                            SLIDER_PARTICLE_PER_TICK,
                            CENTER,
                            gui.guiTop + 25 + DISTANCE * 4,
                            "PARTICLE_SIZE",
                            1F,
                            1000F,
                            ClientProxy.atmosphericState.particleSpawnPerTick,
                            INT
                    ));
                }
            }
        }

        public static void switchMode(GuiDebug gui, DebugType mode) {
            gui.hideAll();
            for (GuiButton button : mode.buttons)
                button.visible = true;
        }
    }

    private static int BUTTON_PREVIOUS_MODE = 98;
    private static int BUTTON_NEXT_MODE = 99;

    private static final int SLIDER_FOG_DENSITY = 0;
    private static final int SLIDER_FOG_RED = 1;
    private static final int SLIDER_FOG_GREEN = 2;
    private static final int SLIDER_FOG_BLUE = 3;

    private static final int SLIDER_LIGHT_INTENSITY = 4;
    private static final int SLIDER_LIGHT_RED = 5;
    private static final int SLIDER_LIGHT_GREEN = 6;
    private static final int SLIDER_LIGHT_BLUE = 7;

    private static final int SLIDER_PARTICLE_SIZE = 8;
    private static final int SLIDER_PARTICLE_LIFESPAN = 9;
    private static final int SLIDER_PARTICLE_DISTANCE = 10;
    private static final int SLIDER_PARTICLE_AMOUNT = 11;
    private static final int SLIDER_PARTICLE_PER_TICK = 12;

    protected int xSize = 256;
    protected int ySize = 166;
    protected int guiLeft;
    protected int guiTop;

    private DebugType mode;

    private void switchMode(DebugType mode) {
        this.mode = mode;
        DebugType.switchMode(this, mode);
    }

    protected void hideAll() {
        for (GuiButton button : buttonList) {
            if (button.id == BUTTON_PREVIOUS_MODE || button.id == BUTTON_NEXT_MODE)
                continue;

            button.visible = false;
        }
    }

    private String getText(int id) {
        switch (id) {
            case SLIDER_FOG_DENSITY: return "Fog: Density";
            case SLIDER_FOG_RED: return "Fog: Red";
            case SLIDER_FOG_GREEN: return "Fog: Green";
            case SLIDER_FOG_BLUE: return "Fog: Blue";

            case SLIDER_LIGHT_INTENSITY: return "Light: Intensity";
            case SLIDER_LIGHT_RED: return "Light: Red";
            case SLIDER_LIGHT_GREEN: return "Light: Green";
            case SLIDER_LIGHT_BLUE: return "Light: Blue";

            case SLIDER_PARTICLE_SIZE: return "Particle: Size";
            case SLIDER_PARTICLE_LIFESPAN: return "Particle: Lifespan";
            case SLIDER_PARTICLE_DISTANCE: return "Particle: Distance";
            case SLIDER_PARTICLE_AMOUNT: return "Particle: Max Amount";
            case SLIDER_PARTICLE_PER_TICK: return "Particle: Per Tick";

            default: return "";
        }
    }

    @Override
    public void initGui() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        for (DebugType type : DebugType.values()) {
            type.addGuiElements(this, this.buttonList);
        }

        this.buttonList.add(new GuiUnicodeGlyphButton(BUTTON_PREVIOUS_MODE, guiLeft + 5, guiTop + 5, 20, 20, "", "\u25C0", 1F));
        this.buttonList.add(new GuiUnicodeGlyphButton(BUTTON_NEXT_MODE, guiLeft + xSize - 25, guiTop + 5, 20, 20, "", "\u25B6", 1F));

        switchMode(DebugType.FOG);
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
        final String header = mode.name();
        final int size = fontRendererObj.getStringWidth(header);

        fontRendererObj.drawString(header, guiLeft + (xSize / 2) - (size / 2), guiTop + 8, fontColor);

        for (GuiButton button : buttonList) {
            if (button instanceof GuiSlider && button.isMouseOver()) {
                String text = getText(button.id);

                if (!text.isEmpty())
                    drawHoveringText(Arrays.asList(text), mouseX, mouseY);
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == BUTTON_PREVIOUS_MODE) {
            int index = this.mode.ordinal() - 1;
            if (index < 0)
                index = DebugType.values().length - 1;

            switchMode(DebugType.values()[index]);
        } else if (button.id == BUTTON_NEXT_MODE) {
            int index = this.mode.ordinal() + 1;
            if (index >= DebugType.values().length)
                index = 0;

            switchMode(DebugType.values()[index]);
        }
    }

    /* GUI_RESPONDER */
    @Override
    public void setEntryValue(int id, boolean value) {

    }

    @Override
    public void setEntryValue(int id, float value) {
        switch (id) {
            case SLIDER_FOG_DENSITY:
                ClientProxy.atmosphericState.fogDensity = value;
                break;
            case SLIDER_FOG_RED:
                ClientProxy.atmosphericState.fogRed = value;
                break;
            case SLIDER_FOG_GREEN:
                ClientProxy.atmosphericState.fogGreen = value;
                break;
            case SLIDER_FOG_BLUE:
                ClientProxy.atmosphericState.fogBlue = value;
                break;
            case SLIDER_LIGHT_INTENSITY:
                ClientProxy.atmosphericState.lightingIntensity = value;
                break;
            case SLIDER_LIGHT_RED:
                ClientProxy.atmosphericState.lightingRed = value;
                break;
            case SLIDER_LIGHT_GREEN:
                ClientProxy.atmosphericState.lightingGreen = value;
                break;
            case SLIDER_LIGHT_BLUE:
                ClientProxy.atmosphericState.lightingBlue = value;
                break;
            case SLIDER_PARTICLE_SIZE:
                ParticleSpeck.killAll = true;
                ClientProxy.atmosphericState.particleSize = value;
                break;
            case SLIDER_PARTICLE_LIFESPAN:
                ParticleSpeck.killAll = true;
                ClientProxy.atmosphericState.particleMaxLifespan = Math.round(value);
                break;
            case SLIDER_PARTICLE_DISTANCE:
                ParticleSpeck.killAll = true;
                ClientProxy.atmosphericState.particleMaxDistance = Math.round(value);
                break;
            case SLIDER_PARTICLE_AMOUNT:
                ParticleSpeck.killAll = true;
                ClientProxy.atmosphericState.particleMaxAmount = Math.round(value);
                break;
            case SLIDER_PARTICLE_PER_TICK:
                ParticleSpeck.killAll = true;
                ClientProxy.atmosphericState.particleSpawnPerTick = Math.round(value);
        }
    }

    @Override
    public void setEntryValue(int id, String value) {

    }
}
