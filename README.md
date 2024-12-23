# idePlugin

1、计时通知: 设置路径 File -> Settings -> UISettingsTimerConfig -> 通知时间间隔/s 

2、根据数据库表结构，生成实体类、mapper、service、controller等代码。

视图工具栏右边mybatisGenerator 

自定义配置文件文件目录：other/ 相关依赖 

```pom
<dependency>   
	<groupId>com.baomidou</groupId>   
	<artifactId>mybatis-plus-boot-starter</artifactId>  
 	<version>3.5.2</version>
</dependency>
<dependency>   
	<groupId>tk.mybatis</groupId>
 	<artifactId>mapper</artifactId>  
 	<version>4.1.5</version> 
</dependency> 
```

3、添加统计输出位置：鼠标右键，选择TimerOutputAction，输出统计信息。