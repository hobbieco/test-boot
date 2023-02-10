package hobbieco.test.socket;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TCP Input Byte Format
 *
 */
@Getter
@AllArgsConstructor
public enum TcpInput {

	HEAD_LENGTH(0, 12, "length", "-", Arrays.asList("key1","key2")),
	HEAD_TITLE(12, 30, "title", StringUtils.SPACE, null),
	BODY_CONTENT(42, 100, "content", null, null)
	;
	
	// Offset
	private int offset;
	// Length
	private int length;
	// JSON Key
	private String key;
	// split separator
	private String separator;
	// split result JSON key
	private List<String> keys;
	
}
