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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import hobbieco.test.config.annotation.MapperAppData;

/**
 * Data Source Configuration
 * PostgreSQL - Application Data
 *
 */
@Configuration
@MapperScan(value = {"hobbieco.test.*"}, annotationClass = MapperAppData.class, sqlSessionFactoryRef = "sqlSessionFactoryAppData")
@EnableTransactionManagement
public class DataSourceConfigAppData {
	
	@Bean(name = "dataSourceAppData")
    @ConfigurationProperties(prefix = "spring.datasource.app-data")
    public DataSource dataSourceMaria() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "sqlSessionFactoryAppData")
    public SqlSessionFactory sqlSessionFactoryMaria(@Qualifier("dataSourceAppData") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:sql/data/**/*_data.xml"));
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:sql/mybatis_data.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplateAppData")
    public SqlSessionTemplate sqlSessionTemplateMaria(@Qualifier("sqlSessionFactoryAppData") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
