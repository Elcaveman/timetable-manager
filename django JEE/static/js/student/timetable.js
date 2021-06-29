function randomColor() {let x= Math.floor(Math.random()*(2**24-1)).toString(16);return '#'+'0'.repeat(6-x.length)+x}

class ReadOnlyLessonCard{
    constructor(bg_color,lesson_id,subject,subject_id,class_,class_id,teacher,teacher_id,room,room_id,lesson_link){
        this.bg_color =bg_color ;//#5d9cec
        this.lesson_id = lesson_id;
        this.lesson_link = lesson_link;

        this.subject = subject;
        this.subject_id = subject_id;

        this.class_ = class_;
        this.class_id = class_id;

        this.teacher = teacher;
        this.teacher_id = teacher_id;

        this.room = room;
        this.room_id = room_id;
    }
    get_template(drag_id=null){
      if (this.lesson_link!="" && this.lesson_link!="#") 
      return `<a href="${this.lesson_link}" target="_blank"><div class="card draggable" draggable="false" style="--background: ${this.bg_color}; --text: white" lesson_id="${this.lesson_id}">
        <div class="container gridd">
            <p s_id="${this.subject_id}">${this.subject}</p>
            <p c_id="${this.class_id}">${this.class_}</p>
            <p t_id="${this.teacher_id}">${this.teacher}</p>
            <p r_id="${this.room_id}">${this.room}</p>
        </div>
      </div></a>`
      else 
      return `<div class="card draggable" draggable="false" style="--background: ${this.bg_color}; --text: white" lesson_id="${this.lesson_id}">
      <div class="container gridd">
          <p s_id="${this.subject_id}">${this.subject}</p>
          <p c_id="${this.class_id}">${this.class_}</p>
          <p t_id="${this.teacher_id}">${this.teacher}</p>
          <p r_id="${this.room_id}">${this.room}</p>
      </div>
    </div>`
    }
}

class TableHandler{
  constructor(timetable,lesson_list,class_id){
    this.selected_class = class_id;
    this.lesson_list = this.reform_lesson_list(lesson_list);
    this.timetable = this.reform_timetable(timetable,this.lesson_list);
    console.log(this.timetable,this.lesson_list);
    this.container = document.getElementById('TT_container');
    this.days = [ 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    this.pretty_classes = this.utils_pertify_class();
  }
  reform_timetable(timetable,lesson_list){
    let obj = {};
    obj['id']=timetable.id;
    obj['description'] = timetable.description;
    obj['freetime'] = timetable.free_time;
    obj['nb_days'] = timetable.nb_days;
    obj['nb_periods'] = timetable.nb_periods;
    obj['hours_per_period'] = timetable.hours_per_period;
    if (timetable.summary == "{}"){
      let class_lis = [];
      let summ = {};
      for (let i=0 ; i<lesson_list.length ; i++){
        summ[lesson_list[i].class_id] = [[0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0]];
        
      }
      obj['summary'] = JSON.stringify(summ);
    }
    else obj['summary'] = timetable.summary;
    console.log(obj['summary'])

    return obj;
  }
  reform_lesson_list(lesson_list){
    let list = [];
    for(let i=0;i<lesson_list.length;i++){
      let obj = {};
      //lesson Data
      obj['id'] = lesson_list[i].id;
      obj['total_lessons'] = lesson_list[i].total_lessons;
      obj['lesson_occ'] = 0;
      obj['lesson_link'] = lesson_list[i].lesson_link;
      obj['color'] = lesson_list[i].color;
      //class
      obj['class'] = lesson_list[i].info;
      obj['class_id'] = lesson_list[i].class_fk;
      obj['class_freetime'] = lesson_list[i].class_freetime;
      obj['class_color'] = lesson_list[i].class_color;
      //room
      obj['room'] = lesson_list[i].room_abrev;
      obj['room_id'] = lesson_list[i].room_fk;
      //subject
      obj['subject'] = lesson_list[i].subject_abrev;
      obj['subject_id'] = lesson_list[i].subject_fk;
      //teacher
      obj['teacher_id'] = lesson_list[i].teacher_fk;
      obj['teacher'] = lesson_list[i].teacher_name;
      obj['teacher_freetime'] = lesson_list[i].teacher_freetime;

      list.push(obj);
    }
    return list;
    
  }
  initTable(){
    function makePeriods(nb_periods){
      //let h = this.timetable.hours_per_period
      let data = "";
      for (let i =1;i<=nb_periods;i++){
        data +=`<th>P${i}</th>`;
      }
      return data
      
    }
    this.container.innerHTML +=`
      <thead>
        <tr style="
        background-color: #fff;
        opacity: 0.9;">
        <th></th>
        <th></th>
        ${makePeriods(this.timetable.nb_periods)}
        </tr>
      </thead>
      <tbody id="TT_body"></tbody>
    `;
  }
  utils_pertify_class(){
    let classObject_list = [];
    function add_class(lesson,list){
      let class_id = lesson.class_id;
      let class_color = lesson.class_color;
      let class_freetime = lesson.class_freetime;
      for (let i=0;i<list.length;i++){
        if (list[i].id == class_id){
          list[i].lessons.push(lesson);
          return null;
        }
      }
      list.push({id:class_id,color:class_color,freetime:class_freetime,name:lesson.class,lessons:[lesson]});
    }

    for (let i=0;i<this.lesson_list.length;i++){
      add_class(this.lesson_list[i],classObject_list);
    }
    return classObject_list;
  }
  utils_get_pretty_class(class_id,classObject_list){
    for (let i =0 ; i<classObject_list.length;i++){
      if(classObject_list[i].id==class_id) return classObject_list[i];
    }
  }
  populateTable(){
    this.initTable();
    function getLesson(lesson_id,lesson_table){
      for (let i =0 ; i<lesson_table.length;i++){
        if (lesson_id==lesson_table[i].id) return lesson_table[i];
      }
    }

    let body = document.getElementById("TT_body");

    let classObject_list = this.pretty_classes
    
    let total_days = this.timetable.nb_days%7;
    let days = this.days.slice(0,total_days);

    let total_periods = this.timetable.nb_periods;

    let summary = JSON.parse(this.timetable.summary);
    let freetime = JSON.parse(this.timetable.freetime);
    let drag_id = 0;
    let html = "";
    for (let class_id in summary){
      console.log(this.selected_class,class_id);
      if(class_id == this.selected_class){
        let classObject = this.utils_get_pretty_class(class_id,classObject_list);
        if (classObject){
          html+=`<tr class='divide' class_id="${class_id}">`
          html += `<td class="classe" rowspan="${total_days}" style="--class_bg:${classObject.color};">${classObject.name}</td>`;
          for (let day in summary[class_id]){
            html += `<td class="day">${days[day]}</td>`;
            for (let period in summary[class_id][day]){
              if(freetime[day][period] == 1){
                html +=`<td class="cell sep" day="${day}" period="${period}"></td>`  
              }
              else if(summary[class_id][day][period] == 0) {
                html += `<td class="cell empty" day="${day}" period="${period}"></td>`
              }
              else{
                let lesson = getLesson(summary[class_id][day][period],classObject.lessons);
                
                let card = new ReadOnlyLessonCard(lesson.color,lesson.id,
                  lesson.subject,lesson.subject_id,lesson.class,lesson.class_id,
                  lesson.teacher,lesson.teacher_id,lesson.room,lesson.room_id,lesson.lesson_link);
                html += `<td class="cell" day="${day}" period="${period}">${card.get_template(++drag_id)}</td>`;
              }
            }
            html+="</tr>"
          }
        }
        else{
          alert("Corrupted timetable! Please delete it");
        }
    }}
    body.innerHTML += html;
  }
}
let class_id= 2;
async function load(){
  let href = window.location.href.split('?')
  // let class_id = parseInt(href[href.length-1],10);
  href = href[href.length-1].split('&');
  let class_id = href[0].split('=')[1]
  let tt_id = href[1].split('=')[1]
  console.log(tt_id,class_id);
  
  let timetable = await fetch(`/Timetable_App/api/timetable/?c_id=${class_id}`).then((res)=>res.json());
  let lesson_list = await fetch(`/Timetable_App/api/lesson/?tt_id=${tt_id}&c_id=${class_id}`).then((res)=>res.json());
  let handler = new TableHandler(timetable[0],lesson_list,class_id);
  handler.populateTable();
}
load();
// let timetable = {id:1,description:"text",nb_days:5,nb_periods:4,hours_per_period:2,
//   freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
//   summary:`{
//     "10":[[1,4,5,6],[7,1,8,8],[4,5,0,0],[7,7,8,0],[9,10,0,0]],
//     "2":[[11,14,15,16],[17,11,18,18],[14,15,0,0],[17,17,18,0],[19,110,0,0]]
//   }`}
// let c1 = randomColor();
// let c2 = randomColor();
// let lesson_list = [
//   {id:11,class:"2GL2",room:"S25",subject:"JAVA",teacher:"Sabo",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// total_lessons:2,lesson_occ:"",lesson_link:"#",class_id:2,subject_id:1,teacher_id:1,
// room_id:9,color:randomColor(),class_color:c2},
//   {id:14,class:"2GL2",room:"S26",subject:"C++",teacher:"onizuka",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// total_lessons:2,lesson_occ:"",lesson_link:"#",class_id:2,subject_id:2,teacher_id:2,
// room_id:10,color:randomColor(),class_color:c2},
//   {id:15,class:"2GL2",room:"S27",subject:"C",teacher:"dug dimadom2",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// total_lessons:2,lesson_occ:"",lesson_link:"#",class_id:2,subject_id:3,teacher_id:9,
// room_id:11,color:randomColor(),class_color:c2},
//   {id:16,class:"2GL2",room:"S28",subject:"C5",teacher:"danny phantom2",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// total_lessons:1,lesson_occ:"",lesson_link:"#",class_id:2,subject_id:4,teacher_id:10,
// room_id:12,color:randomColor(),class_color:c2},
//   {id:17,class:"2GL2",room:"S29",subject:"C6",teacher:"danny12",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// total_lessons:3,lesson_occ:"",lesson_link:"#",class_id:2,subject_id:5,teacher_id:11,
// room_id:13,color:randomColor(),class_color:c1},
//   {id:18,class:"2GL2",room:"S30",subject:"C7",teacher:"danny22",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// total_lessons:3,lesson_occ:"",lesson_link:"#",class_id:2,subject_id:6,teacher_id:12,
// room_id:14,color:randomColor(),class_color:c2},
//   {id:19,class:"2GL2",room:"S31",subject:"C8",teacher:"danny32",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// total_lessons:1,lesson_occ:"",lesson_link:"#",class_id:2,subject_id:7,teacher_id:13,
// room_id:15,color:randomColor(),class_color:c2},
//   {id:110,class:"2GL2",room:"S32",subject:"C9",teacher:"danny42",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// total_lessons:1,lesson_occ:"",lesson_link:"#",class_id:2,subject_id:8,teacher_id:14,
// room_id:16,color:randomColor(),class_color:c2}
// ]

// let handler = new TableHandler(timetable,lesson_list,class_id);
// handler.populateTable();
