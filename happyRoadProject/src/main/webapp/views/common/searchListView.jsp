<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.kh.hrp.common.PageInfo,
          java.util.ArrayList,
          com.kh.hrp.place.model.vo.Place,
          com.kh.hrp.place.model.vo.PlaceSelect"%>
<%
   PageInfo pi = (PageInfo)request.getAttribute("pi");

   ArrayList<PlaceSelect> plist = (ArrayList<PlaceSelect>)request.getAttribute("plist");
   
   String placeTitle = (String)request.getAttribute("title");
   int listNull = (int)request.getAttribute("listNull");
   
   int currentPage = pi.getCurrentPage();
   int startPage = pi.getStartPage();
   int endPage = pi.getEndPage();
   int maxPage = pi.getMaxPage();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
   
   <!-- bootstrap CSS 5.3.1 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
       
    <!-- bootstrap JavaScript 5.3.1 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
   
   <link rel="stylesheet" href="../../resources/css/fonts.css">
   
<style>
.cPage {
   /* border: 1px solid gray; */
   font-size: 30px;
   /* color:black; */
   color: rgb(3, 3, 3);
   border-radius: 30px;
   margin-left: 20px;
}

.notcPage {
   font-size: 30px;
   color: rgb(146, 145, 145);
   margin-left: 20px;
   cursor: pointer;
}

.review-pagebar {
   padding-top: 40px;
   display: flex;
   justify-content: center;
   cursor: pointer;
}
</style>
</head>
<body>
   <%@ include file = "./menubar.jsp"%>

   <!-- 검색결과영역 -->
    <div class="search-container">
        <section id="search-content">
            <div class="search-result-area">
                <div class="search-result-area-div">
                    <button type="button" class="btn btn-secondary" id="btn-order-placeNo" onclick="placeNoList(1)">최신순</button>
                    <button type="button" class="btn btn-secondary" id="btn-order-placeCount" onclick="placeCountList(1)">인기순</button>
                </div>
            </div>
        </section>

        <div class="serach-result-all">
            <div class="search-info-list">
                <ul class="ulclass">
                   <% if (listNull == 0) { %>
                      <li>검색 결과가 없습니다.</li>
                      <div style="height: 70px"></div>
                   <% } else { %>
                   <!-- 일치하지 않는 검색어에도 검색결과가 없습니다 띄우기 -->
                   <% for (PlaceSelect p : plist) { %>
                      <li>
                           <a href="eventdetailView.ed?pno=<%=p.getPlaceNo() %>">
                               <img src="./resources/images/<%=p.getImageChange() %>" alt="검색결과이미지">
                           </a>
                        <div class="result-content">
                            <div class="">
                                <div class="result-title"><a href="eventdetailView.ed?pno=<%=p.getPlaceNo() %>"><%= p.getPlaceTitle() %></a></div>
                                <div class="result-area"><span><%= p.getPlaceAddress() %></span></div>
                            </div>
                        </div>
                    </li>
                    <% } %>
            <% } %>
                </ul>
            </div>
        </div>

        <!-- 페이지네이션 -->
        <div class="review-pagebar">
        </div>
        
        <script>
           /* onloal시 검색어 유지하고 최신순, cpage1 표시 */
         window.onload = function() {
              
            let searchText = '<%=placeTitle%>';
            document.querySelector(".left-section_input").value = searchText;
            
            placeNoList(1);
           }

         /* 최신순 버튼 클릭시 다시 최신순으로 보여주기 */
         function placeNoList(cpage){
            console.log("최신순");
            $.ajax({
               url: "placeNoList.sc",
               data: {
                  title : "<%=placeTitle%>",
                  cpage : cpage
               },
               success: function(result){
                  
                  let pi = result.pi;
                  let pslist = result.pslist;
                  console.log(pi);
                  
                  /* 검색 결과가 없습니다 유지 */
                  if (pslist.length == 0){
                     const noSearch = document.createElement("li");
                     noSearch.innerHTML = "검색 결과가 없습니다.";
                     document.getElementsByClassName('ulclass').appendChild(noSearch);
                  }
                  
                  /* 최신순 그리기 */
                  let str1 = ""
                  for (let r of pslist){
                     str1 += '<div class="serach-result-all">'
                            + '<div class="search-info-list">'
                            + '<ul class="ulclass">' 
                         + '<li>'
                               + '<a href="eventdetailView.ed?pno=' + r.placeNo + '">'
                                + '<img src="' + r.imagePath + r.imageChange + '" alt="검색결과이미지">'
                               + '</a>'
                             + '<div class="result-content">'
                             + '<div class="">'
                             + '<div class="result-title">'
                             + '<a href="eventdetailView.ed?pno=' + r.placeNo + '">' + r.placeTitle + '</a></div>'
                             + '<div class="result-area"><span>"' + r.placeAddress + '"</span></div>'
                             + '</div></div></li>'
                             + '</ul></div></div>'
                  }
                  document.querySelector(".serach-result-all").innerHTML = str1;
                  
                  
                  /* 페이지네이션 그리기 */
                  let str2 = ""
                         for (let i = pi.startPage; i <= pi.endPage; i++){
                              if (i === pi.currentPage){
                                  str2 += "<a class='cPage' onclick='placeNoList("+i+")'>" + i + "</a>"
                              } else {
                                str2 += "<a class='notcPage' onclick='placeNoList("+i+")'>" + i + "</a>"
                              }
                         }
                         document.querySelector(".review-pagebar").innerHTML = str2;

               }, error: function(){
                  console.log("ajax 통신 실패");
               }
            })
         }
         /* 인기순 버튼 클릭시 인기순으로 보여주기 */
         function placeCountList(cpage){
            console.log("인기순");
            $.ajax({
               url: "placeCountList.sc",
               data: {
                  title : "<%=placeTitle%>",
                  cpage : cpage
               },
               success: function(result){
                  console.log(result);      
                  let pi = result.pi;
                  let pslist = result.pslist;
                  
                  /* 검색 결과가 없습니다 유지 */
                  if (pslist.length == 0){
                     const noSearch = document.createElement("li");
                     noSearch.innerHTML = "검색 결과가 없습니다.";
                     document.getElementsByClassName('ulclass').appendChild(noSearch);
                  }
                  
                  /* 인기순 그리기 */
                  let str1 = ""
                     for (let r of pslist){
                        str1 += '<div class="serach-result-all">'
                               + '<div class="search-info-list">'
                               + '<ul class="ulclass">' 
                            + '<li>'
                                  + '<a href="eventdetailView.ed?pno=' + r.placeNo + '">'
                                   + '<img src="' + r.imagePath + r.imageChange + '" alt="검색결과이미지">'
                                  + '</a>'
                                + '<div class="result-content">'
                                + '<div class="">'
                                + '<div class="result-title">'
                                + '<a href="eventdetailView.ed?pno=' + r.placeNo + '">' + r.placeTitle + '</a></div>'
                                + '<div class="result-area"><span>"' + r.placeAddress + '"</span></div>'
                                + '</div></div></li>'
                                + '</ul></div></div>'
                     }
                     document.querySelector(".serach-result-all").innerHTML = str1;
                     
                     
                     /* 페이지네이션 그리기 */
                     let str2 = ""
                            for (let i = pi.startPage; i <= pi.endPage; i++){
                                 if (i === pi.currentPage){
                                     str2 += "<a class='cPage' onclick='placeCountList("+i+")'>" + i + "</a>"
                                 } else {
                                   str2 += "<a class='notcPage' onclick='placeCountList("+i+")'>" + i + "</a>"
                                 }
                            }
                            document.querySelector(".review-pagebar").innerHTML = str2;

                  
               },
               error: function(){
                  console.log("ajax 통신 실패");
               }
            })
         }


         </script>

       
    <%@ include file = "./footer.jsp"%>
       
</body>
</html>