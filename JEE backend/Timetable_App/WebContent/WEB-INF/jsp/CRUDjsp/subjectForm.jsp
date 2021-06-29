<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="http://localhost:8000/static/css/materialize.min.css">
    <link rel="stylesheet" href="http://localhost:8000/static/css/forms.css">
    <link rel="stylesheet" href="http://localhost:8000/static/css/style.css">
    <link href="http://localhost:8000/static/css/material icons.css" rel="stylesheet">
    <link rel="stylesheet" href="http://localhost:8000/static/css/tail.select-light-feather.min.css">
    <link rel="stylesheet" href="http://localhost:8000/static/css/font_awsome.min.css">
    <script src="http://localhost:8000/static/js/tail.select-full.min.js"></script>
    <title>Ensias Timetables</title>
</head>
<body>
     <c:if test="${sessionScope.id == null }">
		   <%@ include file="../nav/student-nav.jspf" %> 
		</c:if>
      <c:if test="${sessionScope.type == 'admin'}">
		   <%@ include file="../nav/admin-nav.jspf" %>
		</c:if>
		<c:if test="${sessionScope.type == 'prof'}">
		   <%@ include file="../nav/teacher-nav.jspf" %> 
		</c:if>
		<div style="padding-bottom:1rem; ">
            <c:if test="${subject!= null}">
                     <div style="text-align: center"><h2 style="color:#b57ebe;">Edit Subject</h2></div>
                 </c:if>
                 <c:if test="${subject == null}">
                      <div style="text-align: center"><h2 style="color:#b57ebe;">Create new Subject</h2></div>
                 </c:if>
				 
            <div class="divider"></div>
        </div>
        <main>
    <div class="container" style="text-align: center;">
        <div class="z-depth-1 grey lighten-4 row" style="display: inline-block; padding: 32px 48px 0px 48px; border: 1px solid #EEE;">

    <div class="row">

				<c:if test="${subject != null}">
		            <form class="col s12" action="update" method="post">
		        </c:if>
		        
		        <c:if test="${subject == null}">
		            <form class="col s12" action="insert" method="post">
		        </c:if>
          <!-- subject_id -->
		  <c:if test="${subject != null}">
          <div class="row number hidden">
                <div class="input-field col s6">
                  <i class="material-icons prefix">account_circle</i>
				  
                    <input  id="input_text" type="number" name="id" value="<c:out value='${subject.getSubjectId()}' />" />
               
                  <label for="input_text">subject ID</label>
                </div>
            </div>
			 </c:if> 
            <!-- module -->
            <div class="row normal-text">
              <div class="input-field col s12">
                <i class="material-icons prefix">account_circle</i>
				<input id="input_text" type="text" data-length="60" name="module" 
                            value="<c:out value='${subject.getSubjectModule()}' />">
               
                <label for="input_text">Module Name</label>
              </div>
            </div>
          <!-- sous-module -->
          <div class="row normal-text">
                <div class="input-field col s12">
                  <i class="material-icons prefix">account_circle</i>
                  <c:if test="${subject!=null}">
				  <input id="submodule" type="text" data-length="60" name="submodule"
                            value="<c:out value='${subject.getSubjectSubmodule()}' />">
                    </c:if>
                    <c:if test="${subject==null}">
                  		<input id="submodule" type="text" data-length="60" name="submodule">
                  	</c:if>
                  <label for="submodule">Sub-Module</label>
                </div>
            
          </div>
		  <!-- abrev -->
            <div class="row">
                    <div class="input-field col s12">
                      <i class="material-icons prefix">account_circle</i>
                      
					  <input type="text" name="abrev" id="input_text"
                            value="<c:out value='${subject.getSubjectAbrev()}' />"
                        />
                      <label for="input_text">Abreviation</label>
                    </div>
                </div>
          <!-- color -->
          <div class="row">
                <label for="color">Color: </label>
				<input  class="input-color-picker" type="color" data-id="color" name="color" 
                            value="<c:out value='${subject.getSubjectColor()}' />"
                    />
               
          </div>

          <!-- type -->
          <div class="row normal-text hidden">
              <div class="input-field col s6">
                <i class="material-icons prefix">account_circle</i>
				
				<input id="type_" type="text" name="type" 
                            value="<c:out value='${subject.getSubjectType()}' />"
                    />
               
                <label for="type">type</label>
              </div>
          </div>
          <div class="row select">
                <label for="select-box">Type : </label>
                <select class="select-search" id="select-box" target="type_" onchange="populateInputfromSelect()">
                    <optgroup label="Ingenieurs">
                    <c:choose>
                    
                    
                    
                    	<c:when test='${subject.getSubjectType().equals("cours")}'>
	                        <option value="cours" selected>cours</option>
	                        <option value="TD">TD</option>
	                        <option value="TP">TP</option>
	                     </c:when>
	                     <c:when test='${subject.getSubjectType().equals("TD")}'>
	                        <option value="cours">cours</option>
	                        <option value="TD" selected>TD</option>
	                        <option value="TP">TP</option>
	                     </c:when>
	                     <c:when test='${subject.getSubjectType().equals("TP")}'>
	                        <option value="cours">cours</option>
	                        <option value="TD">TD</option>
	                        <option value="TP"selected>TP</option>
	                     </c:when>
	                    <c:otherwise>
	                    	<option value="cours">cours</option>
	                        <option value="TD">TD</option>
	                        <option value="TP">TP</option>
	                    </c:otherwise>
	                   </c:choose>
	                       
                    </optgroup>
                </select>
                <script>
                    tail.select(".select-search", {
                        search: true
                    });
                </script>
                
              </div>
          
         <div>
                <button type="submit" class="btn wave-effect">submit</button>
            </div>
        
        
        </form>
      </div>  
</div></div>
</main>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
          var sidenavs = document.querySelectorAll('.sidenav')
          for (var i = 0; i < sidenavs.length; i++){
              M.Sidenav.init(sidenavs[i]);
          }
          var collapsibles = document.querySelectorAll('.collapsible')
          for (var i = 0; i < collapsibles.length; i++){
              M.Collapsible.init(collapsibles[i]);
          }});
        
    </script>
    <script src="http://localhost:8000/static/js/forms.js"></script>
    <%@ include file="/WEB-INF/jsp/messages.jspf" %> 
</body>
</html>