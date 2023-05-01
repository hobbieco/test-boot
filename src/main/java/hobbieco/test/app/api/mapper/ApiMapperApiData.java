package hobbieco.test.app.api.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import hobbieco.test.config.annotation.MapperApiData;

@MapperApiData
public interface ApiMapperApiData {

	public String testSelect();
	
	public int testInsert(Map<String,Object> params);
	
	public int selectLock(@Param("name") String name, @Param("se") String se);
	public int insertLock(@Param("name") String name, @Param("se") String se);
}
