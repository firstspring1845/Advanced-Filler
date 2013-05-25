/**
 * This is not original source
 * Reduced unused method and changes double to int
 * by FirstSpring@Polarstar
 */

/**
 * Copyright (c) SpaceToad, 2011
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package mods.firstspring.advfiller;

import net.minecraftforge.common.ForgeDirection;

public class Position
{

	public int x, y, z;
	public ForgeDirection orientation;

	public Position(int ci, int cj, int ck)
	{
		x = ci;
		y = cj;
		z = ck;
		orientation = ForgeDirection.UNKNOWN;
	}

	public Position(int ci, int cj, int ck, ForgeDirection corientation)
	{
		x = ci;
		y = cj;
		z = ck;
		orientation = corientation;
	}

	public Position(Position p)
	{
		x = p.x;
		y = p.y;
		z = p.z;
		orientation = p.orientation;
	}

	public void moveRight(int step)
	{
		switch (orientation)
		{
		case SOUTH:
			x = x - step;
			break;
		case NORTH:
			x = x + step;
			break;
		case EAST:
			z = z + step;
			break;
		case WEST:
			z = z - step;
			break;
		default:
		}
	}

	public void moveLeft(int step)
	{
		moveRight(-step);
	}

	public void moveForwards(int step)
	{
		switch (orientation)
		{
		case UP:
			y = y + step;
			break;
		case DOWN:
			y = y - step;
			break;
		case SOUTH:
			z = z + step;
			break;
		case NORTH:
			z = z - step;
			break;
		case EAST:
			x = x + step;
			break;
		case WEST:
			x = x - step;
			break;
		default:
		}
	}

	public void moveBackwards(int step)
	{
		moveForwards(-step);
	}

	public void moveUp(int step)
	{
		switch (orientation)
		{
		case SOUTH:
		case NORTH:
		case EAST:
		case WEST:
			y = y + step;
			break;
		default:
		}

	}

	public void moveDown(int step)
	{
		moveUp(-step);
	}

	@Override
	public String toString()
	{
		return "{" + x + ", " + y + ", " + z + "}";
	}

	public Position min(Position p)
	{
		return new Position(p.x > x ? x : p.x, p.y > y ? y : p.y, p.z > z ? z : p.z);
	}

	public Position max(Position p)
	{
		return new Position(p.x < x ? x : p.x, p.y < y ? y : p.y, p.z < z ? z : p.z);
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Position)
		{
			Position p = (Position) o;
			return p.x == x && p.y == y && p.z == z;
		}
		return false;
	}

	// hashCodeが一致する場合はequalsは一致してもしなくてもOK
	// equalsで一致する場合は hashCode も「必ず」一致する必要があるぽよ。
	// Hash系コレクションで使わないならまず大丈夫だけど。
	// thanks to alalwww
	@Override
	public int hashCode()
	{
		return x * y * z;
	}

}
