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
    <style>
    	.row a {
    		padding-left:0.5rem;
    		color:#E57373 !important;
    	}
    </style>
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
				<c:if test="${lesson!= null}">
                     <div style="text-align: center"><h2>Edit Lesson</h2></div>
                 </c:if>
                 <c:if test="${lesson == null}">
                      <div style="text-align: center"><h2>Create new Lesson</h2></div>
                 </c:if>
           
            <div class="divider"></div>
    </div>
        <main>
    <div class="container" style="text-align: center;">
        <div class="z-depth-1 grey lighten-4 row" style="display: inline-block; padding: 32px 48px 0px 48px; border: 1px solid #EEE;">

    <div class="row">

        
			<c:if test="${lesson != null}">
		            <form class="col s12" action="update" method="post">
		        </c:if>
		        
		        <c:if test="${lesson == null}">
		            <form class="col s12" action="insert" method="post">
		        </c:if>
				
            <!-- lesson_id -->
			<c:if test="${lesson != null}">
                   
            <div class="row number hidden">
                    <div class="input-field col s6">
                      <i class="material-icons prefix">account_circle</i>
                       <input type="hidden" id="lesson_id" name="id" value="<c:out value='${lesson.getLessonId()}' />" />
                      <label for="lesson_id">lesson_id</label>
                    </div>
                </div>
				
				</c:if> 

            <!--teacher_id -->
            <div class="row number hidden">
                    <div class="input-field col s6">
                      <i class="material-icons prefix">account_circle</i>
                      <input id="teacher_fk" type="number" value="<c:out value='${lesson.getLessonTeacherFk()}' />">
                      <label for="teacher_id">teacher_id</label>
                    </div>
                </div>
            <div class="row select">
                <label for="select-box">Teacher : </label>
				
				<select class="select-search" id="teacher_fk" name="teacher_fk" target = "teacher_fk" onchange="populateInputfromSelect()">
                        <c:forEach var="teacher" items="${listTeacher}">
                            <c:choose>
                            	<c:when test="${teacher.getTeacherId() == lesson.getLessonTeacherFk()}">
                            		<option value="${teacher.getTeacherId()}" selected>${teacher.getTeacherName()}</option>
                            	</c:when>
                            	<c:otherwise>
                            		<option value="${teacher.getTeacherId()}">${teacher.getTeacherName()}</option>
                            	</c:otherwise>
                        	</c:choose>
                        </c:forEach>
                 </select>
                 <a href="/Timetable_App/teacher/new"><i class="material-icons">create</i></a>
                <script>
                    tail.select(".select-search", {
                        search: true
                    });
                </script>
                
              </div>
            <!-- class_id -->
            <div class="row number hidden">
                    <div class="input-field col s6">
                      <i class="material-icons prefix">account_circle</i>
                      <input id="class_id" type="number" value="<c:out value='${lesson.getLessonClassFk()}' />">
                      <label for="class_id">class_id</label>
                    </div>
                </div>
            <div class="row select" >
                    <label for="select-box">Class : </label>
                    <select id="class_fk" name="class_fk" class="select-search" target ="class_id" onchange="populateInputfromSelect()">
                        <!--<c:forEach var="cl" items="${listClass }">
                            <option value="${cl.getClassId()}">${cl.getClassGroup()}</option>
                        </c:forEach>-->
                       		
                        	<c:forEach var="fc" items="${listFaculty}">
								<optgroup label="${fc.getFacultyYear()}A ${fc.getFacultyName()}">
                        			<c:forEach var="cl" items="${listClass }">
                        				<c:if test="${cl.getClassFacultyFk() == fc.getFacultyId()}">
                        					<c:choose>
												<c:when test="${cl.getClassId() == lesson.getLessonClassFk()}">
                            						<option value="${cl.getClassId()}" selected>${fc.getFacultyYear()} ${fc.getFacultyAbrev()} ${cl.getClassGroup()}</option>
                            					</c:when>
                            					<c:otherwise>
                            						<option value="${cl.getClassId()}">${fc.getFacultyYear()} ${fc.getFacultyAbrev()} ${cl.getClassGroup()}</option>
                            					</c:otherwise>
                            				</c:choose>
                            			</c:if>
                        			</c:forEach>
                        			</optgroup>
                        	</c:forEach>
                        	
                        
                    </select>
                    <a href="/Timetable_App/class/new"><i class="material-icons">create</i></a>
					<!-- <select class="select-search" id="select-class" target ="class_id" onchange="populateInputfromSelect()">
                        <optgroup label="Year 1">
                            <optgroup label="TC">
                                <option value="4">Group 1</option>
                                <option value="5">Group 2</option>
                                <option value="6">Group 3</option>
                            </optgroup>
                            <optgroup label="IEL">
                                <option value="14">Group 1</option>
                                <option value="15">Group 2</option>
                            </optgroup>
                            <optgroup label="ISEM">
                                <option value="24">Group 1</option>
                            </optgroup>
                        </optgroup>
                        <optgroup label="Year 2 ">
                            <optgroup label="GL">
                                <option value="34">Group 1</option>
                                <option value="34">Group 2</option>
                                <option value="35">Group 3</option>
                            </optgroup>
                            <optgroup label="IWIM">
                                <option value="44">Group 1</option>
                            </optgroup>
                        </optgroup>
                        <optgroup label="Year 3 ">
                                <optgroup label="GL">
                                    <option value="54">Group 1</option>
                                </optgroup>
                                <optgroup label="IWIM">
                                    <option value="64">Group 1</option>
                                </optgroup>
                        </optgroup>
                    </select> -->
                    <script>
                        tail.select(".select-search", {
                            search: true
                        });
                    </script>
                    
            </div>
            
            <!-- room_id -->
            <div class="row number hidden">
                    <div class="input-field col s6">
                      <i class="material-icons prefix">account_circle</i>
                      <input id="room_id" type="number" value="<c:out value='${lesson.getLessonRoomFk()}' />">
                      <label for="room_id">room_id</label>
                    </div>
                </div>

            <div class="row select">
                    <label for="select-box">Room : </label>
					<select class="select-search" id="room_fk" name="room_fk"  target = "room_ik" onchange="populateInputfromSelect()">
                        
                        <c:forEach var="room" items="${listRoom}">
                        <c:choose>
							<c:when test="${room.getRoomId() == lesson.getLessonRoomFk()}">
                            	<option value="${room.getRoomId()}" selected>${room.getRoomAbrev()}</option>
                            </c:when>
                            <c:otherwise>
                            	<option value="${room.getRoomId()}">${room.getRoomAbrev()}</option>
                           
                           </c:otherwise>
                          </c:choose>
                        </c:forEach>
                    <!--</select>
                    <select class="select-search" id="select-class" target = "room_id" onchange="populateInputfromSelect()">
                        <option value="1">S10</option>
                        <option value="2">S11</option>
                        <option value="3">S12</option>
                        <option value="4">S13</option>
                        <option value="5">S14</option>
                        <option value="6">S15</option>
                        <option value="7">S16</option>
                    </select>-->
                    </select>
                    <a href="/Timetable_App/room/new"><i class="material-icons">create</i></a>
                    <script>
                        tail.select(".select-search", {
                            search: true
                        });
                    </script>       
            </div>
            <!-- subject_id -->
            <div class="row number hidden">
                    <div class="input-field col s6">
                      <i class="material-icons prefix">account_circle</i>
                      <input id="subject_id" type="number" value="<c:out value='${lesson.getLessonSubjectFk()}' />">
                      <label for="subject_id">subject_id</label>
                    </div>
                </div>

            <div class="row select">
                    <label for="select-box">Subject : </label>
					
					<select class="select-search" id="subject_fk" name="subject_fk" target = "subject_fk" onchange="populateInputfromSelect()">
                        <c:forEach var="subject" items="${listSubject }">
							<c:choose>
							<c:when test="${subject.getSubjectId() == lesson.getLessonSubjectFk()}">
                            	<option value="${subject.getSubjectId()}" selected>${subject.getSubjectAbrev()}</option>
                            </c:when>
                            <c:otherwise>
                            	<option value="${subject.getSubjectId()}">${subject.getSubjectAbrev()}</option>
                            </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <a href="/Timetable_App/subject/new"><i class="material-icons">create</i></a>
					
                    <!--<select class="select-search" id="select-subject" target = "subject_id" onchange="populateInputfromSelect()">
                        <optgroup label="Module 1">
                            <option value="1">SM11</option>
                            <option value="2">SM12</option>
                            <option value="3">SM13</option>
                            <option value="4">SM14</option>
                        </optgroup>
                        
                        <optgroup label="Module 2">
                            <option value="11">SM21</option>
                            <option value="12">SM22</option>
                            <option value="13">SM23</option>
                        </optgroup>
                    </select>-->
                    <script>
                        tail.select(".select-search", {
                            search: true
                        });
                    </script>       
            </div>
            <!-- timetable_id -->
            <div class="row number hidden">
                    <div class="input-field col s6">
                      <i class="material-icons prefix">account_circle</i>
                      <input id="timetable_id" type="number" name="timetable_id" value="<c:out value='${lesson.getLessonTimetableFk()}' />">
                      <label for="timetable_id">timetable_id</label>
                    </div>
                </div>

            <div class="row select">
                    <label for="select-box">Timetable : </label>
					
					<select id="timetable_fk"  class="select-description" name="timetable_fk" target = "timetable_fk" onchange="populateInputfromSelect()">
                        <c:forEach var="timetable" items="${listTimetable }">
                        	<c:choose>
                        	<c:when test="${timetable.getTimetableId() == lesson.getLessonTimetableFk()}">
                            <option value="${timetable.getTimetableId()}" data-description="${timetable.getTimetableDescription()}" selected>${timetable.getTimetableId()}</option>
                        	</c:when>
                        	<c:otherwise>
                        		<option value="${timetable.getTimetableId()}" data-description="${timetable.getTimetableDescription()}">${timetable.getTimetableId()}</option>
                        	</c:otherwise>
                        	</c:choose>
                        </c:forEach>
                    </select>
					<a href="/Timetable_App/timetable/new"><i class="material-icons">create</i></a>
                    <!--<select class="select-description" id="select-timetable" target = "timetable_id" onchange="populateInputfromSelect()">
                            <option value="1" data-description="Lorem ipsum dolor sit amet consectetur, adipisicing elit. Nihil, vel.">
                                Timetable 1</option>
                            <option value="2" data-description="Lorem ipsum dolor sit amet consectetur, adipisicing elit. Nihil, vel.">Timetable 2</option>
                            <option value="3" data-description="Lorem ipsum dolor sit amet consectetur, adipisicing elit. Nihil, vel.">Timetable 3</option>
                    </select>-->
					
                    <script>
                        tail.select(".select-description", {
                            search: true,
                            descriptions: true
                        });
                    </script>       
            </div> 
            <!-- total_lessons -->
            <div class="row number">
                    <div class="input-field col s12">
                      <i class="material-icons prefix">hourglass_empty</i>
                      <input id="total_lessons" type="number" name="total_lessons" value="<c:out value='${lesson.getTotalLessons()}' />">
                      <label for="total_lessons">Total lessons</label>
                    </div>
                </div>
            <!-- color -->
            <div class="row">
                <label for="color">Color: </label>
				<input class="input-color-picker" type="color" data-id="color" name="color" 
                            value="<c:out value='${lesson.getLessonColor()}' />" />
           
              </div>
              
            <!-- lesson_link -->
            <div class="row normal-text">
                <div class="input-field col s12">
                <div class="input-field col s12">
                  <i class="material-icons prefix">insert_link</i>
				  <input type="url" id="lesson_link" name="lesson_link" data-length="200"
                            value="<c:out value='${lesson.getLessonLink()}' />" />
                  
                  <label for="lesson_link">Meeting Link</label>
                </div>
            </div>
         <div>
            <button type="submit" class="btn wave-effect">submit</button>
        </div>
        
        
        </form>
      </div>  
</div></div></div>
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
          /*$(document).ready(function() {
            $('input, textarea').characterCounter();
          });*/
    </script>
    <script src="http://localhost:8000/static/js/forms.js"></script>
    <%@ include file="/WEB-INF/jsp/messages.jspf" %> 
</body>
</html>