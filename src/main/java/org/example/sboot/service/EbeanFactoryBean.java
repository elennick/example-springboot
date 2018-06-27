package org.example.sboot.service;

import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import io.ebean.spring.txn.SpringJdbcTransactionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
//import io.ebean.springsupport.txn.SpringAwareJdbcTransactionManager;

/**
 * Spring factory for creating the EbeanServer singleton.
 */
@Component
public class EbeanFactoryBean implements FactoryBean<EbeanServer> {

  @Autowired
  private CurrentUser currentUser;

  @Autowired
  private DataSource dataSource;

  @Override
  public EbeanServer getObject() {

    ServerConfig config = new ServerConfig();
    config.setName("db");
    config.setCurrentUserProvider(currentUser);

    config.setDataSource(dataSource);
    config.setExternalTransactionManager(new SpringJdbcTransactionManager());
    config.loadFromProperties();
    config.loadTestProperties(); //todo what does this get replaced with?

    return EbeanServerFactory.create(config);
  }

  @Override
  public Class<?> getObjectType() {
    return EbeanServer.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }
}
