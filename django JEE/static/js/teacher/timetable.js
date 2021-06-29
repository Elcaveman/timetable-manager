function randomColor() {let x= Math.floor(Math.random()*(2**24-1)).toString(16);return '#'+'0'.repeat(6-x.length)+x}

class ReadOnlyLessonCard{
    constructor(bg_color,lesson_id,subject,subject_id,class_,class_id,teacher,teacher_id,room,room_id){
        this.bg_color =bg_color ;//#5d9cec
        this.lesson_id = lesson_id;

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
  constructor(timetable,lesson_list,teacher_id){
    this.selected_teacher = teacher_id;
    this.lesson_list =  this.reform_lesson_list(lesson_list);
    this.timetable = this.reform_timetable(timetable,this.lesson_list);
    this.container = document.getElementById('TT_container');
    console.log(this.timetable);
    this.days = [ 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
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
    function makeCells(nb_days,nb_periods,days_list){
      let data = '';
      for (let day=0;day<nb_days;day++){
        data +="<tr>"
        data += `<td class="day">${days_list[day]}</td>`
        for (let period=0;period<nb_periods;period++){
          data += `<td class="cell sep" day="${day}" period="${period}"></td>`
        }
        data +="</tr>"
      }
      return data;
    }
    this.container.innerHTML +=`
      <thead>
        <tr style="
        background-color: #fff;
        opacity: 0.9;">
        <th></th>
        ${makePeriods(this.timetable.nb_periods)}
        </tr>
      </thead>
      <tbody id="TT_body">
        ${makeCells(this.timetable.nb_days,this.timetable.nb_periods,this.days)}
      </tbody>
    `;
  }

  populateTable(){
    function get_pos(lesson,summary){
      let k = [];
      for (let i=0;i<summary.length;i++){
        for (let j=0;j<summary[i].length;j++){
          if (summary[i][j] == lesson.id){
            k.push({day:i,period:j});
          }
        }
    }
    return k;
      
    }
    this.initTable();
    let body = document.getElementById("TT_body");
    let total_days = this.timetable.nb_days%7;
    let days = this.days.slice(0,total_days);
    let total_periods = this.timetable.nb_periods;
    let summary = JSON.parse(this.timetable.summary);
    let freetime = JSON.parse(this.timetable.freetime);

    for (let i=0;i<this.lesson_list.length;i++){
      let lesson = this.lesson_list[i];
      if(lesson.teacher_id == this.selected_teacher){
        //search for it's position in the timetable summary
        let pos_list = get_pos(lesson,summary[lesson.class_id]);
        if (pos_list){
          for(let pp=0;pp<pos_list.length;pp++){
            let card = new ReadOnlyLessonCard(lesson.color,lesson.id,
            lesson.subject,lesson.subject_id,lesson.class,lesson.class_id,
            lesson.teacher,lesson.teacher_id,lesson.room,lesson.room_id);
            let td =this.container.querySelector(`[day='${pos_list[pp].day}'][period='${pos_list[pp].period}']`)
            if(td.classList.contains('sep')){
              td.classList.remove('sep');
            }
            else{
              alert('Timetable data corrupted! contact admin');
            }
            td.innerHTML =  card.get_template();
          }
        }
      }
    }
  }


}
async function load(){
  let href = window.location.href.split('/')
  href = href[href.length-1].split('?tt_id=')[1]
  let tt_id = parseInt(href,10);

  let timetable = await fetch(`/Timetable_App/api/timetable/?tt_id=${tt_id}`).then((res)=>res.json());
  let lesson_list = await fetch(`/Timetable_App/api/lesson/?tt_id=${tt_id}`).then((res)=>res.json());

  let handler = new TableHandler(timetable[0],lesson_list,teacher_id);
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
//   {id:1,class:"2GL1",class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,1,1]]",room:"S29",subject:"JAVA",teacher:"Sabo",
//   teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// total_lessons:4,lesson_occ:"",lesson_link:"#",class_id:10,subject_id:1,teacher_id:1,
// room_id:13,color:randomColor(),class_color:c1},
//   {id:4,class:"2GL1",class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,1,1]]",room:"S29",subject:"JAVA",teacher:"Sabo",
//   teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// total_lessons:4,lesson_occ:"",lesson_link:"#",class_id:10,subject_id:1,teacher_id:1,
// room_id:13,color:randomColor(),class_color:c1},
//   {id:5,class:"2GL1",class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,1,1]]",room:"S29",subject:"JAVA",teacher:"Sabo",
//   teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// total_lessons:4,lesson_occ:"",lesson_link:"#",class_id:10,subject_id:1,teacher_id:1,
// room_id:13,color:randomColor(),class_color:c1},
//   {id:6,class:"2GL1",room:"L28",subject:"C5",teacher:"danny",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,1,1]]",
// total_lessons:2,lesson_occ:"",lesson_link:"#",class_id:10,subject_id:4,teacher_id:4,
// room_id:4,color:randomColor(),class_color:c1},
//   {id:7,class:"2GL1",room:"L29",subject:"C6",teacher:"danny1",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,1,1]]",
// total_lessons:3,lesson_occ:"",lesson_link:"#",class_id:10,subject_id:5,teacher_id:5,
// room_id:5,color:randomColor(),class_color:c1},
//   {id:8,class:"2GL1",room:"L30",subject:"C7",teacher:"danny2",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,1,1]]",
// total_lessons:3,lesson_occ:"",lesson_link:"#",class_id:10,subject_id:6,teacher_id:6,
// room_id:6,color:randomColor(),class_color:c1},
//   {id:9,class:"2GL1",room:"L31",subject:"C8",teacher:"danny3",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,1,1]]",
// total_lessons:3,lesson_occ:"",lesson_link:"#",class_id:10,subject_id:7,teacher_id:7,
// room_id:7,color:randomColor(),class_color:c1},
//   {id:10,class:"2GL1",room:"L32",subject:"C9",teacher:"danny4",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,1,1]]",
// total_lessons:1,lesson_occ:"",lesson_link:"#",class_id:10,subject_id:8,teacher_id:8,
// room_id:8,color:randomColor(),class_color:c1},

//   {id:17,class:"2GL2",room:"S25",subject:"JAVA",teacher:"Sabo",
// teacher_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// class_freetime:"[[0,0,0,0],[0,0,0,0],[0,0,1,1],[0,0,0,0],[0,0,0,0]]",
// total_lessons:2,lesson_occ:"",lesson_link:"#",class_id:2,subject_id:1,teacher_id:1,
// room_id:9,color:randomColor(),class_color:c2},
//   {id:11,class:"2GL2",room:"S26",subject:"C++",teacher:"onizuka",
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
//   {id:14,class:"2GL2",room:"S29",subject:"C6",teacher:"danny12",
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
// let handler = new TableHandler(timetable,lesson_list,teacher_id);
// handler.populateTable();
