package com.zaico.cms.servlets.skill;

import com.zaico.cms.dao.implementation.FactoryDAO;
import com.zaico.cms.dao.interfaces.SkillDAO;
import com.zaico.cms.entities.Skill;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by nzaitsev on 25.08.2016.
 */
public class AllSkillsServlet extends HttpServlet {

    /**
     * Get method handler
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            SkillDAO skillDAO = FactoryDAO.getSkillDAOInstance();
            List<Skill> allSkills = skillDAO.getAll();
            if (allSkills.size() == 0) {
                request.setAttribute("infoMessage", "No skill ");
            }
            request.setAttribute("skills", allSkills);
            request.setAttribute("title","CMS Skills");
            request.setAttribute("cmsheader","Skills");
            request.getRequestDispatcher("pages/skill/allskills.jsp").forward(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }

    /**
     * Post method handler
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
