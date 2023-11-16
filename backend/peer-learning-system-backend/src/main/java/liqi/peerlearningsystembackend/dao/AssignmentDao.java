package liqi.peerlearningsystembackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import liqi.peerlearningsystembackend.pojo.AssignmentPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssignmentDao extends MPJBaseMapper<AssignmentPojo> {
}
