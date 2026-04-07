package de.dafuqs.starryskies.client;

import de.dafuqs.starryskies.StarrySkies;
import net.minecraft.resources.Identifier;

public enum StarrySkyBoxTextures {
		INSTANCE;

		public Identifier UP;
		public Identifier DOWN;
		public Identifier WEST;
		public Identifier EAST;
		public Identifier NORTH;
		public Identifier SOUTH;
		
		public void set(String up, String down, String west, String east, String north, String south) {
			UP = StarrySkies.id(up);
			DOWN = StarrySkies.id(down);
			WEST = StarrySkies.id(west);
			EAST = StarrySkies.id(east);
			NORTH = StarrySkies.id(north);
			SOUTH = StarrySkies.id(south);
		}
	}