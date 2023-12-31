package com.tsw.studentsupps.Controller;

import com.tsw.studentsupps.Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("Utente")!=null) {
            response.sendRedirect("Account");
            return;
        }

        String username= request.getParameter("username");
        String password= request.getParameter("password");

        String usernameRGX= "^[A-Za-z][A-Za-z0-9_]{4,29}$";
        String emailRGX= "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";
        String passwordRGX= "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\\[{}\\]:;',?*~$^\\-+=<>]).{8,30}$";

        if(!username.matches(usernameRGX) && !username.matches(emailRGX)) {
            request.setAttribute("loginStatus", "usernameWrongPattern");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/Account");
            dispatcher.forward(request, response);
            return;
        }
        if(!password.matches(passwordRGX)) {
            request.setAttribute("loginStatus", "passwordWrongPattern");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/Account");
            dispatcher.forward(request, response);
            return;
        }

        Utente u= UtenteDAO.doRetrieveByUsernamePassword(username, password);

        if(u!= null) {
            HttpSession session= request.getSession();
            session.setAttribute("Utente", u);

            Carrello oldCart;
            if(session.getAttribute("Cart")==null) {
                oldCart= new Carrello();
                CarrelloDAO.doSave(oldCart);
                session.setAttribute("Cart", oldCart);
            } else
                oldCart= (Carrello) session.getAttribute("Cart");

            Carrello userCart= CarrelloDAO.doRetrieveById(UtenteDAO.doRetrieveIdCart(u));
            if(userCart==null) {
                request.setAttribute("errorMessage", "Carrello utente: null");
                RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/results/error.jsp");
                dispatcher.forward(request,response);
                return;
            }

            //Ricalcolo da zero il totale al login
            List<String> prodList= ProdottocarrelloDAO.doRetrieveProdotti(userCart.getId());
            userCart.setTotale(0);
            for(String idProd: prodList) {
                int quantita= ProdottocarrelloDAO.doRetrieveQuantita(userCart.getId(), idProd);
                Prodotto prod= ProdottoDAO.doRetrieveById(idProd);
                if(prod==null) {
                    continue;
                }
                BigDecimal price= BigDecimal.valueOf(prod.getPrezzo());
                userCart.setTotale(
                        (BigDecimal.valueOf(userCart.getTotale()).add(
                                price.multiply(BigDecimal.valueOf(quantita))
                        )).doubleValue());
            }

            prodList= ProdottocarrelloDAO.doRetrieveProdotti(oldCart.getId());
            for(String idProd: prodList) {
                int quantitaOldCart= ProdottocarrelloDAO.doRetrieveQuantita(oldCart.getId(), idProd);
                int quantitaUserCart= ProdottocarrelloDAO.doRetrieveQuantita(userCart.getId(), idProd);
                if(quantitaUserCart == -1) {
                    ProdottocarrelloDAO.doSave(userCart.getId(), idProd, quantitaOldCart);
                }
                else
                    ProdottocarrelloDAO.doUpdateQuantita(userCart.getId(), idProd, quantitaOldCart+quantitaUserCart);

                Prodotto prod= ProdottoDAO.doRetrieveById(idProd);
                if(prod==null) {
                    continue;
                }
                BigDecimal price= BigDecimal.valueOf(prod.getPrezzo());
                userCart.setTotale(
                        (BigDecimal.valueOf(userCart.getTotale()).add(
                                price.multiply(BigDecimal.valueOf(quantitaOldCart))
                        )).doubleValue());
            }

            CarrelloDAO.doUpdate(userCart);
            CarrelloDAO.doDelete(oldCart);
            session.setAttribute("Cart", userCart);
            response.sendRedirect(request.getContextPath()+'/');
        }
        else {
            request.setAttribute("loginStatus", "failedLogin");
            RequestDispatcher dispatcher= request.getRequestDispatcher("/Account");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("Account");
    }
}
