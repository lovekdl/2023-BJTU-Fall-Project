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
@TableName(value = "counter")
public class CounterPojo {

    @TableId(value = "authority", type = IdType.AUTO)
    private Integer authority;

    @TableField(value = "uid")
    private Integer uid;
}
