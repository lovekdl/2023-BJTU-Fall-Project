package liqi.peerlearningsystembackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<UserPojo> {
}
