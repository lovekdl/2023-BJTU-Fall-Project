package liqi.peerlearningsystembackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sc")
public class SCPojo {

    @TableField(value = "userUUID")
    private String userUUID;

    @TableField(value = "courseUUID")
    private String courseUUID;

    @TableField(value = "studentName")
    private String studentName;

    @TableField(value = "courseID")
    private Integer courseID;
}
