package ${facadePackage};

import ${requestPackage}.${entity}SaveRequest;
import ${requestPackage}.${entity}DeleteRequest;
import ${requestPackage}.${entity}UpdateRequest;
import ${requestPackage}.${entity}DetailRequest;
import ${requestPackage}.${entity}PageListRequest;
import ${responsePackage}.${entity}DetailResponse;
import ${responsePackage}.${entity}PageListResponse;
import ${superServiceClassPackage};

/**
* ${table.comment!}
* @author ${author}
* @since ${date}
*/
public interface ${entity}Facade {

/**
* 新增
* @param request
* @return
*/
void save(${entity}SaveRequest request);

/**
* 删除
* @param request
* @return
*/
void delete(${entity}DeleteRequest request);

/**
* 编辑
* @param request
* @return
*/
void update(${entity}UpdateRequest request);

/**
* 详情
* @param request
* @return
*/
${entity}DetailResponse detail(${entity}DetailRequest request);

/**
* 分页列表
* @param request
* @return
*/
PageInfo
<${entity}PageListResponse> pageList(${entity}PageListRequest request);

  }