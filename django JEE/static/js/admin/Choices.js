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
    this.getTimetableByUser = "/Timetable_App/api/timetable/"
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
  async createTimeTableCards(){
    let has_description=true;
    this.utils_clean_container();
    let apiURL = this.getTimetableByUser;
    if (apiURL){
      let response = await fetch(apiURL);
      let timetables = await response.json();
      //let t= "Lorem ipsum dolor sit amet consectetur adipisicing elit. Laboriosam optio eos fuga molestias enim laudantium."
      //let timetables = JSON.parse(`[{"id":1,"description":"${t}"},{"id":2,"description":"${t}"},{"id":6,"description":"${t}"},
      //{"id":8,"description":"${t}"},{"id":9,"description":"${t}"},{"id":10,"description":"${t}"},{"id":11,"description":"${t}"}]`)
      timetables.forEach((elt,index)=>{
        let card = new Card(index,`Timetable ${elt.id}`,elt.description,`/Timetable_App/timetables?tt_id=${elt.id}`,"");
        this.container.innerHTML += has_description?card.D_choice_template():card.noD_choice_template()
      })
    }
    }
}
//let teacher_id = document.getElementById("user_id").getAttribute("user_id")

let k = new CardsSetter();
k.createTimeTableCards();

// let div = document.getElementById("cards_container");
// console.log(div)
// for (i=0 ;i<10;i++){
//   card = new Card("title "+i,"description "+i,"#")
//   div.innerHTML += card.D_choice_template();
// }


