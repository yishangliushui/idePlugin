package ${servicePackageImpl};

import ${entityPackage}.${entity};
import ${mapperPackage}.${table.mapperName};
import ${servicePackage}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

/**
* ${table.comment!} 服务实现类
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
  open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

  }
<#else>
  public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {



  }
</#if>
