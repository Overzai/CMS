package com.zaico.cms.servlets.user;

import com.zaico.cms.entities.Role;
import com.zaico.cms.entities.User;
import com.zaico.cms.servicies.implementation.FactoryService;
import com.zaico.cms.servicies.implementation.UserServiceImpl;
import com.zaico.cms.servicies.interfaces.RoleService;
import com.zaico.cms.servicies.interfaces.UserService;
import com.zaico.cms.utility.ExceptionHandler;


import org.apache.log4j.LogManager; import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nzaitsev on 02.09.2016.
 */
public class UserUpdateServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);
    UserService userService = FactoryService.getUserServiceInstance();
    RoleService roleService = FactoryService.getRoleServiceInstance();
    User user = null;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            user = userService.findUser(id);
            request.setAttribute("user",user);
            List<Role> allRoles = roleService.findAllRoles();
            request.setAttribute("roles",allRoles);

        } catch (Exception e) {
            LOG.info("User \""+user.getLogin()+ "\" notfounded at "+new Date());
            String errMess = ExceptionHandler.handleException(e);
        }
        request.setAttribute("action","/updateuser");
        request.setAttribute("button","UPDATE");
        request.setAttribute("title","CMS Update user");
        request.setAttribute("cmsheader","Update user "+user.getLogin());
        request.getRequestDispatcher("pages/user/user.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String userName = request.getParameter("username");
            String userDesc = request.getParameter("password");
            String[] roles = request.getParameterValues("roles");
            user.setLogin(userName);
            user.setPassword(userDesc);
            List<Role> userRoles = new ArrayList<Role>();
            userService.clearRoles(user);
            // if role id not null
            if (roles != null || roles.length != 0) {
                for ( String roleId: roles) {
                    // Find each role with id and add to role list
                    long rid = Long.parseLong(roleId);
                    userRoles.add(roleService.findRole(rid));
                }
            }
            // set all finded role as user role
            user.setRoles(userRoles);
            userService.updateUser(user);
            LOG.info("User "+user.getLogin()+ " updated at "+new Date());
            request.setAttribute("sucMessage","User \""+user.getLogin()+ "\" updated successfully");
        } catch (Exception e) {
            String errMess = ExceptionHandler.handleException(e);
            LOG.info(errMess);
            request.setAttribute("errMessage",errMess);
            doGet(request,response);
        }
        request.getRequestDispatcher("/users").forward(request, response);

    }
}
