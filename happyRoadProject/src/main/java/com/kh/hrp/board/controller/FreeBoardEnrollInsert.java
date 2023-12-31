package com.kh.hrp.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.hrp.board.model.service.BoardService;
import com.kh.hrp.board.model.vo.Board;

/**
 * Servlet implementation class FreeBoardEnrollUpdate
 */
@WebServlet("/enrollInsert.fb")
public class FreeBoardEnrollInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardEnrollInsert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		String boardTitle = request.getParameter("boardTitle");
		String boardContend = request.getParameter("boardContend");
		
		Board b = new Board();
		b.setBoardTitle(boardTitle);
		b.setBoardContent(boardContend);
		
		int result = new BoardService().insertBoard(b, userNo);
		
		
		if (result > 0) {
			request.getSession().setAttribute("alertMsg", "일반게시글 작성 성공");
			
			response.sendRedirect(request.getContextPath() + "/freeboardForm.fb?cpage=1");
			
		} else {
			request.setAttribute("errorMsg", "일반게시글 작성 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
