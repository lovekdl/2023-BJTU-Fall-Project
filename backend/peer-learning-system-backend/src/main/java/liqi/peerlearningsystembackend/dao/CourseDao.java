package liqi.peerlearningsystembackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import liqi.peerlearningsystembackend.pojo.CoursePojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseDao extends MPJBaseMapper<CoursePojo> {
}
