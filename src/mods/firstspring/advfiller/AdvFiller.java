package mods.firstspring.advfiller;

import java.util.HashSet;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraftforge.common.Property;
import buildcraft.BuildCraftBuilders;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftFactory;
import buildcraft.core.CreativeTabBuildCraft;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="AdvFiller", name="Advanced Filler", version="Build 14", dependencies = "required-after:BuildCraft|Core;required-after:BuildCraft|Builders")
@NetworkMod(channels = {"advfiller_client", "advfiller_server"}, clientSideRequired=true, serverSideRequired=false, packetHandler = PacketHandler.class)
public class AdvFiller {
	@Instance("AdvFiller")
	public static AdvFiller instance;
	
	protected static Block advFiller;
	protected static Block redMarker;
	
	protected static boolean removeModeDrop;
	protected static boolean recipeHarder;
	protected static boolean breakEffect;
	protected static boolean bcFrameRenderer;
	
	protected static int advFillerID;
	protected static int redMarkerID;
	protected static int loopTick;
	protected static int maxDistance;
	protected static int energyRate;
	
	protected static HashSet<Integer> fillingSet = new HashSet();
	
	@PreInit
	public void loadConfiguration(FMLPreInitializationEvent event){
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		cfg.load();
		Property prop;
		prop = cfg.get(Configuration.CATEGORY_BLOCK, "AdvancedFiller_ID", 1192);
		advFillerID = prop.getInt();
		prop = cfg.get(Configuration.CATEGORY_BLOCK, "RedMarker_ID", 1193);
		redMarkerID = prop.getInt();
		prop = cfg.get(Configuration.CATEGORY_GENERAL, "Remove_Mode_Block_Drop", true);
		removeModeDrop = prop.getBoolean(true);
		prop = cfg.get(Configuration.CATEGORY_GENERAL, "Loop_Mode_Counter_Tick", 20);
		loopTick = prop.getInt();
		prop = cfg.get(Configuration.CATEGORY_GENERAL, "Max_Distance", 64);
		maxDistance = prop.getInt();
		prop = cfg.get(Configuration.CATEGORY_GENERAL, "Energy_Usage_Rate", 25);
		energyRate = prop.getInt();
		prop = cfg.get(Configuration.CATEGORY_GENERAL, "Recipe_Harder", false);
		recipeHarder = prop.getBoolean(true);
		prop = cfg.get(Configuration.CATEGORY_GENERAL, "Break_Particles_and_Sounds", true);
		breakEffect = prop.getBoolean(true);
		prop = cfg.get(Configuration.CATEGORY_GENERAL, "Use_BuildCraft_Frame_Render", false);
		bcFrameRenderer = prop.getBoolean(true);
		prop = cfg.get(Configuration.CATEGORY_GENERAL, "FillingID", "0,8,9,10,11,31,32,78");
		String[] str = prop.getString().split(",");
		try{
		for(String s : str)
			fillingSet.add(Integer.parseInt(s));
		}catch(NumberFormatException e){
			throw new RuntimeException("Printed By Advanced Filler:Wrong Config Option Format : FillingID" + 
										System.getProperty("line.separator") + 
										"Advanced Fillerにより出力:FillingIDオプションの記述法が間違っています。");
		}
		cfg.save();
	}
	
	//クァーリーよりコピペ、ワールドが読み込まれた時に範囲外のチャンクを読み込ませる
	public class AdvFillerChunkloadCallback implements ForgeChunkManager.OrderedLoadingCallback {
		@Override
		public void ticketsLoaded(List<Ticket> tickets, World world) {
			for (Ticket ticket : tickets) {
				int xCoord = ticket.getModData().getInteger("xCoord");
				int yCoord = ticket.getModData().getInteger("yCoord");
				int zCoord = ticket.getModData().getInteger("zCoord");
				ForgeChunkManager.forceChunk(ForgeChunkManager.requestTicket(AdvFiller.instance, world, Type.NORMAL), new ChunkCoordIntPair(xCoord >> 4, zCoord >> 4));
				ForgeChunkManager.releaseTicket(ticket);
			}
		}

		@Override
		public List<Ticket> ticketsLoaded(List<Ticket> tickets, World world, int maxTicketCount) {
			List<Ticket> validTickets = Lists.newArrayList();
			for (Ticket ticket : tickets) {
				int xCoord = ticket.getModData().getInteger("xCoord");
				int yCoord = ticket.getModData().getInteger("yCoord");
				int zCoord = ticket.getModData().getInteger("zCoord");
				
				int blId = world.getBlockId(xCoord, yCoord, zCoord);
				if (blId == AdvFiller.advFillerID) {
					validTickets.add(ticket);
				}
			}
			return validTickets;
		}

	}
	
	@Init
	public void load(FMLInitializationEvent event){
		ForgeChunkManager.setForcedChunkLoadingCallback(this, new AdvFillerChunkloadCallback());
		CommonProxy.proxy.registerRenderer();
		advFiller = new BlockAdvFiller(advFillerID, Material.iron).setCreativeTab(CreativeTabBuildCraft.tabBuildCraft).setUnlocalizedName("advfiller");
		GameRegistry.registerBlock(advFiller, "advfiller");
		GameRegistry.registerTileEntity(TileAdvFiller.class, "AdvancedFiller");
		LanguageRegistry.addName(advFiller, "Advanced Filler");
		LanguageRegistry.instance().addNameForObject(advFiller, "ja_JP", "フィラー改");
		redMarker = new BlockRedMarker(redMarkerID).setCreativeTab(CreativeTabBuildCraft.tabBuildCraft).setUnlocalizedName("redmarker");
		GameRegistry.registerBlock(redMarker, "redmarker");
		GameRegistry.registerTileEntity(TileRedMarker.class, "RedMarker");
		LanguageRegistry.addName(redMarker, "Transformation Marker");
		LanguageRegistry.instance().addNameForObject(redMarker, "ja_JP", "変換マーカー");
		if(recipeHarder){
			GameRegistry.addRecipe(new ItemStack(advFiller, 1), new Object[]{	"M",
																				"F",
																				"Q",
																				'M', BuildCraftBuilders.markerBlock,
																				'F', BuildCraftBuilders.fillerBlock,
																				'Q', BuildCraftFactory.quarryBlock});
		}else{
			GameRegistry.addRecipe(new ItemStack(advFiller, 1), new Object[]{	"IFI",
																				"GIG",
																				"DPD",
																				'I', BuildCraftCore.ironGearItem,
																				'G', BuildCraftCore.goldGearItem,
																				'D', BuildCraftCore.diamondGearItem,
																				'F', BuildCraftBuilders.fillerBlock,
																				'P', Item.pickaxeDiamond});
		}
		GameRegistry.addRecipe(new ItemStack(redMarker), new Object[]{	"R",
																		"M",
																		'R', Item.redstone,
																		'M', BuildCraftBuilders.markerBlock});
	}

}

