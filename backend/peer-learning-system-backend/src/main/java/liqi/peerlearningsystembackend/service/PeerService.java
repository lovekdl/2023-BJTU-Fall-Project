package liqi.peerlearningsystembackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import liqi.peerlearningsystembackend.dao.CounterDao;
import liqi.peerlearningsystembackend.dao.PeerDao;
import liqi.peerlearningsystembackend.pojo.CounterPojo;
import liqi.peerlearningsystembackend.pojo.HomeworkPojo;
import liqi.peerlearningsystembackend.pojo.PeerPojo;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import liqi.peerlearningsystembackend.utils.Constants;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PeerService {

    @Autowired
    PeerDao peerDao;

    @Autowired
    UserService userService;

    @Autowired
    CounterDao counterDao;

    /**
     * 添加一条peer
     * @param homeworkUUID 作业UUID
     * @param userUUID 学生UUID
     * @param assignmentUUID 作业UUID
     * @return 返回该作业的UUID
     */
    public String addPeer(String userUUID, String homeworkUUID, String assignmentUUID
                            , String username, Integer homeworkID, Integer assignmentID) {
        String uuid = UUID.randomUUID().toString();
        try {
            Integer peerID = counterDao.selectById(Constants.PEER_COUNTER).getUid();

            peerDao.insert(new PeerPojo(uuid, peerID, userUUID, homeworkUUID, assignmentUUID, username, homeworkID, assignmentID, null, null, "正在互评中"));

            // 更新计数器
            CounterPojo counterPojo = counterDao.selectById(Constants.PEER_COUNTER);
            counterPojo.setUid(counterPojo.getUid() + 1);
            counterDao.updateById(counterPojo);
            return uuid;
        } catch (Exception e) {
            return "ERROR";
        }
    }

    /**
     * 根据peerID删除peer
     * @param peerID peerID
     * @return 返回"OK"或"ERROR"
     */
    public String deletePeerByID(Integer peerID) {
        try {
            peerDao.selectOne(new QueryWrapper<PeerPojo>().eq("peerID", peerID));
            peerDao.deleteById(peerID);
            return "OK";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    /**
     * 根据homeworkUUID和userUUID获取peerID
     * @param homeworkUUID 作业UUID
     * @param userUUID 学生UUID
     * @return 返回uuid或"ERROR"
     */
    @Nullable
    public PeerPojo getPeerByHomeworkUUIDAndUserUUID(Integer homeworkUUID, Integer userUUID) {
        return peerDao.selectOne(new QueryWrapper<PeerPojo>().eq("homeworkUUID", homeworkUUID).eq("userUUID", userUUID));
    }

    /**
     * 根据peerID获取peer
     * @param uuid peerID
     * @return 返回peer
     */
    @Nullable
    public PeerPojo getPeerByUUID(String uuid) {
        return peerDao.selectOne(new QueryWrapper<PeerPojo>().eq("uuid", uuid));
    }

    /**
     * 根据peerID获取peer
     * @param peerID peerID
     * @return 返回peer
     */
    @Nullable
    public PeerPojo getPeerByID(Integer peerID) {
        return peerDao.selectOne(new QueryWrapper<PeerPojo>().eq("peerID", peerID));
    }

    /**
     * 根据userUUID获取peer列表
     * @param userUUID 学生UUID
     * @return 返回peer
     */
    @Nullable
    public List<PeerPojo> getPeerListByUserUUID(String userUUID) {
        return peerDao.selectList(new QueryWrapper<PeerPojo>().eq("userUUID", userUUID));
    }

    /**
     * 根据userUUID和assignmentUUID获取peer列表
     */
    @Nullable
    public List<PeerPojo> getPeerListByUserUUIDAndAssignmentUUID(String userUUID, String assignmentUUID) {
        return peerDao.selectList(new QueryWrapper<PeerPojo>().eq("userUUID", userUUID).eq("assignmentUUID", assignmentUUID));
    }



    /**
     * 根据homeworkUUID获取peer列表
     * @param homeworkUUID 作业UUID
     * @return 返回peer
     */
    @Nullable
    public List<PeerPojo> getPeerListByHomeworkUUID(Integer homeworkUUID) {
        return peerDao.selectList(new QueryWrapper<PeerPojo>().eq("homeworkUUID", homeworkUUID));
    }

    /**
     * 设置互评作业分数
     */
    public String setScore(int peerID, int score) {
        try {
            PeerPojo peer = getPeerByID(peerID);
            if (peer == null)
                return "ERROR";
            peer.setScore(score);
            peerDao.updateById(peer);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 设置互评作业评论
     */
    public String setComment(int peerID, String comment) {
        try {
            PeerPojo peer = getPeerByID(peerID);
            if (peer == null)
                return "ERROR";
            peer.setComment(comment);
            peerDao.updateById(peer);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 设置互评作业状态
     */
    public String setStatus(int peerID, String status) {
        try {
            PeerPojo peer = getPeerByID(peerID);
            if (peer == null)
                return "ERROR";
            peer.setStatus(status);
            peerDao.updateById(peer);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

}
