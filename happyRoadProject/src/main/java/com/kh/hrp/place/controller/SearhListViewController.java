package com.kh.hrp.place.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.hrp.common.PageInfo;
import com.kh.hrp.common.PageInfoController;
import com.kh.hrp.place.model.service.PlaceService;
import com.kh.hrp.place.model.vo.Place;
import com.kh.hrp.place.model.vo.PlaceSelect;

/**
 * Servlet implementation class SearhListView
 */
@WebServlet("/search.sc")
public class SearhListViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

    public SearhListViewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String placeTitle = request.getParameter("title");
		int listNull = 0;
		PlaceService pService = new PlaceService();
		PageInfo pi = new PageInfo();
		
		if (placeTitle == "") { // 검색어 입력 없을시
			listNull = 0;
			
			pi =  PageInfoController.pageController(0, 1, 1, 1);
			request.setAttribute("listNull", listNull);
			request.setAttribute("pi", pi);
			request.setAttribute("title", placeTitle);
			
			System.out.println(pi);
			
			request.getRequestDispatcher("views/common/searchListView.jsp").forward(request, response);
		} else {
			listNull = 1;
			int searchCount = pService.selectSearchCount(placeTitle);
			int currentPage = Integer.parseInt(request.getParameter("cpage"));
			
			pi = PageInfoController.pageController(searchCount, currentPage, 5, 5);
			ArrayList<PlaceSelect> plist = pService.selectSearchList(placeTitle, pi);
			
			if(plist.isEmpty()) {
				listNull = 0;
			}else {
				listNull = 1;
			}
			System.out.println(searchCount);
			System.out.println(currentPage);
			System.out.println(pi);
			
			request.setAttribute("listNull", listNull);
			request.setAttribute("plist", plist);
			request.setAttribute("pi", pi);
			request.setAttribute("title", placeTitle);
			
			
			request.getRequestDispatcher("views/common/searchListView.jsp").forward(request, response);
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
