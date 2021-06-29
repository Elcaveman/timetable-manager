//override the event listner class for our own convenience
(function() {
  let target = EventTarget.prototype;
  let functionName = 'addEventListener';
  let func = target[functionName];

  let symbolHidden = Symbol('hidden');

  function hidden(instance) {
      if (instance[symbolHidden] === undefined) {
          let area = {};
          instance[symbolHidden] = area;
          return area;
      }

      return instance[symbolHidden];
  }

  function listenersFrom(instance) {
      let area = hidden(instance);
      if (!area.listeners) { area.listeners = []; }
      return area.listeners;
  }

  target[functionName] = function(type, listener) {
      let listeners = listenersFrom(this);

      listeners.push({ type, listener });

      func.apply(this, [type, listener]);
  };
  //!
  target['removeEventListeners'] = function(targetType, excludedElement = null) {
      let self = this;
      let listeners = listenersFrom(this);
      let removed = [];
      if (excludedElement !== self || excludedElement === null) {

          listeners.forEach(item => {
              let type = item.type;
              let listener = item.listener;
              if (type == targetType) {
                  self.removeEventListener(type, listener);
              }
          });
      }
  };
})(); //instant call

class Card{
  constructor(id,title,description,link,data){
    this.id = id
    this.title = title;
    this.description = description;
    this.link = link;
    this.data = data;
  }
  D_choice_template(){return `<div class="col s12 m4 l4 xl3">
    <div class="card animate fadeUp" style="max-width: 250px;" id="card${this.id}" data="${this.data}">
    <div class="card-image waves-effect waves-block waves-light" style="max-height: 400px;">
      <img class="activator" src="https://images.unsplash.com/photo-1591101462489-113669e8178c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80">
    </div>
    <div class="card-content">
      <span class="card-title activator grey-text text-darken-4">
      ${this.title}
      ${this.link?`<a href="${this.link}">`:""}
        <p style="color: #f2a4a8;font-size:1rem;">Go here!</p>
      ${this.link?"</a>":""}

    </div>
    <div class="card-reveal">
  <span class="card-title grey-text text-darken-4">${this.title}<i class="material-icons right">close</i></span>
      <p>${this.description}</p>
    </div>
    </div>
    </div>`}
  noD_choice_template(){return `<div class="col s12 m4 l4 xl3">
    <div class="card animate fadeUp" style="max-width: 250px;" id="card${this.id}" data="${this.data}">
    <div class="card-image waves-effect waves-block waves-light" style="max-height: 400px;">
      <img src="https://images.unsplash.com/photo-1591101462489-113669e8178c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80">
    </div>
    <div class="card-content">
      <span class="card-title grey-text text-darken-4">
      ${this.title}
      ${this.link?`<a href="${this.link}">`:""}
        <p style="color: #f2a4a8;font-size:1rem;">Go here!</p>
      ${this.link?"</a>":""}
    </div>
    </div>
    </div>`}
}

class CardsSetter{
  constructor(){
    this.container = document.getElementById("cards_container");
    this.getYears = "/Timetable_App/api/years/"
    this.getFacultyByYear = "/Timetable_App/api/faculty/?year="
    this.getClassByFaculty = "/Timetable_App/api/class/?faculty="
    this.getTimetableByClass = "/Timetable_App/api/timetable/?c_id="
  }
  utils_get_CSSclass(html_element,class_){
    if(!class_){
      class_ = "card"
    }
    if (html_element==null){return null}
    if (html_element.classList.contains(class_)){return html_element;}
    else{
      return this.utils_get_CSSclass(html_element.parentElement,class_)
    }
  }
  utils_clean_container(){
    this.container.innerHTML = "";
  }
  async initEventListener(instance,func,...args){
    if (func && instance){
      instance.container.removeEventListeners('click');
      instance.container.addEventListener('click',(elt)=>{
        if (!(elt.target.classList.contains("activator"))){
          if (!(elt.innerText == "close")){
            if (!instance.utils_get_CSSclass(elt.target,"card-reveal")){
              let card = instance.utils_get_CSSclass(elt.target);
              if (card!=null){
                let data = card.getAttribute("data").split(",");
                func(...data,...args,instance);
              }
             
            }
          }
        }})
    }
  }
  async createYearCards(){
    let has_description = false
    let apiURL = this.getYears
    if (apiURL){
      let response = await fetch(apiURL);
      let years = await response.json();//[{"year":1},{"year":2},{"year":3}]
      //let years = JSON.parse('[{"year":1},{"year":2},{"year":3}]');
      years.forEach((elt,index)=>{
        let card = new Card(index,elt.year+" Year","","",elt.year);
        this.container.innerHTML += has_description?card.D_choice_template():card.noD_choice_template()
      })
      this.initEventListener(this,this.createFacultyCards,false);
    }
  }
  async createFacultyCards(year ,has_description,instance){
    instance.utils_clean_container();
    let apiURL = instance.getFacultyByYear + year;
    if (apiURL){
      let response = await fetch(apiURL);
      let faculties = await response.json();

      //[{"id":4,"abrv":"GL"},{"id":2,"abrv":"IWIM"},{"id":6,"abrv":"ISEM"}]
      //let faculties = JSON.parse('[{"id":4,"abrv":"GL"},{"id":2,"abrv":"IWIM"},{"id":6,"abrv":"ISEM"},{"id":8,"abrv":"Iel"},{"id":13,"abrv":"AI"}]')
      console.log(faculties)
      if(faculties.length!=0){
      faculties.forEach((elt,index)=>{
        let card = new Card(index,year+" " + elt.abrev,"","",elt.id+","+elt.abrev);
        instance.container.innerHTML += has_description?card.D_choice_template():card.noD_choice_template()
      })
      instance.initEventListener(instance,instance.createClassCards,year,false);
    }
      else{
        instance.container.innerHTML = "<p>No faculties Available</p>"
      }
    }
  }
  async createClassCards(faculty_id,faculty_abrv,year,has_description,instance){
    instance.utils_clean_container();
    let apiURL = instance.getClassByFaculty+faculty_id;
    if (apiURL){
      let response = await fetch(apiURL);
      let classes = await response.json();//[{"id":1,"group":"1"},{"id":2,"abrv":"2"},{"id":6,"abrv":"3"},{"id":8,"abrv":"4"}]
      //let classes = JSON.parse('[{"id":1,"group":"1"},{"id":2,"group":"2"},{"id":6,"group":"3"},{"id":8,"group":"4"}]')
      console.log(classes)
      if(classes.length!=0){
      classes.forEach((elt,index)=>{
        let card = new Card(index,year+" "+faculty_abrv+" "+ elt.group,"","",elt.id);
        instance.container.innerHTML += has_description?card.D_choice_template():card.noD_choice_template()
      })
      instance.initEventListener(instance,instance.createTimeTableCards,true);
      }
      else{
        instance.container.innerHTML = "<p>No Classes Available</p>"
      }
    }
  }
  async createTimeTableCards(class_id,has_description,instance){
    instance.utils_clean_container();
    let apiURL = instance.getTimetableByClass+class_id;
    if (apiURL){
      let response = await fetch(apiURL);
      let timetables = await response.json();
      //let t= "Lorem ipsum dolor sit amet consectetur adipisicing elit. Laboriosam optio eos fuga molestias enim laudantium."
      //let timetables = JSON.parse(`[{"id":1,"description":"${t}"},{"id":2,"description":"${t}"},{"id":6,"description":"${t}"},
      //{"id":8,"description":"${t}"},{"id":9,"description":"${t}"},{"id":10,"description":"${t}"},{"id":11,"description":"${t}"}]`)
      console.log(timetables)
      if(timetables.length!=0){
      timetables.forEach((elt,index)=>{
        console.log(`/Timetable_App/timetables?tt_id=${elt.id}`);
        let card = new Card(index,`Timetable ${elt.id}`,elt.description,`/Timetable_App/classTimetable?c_id=${class_id}&tt_id=${elt.id}`,"");
        instance.container.innerHTML += has_description?card.D_choice_template():card.noD_choice_template()
      })
      instance.initEventListener(null,null);
      }
      else{
        instance.container.innerHTML = "<p>No Timetables Available</p>"
      }
    }
    }
}


let k = new CardsSetter();
k.createYearCards();

// let div = document.getElementById("cards_container");
// console.log(div)
// for (i=0 ;i<10;i++){
//   card = new Card("title "+i,"description "+i,"#")
//   div.innerHTML += card.D_choice_template();
// }