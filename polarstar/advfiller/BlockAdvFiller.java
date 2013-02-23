package polarstar.advfiller;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.core.Position;
import buildcraft.api.tools.IToolWrench;
import buildcraft.core.utils.Utils;

public class BlockAdvFiller extends BlockContainer {
	
	final int textureTop = 1;
	final int textureSide = 2;
	int textureFrontOn = 16;
	int textureFrontOff = 16 + 1;

	public BlockAdvFiller(int par1, Material par2Material) {
		super(par1, par2Material);
		setHardness(0.7F);
		if(AdvFiller.useOldTexture){
			textureFrontOn = 0;
			textureFrontOff = 3;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileAdvFiller();
	}
	
	@Override
	public String getTextureFile(){
		return "/polarstar/advfiller/sprite/advfiller.png";
	}
	
	@SuppressWarnings("all")
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l) {
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
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
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
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		super.onBlockPlacedBy(world, i, j, k, entityliving);
		if(entityliving == null)
			return;
		if(world.isRemote)
			return;
		ForgeDirection orientation = Utils.get2dOrientation(new Position(entityliving.posX, entityliving.posY, entityliving.posZ), new Position(i, j, k));
		world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal());
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

}
