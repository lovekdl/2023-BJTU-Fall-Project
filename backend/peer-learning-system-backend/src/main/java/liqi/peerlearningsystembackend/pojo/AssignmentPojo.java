package liqi.peerlearningsystembackend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "assignment")
public class AssignmentPojo {

    @TableId(value = "uuid", type = IdType.AUTO)
    private String uuid;

    @TableField(value = "assignmentID")
    private Integer assignmentID;

    @TableField(value = "courseUUID")
    private String courseUUID;

    @TableField(value = "title")
    private String title;

    @TableField(value = "content")
    private String content;

    @TableField(value = "filePath")
    private String filePath;

    @TableField(value = "deadline")
    private String deadline;

    @TableField(value = "status")
    private String status;

    @TableField(value = "excellent", updateStrategy = FieldStrategy.IGNORED)
    private String excellent;

    @TableField(value = "answer", updateStrategy = FieldStrategy.IGNORED)
    private String answer;
}
