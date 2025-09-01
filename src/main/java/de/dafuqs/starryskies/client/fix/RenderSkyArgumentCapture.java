package de.dafuqs.starryskies.client.fix;

import net.minecraft.client.render.*;

// Workaround for the fact that FAPI does *not* provide frameGraphBuilder and framebufferSet to the API user
// which are REQUIRED for sky rendering (also capturing sky fog for accurate skybox rendering)
public interface RenderSkyArgumentCapture {
	DefaultFramebufferSet starrySkies$framebufferSet();
	
	FrameGraphBuilder starrySkies$frameGraphBuilder();
	
	Fog starrySkies$fog();
}
