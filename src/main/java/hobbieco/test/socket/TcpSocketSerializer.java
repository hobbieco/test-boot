package hobbieco.test.socket;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.core.serializer.Serializer;
import org.springframework.stereotype.Component;

/**
 * 
 *
 */
@Component
public class TcpSocketSerializer implements Serializer<byte[]> {
	
	@Override
	public void serialize(byte[] bytes, OutputStream outputStream) throws IOException {
		outputStream.write(bytes, 0, bytes.length);
	}
}
