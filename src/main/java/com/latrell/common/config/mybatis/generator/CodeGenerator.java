package com.latrell.common.config.mybatis.generator;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * mybatis-plus 代码生成器
 *
 * @author liz
 * @date 2022-10-11 17:12:03
 */
public class CodeGenerator {

	/**
	 * 作者名称，用于生成类上的注释
	 */
	private final static String AUTHOR_NAME = "liz";

	/**
	 * 生成文件输出路径
	 */
	private final static String OUT_PUT_DIR = "E:\\latrell-code\\src\\main\\java";

	/**
	 * 生成xml文件输出路径
	 */
	private final static String OUT_PUT_DIR_XML = "E:\\latrell-code\\src\\main\\resources\\mapper\\";

	/**
	 * 类的包名
	 */
	private final static String PACKAGE_NAME = "com.latrell.test";

	/**
	 * 数据库ip端口
	 */
	private final static String DB_IP = "localhost:3306";

	/**
	 * 数据库用户名
	 */
	private final static String USER_NAME = "root";

	/**
	 * 数据库密码
	 */
	private final static String PASSWORD= "123456";

	/**
	 * 乐观锁对应的数据库字段名
	 * 注意这边的字段名要区分大小写，要和数据库里的一样
	 */
	private final static String VERSION_FIELD = "VERSION";

	/**
	 * 逻辑删除对应的数据库字段名
	 */
	private final static String LOGIC_DELETE_FIELD = "REMOVED";

	/**
	 * 数据更新时间对应的数据库字段名
	 */
	private final static String UPDATED_TIME_FIELD = "UPDATED_TIME";

	/**
	 * 数据创建时间对应的数据库字段名
	 */
	private final static String CREATED_TIME_FIELD = "CREATED_TIME";

	/**
	 * 数据创建用户id对应的数据库字段名
	 */
	private final static String CREATED_BY_FIELD = "CREATED_BY";

	/**
	 * 数据更新用户id对应的数据库字段名
	 */
	private final static String UPDATED_BY_FIELD = "UPDATED_BY";


	public static void main(String[] args){
		// 数据库名称
		String database = "common-tools";
		// 不生成表前缀,如auth_dict 不设置的话生成的类名都未AuthDictController
		// 配置了这个去除前缀就为DictController,建议除去同一模块的前缀
		String tablePrefix = "t_";
		// 表名称
		String[] tableNames = {"t_user"};
		// //实体属性 Swagger2 注解是否开启
		boolean swaggerOpen = true;
		generateByTables(tablePrefix, swaggerOpen,database, tableNames);

		System.out.println("completed...");
	}

	public static void generateByTables(String tablePrefix, boolean swaggerOpen, String database, String... tableNames) {

		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(OUT_PUT_DIR)
				.setAuthor(AUTHOR_NAME)
				.setOpen(false)
				.setSwagger2(swaggerOpen)
				.setIdType(IdType.AUTO)
				.setServiceName("%sService")
				.setEntityName("%s")
				.setBaseResultMap(true)
				.setBaseColumnList(true)
				.setEnableCache(false)
				.setDateType(DateType.ONLY_DATE)
				.setFileOverride(false);

		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setUrl("jdbc:mysql://"+DB_IP+"/"+database+"?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai")
				.setDriverName("com.mysql.cj.jdbc.Driver")
				.setUsername(USER_NAME)
				.setPassword(PASSWORD)
				.setDbType(DbType.MYSQL);
		mpg.setDataSource(dsc);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent(PACKAGE_NAME)
				.setEntity("domain")
				.setController("controller")
				.setService("service")
				.setServiceImpl("service.impl")
				.setMapper("mapper")
				.setXml("");

		mpg.setPackageInfo(pc);

		// 自动填充,下面设置新增的时候自动填充创建时间和更新时间，更新的时候自动填充更新时间
		TableFill createTime = new TableFill(CREATED_TIME_FIELD, FieldFill.INSERT);
		TableFill updateTime = new TableFill(UPDATED_TIME_FIELD, FieldFill.INSERT_UPDATE);
		TableFill createdBy = new TableFill(CREATED_BY_FIELD, FieldFill.INSERT);
		TableFill updatedBy = new TableFill(UPDATED_BY_FIELD, FieldFill.UPDATE);
		List<TableFill> arrayList = new ArrayList();
		arrayList.add(createTime);
		arrayList.add(updateTime);
		arrayList.add(createdBy);
		arrayList.add(updatedBy);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// 是否启用lombok
		strategy.setEntityLombokModel(true)
				.setCapitalMode(true)
				.setNaming(NamingStrategy.underline_to_camel)
				.setRestControllerStyle(true)
				// 数据库is_XXX的字段生成实体类的时候删除is前缀
				.setEntityBooleanColumnRemoveIsPrefix(true)
				.setTableFillList(arrayList)
				// 乐观锁对应字段
				.setVersionFieldName(VERSION_FIELD)
				// 逻辑删除字段名
				.setLogicDeleteFieldName(LOGIC_DELETE_FIELD)
				.setColumnNaming(NamingStrategy.underline_to_camel)
				.setInclude(tableNames);

		//不生成表前缀
		if(StrUtil.isNotBlank(tablePrefix)){
			strategy.setTablePrefix(tablePrefix);
		}

		// 注入自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
			}
		};

		// 调整 xml 生成目录演示
		List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
		focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return OUT_PUT_DIR_XML + tableInfo.getEntityName() + "Mapper.xml";
			}
		});
		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);

		// 关闭默认 xml 生成，调整生成 至 根目录
		TemplateConfig tc = new TemplateConfig();
		tc.setXml(null);
		mpg.setTemplate(tc);


		mpg.setStrategy(strategy);
		mpg.execute();
	}

}
