package fr.krepe.belzebutil.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_WEEBOS = "key.category.belzebutil.belzebutil";
    public static final String KEY_DRINK_WATER = "key.belzebutil.drink_water";

    public static final KeyMapping DRINKING_KEY = new KeyMapping(KEY_DRINK_WATER, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_WEEBOS);

    public static final KeyMapping MONEY_UP = new KeyMapping(KEY_DRINK_WATER, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PAGE_DOWN, KEY_CATEGORY_WEEBOS);

    public static final KeyMapping MONEY_DOWN = new KeyMapping(KEY_DRINK_WATER, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PAGE_UP, KEY_CATEGORY_WEEBOS);
}
