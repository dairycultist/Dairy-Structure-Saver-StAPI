# StAPI-Structure-Utility

helper mod for making structures that don't collide in stapi

classes:
- Room (abstract, must instance with a width/height/length bounding box for placement collision & position/direction of every door, and override a build() function that is passed a LocalRotationPlacer)
- StructurePlacer (after instancing, you can .addRoom and then .place by providing a position and a maximum depth)
- Placer (abstract, has three basic utilities: placeBlock, fillRect, and hollowRect)
- GlobalPlacer (must instance with a world and random)
- LocalRotationPlacer (must instance with a world, random, position, width/height/length, and direction enum, provides useful utilities that handle position and rotation automatically)

```
import net.minecraft.block.Block;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class GenerationHelper {

    public class Room {

        int width, height, length;
        SubGenerator builder;
        Door[] doors;
    }

    public class Door {

        int direction; // NESW
        int localX, localY, localZ;
    }

    public interface SubGenerator {

        void generate(World gWorld, Random gRandom, int gx, int gy, int gz, int gw, int gh, int gl, int[] data);
    }

    public static ChestBlockEntity lootChest(World world, Random random, int x, int y, int z, int tries, Item[] items, int[] mins, int[] maxes) {

        world.setBlock(x, y, z, Block.CHEST.id);
        ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(x, y, z);

        for (int i = 0; i < tries; i++) {

            int itemIndex = random.nextInt(items.length);

            chest.setStack(random.nextInt(chest.size()), new ItemStack(items[itemIndex], random.nextInt(mins[itemIndex], maxes[itemIndex])));
        }

        return chest;
    }

    // TODO reference dungeon design here https://youtu.be/snTfoz_xyQg?t=12180
    public static void generateMultiFeature(World world, Random random, int startX, int startY, int startZ,
                                            int maxDepth, Room[] possibleRooms) {

        // FIRST
        // set up a command to spawn a TestFeature https://modrinth.com/mod/retrocommands
    }

    public static void replaceRect(World world, int fromId, int toId, int x, int y, int z, int w, int h, int l) {

        for (int ox = x; ox < x + w; ox++)
            for (int oy = y; oy < y + h; oy++)
                for (int oz = z; oz < z + l; oz++)
                    if (world.getBlockId(ox, oy, oz) == fromId)
                        world.setBlockWithoutNotifyingNeighbors(ox, oy, oz, toId);
    }

    public static void replaceRectRandomly(World world, Random random, int oneOutOf, int fromId, int toId, int x, int y, int z, int w, int h, int l) {

        for (int ox = x; ox < x + w; ox++)
            for (int oy = y; oy < y + h; oy++)
                for (int oz = z; oz < z + l; oz++)
                    if (world.getBlockId(ox, oy, oz) == fromId && random.nextInt(oneOutOf) == 0)
                        world.setBlockWithoutNotifyingNeighbors(ox, oy, oz, toId);
    }

    public static void fillRect(World world, int blockId, int x, int y, int z, int w, int h, int l) {

        for (int ox = x; ox < x + w; ox++)
            for (int oy = y; oy < y + h; oy++)
                for (int oz = z; oz < z + l; oz++)
                    world.setBlockWithoutNotifyingNeighbors(ox, oy, oz, blockId);
    }

    public static void fillRectMeta(World world, int blockId, int meta, int x, int y, int z, int w, int h, int l) {

        for (int ox = x; ox < x + w; ox++)
            for (int oy = y; oy < y + h; oy++)
                for (int oz = z; oz < z + l; oz++)
                    world.setBlockWithoutNotifyingNeighbors(ox, oy, oz, blockId, meta);
    }

    public static void fillHollowRect(World world, int blockId, int x, int y, int z, int w, int h, int l) {

        fillRect(world, blockId, x, y, z, w, h, l);
        fillRect(world, 0, x + 1, y + 1, z + 1, w - 2, h - 2, l - 2);
    }
}
```
