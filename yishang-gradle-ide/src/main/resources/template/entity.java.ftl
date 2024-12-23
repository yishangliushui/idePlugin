package ${entityPackage};

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* ${table.comment!}
* @author ${author}
* @since ${date}
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "${table.name}")
public class ${entity} implements Serializable {

private static final long serialVersionUID = 1L;

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
      /**
      * ${field.comment}
      */
    </#if>
    <#if field.keyFlag>
    <#-- 主键 -->
        <#if field.keyIdentityFlag>
          @Id(value = "${field.name}", type = IdType.AUTO)
        <#elseif idType??>
          @Id(value = "${field.name}", type = IdType.${idType})
        <#elseif field.convert>
          @Id("${field.name}")
        </#if>
    <#-- 普通字段 -->
    <#elseif field.fill??>
    <#-- -----   存在字段填充设置   ----->
        <#if field.convert>
          @Column(value = "${field.name}", fill = FieldFill.${field.fill})
        <#else>
          @Column(fill = FieldFill.${field.fill})
        </#if>
    <#elseif field.convert>
      @Column("${field.name}")
    </#if>
  private ${field.propertyType} ${field.propertyName};
</#list>

}

