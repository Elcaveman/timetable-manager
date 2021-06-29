let nb_days = 5;
let nb_periods = 4;
function randomColor() {let x= Math.floor(Math.random()*(2**24-1)).toString(16);return '#'+'0'.repeat(6-x.length)+x}
function populateColor(){
  let col = document.querySelector('input[type="color"]');
  if (col){
  if (col.value=='#000000' ||col.value=='#ffffff'){
    col.value = randomColor();
  }

}
}
function populateInputfromSelect(){
  let select_items = document.querySelectorAll('select');
  for (let i=0; i<select_items.length;i++){

    let target = select_items[i].getAttribute('target');
    if(target){
    document.getElementById(target).value = select_items[i].value;
    console.log(document.getElementById(target).value);
  }
  }
}
function populateFT(){
  if(document.getElementById('FT_input').value){
  let value = JSON.parse(document.getElementById('FT_input').value);
  let tds = document.querySelectorAll('[day][period]');
  for (let i =0; i< tds.length ; i++){
    let day = tds[i].getAttribute('day');
    let period = tds[i].getAttribute('period');
    let v = value[day][period];
    if (v==0) tds[i].querySelector('input').checked = true;
    if (v==1) tds[i].querySelector('input').checked = false;
  }}
}
function init_table(nb_days,nb_periods){
  let v = [];
  for(let i=0;i<nb_days;i++){
    let s =[]
    for(let j=0;j<nb_periods;j++){
      s.push(0);
    }
    v.push(s)
  }
  return v;
}
function updateFT(){
  let value = init_table(nb_days,nb_periods);
  let tds = document.querySelectorAll('[day][period]');
  for (let i =0; i< tds.length ; i++){
    let day = tds[i].getAttribute('day');
    let period = tds[i].getAttribute('period');
    let checked = tds[i].querySelector('input').checked
    if (checked) value[day][period] = 0;
    else value[day][period] = 1;
  }
  document.getElementById('FT_input').value = JSON.stringify(value);
}
function makePeriods(nb_periods){
            //let h = this.timetable.hours_per_period
            let data = "";
            for (let i =1;i<=nb_periods;i++){
              data +=`<th>P${i}</th>`;
            }
            return data
  }
function makeCells(nb_days,nb_periods){
            let days_list = [ 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
            let data = '';
            for (let day=0;day<nb_days;day++){
              data +="<tr>"
              data += `<td class="day">${days_list[day]}</td>`
              for (let period=0;period<nb_periods;period++){
                data += `<td class="cell sep" day="${day}" period="${period}">
                    <label>
                        <input type="checkbox" class="filled-in" checked="checked" onclick="updateFT()" />
                        <span></span>
                      </label>
                </td>`
              }
              data +="</tr>"
            }
            return data;
  }

let freetime =  document.getElementById('freetime');

if (freetime){
freetime.innerHTML +=`
    <thead>
      <tr style="
      background-color: #fff;
      opacity: 0.9;">
      <th></th>
      ${makePeriods(nb_periods)}
      </tr>
    </thead>
    <tbody id="TT_body">
      ${makeCells(nb_days,nb_periods)}
    </tbody>
  `;
  populateFT();
}
populateInputfromSelect();
populateColor()