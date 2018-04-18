package me.conortheoreo.nametag;

import cc.hyperium.Hyperium;
import cc.hyperium.event.*;
import cc.hyperium.internal.addons.IAddon;
import me.conortheoreo.nametag.visual.TagRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.io.*;

public class NametagMod implements IAddon {

    public static boolean SHOW_NAMETAG;
    public static EnumChatFormatting NAMETAG_COLOR;
    private static Minecraft mc;
    public static String VERSION = "1.0";

    @Override
    public void onLoad() {
        System.out.println("Sucesfully loaded NametagMod!");
        EventBus.INSTANCE.register(this);
    }

    @Override
    public void onClose() {
        System.out.println("Closing...");
    }

    @InvokeEvent(priority = Priority.LOW)
    public void init(final InitializationEvent event) {
        EventBus.INSTANCE.register((Object) new TagRenderer());
        Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new NametagCommand(this));
        load();
    }

    public static void save() {
        final File file = new File(NametagMod.mc.mcDataDir, "nametagmod.dat");
        try {
            final FileOutputStream fos = new FileOutputStream(file);
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeBoolean(NametagMod.SHOW_NAMETAG);
            oos.writeObject(NametagMod.NAMETAG_COLOR);
            oos.close();
            fos.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void load() {
        final File file = new File(NametagMod.mc.mcDataDir, "nametagmod.dat");
        if (file.exists()) {
            try {
                final FileInputStream fis = new FileInputStream(file);
                final ObjectInputStream ois = new ObjectInputStream(fis);
                NametagMod.SHOW_NAMETAG = ois.readBoolean();
                NametagMod.NAMETAG_COLOR = (EnumChatFormatting)ois.readObject();
                ois.close();
                fis.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void setNextColor() {
        boolean set = false;
        for (final EnumChatFormatting color : EnumChatFormatting.values()) {
            if (set) {
                NametagMod.NAMETAG_COLOR = color;
                return;
            }
            if (color == NametagMod.NAMETAG_COLOR) {
                set = true;
            }
        }
        if (set) {
            NametagMod.NAMETAG_COLOR = EnumChatFormatting.values()[0];
        }
    }

    static {
        NametagMod.SHOW_NAMETAG = true;
        NametagMod.NAMETAG_COLOR = EnumChatFormatting.WHITE;
        NametagMod.mc = Minecraft.getMinecraft();
    }

}