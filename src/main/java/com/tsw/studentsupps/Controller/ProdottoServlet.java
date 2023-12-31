package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/Shop/Prodotto")
public class ProdottoServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String pName=request.getParameter("prodName");
        if(pName== null){
            response.sendRedirect(request.getContextPath()+"/Shop");
            return;
        }
        Prodotto p=ProdottoDAO.doRetrieveByName(pName);
        if(p== null){
            response.sendRedirect(request.getContextPath()+"/Shop");
            return;
        }
        Sconto sc = ScontoDAO.doRetrieveById(ProdottoDAO.doRetrieveDiscountId(p.getId())) ;
        request.setAttribute("prodotto",p);
        request.setAttribute("recensioni", RecensioneDAO.doRetrieveByIdProdotto(p.getId()));
        request.setAttribute("sconto", sc);
        RequestDispatcher dispatcher= request.getRequestDispatcher("/pages/Product.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

}
