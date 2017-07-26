# druidMultiDatabaseDemo
druid数据库连接池,配置多个数据库源

spring 2.5以后，spring 删除了JotmFactoryBean ，spring不再提供对jotm提供支持
spring atomikos 集成
atomikos需要的jar
atomikos-util-3.9.1.jar
transactions-3.9.1.jar
transactions-api-3.9.1.jar
transactions-jdbc-3.9.1.jar
transactions-jta-3.9.1.jar

db.properties
[java] view plaincopy

  1. datasource.type=oracle  
  2. datasource.driverClassName=oracle.jdbc.driver.OracleDriver  
  3.   
  4. datasource.url.read=jdbc\:oracle\:thin\:@172.16.20.129\:1521\:read  
  5. datasource.username.read=system  
  6. datasource.password.read=password  
  7.   
  8.   
  9. datasource.url.write=jdbc\:oracle\:thin\:@172.16.20.129\:1521\:write  
  10. datasource.username.write=system  
  11. datasource.password.write=password  

applicationContext.xml
[html] view plaincopy


  1. <?xml version="1.0" encoding="UTF-8"?>  
  2. <beans xmlns="http://www.springframework.org/schema/beans"  
  3.     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"  
  4.     xmlns:aop="http://www.springframework.org/schema/aop" xmlns:context="http://www.springframework.org/schema/context"  
  5.     xmlns:jee="http://www.springframework.org/schema/jee" xmlns:tx="http://www.springframework.org/schema/tx"  
  6.     xsi:schemaLocation="  
  7.             http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd  
  8.             http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd  
  9.             http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd  
  10.             http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-3.0.xsd  
  11.             http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.0.xsd">  
  12.   
  13. <!-- 数据源定义文件 -->  
  14.     <context:property-placeholder location="classpath:db.properties" />  
  15.   
  16.     <!-- 设置数据源属性 -->  
  17.     <bean id="dataSourceRead" class="com.atomikos.jdbc.AtomikosDataSourceBean"  
  18.         init-method="init" destroy-method="close">  
  19.         <property name="uniqueResourceName" value="read_resource" />    
  20.         <property name="xaDataSourceClassName" value="com.alibaba.druid.pool.xa.DruidXADataSource" />    
  21.     <property name="xaProperties">    
  22.         <props>    
  23.             <prop key="url">${datasource.url.read}</prop>    
  24.             <prop key="username">${datasource.username.read}</prop>    
  25.             <prop key="password">${datasource.password.read}</prop>    
  26.               
  27.         </props>    
  28.     </property>    
  29.     </bean>  
  30.   
  31.     <bean id="jdbcTemplateRead" class="org.springframework.jdbc.core.JdbcTemplate">  
  32.         <property name="dataSource" ref="dataSourceRead" />  
  33.     </bean>  
  34.       
  35.     <!-- 设置数据源属性 -->  
  36.     <bean id="dataSourceWrite" class="com.atomikos.jdbc.AtomikosDataSourceBean"  
  37.         init-method="init" destroy-method="close">  
  38.         <property name="uniqueResourceName" value="write_resource" />    
  39.         <property name="xaDataSourceClassName" value="com.alibaba.druid.pool.xa.DruidXADataSource" />    
  40.     <property name="xaProperties">    
  41.         <props>    
  42.             <prop key="url">${datasource.url.write}</prop>    
  43.             <prop key="username">${datasource.username.write}</prop>    
  44.             <prop key="password">${datasource.password.write}</prop>    
  45.               
  46.         </props>    
  47.     </property>    
  48.     </bean>  
  49.   
  50.     <bean id="jdbcTemplateWrite" class="org.springframework.jdbc.core.JdbcTemplate">  
  51.         <property name="dataSource" ref="dataSourceWrite" />  
  52.     </bean>  
  53.   
  54.      <!-- atomikos事务管理器 -->  
  55.     <bean id="atomikosTransactionManager" class="com.atomikos.icatch.jta.UserTransactionManager"   
  56.         init-method="init" destroy-method="close">  
  57.         <property name="forceShutdown">  
  58.             <value>true</value>  
  59.         </property>  
  60.     </bean>  
  61.   
  62.     <bean id="atomikosUserTransaction" class="com.atomikos.icatch.jta.UserTransactionImp">  
  63.         <property name="transactionTimeout" value="300" />  
  64.     </bean>  
  65.   
  66.     <!-- spring 事务管理器 -->  
  67.     <bean id="jtaTransactionManager"  
  68.         class="org.springframework.transaction.jta.JtaTransactionManager">  
  69.         <property name="transactionManager" ref="atomikosTransactionManager"/>  
  70.         <property name="userTransaction" ref="atomikosUserTransaction" />  
  71.         <property name="allowCustomIsolationLevels" value="true"/>   
  72.     </bean>  
  73.   
  74.   
  75.   
  76.     <!-- 配置事务传播特性 -->  
  77.     <tx:advice id="transactionAdvice" transaction-manager="jtaTransactionManager">  
  78.         <tx:attributes>  
  79.               
  80.             <tx:method name="save*" propagation="REQUIRED" rollback-for="Exception"/>  
  81.             <tx:method name="update*" propagation="REQUIRED" rollback-for="Exception"/>  
  82.             <tx:method name="move*" propagation="REQUIRED" rollback-for="Exception"/>  
  83.             <tx:method name="*" propagation="SUPPORTS" read-only="true"/>  
  84.         </tx:attributes>  
  85.     </tx:advice>  
  86.   
  87.     <!-- 配置相关类的方法参与事务 -->  
  88.     <aop:config>  
  89.         <aop:advisor pointcut="execution(* service.*.*(..))"  
  90.             advice-ref="transactionAdvice" />  
  91.     </aop:config>  
  92.   
  93.     <!--spring会按照这个包路径进行扫描 -->  
  94.     <context:component-scan base-package="*" />  
  95.   
  96.     <!-- 使用注解方式定义事务 -->  
  97.     <tx:annotation-driven transaction-manager="jtaTransactionManager" />  
  98.   
  99.     <!-- 使用AspectJ方式配置AOP -->  
  100.     <aop:aspectj-autoproxy proxy-target-class="true" />  
  101.       
  102. </beans>  

transactions.properties

[html] view plaincopy


  1. # SAMPLE PROPERTIES FILE FOR THE TRANSACTION SERVICE  
  2. # THIS FILE ILLUSTRATES THE DIFFERENT SETTINGS FOR THE TRANSACTION MANAGER  
  3. # UNCOMMENT THE ASSIGNMENTS TO OVERRIDE DEFAULT VALUES;  
  4.   
  5. # Required: factory implementation class of the transaction core.  
  6. # NOTE: there is no default for this, so it MUST be specified!   
  7. #   
  8. com.atomikos.icatch.service=com.atomikos.icatch.standalone.UserTransactionServiceFactory  
  9.   
  10.       
  11. # Set base name of file where messages are output   
  12. # (also known as the 'console file').  
  13. #  
  14. # com.atomikos.icatch.console_file_name = tm.out  
  15.   
  16. # Size limit (in bytes) for the console file;  
  17. # negative means unlimited.  
  18. #  
  19. # com.atomikos.icatch.console_file_limit=-1  
  20.   
  21. # For size-limited console files, this option  
  22. # specifies a number of rotating files to   
  23. # maintain.  
  24. #  
  25. # com.atomikos.icatch.console_file_count=1  
  26.   
  27. # Set the number of log writes between checkpoints  
  28. #  
  29. # com.atomikos.icatch.checkpoint_interval=500  
  30.   
  31. # Set output directory where console file and other files are to be put  
  32. # make sure this directory exists!  
  33. #  
  34. # com.atomikos.icatch.output_dir = ./  
  35.   
  36. # Set directory of log files; make sure this directory exists!  
  37. #  
  38. # com.atomikos.icatch.log_base_dir = ./  
  39.   
  40. # Set base name of log file  
  41. # this name will be  used as the first part of   
  42. # the system-generated log file name  
  43. #  
  44. # com.atomikos.icatch.log_base_name = tmlog  
  45.   
  46. # Set the max number of active local transactions   
  47. # or -1 for unlimited.  
  48. #  
  49. # com.atomikos.icatch.max_actives = 50  
  50.   
  51. # Set the default timeout (in milliseconds) for local transactions  
  52. #  
  53. # com.atomikos.icatch.default_jta_timeout = 10000  
  54.   
  55. # Set the max timeout (in milliseconds) for local transactions  
  56. #  
  57. # com.atomikos.icatch.max_timeout = 300000  
  58.   
  59. # The globally unique name of this transaction manager process  
  60. # override this value with a globally unique name  
  61. #  
  62. # com.atomikos.icatch.tm_unique_name = tm  
  63.   
  64. # Do we want to use parallel subtransactions? JTA's default  
  65. # is NO for J2EE compatibility  
  66. #  
  67. # com.atomikos.icatch.serial_jta_transactions=true  
  68.                   
  69. # If you want to do explicit resource registration then  
  70. # you need to set this value to false.  
  71. #  
  72. # com.atomikos.icatch.automatic_resource_registration=true    
  73.   
  74. # Set this to WARN, INFO or DEBUG to control the granularity  
  75. # of output to the console file.  
  76. #  
  77. # com.atomikos.icatch.console_log_level=WARN  
  78.   
  79. # Do you want transaction logging to be enabled or not?  
  80. # If set to false, then no logging overhead will be done  
  81. # at the risk of losing data after restart or crash.  
  82. #  
  83. # com.atomikos.icatch.enable_logging=true  
  84.   
  85. # Should two-phase commit be done in (multi-)threaded mode or not?  
  86. # Set this to false if you want commits to be ordered according  
  87. # to the order in which resources are added to the transaction.  
  88. #  
  89. # NOTE: threads are reused on JDK 1.5 or higher.   
  90. # For JDK 1.4, thread reuse is enabled as soon as the   
  91. # concurrent backport is in the classpath - see   
  92. # http://mirrors.ibiblio.org/pub/mirrors/maven2/backport-util-concurrent/backport-util-concurrent/  
  93. #  
  94. # com.atomikos.icatch.threaded_2pc=false  
  95.   
  96. # Should shutdown of the VM trigger shutdown of the transaction core too?  
  97. #  
  98. # com.atomikos.icatch.force_shutdown_on_vm_exit=false  

dao

[java] view plaincopy


  1. package dao.impl;  
  2.   
  3. import org.springframework.beans.factory.annotation.Autowired;  
  4. import org.springframework.jdbc.core.JdbcTemplate;  
  5. import org.springframework.stereotype.Repository;  
  6.   
  7. import bean.User;  
  8.   
  9. @Repository("userDaoImplRead")  
  10. public class UserDaoImplRead extends UserDaoAbs{  
  11.   
  12.     @Autowired  
  13.     JdbcTemplate jdbcTemplateRead;  
  14.       
  15.     //方法  
  16.   
  17. }  

[java] view plaincopy


  1. package dao.impl;  
  2.   
  3. import org.springframework.beans.factory.annotation.Autowired;  
  4. import org.springframework.jdbc.core.JdbcTemplate;  
  5. import org.springframework.stereotype.Repository;  
  6.   
  7. import bean.User;  
  8.   
  9. @Repository("userDaoImplWrite")  
  10. public class UserDaoImplWrite extends UserDaoAbs{  
  11.   
  12.     @Autowired  
  13.     JdbcTemplate jdbcTemplateWrite;  
  14.     //方法  
  15. }  



配置多数据源及多个的jdbcTemple
在applicationContext.xml中配置了一个新的DataSource，同时，添加一个新的jdbcTemple，代码如下：
<bean id="hrjdbcTemple" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="hrdataSource"/>
</bean>
但是，在项目启动时，报了这样的错误，如下：
expected single matching bean but found 2:  jdbcTemple， hrjdbcTemple
起初，怀疑是datasource的问题，但是不过我如何尝试，都说明datasource是没有任何问题的。于是，从jdbcTemple入手，查找了下网上资料，实例化jdbcTemple大概有三种，由于项目是用扫描注入的，所以有些方法就用不了。于是，从配置注入的想法暂时取消，上面配置只好暂时作罢，先注释掉了。以下是我从代码中实例化jdbcTemple的方法，如下：
    private JdbcTemplate hrjdbcTemplate;

    @Autowired
    public void setHrjdbcTemplate(@Qualifier("hrdataSource") DataSource dataSource) {
        this.hrjdbcTemplate = new JdbcTemplate(dataSource);
    }
此代码是直接写在service中的。
说明：
  ● 为了跟项目中原有的jdbcTemple做区分，这里用hrjdbcTemplate，实例其实是一样的。
  ● 这里要注意的是DataSource，这个是比较关键的，@Qualifier(“hrdataSource”)
  ● 这个是配置文件中配置的新数据源，在这里明确指定。 
关于DataSource的导入jar，也是个问题，因为配置文件中使用的是tomcat的pool下的DataSource，所以这里也使用该jar，但是编译就一直报错，查看了下jar都有。后来改成了Java.sql下面的jar，就OK了。原因未知！
虽然，目前是可以正常运行，但是对于以上情况也是一知半解的。
