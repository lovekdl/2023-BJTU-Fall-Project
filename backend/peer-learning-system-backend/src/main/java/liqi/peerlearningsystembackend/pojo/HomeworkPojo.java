package liqi.peerlearningsystembackend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "homework")
public class HomeworkPojo {

    @TableId(value = "uuid", type = IdType.AUTO)
    private String uuid;

    @TableField(value = "homeworkID")
    private Integer homeworkID;

    @TableField(value = "assignmentUUID")
    private String assignmentUUID;

    @TableField(value = "userUUID")
    private String userUUID;

    @TableField(value = "score")
    private Integer score;

    @TableField(value = "submitTime")
    private String submitTime;

    @TableField(value = "content")
    private String content;

    @TableField(value = "filePath")
    private String filePath;

    @TableField(value = "argument", updateStrategy = FieldStrategy.IGNORED)
    private String argument;
}
