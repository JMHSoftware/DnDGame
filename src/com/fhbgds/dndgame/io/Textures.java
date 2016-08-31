package com.fhbgds.dndgame.io;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Textures {
	
	public static int decodePNGIntoTexture(String name){
		ByteBuffer buf = null;
		try {
			System.out.println("Attempting to load \"" + name + ".png\"");
		    InputStream in = new FileInputStream("textures/" + name + ".png");
		    PNGDecoder decoder = new PNGDecoder(in);
		     
		    buf = ByteBuffer.allocateDirect(
		            4 * decoder.getWidth() * decoder.getHeight());
		    decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
		    buf.flip();
		     
		    in.close();
		    
		    int texId = GL11.glGenTextures();
		    GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
		    GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		    
		    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		    
		    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		    
		    System.out.println("Success, " + texId);
		    return texId;
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return -1;
	}
	
}
