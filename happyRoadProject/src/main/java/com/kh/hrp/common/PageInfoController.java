package com.kh.hrp.common;

public class PageInfoController {
	
	public static PageInfo pageController(int listCount, int currentPage, int pageLimit, int boardLimit){
		// pageController(현재 총 게시글 수, 현재 페이지(즉, 사용자가 요청한 페이지), 페이지 하단에 보여질 페이징바의 페이지 최대의 개수, 한 페이지내에 보여질 게시글 최대갯수)
		
		int maxPage = (int)Math.ceil((double)listCount / boardLimit); //가장 마지막페이지(총 페이지의 수)
		int startPage = ((currentPage - 1) / pageLimit) * pageLimit+1; //페이징바의 시작수
		int endPage = startPage + pageLimit - 1; //페이징바의 끝수
		
		endPage = endPage > maxPage ? maxPage : endPage;
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		return pi;
	}
}
