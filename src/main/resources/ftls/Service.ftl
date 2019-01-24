package ${BasePackageName}${ServicePackageName};

import ${BasePackageName}${DaoPackageName}.${ClassName}Mapper;
import ${BasePackageName}${EntityPackageName}.${ClassName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.modules.base.dao.BaseMapper;
import com.commons.apiresult.ApiResult;
import com.commons.utils.PageMode;

/**
* Author ${Author}
* Date  ${Date}
*/
@Service("${EntityName}Service")
public class ${ClassName}Service {
	
    @Autowired
    private ${ClassName}Mapper ${EntityName}Mapper;
	@Autowired
	private BaseMapper baseMapper;
	
	public void searchPage(ApiResult apiResult, PageMode pageMode, ${ClassName} ${EntityName}) {
		pageMode.setSqlFrom("select * from ${ClassName}");
		Long total = baseMapper.selectLong(pageMode.getSearchCountSql());// 查询总条数
		pageMode.setTotal(total);
		List<Map<String, Object>> data = baseMapper.select(pageMode.getMysqlLimit());
		pageMode.setApiResult(apiResult, data);
	}
	
    public ${ClassName} get(Integer id){
        return ${EntityName}Mapper.get(id);
    }

    public List<${ClassName}> findList(${ClassName} ${EntityName}) {
        return ${EntityName}Mapper.findList(${EntityName});
    }

    public List<${ClassName}> findAllList() {
        return ${EntityName}Mapper.findAllList();
    }

    public Integer insert(${ClassName} ${EntityName}) {
         ${EntityName}Mapper.insert(${EntityName});
         return ${EntityName}.${PrimaryKeyMethod};
    }

    public int insertBatch(List<${ClassName}> ${EntityName}s){
        return ${EntityName}Mapper.insertBatch(${EntityName}s);
    }

    public int update(${ClassName} ${EntityName}) {
        return ${EntityName}Mapper.update(${EntityName});
    }

    public int delete(Integer id) {
        return ${EntityName}Mapper.delete(id);
    }

}
