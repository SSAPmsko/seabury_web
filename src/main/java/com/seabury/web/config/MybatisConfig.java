package com.seabury.web.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
public class MybatisConfig {
    @Autowired
    @Qualifier(value = "integratedDataSource")
    private DataSource integratedDataSource;

    @Autowired
    @Qualifier(value = "dataSource")
    private DataSource dataSource;


    @Bean
    public SqlSessionFactory integratedSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(integratedDataSource);

        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/integrated/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);
        //sqlSessionFactoryBean.setTypeAliasesPackage("com.seabury.web.integrated.*.model");

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.setMapUnderscoreToCamelCase(false);

        return sqlSessionFactory;
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/dose/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);
        //sqlSessionFactoryBean.setTypeAliasesPackage("com.seabury.web.dose.*.model");

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.setMapUnderscoreToCamelCase(false);

        return sqlSessionFactory;
    }

    @Bean
    public SqlSession integratedSqlSession() throws Exception {
        return new SqlSessionTemplate(integratedSqlSessionFactoryBean());
    }

    @Bean
    @Primary
    public SqlSession sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryBean());
    }
}
