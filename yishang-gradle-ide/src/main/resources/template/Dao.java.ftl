package ${mapperPackage};

import ${entityPackage}.${entity};
import ${superMapperClassPackage};

/**
* ${table.comment!} Dao 接口
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
  interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
  public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

  }
</#if>
