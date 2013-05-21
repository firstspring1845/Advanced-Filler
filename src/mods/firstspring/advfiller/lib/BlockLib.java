package mods.firstspring.advfiller.lib;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockLib {
	
	/**
	 * @param stack:挿入するアイテム
	 * @param inv:挿入先インベントリ
	 * @return 余ったアイテムもしくはNull
	 */
	public static ItemStack insertStackToInventory(ItemStack s, IInventory inv){
		ItemStack stack = s.copy();
		for(int i = 0; i < inv.getSizeInventory(); i++){
			ItemStack is = inv.getStackInSlot(i);
			if(is == null){
				inv.setInventorySlotContents(i, stack);
				return null;
			}
			if(stack.isItemEqual(is)){
				int move = Math.min(stack.stackSize, 64 - is.stackSize);
				stack.stackSize -= move;
				is.stackSize += move;
				if(stack.stackSize == 0)
					return null;
			}
		}
		return stack;
	}
	
	public static ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z){
		Block block = Block.blocksList[world.getBlockId(x, y, z)];
		if(block == null)
			return new ArrayList();
		int meta = world.getBlockMetadata(x, y, z);
		return block.getBlockDropped(world, x, y, z, meta, 0);
	}

}
