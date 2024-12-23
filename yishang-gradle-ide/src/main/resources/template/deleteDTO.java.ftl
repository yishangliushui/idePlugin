package ${requestPackage};

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
* 删除
* @author ${author}
* @since ${date}
*/
@Data
public class ${entity}DeleteRequest {

/**
* 主键
*/
@NotNull(message = "主键不能为空")
private Long id;

}

