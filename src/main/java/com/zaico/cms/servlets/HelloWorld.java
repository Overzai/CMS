package com.zaico.cms.servlets;

/**
 * Created by nzaitsev on 18.08.2016.
 */
import com.zaico.cms.dao.implementation.FactoryDAO;
import com.zaico.cms.dao.interfaces.OrderDAO;
import com.zaico.cms.dao.interfaces.WorkerDAO;
import com.zaico.cms.entities.Order;
import com.zaico.cms.entities.Worker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/main")
public class HelloWorld extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("name", "EnterOrderNumber");
        req.setAttribute("date", new Date() );

        req.getRequestDispatcher("pages/main.jsp").forward(req, resp);
    }

}