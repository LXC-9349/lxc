package com.modules.gen.invoker;

import java.sql.SQLException;

import com.modules.gen.invoker.base.BaseBuilder;
import com.modules.gen.invoker.base.BaseInvoker;
import com.modules.gen.task.ControllerTask;
import com.modules.gen.task.DaoTask;
import com.modules.gen.task.EntityTask;
import com.modules.gen.task.MapperTask;
import com.modules.gen.task.ServiceTask;
import com.modules.gen.utils.GeneratorUtil;
import com.modules.gen.utils.StringUtil;

/**
 * Author GreedyStar
 * Date   2018/9/5
 */
public class SingleInvoker extends BaseInvoker {

    @Override
    protected void getTableInfos() throws SQLException {
        tableInfos = connectionUtil.getMetaData(tableName);
    }

    @Override
    protected void initTasks() {
        taskQueue.add(new DaoTask(className));
        taskQueue.add(new ServiceTask(className,tableInfos));
        taskQueue.add(new ControllerTask(className));
        taskQueue.add(new EntityTask(className, tableInfos));
        taskQueue.add(new MapperTask(className, tableName, tableInfos));
    }

    public static class Builder extends BaseBuilder {

		private SingleInvoker invoker = new SingleInvoker();

        public Builder setTableName(String tableName) {
            invoker.setTableName(tableName);
            return this;
        }

        public Builder setClassName(String className) {
            invoker.setClassName(className);
            return this;
        }

        @Override
        public BaseInvoker build(){
            if (!isParamtersValid()) {
                return null;
            }
            return invoker;
        }

        @Override
        public void checkBeforeBuild() throws Exception {
            if (StringUtil.isBlank(invoker.getTableName())) {
                throw new Exception("Expect table's name, but get a blank String.");
            }
            if (StringUtil.isBlank(invoker.getClassName())) {
                invoker.setClassName(GeneratorUtil.generateClassName(invoker.getTableName()));
            }
        }
    }

}
