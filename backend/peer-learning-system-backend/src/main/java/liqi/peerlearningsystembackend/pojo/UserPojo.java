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
@TableName(value = "user")
public class UserPojo {

    @TableId(value = "uuid", type = IdType.AUTO)
    private String uuid;

    @TableField(value = "uid")
    private Integer uid;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "email")
    private String email;

    @TableField(value = "authority")
    private Integer authority;


    public UserPojo(String uuid, Integer uid, String username, String password) {
        this.uuid = uuid;
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.email = null;
        this.authority = 0;
    }

}
