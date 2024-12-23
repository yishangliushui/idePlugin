package ${requestPackage};

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
* 详情
* @author ${author}
* @since ${date}
*/
@Data
public class ${entity}DetailRequest {

/**
* 主键
*/
@NotNull(message = "主键不能为空")
private Long id;

}

