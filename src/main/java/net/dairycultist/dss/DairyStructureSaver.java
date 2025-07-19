package net.dairycultist.dss;

import com.matthewperiut.retrocommands.api.Command;
import com.matthewperiut.retrocommands.api.CommandRegistry;
import com.matthewperiut.retrocommands.util.SharedCommandSource;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.world.LightType;

public class DairyStructureSaver implements ModInitializer {

    public static int x1, y1, z1;
    public static int x2, y2, z2;
    public static boolean corner1Valid = false;
    public static boolean corner2Valid = false;

    public void onInitialize() {

        if (FabricLoader.getInstance().isModLoaded("retrocommands")) {

            CommandRegistry.add(new Command() {

                public void command(SharedCommandSource sharedCommandSource, String[] strings) {

                    if (strings.length == 1) {
                        sharedCommandSource.sendFeedback("Syntax: /structure [corner|paste|save]");
                        return;
                    }

                    switch (strings[1].toLowerCase()) {

                        case "corner":

                            if (strings.length != 3) {
                                sharedCommandSource.sendFeedback("Syntax: /structure corner [1|2]");
                                return;
                            }

                            switch (strings[2]) {

                                case "1":

                                    x1 = (int) sharedCommandSource.getPlayer().x;
                                    y1 = (int) sharedCommandSource.getPlayer().y - 2;
                                    z1 = (int) sharedCommandSource.getPlayer().z;
                                    sharedCommandSource.sendFeedback("Set corner 1 to " + x1 + "," + y1 + "," + z1 + " (inclusive)");
                                    sharedCommandSource.sendFeedback("(AKA the position of the block you were standing on)");
                                    corner1Valid = true;
                                    break;

                                case "2":

                                    if (!corner1Valid) {
                                        sharedCommandSource.sendFeedback("Set corner 1 first!");
                                        break;
                                    }

                                    x2 = (int) sharedCommandSource.getPlayer().x;
                                    y2 = (int) sharedCommandSource.getPlayer().y - 2;
                                    z2 = (int) sharedCommandSource.getPlayer().z;

                                    if (x2 < x1 || y2 < y1 || z2 < z1) {
                                        sharedCommandSource.sendFeedback("Corner 2 must be greater (or equal to) than corner 1 on all three axes!");
                                        break;
                                    }

                                    sharedCommandSource.sendFeedback("Set corner 2 to " + x2 + "," + y2 + "," + z2 + " (inclusive)");
                                    sharedCommandSource.sendFeedback("(AKA the position of the block you were standing on)");
                                    x2++;
                                    y2++;
                                    z2++;

                                    // update blocks so that they appear highlighted
                                    for (int x = x1; x < x2; x++)
                                        for (int y = y1; y < y2; y++)
                                            for (int z = z1; z < z2; z++)
                                                sharedCommandSource.getPlayer().world.blockUpdateEvent(x, y, z);

                                    corner2Valid = true;
                                    break;

                                default:
                                    sharedCommandSource.sendFeedback("Syntax: /structure corner [1|2]");
                            }

                            break;

                        // /structure paste ~ ~ ~
                        case "paste":

                            if (
                                    strings.length != 5
                                    || isNotValidBlockPos(strings[2])
                                    || isNotValidBlockPos(strings[3])
                                    || isNotValidBlockPos(strings[4])
                            ) {
                                sharedCommandSource.sendFeedback("Syntax: /structure paste x y z (either int or ~)");
                                return;
                            }

                            int posX = getBlockPos(strings[2], (int) sharedCommandSource.getPlayer().x);
                            int posY = getBlockPos(strings[3], (int) sharedCommandSource.getPlayer().y);
                            int posZ = getBlockPos(strings[4], (int) sharedCommandSource.getPlayer().z);

                            // check to make sure there isn't overlap
                            if (
                                    Math.abs(posX - x1) < x2 - x1 &&
                                    Math.abs(posY - y1) < y2 - y1 &&
                                    Math.abs(posZ - z1) < z2 - z1
                            ) {
                                sharedCommandSource.sendFeedback("Paste structure cannot overlap source structure!");
                                return;
                            }

                            for (int x = 0; x < x2 - x1; x++)
                                for (int y = 0; y < y2 - y1; y++)
                                    for (int z = 0; z < z2 - z1; z++)
                                        sharedCommandSource.getPlayer().world.setBlock(posX + x, posY + y, posZ + z, sharedCommandSource.getPlayer().world.getBlockId(x1 + x, y1 + y, z1 + z));
                            break;

                        // /structure save house
                        case "save":

                            for (int y = y1; y < y2; y++) {
                                for (int x = x1; x < x2; x++) {
                                    for (int z = z1; z < z2; z++) {
                                        System.out.println(sharedCommandSource.getPlayer().world.getBlockId(x, y, z));
                                    }
                                }
                            }
                            break;

                        default:
                            sharedCommandSource.sendFeedback("Syntax: /structure [corner|paste|save]");
                    }
                }

                public String name() {
                    return "structure";
                }

                public void manual(SharedCommandSource sharedCommandSource) {
                    sharedCommandSource.sendFeedback("Run '/structure' to get hints on how to use the command");
                }
            });

            System.out.println("DairyStructureSaver commands successfully initialized!");

        } else {

            System.err.println("\n\n###\nDairyStructureSaver commands could not be initialized!\nPlease check you have retrocommands installed!\n###\n");
        }

    }

    private static boolean isNotValidBlockPos(String str) {

        if (str.equals("~"))
            return false;

        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private static int getBlockPos(String str, int local) {

        if (str.equals("~"))
            return local;

        return Integer.parseInt(str);
    }
}
