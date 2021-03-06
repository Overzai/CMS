package com.zaico.cms.servicies.implementation;

import com.zaico.cms.dao.implementation.FactoryDAO;
import com.zaico.cms.dao.interfaces.RoleDAO;
import com.zaico.cms.dao.interfaces.UserDAO;
import com.zaico.cms.entities.Role;
import com.zaico.cms.entities.User;
import com.zaico.cms.servicies.interfaces.RoleService;
import com.zaico.cms.utility.ErrorCode;
import com.zaico.cms.utility.ExceptionCMS;


import org.apache.log4j.LogManager; import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by nzaitsev on 17.08.2016.
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    // Logger
    private static final Logger LOG = LogManager.getLogger(RoleServiceImpl.class);
    // DAO
    @Autowired
    private RoleDAO roleDAO;

    /**
     * Create new Role
     * @param role
     * @return
     * @throws ExceptionCMS
     */
    public Role createRole(Role role) throws ExceptionCMS {
        try {
            return roleDAO.create(role);
        } catch (Exception e) {
            String errMes = "Role creation error:"+new Date();
            LOG.info(errMes);
            throw new ExceptionCMS(errMes, ErrorCode.ROLE_CREATION_ERROR);
        }
    }

    /**
     * Find role be id
     * @param id
     * @return
     * @throws ExceptionCMS
     */
    public Role findRole(long id) throws ExceptionCMS {
        try {
            return roleDAO.read(id);
        } catch (Exception e) {
            String errMes = "Role not found:"+new Date();
            LOG.info(errMes);
            throw new ExceptionCMS(errMes,ErrorCode.ROLE_NOT_FOUND);
        }
    }

    public List<Role> findAllRoles() throws ExceptionCMS {
        try {
            return roleDAO.getAll();
        } catch (Exception e) {
            String errMes = "ALL ROLES ERRRRPOR:"+new Date();
            LOG.info(errMes);
            throw new ExceptionCMS(errMes,ErrorCode.ROLE_NOT_FOUND);
        }
    }

    /**
     * Update role entity
     * @param role
     * @return
     * @throws ExceptionCMS
     */
    public Role updateRole(Role role) throws ExceptionCMS {
        try {
            return roleDAO.update(role);
        } catch (Exception e) {
            String errMes = "Role update error :"+new Date();
            LOG.info(errMes);
            throw new ExceptionCMS(errMes,ErrorCode.ROLE_CANNOT_BE_UPDATED);
        }
    }

    /**
     * Delete role entity
     * @param role
     * @throws ExceptionCMS
     */
    public void deleteRole(Role role) throws ExceptionCMS {
        try {
            roleDAO.delete(role);
        } catch (Exception e) {
            String errMes = "Role delete error :"+new Date();
            LOG.info(errMes);
            throw new ExceptionCMS(errMes,ErrorCode.ROLE_CANNOT_BE_DELETED);
        }
    }
}
