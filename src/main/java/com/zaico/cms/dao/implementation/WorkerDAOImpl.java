package com.zaico.cms.dao.implementation;

import com.zaico.cms.dao.interfaces.WorkerDAO;
import com.zaico.cms.entities.Worker;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by nzaitsev on 10.08.2016.
 * @author ZAITNIK
 * Class for implementation abstract & interfaces
 */
@Repository("workerDao")
public class WorkerDAOImpl extends AbstractDAO<Worker> implements WorkerDAO {
    /**
     * Get all user
     * @return user like result list
     */
    @Transactional
    public List<Worker> getBySkill(long skillId) {
        List<Worker> result = null;
        Query query = em.createNamedQuery("Worker.getBySkill");
        query.setParameter("skill",skillId);
        result = query.getResultList();
        return result;
    }
}
