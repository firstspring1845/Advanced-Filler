//Thanks to alalwww(Table code)

package mods.firstspring.advfiller.lib;

import java.util.HashMap;
import java.util.Map;

import mods.firstspring.advfiller.AdvFiller;
import mods.firstspring.advfiller.Position;
import mods.firstspring.advfiller.TileAdvFiller;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.BuildCraftBuilders;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftFactory;
import buildcraft.api.core.IAreaProvider;
import buildcraft.builders.TileMarker;
import buildcraft.core.Box;
import buildcraft.core.CreativeTabBuildCraft;
import buildcraft.core.utils.Utils;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

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
	
	public static ItemStack addToRandomInventory(ItemStack stack, World worldObj, int xCoord, int yCoord, int zCoord,ForgeDirection dir){
		return Utils.addToRandomInventory(stack, worldObj, xCoord, yCoord, zCoord, dir);
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
	
	public static void removeMarker(IAreaProvider i){
		if(i instanceof TileMarker)
			((TileMarker)i).removeFromWorld();
	}
	
	public static void addRecipe(){
		if(AdvFiller.recipeHarder){
			GameRegistry.addRecipe(new ItemStack(AdvFiller.advFiller, 1), new Object[]{	"M",
																				"F",
																				"Q",
																				'M', BuildCraftBuilders.markerBlock,
																				'F', BuildCraftBuilders.fillerBlock,
																				'Q', BuildCraftFactory.quarryBlock});
		}else{
			GameRegistry.addRecipe(new ItemStack(AdvFiller.advFiller, 1), new Object[]{	"IFI",
																				"GIG",
																				"DPD",
																				'I', BuildCraftCore.ironGearItem,
																				'G', BuildCraftCore.goldGearItem,
																				'D', BuildCraftCore.diamondGearItem,
																				'F', BuildCraftBuilders.fillerBlock,
																				'P', Item.pickaxeDiamond});
		}
		GameRegistry.addRecipe(new ItemStack(AdvFiller.redMarker), new Object[]{	"R",
																		"M",
																		'R', Item.redstone,
																		'M', BuildCraftBuilders.markerBlock});
	}
	
	public static CreativeTabs getTab(){
		if(loaded)
			return CreativeTabBuildCraft.tabBuildCraft;
		return CreativeTabs.tabRedstone;
	}
	
	public void registerRedMarker(){
		
	}

}
