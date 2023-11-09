package liqi.peerlearningsystembackend.service;

import liqi.peerlearningsystembackend.dao.AssignmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentService {

    @Autowired
    AssignmentDao assignmentDao;
}
