package hobbieco.test.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import hobbieco.test.config.annotation.MapperApiData;

/**
 * Data Source Configuration
 * SQLite - API Data
 *
 */
@Configuration
@MapperScan(value = {"hobbieco.test.*"}, annotationClass = MapperApiData.class, sqlSessionFactoryRef = "sqlSessionFactoryApiData")
@EnableTransactionManagement
public class DataSourceConfigApiData {
	
	@Bean(name = "dataSourceApiData")
    @ConfigurationProperties(prefix = "spring.datasource.api-data")
    DataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "sqlSessionFactoryApiData")
    SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceApiData") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:sql/api/**/*_api.xml"));
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:sql/mybatis_api.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplateApiData")
    SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactoryApiData") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    @Bean(name = "transactionApiData")
    DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSourceApiData") DataSource dataSource) {
    	DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
    	dataSourceTransactionManager.setDataSource(dataSource);
    	return dataSourceTransactionManager;
    }
}
