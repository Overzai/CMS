package com.zaico.cms.servlets.skill;

import com.zaico.cms.entities.Skill;
import com.zaico.cms.servicies.implementation.FactoryService;
import com.zaico.cms.servicies.implementation.SkillServiceImpl;
import com.zaico.cms.servicies.implementation.UserServiceImpl;
import com.zaico.cms.servicies.interfaces.SkillService;
import com.zaico.cms.utility.ExceptionHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by nzaitsev on 31.08.2016.
 */
@WebServlet("/newskill")
public class SkillCreate extends HttpServlet {

    private static final Log LOG = LogFactory.getLog(SkillServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action","/newskill");
        request.setAttribute("button","CREATE");
        request.getRequestDispatcher("pages/skill/skill.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String skillName = request.getParameter("skillname");
        String skillDesc = request.getParameter("skilldesc");

        try {
            SkillService skillService = FactoryService.getSkillServiceInstance();
            Skill skill = new Skill(skillName,skillDesc);
            skillService.createSkill(skill);
            String message = "Skill \""+skillName+"\" created at "+new Date();
            LOG.info(message);
            request.setAttribute("sucMessage",message);
        } catch (Exception e) {
            String errorMessage = ExceptionHandler.handleException(e);
            request.setAttribute("errMessage"+" Try again please, check parameters", errorMessage);
        }
        request.getRequestDispatcher("/skills").forward(request, response);
    }
}