package com.zaico.cms.servlets.user;

import com.zaico.cms.entities.User;
import com.zaico.cms.servicies.implementation.FactoryService;
import com.zaico.cms.servicies.implementation.UserServiceImpl;
import com.zaico.cms.servicies.interfaces.UserService;
import com.zaico.cms.utility.ErrorCode;
import com.zaico.cms.utility.ExceptionCMS;
import com.zaico.cms.utility.ExceptionHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by nzaitsev on 02.09.2016.
 */
@WebServlet("/deleteuser")
public class UserDeleteServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
    UserService userService = FactoryService.getUserServiceInstance();
    User user = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            user = userService.findUser((long)id);
            request.setAttribute("user",user);
            request.setAttribute("infoMessage","You want to delete this role. Are you sure?");
        } catch (Exception e) {
            LOG.info("User \""+user.getLogin()+ "\" notfounded at "+new Date());
            String errMess = ExceptionHandler.handleException(e);
        }
        request.setAttribute("action","/deleteuser");
        request.setAttribute("disabled","disabled");
        request.setAttribute("button","DELETE");
        request.getRequestDispatcher("pages/user/user.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String sessionUser = "";
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("user")) {
                    sessionUser = cookie.getValue();                }
            }

            if ( sessionUser == user.getLogin()) {
                throw new ExceptionCMS("You can`t delete youself!", ErrorCode.USER_CANNOT_BE_DELETED);
            }
            userService.deleteUser(user);
            String message = "User \""+user.getLogin()+"\" deleted successfully";
            LOG.info(message);
            request.setAttribute("infoMessage",message);
        } catch (Exception e) {
            String message = ExceptionHandler.handleException(e);
            request.setAttribute("errMessage",message);
        }
        request.getRequestDispatcher("/users").forward(request,response);
    }
}
