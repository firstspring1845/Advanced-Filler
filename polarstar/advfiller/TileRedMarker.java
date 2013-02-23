package polarstar.advfiller;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import buildcraft.api.core.IAreaProvider;
import buildcraft.builders.TileMarker;

public class TileRedMarker extends TileMarker implements IAreaProvider {
	int xmin,ymin,zmin,xmax,ymax,zmax;
	@Override
	public int xMin() {
		return xmin;
	}

	@Override
	public int yMin() {
		return ymin;
	}

	@Override
	public int zMin() {
		return zmin;
	}

	@Override
	public int xMax() {
		return xmax;
	}

	@Override
	public int yMax() {
		return ymax;
	}

	@Override
	public int zMax() {
		return zmax;
	}

	@Override
	public void removeFromWorld() {
		worldObj.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
		AdvFiller.redMarker.dropBlockAsItem(worldObj, xCoord, yCoord, zCoord, 0, 0);
	}
	
	@Override
	public void updateEntity(){
		if(worldObj.isRemote)
			return;
		List<TileEntity> tile = getNeighborTileEntityList();
		TileAdvFiller filler = null;
		for(TileEntity te : tile)
			if(te instanceof TileAdvFiller)
				filler = (TileAdvFiller)te;
		if(filler == null)
			return;
		xmin = filler.fromX;
		ymin = filler.fromY;
		zmin = filler.fromZ;
		xmax = filler.toX;
		ymax = filler.toY;
		zmax = filler.toZ;
	}
	
	public List<TileEntity> getNeighborTileEntityList(){
		ArrayList<TileEntity> list = new ArrayList();
		if(worldObj == null)
			return list;
		TileEntity[] tile = new TileEntity[6];
		tile[0] = worldObj.getBlockTileEntity(xCoord-1, yCoord, zCoord);
		tile[1] = worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord);
		tile[2] = worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
		tile[3] = worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord);
		tile[4] = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord+1);
		tile[5] = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord-1);
		for(TileEntity tileBuf:tile)
			if(tileBuf != null)
				list.add(tileBuf);
		return list;
	}

}
