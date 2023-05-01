package hobbieco.test.app.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import hobbieco.test.config.annotation.MapperApiData;

@MapperApiData
public interface AwxMapperApiData {

	public int updateSe(@Param("targetData") String targetData, @Param("se") String se, @Param("targetSe") String targetSe);
	
	public int insertData(@Param("targetData") String targetData, List<Map<String,Object>> list);
	
	public List<Map<String,String>> selectData(@Param("targetData") String targetData, @Param("orderBy") String orderBy);
}
