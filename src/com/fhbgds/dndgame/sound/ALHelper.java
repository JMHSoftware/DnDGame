package com.fhbgds.dndgame.sound;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class ALHelper {

String soundLoc = "files";
	
	String[] files = new String[] {soundLoc + "/menu.wav", soundLoc + "/click.wav"};
	IntBuffer[] sources = new IntBuffer[getFiles().length];
	IntBuffer[] buffers = new IntBuffer[getFiles().length];
	FloatBuffer sourcePos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	FloatBuffer sourceVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	FloatBuffer listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	FloatBuffer listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	FloatBuffer listenerOri = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f }).rewind();
	
	HashMap<String, IntBuffer> sourceMap = new HashMap<String, IntBuffer>();

	public void initAL(){
		try{
			AL.create();
			for(int i = 0; i < getFiles().length; i++){
				getSources()[i] = BufferUtils.createIntBuffer(1);
				buffers[i] = BufferUtils.createIntBuffer(1);
			}
			loadALData();
			AL10.alGetError();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		AL10.alGetError();
		setListenerValues();
		sourceMap.put("menu", getSources()[0]);
		sourceMap.put("click", getSources()[1]);
	}
	
	public void killALData() {
		for(int i = 0; i < getFiles().length; i++) {
			AL10.alSourceStop(getSources()[i].get(0));
		}
		for(int i = 0; i < getFiles().length; i++){
			AL10.alDeleteSources(getSources()[i]);
			AL10.alDeleteBuffers(buffers[i]);
		}
		AL.destroy();
	}
	
	void setListenerValues() {
		AL10.alListener(AL10.AL_POSITION   , listenerPos);
		AL10.alListener(AL10.AL_VELOCITY   , listenerVel);
		AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
	}
	
	public int loadALData() throws Exception {
		for(int i = 0; i < getFiles().length; i++){
			if(buffers[i] == null) buffers[i] = BufferUtils.createIntBuffer(1);
			AL10.alGenBuffers(buffers[i]);
			InputStream in = null;
			BufferedInputStream bin = null;
			try {
				in = ALHelper.class.getResourceAsStream(getFiles()[i]);
				bin = new BufferedInputStream(in);
				WaveData wavFile = WaveData.create(bin);
				AL10.alBufferData(buffers[i].get(0), wavFile.format, wavFile.data, wavFile.samplerate);
				wavFile.dispose();
			} catch (Exception e) {
				e.printStackTrace();
				return AL10.AL_FALSE;
			} finally {
				if(in != null)in.close();
				if(bin != null)bin.close();
			}
			
			if(getSources()[i] == null) getSources()[i] = BufferUtils.createIntBuffer(1);
			AL10.alGenSources(getSources()[i]);
		}
		if (AL10.alGetError() == AL10.AL_NO_ERROR) return AL10.AL_TRUE;
		return AL10.AL_FALSE;
	}
	
	public void playSound(String name, float pitch, float gain, boolean loop){
		IntBuffer source = sourceMap.get(name);
		if(source == null){
			System.err.println("Invalid sound name: \"" + name + "\"");
			return;
		}
		int count = 0;
		while(source != getSources()[count]){
			count++;
		}
		AL10.alSourcei(source.get(0), AL10.AL_BUFFER,   buffers[count].get(0));
		AL10.alSourcef(source.get(0), AL10.AL_PITCH,    pitch    			 );
		AL10.alSourcef(source.get(0), AL10.AL_GAIN,     gain     			 );
		AL10.alSource (source.get(0), AL10.AL_POSITION, sourcePos			 );
		AL10.alSource (source.get(0), AL10.AL_VELOCITY, sourceVel			 );
		if(loop){
			AL10.alSourcei(source.get(0), AL10.AL_LOOPING, AL10.AL_TRUE);
		}
		AL10.alSourcePlay(source);
	}

	public IntBuffer[] getSources() {
		return sources;
	}
	
	public String[] getFiles() {
		return files;
	}
	
}
