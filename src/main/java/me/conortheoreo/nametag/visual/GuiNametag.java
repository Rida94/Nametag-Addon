package me.conortheoreo.nametag.visual;

import me.conortheoreo.nametag.NametagMod;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiNametag extends GuiScreen {
    GuiButton toggleButton;
    GuiButton nametagColor;

    public void initGui() {
        this.buttonList.add(this.toggleButton = new GuiButton(0, this.width / 2 - 100, this.height / 2 - 50, 200, 20, "Toggle Nametag"));
        this.buttonList.add(this.nametagColor = new GuiButton(1, this.width / 2 - 100, this.height / 2 - 20, 200, 20, "Color: "));
        this.updateNames();
    }

    public void drawScreen(final int x, final int y, final float partial) {
        this.drawDefaultBackground();
        super.drawScreen(x, y, partial);
    }

    protected void actionPerformed(final GuiButton button) {
        if (button.id == 0) {
            NametagMod.SHOW_NAMETAG = !NametagMod.SHOW_NAMETAG;
        }
        if (button.id == 1) {
            NametagMod.setNextColor();
        }
        this.updateNames();
    }

    private void updateNames() {
        final String checkMark = Character.toString('\u2713');
        this.toggleButton.displayString = (NametagMod.SHOW_NAMETAG ? ("Toggle Nametag " + checkMark) : "Toggle Nametag");
        this.nametagColor.displayString = "Color: " + NametagMod.NAMETAG_COLOR.getFriendlyName().toUpperCase();
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        NametagMod.save();
    }
}
