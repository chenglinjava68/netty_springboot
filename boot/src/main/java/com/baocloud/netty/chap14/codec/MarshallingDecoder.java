package com.baocloud.netty.chap14.codec;

import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import io.netty.buffer.ByteBuf;

public class MarshallingDecoder {
	private final Unmarshaller unmarshaller;

	public MarshallingDecoder() throws Exception {
		unmarshaller = MarshallingCodecFactory.buildUnMarshalling();
	}
	
	protected Object decode(ByteBuf in) throws Exception{
		int objectSize=in.readInt();
		ByteBuf buf=in.slice(in.readerIndex(), objectSize);
		ByteInput input=new ChannelBufferByteInput(buf);
		try {
			unmarshaller.start(input);
			Object obj=unmarshaller.readObject();
			unmarshaller.finish();
			in.readerIndex(in.readerIndex()+objectSize);
			
			return obj;
		} finally {
			unmarshaller.close();
		}
		
		
	}

}
