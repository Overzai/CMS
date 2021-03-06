package com.zaico.cms.servlets;

import com.zaico.cms.entities.Role;
import com.zaico.cms.entities.User;
import com.zaico.cms.servicies.implementation.FactoryService;
import com.zaico.cms.servicies.implementation.UserServiceImpl;
import com.zaico.cms.servicies.interfaces.UserService;
import com.zaico.cms.utility.ErrorCode;
import com.zaico.cms.utility.ExceptionCMS;
import com.zaico.cms.utility.ExceptionHandler;


import org.apache.log4j.LogManager; import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nzaitsev on 22.08.2016.
 */
public class LoginServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            request.setAttribute("cmsheader","You must login to work with CMS");
        }
        request.getRequestDispatcher("pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("ulog");
        String password = request.getParameter("upass");
        UserService userService = FactoryService.getUserServiceInstance();
        try {
//            Try to login through service and DAO
            /* SMTH wrong here, with bad credentials still work */
            User user = userService.login(name, password);
            String username = user.getLogin();
            if (user != null) {
//                Logging user login
                LOG.info("Success login "+username);
//                Star session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
//                Set role as cookies
                List<Role> roleList;
                roleList = user.getRoles();
                Cookie userRole = null;
                for ( Role role : roleList) {
                    userRole = new Cookie("role",role.toString(1));
                    session.setAttribute("role",role.toString(1));
                    userRole.setMaxAge(-1);
                }
//                Setting session to expiry in 30 mins
                session.setMaxInactiveInterval(30 * 60);
                Cookie userSession = new Cookie("user", username);
                userSession.setMaxAge(30 * 60);
                response.addCookie(userRole);
                response.addCookie(userSession);
                response.sendRedirect("/main");
            }
         }
        catch (Exception e) {
//            Logging failed login
            LOG.info("Failed login "+name);
//            Handle exception
            String errorMessage = ExceptionHandler.handleException(e);
            request.setAttribute("errMessage", errorMessage);
            request.setAttribute("infoMessage", "Try again, please");
            request.setAttribute("title", "CMS Login page");
            request.getRequestDispatcher("pages/login.jsp").forward(request, response);
        }
    }
}
