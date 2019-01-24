package com.modules.gen.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.modules.gen.task.base.BaseTask;
import com.modules.gen.utils.ConfigUtil;
import com.modules.gen.utils.FileUtil;
import com.modules.gen.utils.FreemarketConfigUtils;
import com.modules.gen.utils.StringUtil;

import freemarker.template.TemplateException;

public class DaoTask extends BaseTask {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3125439552781924751L;

	public DaoTask(String className) {
        super(className);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Dao填充数据
        System.out.println("Generating " + className + "Mapper.java");
        Map<String, String> daoData = new HashMap<>();
        daoData.put("BasePackageName", ConfigUtil.configuration.getPackageName());
        daoData.put("DaoPackageName", "modules."+className.toLowerCase()+"."+ConfigUtil.configuration.getPath().getDao());
        daoData.put("EntityPackageName", "modules."+className.toLowerCase()+"."+ConfigUtil.configuration.getPath().getEntity());
        daoData.put("Author", ConfigUtil.configuration.getAuthor());
        daoData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        daoData.put("ClassName", className);
        daoData.put("EntityName", StringUtil.firstToLowerCase(className));
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.configuration.getPackageName()) + StringUtil.package2Path(daoData.get("DaoPackageName"));
        String fileName = className + "Mapper.java";
        // 生成dao文件
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_DAO, daoData, filePath + fileName,filePath);
    }
}
