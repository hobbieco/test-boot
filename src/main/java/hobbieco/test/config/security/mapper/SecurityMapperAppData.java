package hobbieco.test.config.security.mapper;

import java.util.Map;

import hobbieco.test.config.annotation.MapperAppData;

@MapperAppData
public interface SecurityMapperAppData {

	public Map<String,String> selectUser();
}
