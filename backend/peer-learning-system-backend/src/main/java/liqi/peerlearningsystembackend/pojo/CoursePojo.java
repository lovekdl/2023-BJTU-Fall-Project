package liqi.peerlearningsystembackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "course")
public class CoursePojo {

    @TableId(value = "uuid", type = IdType.AUTO)
    private String uuid;

    @TableField(value = "courseID")
    private Integer courseID;

    @TableField(value = "userUUID")
    private String userUUID;

    @TableField(value = "courseName")
    private String courseName;

    @TableField(value = "intro")
    private String intro;

    @TableField(value = "number")
    private Integer number;

}
