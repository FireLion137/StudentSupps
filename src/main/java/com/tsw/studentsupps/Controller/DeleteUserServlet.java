package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.Utente;
import com.tsw.studentsupps.Model.UtenteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/DeleteUser")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente callingUser= (Utente) request.getSession().getAttribute("Utente");
        String userToDeleteId= request.getParameter("id");
        if(!UUID.fromString(userToDeleteId).toString().equals(userToDeleteId)) {
            request.setAttribute("errorMessage", "UUID non valido");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }
        Utente userToDelete= UtenteDAO.doRetrieveById(userToDeleteId);
        if(!callingUser.equals(UtenteDAO.doRetrieveById(callingUser.getId())) || userToDelete == null) {
            request.setAttribute("errorMessage", "Dati Utente Session/DB non coincidenti");
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request,response);
            return;
        }

        if(callingUser.isAdmin() || userToDelete.getId().equals(callingUser.getId())) {
            UtenteDAO.doDelete(userToDelete);
            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/updateSuccess.jsp");
            dispatcher.forward(request,response);
            return;
        }

        request.setAttribute("errorMessage", "Delete Servlet Error");
        RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+'/');
    }
}
