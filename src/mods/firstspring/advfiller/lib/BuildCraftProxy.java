//Thanks to alalwww(Table code)

package mods.firstspring.advfiller.lib;

import java.util.HashMap;
import java.util.Map;

import mods.firstspring.advfiller.Position;
import mods.firstspring.advfiller.TileAdvFiller;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.BuildCraftFactory;
import buildcraft.core.Box;
import buildcraft.core.utils.Utils;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import cpw.mods.fml.common.Loader;

public class BuildCraftProxy {

	public static final boolean loaded = Loader.isModLoaded("BuildCraft|Core");

	public static final BuildCraftProxy proxy = new BuildCraftProxy();
	private HashMap<Integer,HashMap<Position,Box>> box;
	private Table<Integer,Position,Box> table;

	private BuildCraftProxy(){
		Map<Integer, Map<Position, Box>> backingMap = Maps.newHashMap();
		Supplier<Map<Position, Box>> factory = newMapFactory();
		table = Tables.newCustomTable(backingMap, factory);
	}

	static <C, V> Supplier<Map<C, V>> newMapFactory() {
		return new MapFactory<C, V>();
	}

	static final class MapFactory<C, V> implements Supplier<Map<C, V>> {

		public Map<C, V> get() {
			return Maps.newHashMap();
		}

	}

	public static void addToRandomPipeEntry(TileEntity tile, ForgeDirection orient, ItemStack stack){
		if(!loaded)
			return;
		Utils.addToRandomPipeEntry(tile, ForgeDirection.UNKNOWN, stack);
	}

	public static int getFrameBlockId(){
		if(loaded)
			return BuildCraftFactory.frameBlock.blockID;
		return Block.glass.blockID;
	}

	public Box getBox(TileAdvFiller t){
		Position p = new Position(t.xCoord, t.yCoord, t.zCoord);
		if(!table.contains(t.dim, p)){
			table.put(t.dim, p, new Box());
		}
		return table.get(t.dim, p);
	}

}
