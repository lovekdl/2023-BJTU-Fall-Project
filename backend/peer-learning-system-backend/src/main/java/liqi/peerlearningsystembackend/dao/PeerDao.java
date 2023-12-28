package liqi.peerlearningsystembackend.dao;

import com.github.yulichang.base.MPJBaseMapper;
import liqi.peerlearningsystembackend.pojo.PeerPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PeerDao extends MPJBaseMapper<PeerPojo> {
}
