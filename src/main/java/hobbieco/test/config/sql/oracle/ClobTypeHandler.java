package hobbieco.test.config.sql.oracle;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class ClobTypeHandler extends BaseTypeHandler<String> {

	  @Override
	  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
	      throws SQLException {
	    StringReader reader = new StringReader(parameter);
	    ps.setCharacterStream(i, reader, parameter.length());
	  }

	  @Override
	  public String getNullableResult(ResultSet rs, String columnName)
	      throws SQLException {
		  Clob clob = rs.getClob(columnName);
	    return toString(clob);
	  }

	  @Override
	  public String getNullableResult(ResultSet rs, int columnIndex)
	      throws SQLException {
		  Clob clob = rs.getClob(columnIndex);
	    return toString(clob);
	  }

	  @Override
	  public String getNullableResult(CallableStatement cs, int columnIndex)
	      throws SQLException {
		  Clob clob = cs.getClob(columnIndex);
	    return toString(clob);
	  }

	  private String toString(Clob clob) throws SQLException {
	    return clob == null ? null : clob.getSubString(1, (int) clob.length());
	  }

}
