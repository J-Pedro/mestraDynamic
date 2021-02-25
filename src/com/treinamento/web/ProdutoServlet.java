package com.treinamento.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treinamento.dao.ProdutoDAO;
import com.treinamento.model.Produto;

/**
 * Servlet implementation class ProdutoServlet
 */
@WebServlet("/")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdutoDAO produtoBO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProdutoServlet() {
    	this.produtoBO = new ProdutoDAO();
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String action = request.getServletPath();
		
     
            switch (action) {
                case "/new":
                	showNewForm(request, response);
                    break;
                case "/insert":
    				try {
    					insertProduto(request, response);
    				} catch (SQLException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                    break;
                case "/delete":
                	try {
                		deleteProduto(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "/edit":
				try {
					showEditForm(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                case "/update":
                	try {
                	updateProduto(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;
                default:        	
    				try {
    					listproduto(request, response);
    				} catch (SQLException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                    break;
            }

}
	
    private void listproduto(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException, ServletException {
        List < Produto > listproduto = produtoBO.selectAllUsers();
        request.setAttribute("listproduto", listproduto);
        RequestDispatcher dispatcher = request.getRequestDispatcher("produto-list.jsp");
        dispatcher.forward(request, response);
    }
	
    private void insertProduto(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        String nome = request.getParameter("nome");
        String tipo = request.getParameter("tipo");
        Produto newProduto = new Produto(nome, tipo);
        produtoBO.insertProduto(newProduto);
        response.sendRedirect("list");
    }
    
    private void deleteProduto(HttpServletRequest request, HttpServletResponse response)
    	    throws SQLException, IOException {
    	        int id = Integer.parseInt(request.getParameter("id"));
    	        produtoBO.deleteProduto(id);
    	        response.sendRedirect("list");

    	    }
    
    private void updateProduto(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String tipo = request.getParameter("tipo");

        Produto produto = new Produto(id, nome, tipo);
        ProdutoDAO.updateProduto(produto);
        response.sendRedirect("list");
    }

	
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("produto-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Produto existingUser = produtoBO.selectProduto(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("produto-form.jsp");
        request.setAttribute("produto", existingUser);
        dispatcher.forward(request, response);

    }
	
	
}
