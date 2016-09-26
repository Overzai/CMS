package com.zaico.cms.servlets.worker;

import com.zaico.cms.entities.Worker;
import com.zaico.cms.servicies.implementation.FactoryService;
import com.zaico.cms.servicies.implementation.UserServiceImpl;
import com.zaico.cms.servicies.implementation.WorkerServiceImpl;
import com.zaico.cms.servicies.interfaces.WorkerService;
import com.zaico.cms.utility.ExceptionHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by nzaitsev on 02.09.2016.
 */
@WebServlet("/workers")
public class WorkerAllServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(WorkerServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WorkerService workerService = FactoryService.getWorkerServiceInstance();
        if (request.getSession().getAttribute("user") != null) {
            try {
                List<Worker> workers = workerService.findAllWorkers();
                request.setAttribute("workers",workers);
            } catch (Exception e) {
                String errMes = ExceptionHandler.handleException(e);
                LOG.info(errMes);
            }
            request.getRequestDispatcher("pages/worker/allworkers.jsp").forward(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
