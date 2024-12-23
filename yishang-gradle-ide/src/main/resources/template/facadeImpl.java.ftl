package ${facadeImplPackage};

import ${facadePackage}.${entity}Facade;
import ${servicePackage}.${table.serviceName};
import ${requestPackage}.${entity}SaveRequest;
import ${requestPackage}.${entity}DeleteRequest;
import ${requestPackage}.${entity}UpdateRequest;
import ${requestPackage}.${entity}DetailRequest;
import ${requestPackage}.${entity}PageListRequest;
import ${responsePackage}.${entity}DetailResponse;
import ${responsePackage}.${entity}PageListResponse;

import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

/**
* ${table.comment!}
*
* @author ${author}
* @since ${date}
*/
@Service
public class ${entity}FacadeImpl implements ${entity}Facade {

@Autowired
private ${entity}Service ${entity?uncap_first}Service;

/**
* 新增
* @param request
* @return
*/
@Transactional(rollbackFor = Exception.class)
@Override
public void save(${entity}SaveRequest request) {

}

/**
* 删除
* @param request
* @return
*/
@Transactional(rollbackFor = Exception.class)
@Override
public void delete(${entity}DeleteRequest request) {

}

/**
* 编辑
* @param request
* @return
*/
@Transactional(rollbackFor = Exception.class)
@Override
public void update(${entity}UpdateRequest request) {

}

/**
* 详情
* @param request
* @return
*/
@Override
public ${entity}DetailResponse detail(${entity}DetailRequest request) {
return null;
}

/**
* 分页列表
* @param request
* @return
*/
@Override
public PageInfo
<${entity}PageListResponse> pageList(${entity}PageListRequest request) {
  return null;
  }

  }