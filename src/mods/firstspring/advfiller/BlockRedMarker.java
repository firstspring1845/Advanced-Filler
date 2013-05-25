package mods.firstspring.advfiller;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import buildcraft.builders.BlockMarker;
import buildcraft.builders.BuildersProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRedMarker extends BlockMarker
{

	public BlockRedMarker(int i)
	{
		super(i);
	}

	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return new TileRedMarker();
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int side)
	{
		if (!BuildersProxy.canPlaceTorch(world, x, y - 1, z))
		{
			this.dropBlockAsItem(world, x, y, z, 0, 0);
			world.setBlock(x, y, z, 0);
		}
	}

	// 下向きにしか置けなくする
	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		return BuildersProxy.canPlaceTorch(world, i, j - 1, k);
	}

	@Override
	// 方向合わせ、マーカーのプログラムって色々とおかしい気がするすごくする
	public void onBlockAdded(World world, int x, int y, int z)
	{
		world.setBlockMetadataWithNotify(x, y, z, 5, 1);
		onNeighborBlockChange(world, x, y, z, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		blockIcon = ir.registerIcon("firstspring/advfiller:redMarker");
	}

}
