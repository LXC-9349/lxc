package com.modules.gen.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.modules.gen.entity.ColumnInfo;
import com.modules.gen.task.base.BaseTask;
import com.modules.gen.utils.ConfigUtil;
import com.modules.gen.utils.FileUtil;
import com.modules.gen.utils.FreemarketConfigUtils;
import com.modules.gen.utils.GeneratorUtil;
import com.modules.gen.utils.StringUtil;

import freemarker.template.TemplateException;

public class MapperTask extends BaseTask {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6804395704578929055L;

	/**
     * 单表Mapper
     */
    public MapperTask(String className, String tableName, List<ColumnInfo> infos) {
        this(tableName, className, null, null, null, infos, null);
    }

    /**
     * 一对多Mapper
     */
    public MapperTask(String tableName, String className, String parentTableName, String parentClassName, String foreignKey, List<ColumnInfo> tableInfos, List<ColumnInfo> parentTableInfos) {
        this(tableName, className, parentTableName, parentClassName, foreignKey, null, null, tableInfos, parentTableInfos);
    }

    /**
     * 多对多Mapper
     */
    public MapperTask(String tableName, String className, String parentTableName, String parentClassName, String foreignKey, String parentForeignKey, String relationalTableName, List<ColumnInfo> tableInfos, List<ColumnInfo> parentTableInfos) {
        super(tableName, className, parentTableName, parentClassName, foreignKey, parentForeignKey, relationalTableName, tableInfos, parentTableInfos);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Mapper填充数据
        System.out.println("Generating " + className + "Mapper.xml");
        Map<String, String> mapperData = new HashMap<>();
        mapperData.put("PackageName", ConfigUtil.configuration.getPackageName() + ".modules"+className.toLowerCase()+"."+ ConfigUtil.configuration.getPath().getDao());
        mapperData.put("BasePackageName", ConfigUtil.configuration.getPackageName());
        mapperData.put("DaoPackageName", "modules."+className.toLowerCase()+"."+ConfigUtil.configuration.getPath().getDao());
        mapperData.put("EntityPackageName", "modules."+className.toLowerCase()+"."+ConfigUtil.configuration.getPath().getEntity());
        mapperData.put("ClassName", className);
        mapperData.put("EntityName", StringUtil.firstToLowerCase(className));
        mapperData.put("TableName", tableName);
        mapperData.put("InsertProperties", GeneratorUtil.generateMapperInsertProperties(tableInfos));
        mapperData.put("PrimaryKey", getPrimaryKeyColumnInfo(tableInfos).getColumnName());
        mapperData.put("WhereId", "#{" + getPrimaryKeyColumnInfo(tableInfos).getPropertyName() + "}");
        mapperData.put("Id", "#{id}");
        if (!StringUtil.isBlank(parentForeignKey)) { // 多对多
            mapperData.put("ColumnMap", GeneratorUtil.generateMapperColumnMap(tableName, parentTableName, tableInfos, parentTableInfos, StringUtil.firstToLowerCase(parentClassName)));
            mapperData.put("ResultMap", GeneratorUtil.generateMapperResultMap(tableInfos));
            mapperData.put("Association", "");
            mapperData.put("Collection", GeneratorUtil.generateMapperCollection(parentTableInfos, parentClassName, ConfigUtil.configuration.getPackageName() + ConfigUtil.configuration.getPath().getEntity()));
            mapperData.put("InsertBatchValues", GeneratorUtil.generateMapperInsertBatchValues(tableInfos, StringUtil.firstToLowerCase(className)));
            mapperData.put("InsertValues", GeneratorUtil.generateMapperInsertValues(tableInfos));
            mapperData.put("UpdateProperties", GeneratorUtil.generateMapperUpdateProperties(tableInfos));
            mapperData.put("Joins", GeneratorUtil.generateMapperJoins(tableName, parentTableName, relationalTableName, foreignKey, parentForeignKey, getPrimaryKeyColumnInfo(tableInfos).getColumnName(), getPrimaryKeyColumnInfo(parentTableInfos).getColumnName()));
        } else if (!StringUtil.isBlank(foreignKey)) { // 一对多
            mapperData.put("ColumnMap", GeneratorUtil.generateMapperColumnMap(tableName, parentTableName, tableInfos, parentTableInfos, StringUtil.firstToLowerCase(parentClassName), foreignKey));
            mapperData.put("ResultMap", GeneratorUtil.generateMapperResultMap(tableInfos));
            mapperData.put("Association", GeneratorUtil.generateMapperAssociation(parentTableInfos, parentClassName, ConfigUtil.configuration.getPackageName() + ConfigUtil.configuration.getPath().getEntity()));
            mapperData.put("Collection", "");
            mapperData.put("InsertBatchValues", GeneratorUtil.generateMapperInsertBatchValues(tableInfos, StringUtil.firstToLowerCase(className), StringUtil.firstToLowerCase(parentClassName), foreignKey, getPrimaryKeyColumnInfo(parentTableInfos).getPropertyName()));
            mapperData.put("InsertValues", GeneratorUtil.generateMapperInsertValues(tableInfos, StringUtil.firstToLowerCase(parentClassName), foreignKey, getPrimaryKeyColumnInfo(parentTableInfos).getPropertyName()));
            mapperData.put("UpdateProperties", GeneratorUtil.generateMapperUpdateProperties(tableInfos, StringUtil.firstToLowerCase(parentClassName), foreignKey, getPrimaryKeyColumnInfo(parentTableInfos).getPropertyName()));
            mapperData.put("Joins", GeneratorUtil.generateMapperJoins(tableName, parentTableName, foreignKey, getPrimaryKeyColumnInfo(parentTableInfos).getColumnName()));
        } else { // 单表
            mapperData.put("ColumnMap", GeneratorUtil.generateMapperColumnMap(tableName, tableInfos));
            mapperData.put("ResultMap", GeneratorUtil.generateMapperResultMap(tableInfos));
            mapperData.put("Association", "");
            mapperData.put("Collection", "");
            mapperData.put("InsertBatchValues", GeneratorUtil.generateMapperInsertBatchValues(tableInfos, StringUtil.firstToLowerCase(className)));
            mapperData.put("InsertValues", GeneratorUtil.generateMapperInsertValues(tableInfos));
            mapperData.put("UpdateProperties", GeneratorUtil.generateMapperUpdateProperties(tableInfos));
            mapperData.put("Joins", "");
        }
        String filePath = FileUtil.getResourcePath() + StringUtil.package2Path(ConfigUtil.configuration.getPath().getMapper());
        String fileName = className + "Mapper.xml";
        // 生成Mapper文件
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_MAPPER, mapperData, filePath + fileName,filePath);
    }

    private ColumnInfo getPrimaryKeyColumnInfo(List<ColumnInfo> list) {
        for (ColumnInfo columnInfo : list) {
            if (columnInfo.isPrimaryKey()) {
                return columnInfo;
            }
        }
        return null;
    }

}
