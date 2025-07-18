# Lactose Utility \[StAPI]

A static utility mod for streamlining feature generation.

`/copy ~ ~ ~ 10 10 10`
`/paste ~ ~ ~`
`/savecopied house`

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
}
```
