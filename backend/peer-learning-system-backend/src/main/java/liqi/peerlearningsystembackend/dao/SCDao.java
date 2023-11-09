package liqi.peerlearningsystembackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import liqi.peerlearningsystembackend.pojo.SCPojo;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface SCDao extends MPJBaseMapper<SCPojo> {
}
