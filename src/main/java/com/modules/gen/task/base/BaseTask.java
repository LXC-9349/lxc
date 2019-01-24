package com.modules.gen.task.base;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.modules.gen.entity.ColumnInfo;
import com.modules.gen.utils.ConfigUtil;
import com.modules.gen.utils.StringUtil;

import freemarker.template.TemplateException;

/**
 * Author GreedyStar
 * Date   2018/4/20
 */
public abstract class BaseTask implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -719969616752552886L;
	protected String tableName;
    protected String className;
    protected String parentTableName;
    protected String parentClassName;
    protected String foreignKey;
    protected String relationalTableName;
    protected String parentForeignKey;
    protected List<ColumnInfo> tableInfos;
    protected List<ColumnInfo> parentTableInfos;

    /**
     * Controller、Dao
     *
     * @param className
     */
    public BaseTask(String className) {
        this.className = className;
    }

    /**
     * Services
     *
     * @param className
     */
    public BaseTask(String className, List<ColumnInfo> tableInfos) {
		this.className = className;
		this.tableInfos = tableInfos;
	}


	/**
     * Entity
     *
     * @param className
     * @param parentClassName
     * @param foreignKey
     * @param tableInfos
     */
    public BaseTask(String className, String parentClassName, String foreignKey, String parentForeignKey, List<ColumnInfo> tableInfos) {
        this.className = className;
        this.parentClassName = parentClassName;
        this.foreignKey = foreignKey;
        this.parentForeignKey = parentForeignKey;
        this.tableInfos = tableInfos;
    }


    /**
     * Mapper
     *
     * @param tableName
     * @param className
     * @param parentTableName
     * @param parentClassName
     * @param foreignKey
     * @param parentForeignKey
     * @param tableInfos
     * @param parentTableInfos
     */
    public BaseTask(String tableName, String className, String parentTableName, String parentClassName, String foreignKey, String parentForeignKey, String relationalTableName, List<ColumnInfo> tableInfos, List<ColumnInfo> parentTableInfos) {
        this.tableName = tableName;
        this.className = className;
        this.parentTableName = parentTableName;
        this.parentClassName = parentClassName;
        this.foreignKey = foreignKey;
        this.parentForeignKey = parentForeignKey;
        this.relationalTableName = relationalTableName;
        this.tableInfos = tableInfos;
        this.parentTableInfos = parentTableInfos;
    }

    public abstract void run() throws IOException, TemplateException;

    @Deprecated
    protected void createFilePathIfNotExists(String filePath) {
        if (!StringUtil.isBlank(ConfigUtil.configuration.getPackageName())) { // 用户配置了包名，不进行检测
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) { // 检测文件路径是否存在，不存在则创建
            file.mkdir();
        }
    }

}
