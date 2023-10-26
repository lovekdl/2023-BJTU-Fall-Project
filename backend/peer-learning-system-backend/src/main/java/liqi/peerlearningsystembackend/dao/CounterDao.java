package liqi.peerlearningsystembackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import liqi.peerlearningsystembackend.pojo.CounterPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CounterDao extends BaseMapper<CounterPojo> {
}
