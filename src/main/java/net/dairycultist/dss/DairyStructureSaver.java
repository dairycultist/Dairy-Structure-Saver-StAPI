package net.dairycultist.dss;

import com.matthewperiut.retrocommands.api.Command;
import com.matthewperiut.retrocommands.api.CommandRegistry;
import com.matthewperiut.retrocommands.util.SharedCommandSource;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class DairyStructureSaver implements ModInitializer {

    public void onInitialize() {

        if (FabricLoader.getInstance().isModLoaded("retrocommands")) {

            CommandRegistry.add(new Command() {

//                /structure corner 1
//
//                /structure corner 2
//
//                /structure paste ~ ~ ~
//
//                /structure save house

                public void command(SharedCommandSource sharedCommandSource, String[] strings) {

                    switch (strings[1].toLowerCase()) {

                        case "corner":
                            break;

                        case "paste":
                            break;

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
