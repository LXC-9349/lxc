package com.modules.gen.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.modules.gen.entity.ColumnInfo;
import com.modules.gen.task.base.BaseTask;
import com.modules.gen.utils.ConfigUtil;
import com.modules.gen.utils.FileUtil;
import com.modules.gen.utils.FreemarketConfigUtils;
import com.modules.gen.utils.StringUtil;

import freemarker.template.TemplateException;

public class ServiceTask extends BaseTask {

	public ServiceTask(String className, List<ColumnInfo> tableInfos) {
		super(className, tableInfos);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -7563318182648996822L;


	@Override
	public void run() throws IOException, TemplateException {
		// 生成Service填充数据
		System.out.println("Generating " + className + "Service.java");
		Map<String, String> serviceData = new HashMap<>();
		serviceData.put("BasePackageName", ConfigUtil.configuration.getPackageName());
		serviceData.put("ServicePackageName",
				"modules." + className.toLowerCase() + "." + ConfigUtil.configuration.getPath().getService());
		serviceData.put("EntityPackageName", "modules." + className.toLowerCase() + "." +ConfigUtil.configuration.getPath().getEntity());
		serviceData.put("DaoPackageName", "modules." + className.toLowerCase() + "." +ConfigUtil.configuration.getPath().getDao());
		serviceData.put("Author", ConfigUtil.configuration.getAuthor());
		serviceData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		serviceData.put("ClassName", className);
		serviceData.put("EntityName", StringUtil.firstToLowerCase(className));
		String PrimaryKey=getPrimaryKeyColumnInfo(tableInfos).getColumnName();
		serviceData.put("PrimaryKey", PrimaryKey);
		serviceData.put("PrimaryKeyMethod", "get"+PrimaryKey.substring(0, 1).toUpperCase()+PrimaryKey.substring(1)+"()");
		String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.configuration.getPackageName())
				+ StringUtil.package2Path(serviceData.get("ServicePackageName"));
		String fileName = className + "Service.java";
		// 生成Service文件
		FileUtil.generateToJava(FreemarketConfigUtils.TYPE_SERVICE, serviceData, filePath + fileName,filePath);
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
