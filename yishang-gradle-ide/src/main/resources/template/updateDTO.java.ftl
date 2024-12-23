package ${requestPackage};

import lombok.Data;

/**
* 修改
* @author ${author}
* @since ${date}
*/
@Data
public class ${entity}UpdateRequest {
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.propertyName != "createTime" && field.propertyName != "createBy" && field.propertyName != "updateBy" && field.propertyName != "updateTime">
        <#if field.comment!?length gt 0>
          /**
          * ${field.comment}
          */
        </#if>
      private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>

}

