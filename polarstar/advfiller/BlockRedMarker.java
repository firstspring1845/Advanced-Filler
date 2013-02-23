package polarstar.advfiller;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import buildcraft.builders.BlockMarker;
import buildcraft.builders.BuildersProxy;

public class BlockRedMarker extends BlockMarker {

	public BlockRedMarker(int i) {
		super(i);
		blockIndexInTexture = 32;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileRedMarker();
	}
	
	@Override
	public String getTextureFile() {
		return "/polarstar/advfiller/sprite/advfiller.png";
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int side){
		if(!BuildersProxy.canPlaceTorch(world, x, y - 1, z)){
			this.dropBlockAsItem(world, x, y, z, 0, 0);
			world.setBlockWithNotify(x, y, z, 0);
		}
	}
	
	//下向きにしか置けなくする
	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return BuildersProxy.canPlaceTorch(world, i, j - 1, k);
	}
	
	@Override
	//方向合わせ、マーカーのプログラムって色々とおかしい気がするすごくする
	public void onBlockAdded(World world, int x, int y, int z){
		world.setBlockMetadataWithNotify(x, y, z, 5);
		onNeighborBlockChange(world, x, y, z, 0);
	}

}
