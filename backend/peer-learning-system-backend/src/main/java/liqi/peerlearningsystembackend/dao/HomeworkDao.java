package liqi.peerlearningsystembackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import liqi.peerlearningsystembackend.pojo.HomeworkPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HomeworkDao extends BaseMapper<HomeworkPojo> {
}
