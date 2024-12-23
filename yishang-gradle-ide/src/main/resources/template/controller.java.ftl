package ${controllerPackage};

import ${entityPackage}.${entity};
import ${servicePackage}.${table.serviceName};
import com.ly.smallscene.common.base.entity.BaseResponseEntity;
import com.ly.smallscene.common.base.tool.PageInfo;
import ${requestPackage}.${entity}SaveRequest;
import ${requestPackage}.${entity}DeleteRequest;
import ${requestPackage}.${entity}UpdateRequest;
import ${requestPackage}.${entity}DetailRequest;
import ${requestPackage}.${entity}PageListRequest;
import ${responsePackage}.${entity}DetailResponse;
import ${responsePackage}.${entity}PageListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
/**
* @author ${author}
* @since ${date}
*/
@Tag(name = "${(table.comment!?html?replace('\r\n','') )!}")
@RestController
@RequestMapping("${requestMapping}")
public class ${table.controllerName} {

@Autowired
private ${entity}Facade ${entity?uncap_first}Facade;

@Operation(summary = "新增")
@PostMapping(value = "/save")
public BaseResponseEntity save(@RequestBody @Valid ${entity}SaveRequest request) {
${entity?uncap_first}Facade.save(request);
return new BaseResponseEntity();
}

@Operation(summary = "删除")
@PostMapping(value = "/delete")
public BaseResponseEntity delete(@RequestBody @Valid ${entity}DeleteRequest request) {
${entity?uncap_first}Facade.delete(request);
return new BaseResponseEntity();
}

@Operation(summary = "更新")
@PostMapping(value = "/update")
public BaseResponseEntity update(@RequestBody @Valid ${entity}UpdateRequest request) {
${entity?uncap_first}Facade.update(request);
return new BaseResponseEntity();
}

@Operation(summary = "详情")
@PostMapping(value = "/detail")
public BaseResponseEntity detail(@RequestBody @Valid ${entity}DetailRequest request) {
${entity}DetailResponse response = ${entity?uncap_first}Facade.detail(request);
return new BaseResponseEntity(response);
}

@Operation(summary = "分页列表")
@PostMapping(value = "/pageList")
public BaseResponseEntity pageList(@RequestBody ${entity}PageListRequest request) {
PageInfo
<${entity}PageListResponse> response = ${entity?uncap_first}Facade.pageList(request);
  return new BaseResponseEntity(response);
  }

  }