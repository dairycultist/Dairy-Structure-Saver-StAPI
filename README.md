# Lactose Utility \[StAPI]

A utility mod for some of my more complex StAPI mods.

```
Lactose.world = world;
Lactose.localX = 5;
Lactose.localY = 64;
Lactose.localZ = 20;

Lactose.rotationAroundLocal = Lactose.NO_ROTATION;

Lactose.fillRect(0, 0, 0, 10, 10, 10, blockId);
```

```
public class Lactose {

    public static ChestBlockEntity setLootChest(World world, Random random, int x, int y, int z, int tries, Item[] items, int[] mins, int[] maxes) {

        world.setBlock(x, y, z, Block.CHEST.id);
        ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(x, y, z);

        for (int i = 0; i < tries; i++) {

            int itemIndex = random.nextInt(items.length);

            chest.setStack(random.nextInt(chest.size()), new ItemStack(items[itemIndex], random.nextInt(mins[itemIndex], maxes[itemIndex])));
        }

        return chest;
    }

    // steal code from megaliths mod u were working on
}
```
