/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

/**
 * Convertit de mp4 à mp3 en utilisant Xuggle
 * @author nahel
 */
import com.xuggle.mediatool.IMediaReader; 
import com.xuggle.mediatool.IMediaWriter; 
import com.xuggle.mediatool.ToolFactory; 
import com.xuggle.xuggler.ICodec; 

public class VideoToAudio{

    public void convertVideoToAudio(String source, String dest){
        
        IMediaReader reader = ToolFactory.makeReader(source);
        IMediaWriter writer = ToolFactory.makeWriter(dest,reader);

        int sampleRate = 44100;
        int channels = 1;

        writer.addAudioStream(0, 0, ICodec.ID.CODEC_ID_MP3, channels, sampleRate);
        reader.addListener(writer);

        while (reader.readPacket() == null);
    }

}