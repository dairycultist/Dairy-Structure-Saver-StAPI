package net.dairycultist.dss;

import com.matthewperiut.retrocommands.api.Command;
import com.matthewperiut.retrocommands.api.CommandRegistry;
import com.matthewperiut.retrocommands.util.SharedCommandSource;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;

public class DairyStructureSaver implements ModInitializer {

    public static int x1, y1, z1;
    public static int x2, y2, z2;
    public static boolean corner1Valid = false;
    public static boolean corner2Valid = false;

    public void onInitialize() {

        if (FabricLoader.getInstance().isModLoaded("retrocommands")) {

            CommandRegistry.add(new Command() {

                public void command(SharedCommandSource sharedCommandSource, String[] strings) {

                    switch (strings[1].toLowerCase()) {

                        case "corner":

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
                                    corner2Valid = true;
                                    break;

                                default:
                                    sharedCommandSource.sendFeedback("Syntax: /structure corner [1|2]");
                            }

                            break;

                        // /structure paste ~ ~ ~
                        case "paste":

                            for (int x = x1; x < x2; x++) {
                                for (int y = y1; y < y2; y++) {
                                    for (int z = z1; z < z2; z++) {
                                        sharedCommandSource.getPlayer().world.setBlock(x, y, z, Block.DIAMOND_BLOCK.id);
                                    }
                                }
                            }

                            break;

                        // /structure save house
                        case "save":
                            break;

                        default:
                            sharedCommandSource.sendFeedback("Syntax: /structure [corner|paste|save]");
                    }
                }

                public String name() {
                    return "structure";
                }

                public void manual(SharedCommandSource sharedCommandSource) {

                }
            });

            System.out.println("DairyStructureSaver commands successfully initialized!");

        } else {

            System.err.println("\n\n###\nDairyStructureSaver commands could not be initialized!\nPlease check you have retrocommands installed!\n###\n");
        }

    }
}
