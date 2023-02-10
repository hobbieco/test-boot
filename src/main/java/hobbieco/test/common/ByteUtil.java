package hobbieco.test.common;

import java.nio.ByteBuffer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Byte Util
 *
 */
@Slf4j
@Component
public class ByteUtil {

	/**
	 * 문자열 앞에 byte로 추가
	 * @return
	 * @throws Exception
	 */
	public String lpadByte(String str, int size, String pad, String encoding) throws Exception {
		String result = null;
		if (StringUtils.isEmpty(str)) {
			String tmp = rpadByte(str, size, new StringBuilder(pad).reverse().toString(), encoding);
			result = new StringBuilder(tmp).reverse().toString();
		} else {
			byte[] b = str.getBytes(encoding);
			if (b.length == size) {
				result = str;
			} else if (b.length < size) {
				String tmp = rpadByte(new StringBuilder(str).reverse().toString(), size, new StringBuilder(pad).reverse().toString(), encoding);
				result = new StringBuilder(tmp).reverse().toString();
			} else {
				result = new String(b, b.length-size, b.length, encoding);
			}
		}
		return result;
	}

	/**
	 * 문자열 뒤에 byte로 추가
	 * @return
	 * @throws Exception
	 */
	public String rpadByte(String str, int size, String pad, String encoding) throws Exception {
		String result = null;
		if (StringUtils.isEmpty(str)) {
			byte[] pb = pad.getBytes(encoding);
			ByteBuffer bb = ByteBuffer.allocate(size);
			while(bb.hasRemaining()) {
				int s = size - bb.position();
				if (s < pb.length) {
					for (int i=0; i<s; i++) {
						bb.put(pb[i]);
					}
				} else {
					bb.put(pad.getBytes(encoding));
				}
			}
			result = new String(bb.array(), 0, size, encoding);
		} else {
			byte[] b = str.getBytes(encoding);
			if (b.length == size) {
				result = str;
			} else if (b.length < size) {
				byte[] pb = pad.getBytes(encoding);
				ByteBuffer bb = ByteBuffer.allocate(size);
				bb.put(b);
				while(bb.hasRemaining()) {
					int s = size - bb.position();
					if (s < pb.length) {
						for (int i=0; i<s; i++) {
							bb.put(pb[i]);
						}
					} else {
						bb.put(pad.getBytes(encoding));
					}
				}
				result = new String(bb.array(), 0, size, encoding);
			} else {
				result = new String(b, 0, size, encoding);
			}
		}
		return result;
	}
}
