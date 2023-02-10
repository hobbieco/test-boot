package hobbieco.test.app.api.mapper;

import java.util.Map;

import hobbieco.test.config.annotation.MapperAppData;

@MapperAppData
public interface ApiMapperAppData {

	public String testSelect();
	
	public int testInsert(Map<String,Object> params);
}
