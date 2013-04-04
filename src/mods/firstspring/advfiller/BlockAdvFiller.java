package mods.firstspring.advfiller;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.core.Position;
import buildcraft.api.tools.IToolWrench;
import buildcraft.core.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAdvFiller extends BlockContainer {
	
	Icon textureTop;
	Icon textureSide;
	Icon textureFrontOn;
	Icon textureFrontOff;

	public BlockAdvFiller(int par1, Material par2Material) {
		super(par1, par2Material);
		setHardness(0.7F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileAdvFiller();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("all")
	public Icon getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		int m = iblockaccess.getBlockMetadata(i, j, k);

		if (iblockaccess == null)
			return getBlockTextureFromSideAndMetadata(i, m);

		TileEntity tile = iblockaccess.getBlockTileEntity(i, j, k);

		if (tile != null && tile instanceof TileAdvFiller) {
			TileAdvFiller filler = (TileAdvFiller) tile;
			if (l == m) {
				if (!filler.isActive())
					return textureFrontOff;
				else
					return textureFrontOn;
			}
		}
		return getBlockTextureFromSideAndMetadata(l, m);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata(int i, int j) {
		// If no metadata is set, then this is an icon.
		if (j == 0 && i == 3)
			return textureFrontOn;

		if (i == j)
			return textureFrontOn;

		switch (i) {
		case 1:
			return textureTop;
		default:
			return textureSide;
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving, ItemStack is) {
		super.onBlockPlacedBy(world, i, j, k, entityliving, is);
		if(entityliving == null)
			return;
		if(world.isRemote)
			return;
		ForgeDirection orientation = Utils.get2dOrientation(new Position(entityliving.posX, entityliving.posY, entityliving.posZ), new Position(i, j, k));
		world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal(), 3);
		TileAdvFiller tile = (TileAdvFiller)world.getBlockTileEntity(i, j, k);
		tile.player = (EntityPlayer)entityliving;
		tile.orient = orientation;
		tile.placed();
		tile.preInit();
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j,
			int k, EntityPlayer entityplayer, int par6, float par7,
			float par8, float par9) {
		ItemStack is = entityplayer.getCurrentEquippedItem();
		if(is != null && is.getItem() instanceof IToolWrench && ((IToolWrench)is.getItem()).canWrench(entityplayer, i, j, k)){
			if(world.isRemote)
				return true;
			TileAdvFiller tile = (TileAdvFiller)world.getBlockTileEntity(i, j, k);
			tile.player = null;
			tile.doLoop = false;
			if(tile.initializeThread != null)
				tile.initializeThread.stop();
			tile.setDisable();
			tile.initializeThread = new Thread(new AdvFillerInitializeThread(tile));
			tile.initializeThread.start();
			//クライアントにパケット送信
			entityplayer.addChatMessage("AdvFiller : Start Initialize");
			world.markBlockForUpdate(i, j, k);
			return true;
		}
		if(!world.isRemote)
			return true;
		TileAdvFiller tile = (TileAdvFiller)world.getBlockTileEntity(i, j, k);
		CommonProxy.proxy.openGui(i, j, k, tile.left, tile.right, tile.up, tile.down, tile.forward, tile.type, tile.loopMode, tile.removeModeIteration, tile.removeModeDrop);
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir){
		textureTop = ir.registerIcon("firstspring/advfiller:filler_top");
		textureSide = ir.registerIcon("firstspring/advfiller:filler_side");
		textureFrontOn = ir.registerIcon("firstspring/advfiller:filler_front_on");
		textureFrontOff = ir.registerIcon("firstspring/advfiller:filler_front_off");
	}

}
