package com.kh.hrp.place.model.service;

import static com.kh.hrp.common.JDBCTemplate.*;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import com.kh.hrp.common.PageInfo;
import com.kh.hrp.common.model.vo.PlaceImage;
import com.kh.hrp.place.model.dao.PlaceDao;
import com.kh.hrp.place.model.vo.Place;
import com.kh.hrp.place.model.vo.PlaceSelect;
import com.kh.hrp.place.model.vo.Review;

public class PlaceService {
   
   public int selectListCount(int userNo) {
      
      Connection conn = getConnection();
      int listCount = new PlaceDao().selectListCount(conn, userNo);

      close(conn);
      return listCount;
   }
   
   public int selectSearchCount(String placeTitle) {
      
      Connection conn = getConnection();
      int SearchCount = new PlaceDao().selectSearchCount(conn, placeTitle);

      close(conn);
      return SearchCount;
   }
   
   
   public ArrayList<PlaceSelect> placeLikeSelectList(int userNo, PageInfo pi) {
      
      Connection conn = getConnection();
      ArrayList<PlaceSelect> placelist = new PlaceDao().placeLikeSelectList(conn, userNo, pi);
      
      close(conn);
      return placelist;
   }

   public int placeLikeDeleteController(int userNo, int placeNo) {
      
      Connection conn = getConnection();
      int result = new PlaceDao().placeLikeDeleteController(conn, userNo, placeNo);
      if (result > 0) {
         commit(conn);
      } else {
         rollback(conn);
      }
      
      close(conn);
      return result;
   }

   public ArrayList<PlaceSelect> selectSearchList(String placeTitle, PageInfo pi) {
      
      Connection conn = getConnection();
      ArrayList<PlaceSelect> plist = new PlaceDao().selectSearchList(conn, placeTitle, pi);
      
      close(conn);
      return plist;
   }



   
   public Place increaseCount(int placeNo) {
      Connection conn = getConnection();
      PlaceDao pDao = new PlaceDao();
      
      // 1) 조회수 1올리기
      int result = pDao.increaseCount(conn, placeNo);
      Place p = null;
      
      if (result > 0) {   
         commit(conn);
         // 2) detail 객체 불러오기
         p = pDao.selectPlace(conn, placeNo);
      } else {
         rollback(conn);
      }
      close(conn);
      return p;
   }
   
   public ArrayList<PlaceImage> selectPlaceImageList(int placeNo) {
      Connection conn = getConnection();
      ArrayList<PlaceImage> list = new PlaceDao().selectPlaceImageList(conn, placeNo);
      close(conn);
      return list;

   }
   
   public ArrayList<Review> selectReviewList(int placeNo, PageInfo pi){
      Connection conn = getConnection();
      ArrayList<Review> list = new PlaceDao().selectReviewList(conn, placeNo, pi);
      close(conn);
      return list;
      
   }
   
   public int reviewListCount(int placeNo) {
      Connection conn = getConnection();      
      int reviewListCount = new PlaceDao().reviewListCount(conn, placeNo);
      close(conn);
      return reviewListCount;
   }
   
   public boolean checkLike(int placeNo, int userNo) {
      Connection conn = getConnection();      
      boolean isLike = new PlaceDao().checkLike(conn, placeNo, userNo);
      close(conn);
      return isLike;
   }
   
   public int placeLikeInsertController(int userNo, int placeNo) {
      Connection conn = getConnection();
      int result = new PlaceDao().placeLikeInsertController(conn, userNo, placeNo);
      
      if (result > 0) {
         commit(conn);
      } else {
         rollback(conn);
      }
      close(conn);
      return result;
   }

   public ArrayList<PlaceSelect> mainSelectController() {
      Connection conn = getConnection();
      ArrayList<PlaceSelect> list = new PlaceDao().mainSelectController(conn);
      close(conn);
      return list;
   }
   
   public ArrayList<PlaceSelect> mainCountSearch() {

      Connection conn = getConnection();
      ArrayList<PlaceSelect> list = new PlaceDao().mainCountSearch(conn);
      close(conn);
      return list;
   
   }

   

   public int insertManagerPlace(PlaceSelect ps, ArrayList<PlaceImage> list) {
      Connection conn = getConnection();

      int result1 = new PlaceDao().insertManagerPlace(conn, ps);

      int result2 = new PlaceDao().insertPlaceImage(conn, list, ps);
      
      if (result1 > 0 && result2 > 0) {
         commit(conn);
      } else {
         rollback(conn);
      }
      
      close(conn);
      
      return result1 * result2;
   }


   public int insertReview(int placeNo, int userNo, int star, String reviewContent) {
      Connection conn = getConnection();
      int result = new PlaceDao().insertReview(conn, placeNo, userNo, star, reviewContent);
      
      if (result > 0) {
         commit(conn);
      } else {
         rollback(conn);
      }
      close(conn);
      return result;
   }
   
   public int deleteReview(int userNo, int reviewNo) {
      Connection conn = getConnection();
      int result = new PlaceDao().deleteReview(conn, userNo, reviewNo);
      
      if (result > 0) {
         commit(conn);
      } else {
         rollback(conn);
      }
      close(conn);
      return result;
   }

   public ArrayList<PlaceSelect> selectPlaceBoardList(String placeThema, PageInfo pi) {
      Connection conn = getConnection();
      ArrayList<PlaceSelect> pslist = new PlaceDao().selectPlaceBoardList(conn, pi,placeThema);
      close(conn);
      return pslist;

   }
   
   public int selectEventListCount(Date sqlDate, String thema) {
      Connection conn = getConnection();
      int SearchCount = new PlaceDao().selectEventListCount(conn, sqlDate, thema);
      
      close(conn);
      return SearchCount;
   }

   public ArrayList<PlaceSelect> mainEventListSearch(Date sqlDate, String thema, PageInfo pi) {
      Connection conn = getConnection();
      ArrayList<PlaceSelect> pslist = new PlaceDao().mainEventListSearch(conn, thema, sqlDate, pi);
      close(conn);
      return pslist;
   }

   public int insertManagerPlaceImage(Place p,ArrayList<PlaceImage> plist) {
      Connection conn = getConnection();
      int result1 = new PlaceDao().insertManagerPlace(conn, p);
      int result2 = new PlaceDao().insertManagerPlaceImage(conn ,plist);
      if(result1 * result2 > 0) {
         commit(conn);
      }else {
         rollback(conn);
      }
      close(conn);
      return result1*result2;
   }

   public int placeThemaListCount(String placeThema) {
      Connection conn = getConnection();
      int list = new PlaceDao().placeThemaListCount(conn, placeThema);
      return list;
   }

	public ArrayList<PlaceSelect> selectFaPlaceBoardList(String placeThema, PageInfo pi) {
		Connection conn = getConnection();
		ArrayList<PlaceSelect> list = new PlaceDao().selectFaPlaceBoardList(conn, placeThema, pi);
		return list;
	}
	
	public ArrayList<PlaceSelect> selectSearchPlaceCountList(String placeTitle, PageInfo pi) {
		Connection conn = getConnection();
		ArrayList<PlaceSelect> pslist = new PlaceDao().selectSearchPlaceCountList(conn, placeTitle, pi);
		close(conn);
		return pslist;
	}



   
}
