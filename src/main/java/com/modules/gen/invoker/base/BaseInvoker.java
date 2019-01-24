package com.modules.gen.invoker.base;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.modules.gen.db.ConnectionUtil;
import com.modules.gen.entity.ColumnInfo;
import com.modules.gen.task.base.BaseTask;
import com.modules.gen.utils.ConfigUtil;
import com.modules.gen.utils.TaskQueue;

import freemarker.template.TemplateException;

/**
 * Author GreedyStar
 * Date   2018/9/5
 */
public abstract class BaseInvoker implements Invoker {
    protected String tableName;
    protected String className;
    protected String parentTableName;
    protected String parentClassName;
    protected String foreignKey;
    protected String relationalTableName;
    protected String parentForeignKey;
    protected List<ColumnInfo> tableInfos;
    protected List<ColumnInfo> parentTableInfos;
    protected ConnectionUtil connectionUtil = new ConnectionUtil();
	protected TaskQueue<BaseTask> taskQueue = new TaskQueue<BaseTask>();
    private ExecutorService executorPool = Executors.newFixedThreadPool(6);

    private void initDataSource() throws Exception {
        if (!this.connectionUtil.initConnection()) {
            throw new Exception("Failed to connect to database at url:" + ConfigUtil.configuration.getDb().getUrl());
        }
        getTableInfos();
        connectionUtil.close();
    }

    protected abstract void getTableInfos() throws SQLException;

    protected abstract void initTasks();

    @Override
    public void execute() {
        try {
            initDataSource();
            initTasks();
            while (!taskQueue.isEmpty()) {
                BaseTask task = taskQueue.poll();
                executorPool.execute(() -> {
                    try {
                        task.run();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    }
                });
            }
            executorPool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setParentTableName(String parentTableName) {
        this.parentTableName = parentTableName;
    }

    public void setParentClassName(String parentClassName) {
        this.parentClassName = parentClassName;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public void setRelationalTableName(String relationalTableName) {
        this.relationalTableName = relationalTableName;
    }

    public void setParentForeignKey(String parentForeignKey) {
        this.parentForeignKey = parentForeignKey;
    }

    public String getTableName() {
        return tableName;
    }

    public String getClassName() {
        return className;
    }

    public String getParentTableName() {
        return parentTableName;
    }

    public String getParentClassName() {
        return parentClassName;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public String getRelationalTableName() {
        return relationalTableName;
    }

    public String getParentForeignKey() {
        return parentForeignKey;
    }
}
