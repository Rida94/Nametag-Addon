package me.conortheoreo.nametag;

import cc.hyperium.commands.BaseCommand;
import cc.hyperium.commands.CommandException;
import cc.hyperium.event.EventBus;
import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.TickEvent;
import me.conortheoreo.nametag.visual.GuiNametag;
import net.minecraft.client.Minecraft;

public class NametagCommand implements BaseCommand {

    private NametagMod mod;

    public NametagCommand(final NametagMod mod) {
        this.mod = mod;
    }

    @Override
    public String getName() {
        return "nametagmod";
    }

    @Override
    public String getUsage() {
        return "/nametagmod";
    }

    @Override
    public void onExecute(String[] strings) throws CommandException {
        EventBus.INSTANCE.register(this);
    }

    @InvokeEvent
    public void onTick(TickEvent e) {
        EventBus.INSTANCE.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(new GuiNametag());
    }

}
