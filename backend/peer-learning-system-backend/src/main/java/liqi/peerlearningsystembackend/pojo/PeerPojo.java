package liqi.peerlearningsystembackend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "peer")
public class PeerPojo {

    @TableId(value = "uuid", type = IdType.AUTO)
    private String uuid;

    @TableField(value = "peerID")
    private Integer peerID;

    @TableField(value = "userUUID")
    private String userUUID;

    @TableField(value = "homeworkUUID")
    private String homeworkUUID;

    @TableField(value = "assignmentUUID")
    private String assignmentUUID;

    @TableField(value = "username")
    private String username;

    @TableField(value = "homeworkID")
    private Integer homeworkID;

    @TableField(value = "assignmentID")
    private Integer assignmentID;

    @TableField(value = "score", updateStrategy = FieldStrategy.IGNORED)
    private Integer score;

    @TableField(value = "comment", updateStrategy = FieldStrategy.IGNORED)
    private String comment;

    @TableField(value = "status")
    private String status;

}
