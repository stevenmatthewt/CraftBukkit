package net.minecraft.server;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
// CraftBukkit end

final class DispenseBehaviorFlintAndSteel extends DispenseBehaviorItem {

    private boolean b = true;

    DispenseBehaviorFlintAndSteel() {}

    protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
        World world = isourceblock.i();
        BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));

        // CraftBukkit start
        org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
        CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);

        BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new org.bukkit.util.Vector(0, 0, 0));
        if (!BlockDispenser.eventFired) {
            world.getServer().getPluginManager().callEvent(event);
        }

        if (event.isCancelled()) {
            return itemstack;
        }

        if (!event.getItem().equals(craftItem)) {
            // Chain to handler for new item
            ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
            IDispenseBehavior idispensebehavior = (IDispenseBehavior) BlockDispenser.M.get(eventStack.getItem());
            if (idispensebehavior != IDispenseBehavior.a && idispensebehavior != this) {
                idispensebehavior.a(isourceblock, eventStack);
                return itemstack;
            }
        }
        // CraftBukkit end        
        
        if (world.isEmpty(blockposition)) {            
            // CraftBukkit start - Ignition by dispensing flint and steel
            if (!org.bukkit.craftbukkit.event.CraftEventFactory.callBlockIgniteEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ()).isCancelled()) {
                world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
                if (itemstack.isDamaged(1, world.random)) {
                    itemstack.count = 0;
                }
            }
            // CraftBukkit end
        } else if (world.getType(blockposition).getBlock() == Blocks.TNT) {
            Blocks.TNT.postBreak(world, blockposition, Blocks.TNT.getBlockData().set(BlockTNT.EXPLODE, Boolean.valueOf(true)));
            world.setAir(blockposition);
        } else {
            this.b = false;
        }

        return itemstack;
    }

    protected void a(ISourceBlock isourceblock) {
        if (this.b) {
            isourceblock.i().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
        } else {
            isourceblock.i().triggerEffect(1001, isourceblock.getBlockPosition(), 0);
        }

    }
}
