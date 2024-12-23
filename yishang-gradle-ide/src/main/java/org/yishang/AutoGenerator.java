package org.yishang;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AutoGenerator {

	public static String packageName = "org.yishang";
	public static String modelType = "";

	public static void main(String[] args) {
		String url = "jdbc:mysql://10.17.10.202:3306/blacklist_call?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull";
		String username = "root";
		String password = "ucpaas.com";
		String tables = "tb_gateway_score_report_0";
		String author = "wukaibing";
		String generatePath = "C:\\Users\\Administrator\\Desktop\\new_code\\mybatis-\\mybatis-generator\\src\\main\\resources\\test";
		String modelType = "call";
		AutoGenerator.generator(url, username, password, tables, generatePath, author, "org.yishang", modelType);
	}

	public static void generator(String url, String username,
	                             String password, String tables,
	                             String generatePath, String author,
	                             String packageName, String modelType) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("MySQL JDBC Driver not found", e);
		}
		AutoGenerator.packageName = packageName;
		AutoGenerator.modelType = modelType;
		FastAutoGenerator.create(url, username, password)
				.globalConfig(builder -> {
					builder.author(author) // 设置作者
							.fileOverride() // 覆盖已生成文件
							.outputDir(generatePath); // 指定输出目录
				})
				.packageConfig(builder -> {
					builder.parent(""); // 设置父包名（我这边为空，因为我的项目目录结构不统一，所以在代码生成模板中自己定义了包名，如果你的目录结构比较清晰可以在这里设置父目录）
				})
				.strategyConfig(builder -> {
					builder.addInclude(tables) // 设置需要生成的表名
							//Entity 策略配置
							.entityBuilder()
							.enableLombok() //开启lombok
							.enableTableFieldAnnotation();//开启生成实体时生成字段注解 默认值:false（加上这个配置生成的实体字段会有 @TableField()注解）
					builder.mapperBuilder()
							.enableMapperAnnotation() // 开启@Mapper
							.enableBaseColumnList() // 启用 columnList (通用查询结果列)
							.enableBaseResultMap();// 启动resultMap
				})
				.templateEngine(new EnhanceFreemarkerTemplateEngine())
				.execute();
	}

	/**
	 * 代码生成器支持自定义模版
	 */
	public final static class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

		@Override
		protected void outputCustomFile(@NotNull Map<String, String> customFile,
		                                @NotNull TableInfo tableInfo,
		                                @NotNull Map<String, Object> objectMap) {
			String entityName = tableInfo.getEntityName();
			String otherPath = this.getPathInfo(OutputFile.other);
			String modelType = "";
			if (StringUtils.isNotBlank(AutoGenerator.modelType)) {
				modelType = "." + AutoGenerator.modelType;
			}

			String fristPath = "";
			if (StringUtils.isNotBlank(AutoGenerator.packageName)) {
				fristPath = AutoGenerator.packageName + ".";
			}
			//自定义模板参数（定义的这些参数可以在我们的Freemark模板中使用&{}语法取到该值，比如 &{requestMapping}）
			objectMap.put("requestMapping", fristPath + StringUtils.firstToLowerCase(entityName));
			objectMap.put("controllerPackage", fristPath + "controller" + modelType);
			objectMap.put("facadePackage", fristPath + "facade" + modelType);
			objectMap.put("facadeImplPackage", fristPath + "facade" + modelType + ".impl");
			objectMap.put("servicePackage", fristPath + "sevice" + modelType);
			objectMap.put("servicePackageImpl", fristPath + "sevice" + modelType + ".impl");
			objectMap.put("mapperPackage", fristPath + "dao" + modelType);
			objectMap.put("entityPackage", fristPath + "modules.po" + modelType);
			objectMap.put("requestPackage", fristPath + "modules" + ".dto" + modelType);
			objectMap.put("responsePackage", fristPath + "modules" + ".vo" + modelType);

//          objectMap.put("basePackage", AutoGenerator.packageName);
//			objectMap.put("entityPackage", AutoGenerator.packageName + ".entity");
//			objectMap.put("controllerPackage", AutoGenerator.packageName + ".modules." + tableInfo.getEntityName().toLowerCase());
//			objectMap.put("facadePackage", AutoGenerator.packageName + ".modules." + tableInfo.getEntityName().toLowerCase() + ".facade");
//			objectMap.put("facadeImplPackage", AutoGenerator.packageName + ".modules." + tableInfo.getEntityName().toLowerCase() + ".facade.impl");
//			objectMap.put("servicePackage", AutoGenerator.packageName + ".modules." +tableInfo.getEntityName().toLowerCase() + ".sevice");
//			objectMap.put("servicePackageImpl", AutoGenerator.packageName + ".modules." + tableInfo.getEntityName().toLowerCase() + ".sevice.impl");
//			objectMap.put("requestPackage", AutoGenerator.packageName + ".modules." + tableInfo.getEntityName().toLowerCase() + ".request");
//			objectMap.put("responsePackage", AutoGenerator.packageName + ".modules." + tableInfo.getEntityName().toLowerCase() + ".response");


			//设置要生成的文件名以及FreeMark模板文件路径
			customFile = new HashMap<>();
			customFile.put(entityName + StringPool.DOT_JAVA, "/template/entity.java.ftl");
			customFile.put(entityName + "Controller" + StringPool.DOT_JAVA, "/template/controller.java.ftl");
			customFile.put(entityName + "Facade" + StringPool.DOT_JAVA, "/template/facade.java.ftl");
			customFile.put(entityName + "FacadeImpl" + StringPool.DOT_JAVA, "/template/facadeImpl.java.ftl");
			customFile.put(entityName + "Service" + StringPool.DOT_JAVA, "/template/service.java.ftl");
			customFile.put(entityName + "ServiceImpl" + StringPool.DOT_JAVA, "/template/serviceImpl.java.ftl");
			customFile.put(entityName + "Dao" + StringPool.DOT_JAVA, "/template/Dao.java.ftl");
			customFile.put(entityName + "Dao" + StringPool.DOT_XML, "/template/Dao.xml.ftl");
			customFile.put(entityName + "SaveDTO" + StringPool.DOT_JAVA, "/template/saveDTO.java.ftl");
			customFile.put(entityName + "DeleteDTO" + StringPool.DOT_JAVA, "/template/deleteDTO.java.ftl");
			customFile.put(entityName + "UpdateDTO" + StringPool.DOT_JAVA, "/template/updateDTO.java.ftl");
			customFile.put(entityName + "DetailDTO" + StringPool.DOT_JAVA, "/template/detailDTO.java.ftl");
			customFile.put(entityName + "PageListDTO" + StringPool.DOT_JAVA, "/template/pageListDTO.java.ftl");
			customFile.put(entityName + "DetailVO" + StringPool.DOT_JAVA, "/template/detailVO.java.ftl");
			customFile.put(entityName + "PageListVO" + StringPool.DOT_JAVA, "/template/pageListVO.java.ftl");

			//			objectMap.put("entityPackage", AutoGenerator.packageName + ".entity");
//			objectMap.put("controllerPackage", AutoGenerator.packageName + ".modules." + tableInfo.getEntityName().toLowerCase());
//			objectMap.put("facadePackage", AutoGenerator.packageName + ".modules." + tableInfo.getEntityName().toLowerCase() + ".facade");
//			objectMap.put("facadeImplPackage", AutoGenerator.packageName + ".modules." + tableInfo.getEntityName().toLowerCase() + ".facade.impl");
//			objectMap.put("servicePackage", AutoGenerator.packageName + ".modules." +tableInfo.getEntityName().toLowerCase() + ".sevice");
//			objectMap.put("servicePackageImpl", AutoGenerator.packageName + ".modules." + tableInfo.getEntityName().toLowerCase() + ".sevice.impl");
//			objectMap.put("requestPackage", AutoGenerator.packageName + ".modules." + tableInfo.getEntityName().toLowerCase() + ".request");
//			objectMap.put("responsePackage", AutoGenerator.packageName + ".modules." + tableInfo.getEntityName().toLowerCase() + ".response");


			customFile.forEach((key, value) -> {
				//拼接生成文件的目录
				// 判断文件
				String fileName = otherPath + "/";
				String fileNamePath = "";
				if (key.endsWith("Dao.xml") || key.endsWith("Dao.java")) {
					fileNamePath = (String) objectMap.get("mapperPackage");
				} else if (key.endsWith("Controller.java")) {
					fileNamePath = (String) objectMap.get("controllerPackage");
				} else if (key.endsWith("Facade.java")) {
					fileNamePath = (String) objectMap.get("facadePackage");
				} else if (key.endsWith("FacadeImpl.java")) {
					fileNamePath = (String) objectMap.get("facadeImplPackage");
				} else if (key.endsWith("Service.java")) {
					fileNamePath = (String) objectMap.get("servicePackage");
				} else if (key.endsWith("ServiceImpl.java")) {
					fileNamePath = (String) objectMap.get("servicePackageImpl");
				} else if (key.endsWith("SaveDTO.java") || key.endsWith("DeleteDTO.java") || key.endsWith("UpdateDTO.java") || key.endsWith("DetailDTO.java") || key.endsWith("PageListDTO.java")) {
					fileNamePath = (String) objectMap.get("requestPackage");
				} else if (key.endsWith("DetailVO.java") || key.endsWith("PageListVO.java")) {
					fileNamePath = (String) objectMap.get("responsePackage");
				} else if (value.endsWith("/template/entity.java.ftl")) {
					fileNamePath = (String) objectMap.get("entityPackage");
				} else {
//					fileName = fileName + "/" +"key";
				}

				String[] split = fileNamePath.split("\\.");
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < split.length; i++) {
					sb.append(split[i]).append("/");
				}
				fileName = fileName + sb.toString() + "/" + key;
				this.outputFile(new File(fileName), objectMap, value, this.getConfigBuilder().getInjectionConfig().isFileOverride());
			});
		}
	}

}

