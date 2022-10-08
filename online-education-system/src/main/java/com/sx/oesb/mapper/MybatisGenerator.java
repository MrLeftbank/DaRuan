/**
 * @Title MybatisGenerator.java
 * @author 皮龙霖
 * @description 
 * @date 2022年7月1日下午2:58:31
 */
package com.sx.oesb.mapper;


import java.util.Collections;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/** 
* @ClassName MybatisGenerator 
* @Description 代码生成
* @author 皮龙霖
* @date 2022年7月1日 下午2:58:31 
*  
*/
public class MybatisGenerator {
	public static void main(String[] args) {
		FastAutoGenerator.create(
				"jdbc:mysql://121.37.92.215/online_education?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8",
				"root", 
				"123456")
	    .globalConfig(builder -> {
	        builder.author("自动生成") // 设置作者
	            .fileOverride() // 覆盖已生成文件
	            .enableSwagger()
	            .outputDir("C:\\Users\\p'l'l\\Desktop\\hhh"); // 指定输出目录
	    })
	    .packageConfig(builder -> {
	        builder.parent("com.sx.oesb")// 设置父包名
	        	.entity("entity")
	        	.mapper("mapper")
	        	.xml("mapper.xml")
	            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "C:\\Users\\p'l'l\\Desktop\\hhh")); // 设置mapperXml生成路径
	    })
	    .strategyConfig(builder -> {
	        builder.addExclude("teacher_domain")// 设置需要生成的表名
	        .enableCapitalMode()
	        .entityBuilder()
	        ; 
	        
	    })
	    .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
	    .templateConfig(builder -> {builder.controller("").service("").serviceImpl("");})//不用生成controller与service层
	    .execute();
	}
	
}
