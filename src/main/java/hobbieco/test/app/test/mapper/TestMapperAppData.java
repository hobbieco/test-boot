package hobbieco.test.app.test.mapper;

import java.util.Map;

import hobbieco.test.config.annotation.MapperAppData;

@MapperAppData
public interface TestMapperAppData {

	public String testSelect();
	
	public int testInsert(Map<String,Object> params);
}
