package liqi.peerlearningsystembackend.dao;

import com.github.yulichang.base.MPJBaseMapper;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends MPJBaseMapper<UserPojo> {
}
