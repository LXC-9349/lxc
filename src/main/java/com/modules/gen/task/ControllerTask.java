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

@SuppressWarnings("serial")
public class ControllerTask extends BaseTask {

    public ControllerTask(String className) {
        super(className);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Controller填充数据
        System.out.println("Generating " + className + "Controller.java");
        Map<String, String> controllerData = new HashMap<>();
        controllerData.put("BasePackageName", ConfigUtil.configuration.getPackageName());
        controllerData.put("ControllerPackageName", ConfigUtil.configuration.getPath().getController());
        controllerData.put("ServicePackageName", "modules."+className.toLowerCase()+"."+ConfigUtil.configuration.getPath().getService());
        controllerData.put("EntityPackageName", "modules."+className.toLowerCase()+"."+ConfigUtil.configuration.getPath().getEntity());
        controllerData.put("Author", ConfigUtil.configuration.getAuthor());
        controllerData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        controllerData.put("ClassName", className);
        controllerData.put("EntityName", StringUtil.firstToLowerCase(className));
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.configuration.getPackageName()) + StringUtil.package2Path(ConfigUtil.configuration.getPath().getController());
        String fileName = className + "Controller.java";
        // 生成Controller文件
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_CONTROLLER, controllerData, filePath + fileName,filePath);
    }
}
