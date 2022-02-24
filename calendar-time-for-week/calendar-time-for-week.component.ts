import {Component, Input, Output, OnInit, OnChanges, SimpleChanges, EventEmitter } from '@angular/core';
import * as moment from 'moment'


@Component({
  selector: 'app-calendar-time-for-week',
  templateUrl: './calendar-time-for-week.component.html',
  styleUrls: ['./calendar-time-for-week.component.css'],
})
export class CalendarTimeForWeekComponent implements OnInit {

  //Variables de entrada y salida
  @Output() pickHourDay = new EventEmitter<moment.Moment>();
  @Output() pickDate = new EventEmitter<moment.Moment>();

  //Variables uso exclusivo DOC
  week: any = [
    "Lunes",
    "Martes",
    "Miercoles",
    "Jueves",
    "Viernes",
    "Sabado",
    "Domingo"
  ];
  SymbolPrev = "<";
  SymbolNext = ">";
  dateTitle: string;

  //Variables configuracion de calendario
  hoursSelect: any[] = [7,8,9,10,11,12,13,14,15,16,17,18]
  viewedDays = 4

  //Variables gstion de mes
  doubleMonth: number = 0;
  monthAux: any[];
  monthSelect: any[];
  auxDateSelect: any;
  dateSelect: any;

  //Variables gestion de dias a mostrar
  daysOfWeek: any[];
  daysSelect: any[];

  //Fecha y hora seleccionada
  hourPicked: moment.Moment

  //modificador de DOM
  sectionSelected: any;

  constructor() { }

  ngOnInit(): void {
    const currentDate = moment()
    this.getDays(moment(currentDate.format("YYYY/M/D")))
  }

  //modificador de Ids
  tit: string = "tit"
  des: string = "des"
  id(differentiator: String, day: number, hour: number): string{
    return differentiator + day.toString() + "/" +hour.toString()
  }

  //Metodos de gestion de mes
  changeMonth(flag: any, limitDate: number) {
    if (flag < 0) {
      const prevDate = this.dateSelect.clone().subtract(1, "month");
        if(limitDate == 1){
          this.getDaysFromDate(prevDate.format("MM"), prevDate.format("YYYY"), false);
          this.getRangeDays(this.monthSelect.length - this.viewedDays + 1, 0)
          this.dateTitle = this.dateSelect.format('MMMM, yyyy')
          this.doubleMonth = 0
        }
        else{
          this.getDaysFromDate(prevDate.format("MM"), prevDate.format("YYYY"), true);
          this.getRangeDays(limitDate - 1, -1)
          this.dateTitle = this.auxDateSelect.format('MMMM, yyyy') + " - " + this.dateSelect.format('MMMM, yyyy')
          this.doubleMonth = -1
        }
    } else {
      const nextDate = this.dateSelect.clone().add(1, "month");
      if(limitDate == this.monthSelect.length){
        this.getDaysFromDate(nextDate.format("MM"), nextDate.format("YYYY"), false);
        this.getRangeDays(1, 0)
        this.dateTitle = this.dateSelect.format('MMMM, yyyy')
        this.doubleMonth = 0
      }else{
        this.getDaysFromDate(nextDate.format("MM"), nextDate.format("YYYY"), true);
        this.getRangeDays(limitDate + 1, +1)
        this.dateTitle = this.dateSelect.format('MMMM, yyyy') + " - " + this.auxDateSelect.format('MMMM, yyyy')
        this.doubleMonth = +1
      }
    }
  }

  changeMonthFromDoubleMonth(flag: any, limitDate: number, sameButtom: boolean){
    if(sameButtom){
      this.monthSelect = this.monthAux
      this.dateSelect = this.auxDateSelect
    }
    if(flag<0){
      this.getRangeDays(limitDate - this.viewedDays, 0)
      this.dateTitle = this.dateSelect.format('MMMM, yyyy')
    }else{
      this.getRangeDays(limitDate + 1, 0)
      this.dateTitle = this.dateSelect.format('MMMM, yyyy')
    }
    this.doubleMonth = 0
  }

  //Metodos de gestion de dia numero
  getDays(date: moment.Moment){
    this.getDaysFromDate(parseInt(date.format("M")), parseInt(date.format("YYYY")), false)
    if((this.monthSelect.length - parseInt(date.format("D"))) < this.viewedDays - 1){
      const nextDate = this.dateSelect.clone().add(1, "month");
      this.getDaysFromDate(nextDate.format("MM"), nextDate.format("YYYY"), true);
      this.getRangeDays( parseInt(date.format("D")) , 1)
      this.dateTitle = this.dateSelect.format('MMMM, yyyy') + " - " + this.auxDateSelect.format('MMMM, yyyy')
      this.doubleMonth = 1
    }else{
      this.getRangeDays(parseInt(date.format("D")), 0)
      this.dateTitle = this.dateSelect.format('MMMM, yyyy')
      this.doubleMonth = 0
    } 
    this.getDaysFromWeek()
  }
  getRangeDays(date: number, doubleMonth: number){
    this.daysSelect = []
    if(doubleMonth == 0){
      for (let index = 0; index < this.viewedDays; index++) {   
        this.daysSelect.push(this.monthSelect[date + index - 1])
      }
    }else if(doubleMonth < 0){
      const daysRest = this.viewedDays-date
      for (let index = 0; index < daysRest; index++) {   
        this.daysSelect.push(this.monthAux[this.monthAux.length - daysRest + index])
      }
      for (let index = 0; index < this.viewedDays - daysRest; index++) {   
        this.daysSelect.push(this.monthSelect[index])
      }
    }else{
      const daysRest = this.monthSelect.length-date+1
      for (let index = 0; index < daysRest; index++) {   
        this.daysSelect.push(this.monthSelect[date - 1 + index])
      }
      for (let index = 0; index < this.viewedDays - daysRest; index++) {   
        this.daysSelect.push(this.monthAux[index])
      }
    }
  }
  getDaysFromDate(month: any, year: any, monthAux : boolean) {

    const startDate = moment(`${year}/${month}/01`)
    const endDate = startDate.clone().endOf('month')
    if(monthAux)
      this.auxDateSelect = startDate;
    else
      this.dateSelect = startDate;

    const diffDays = endDate.diff(startDate, 'days', true)
    const numberDays = Math.round(diffDays);

    const arrayDays = Object.keys([...Array(numberDays)]).map((a: any) => {
      a = parseInt(a) + 1;
      const dayObject = moment(`${year}-${month}-${a}`);
      return {
        name: dayObject.format("dddd"),
        value: a,
        indexWeek: dayObject.isoWeekday()
      };
    });
    if(monthAux)
      this.monthAux = arrayDays;
    else
      this.monthSelect = arrayDays;
  }

  //Medotos de gestion de dia texto
  getDaysFromWeek(){
    if(this.viewedDays >= 7)
      this.daysOfWeek = this.week;
    else{
      this.daysOfWeek = []
      this.daysSelect.forEach(element => {
        this.daysOfWeek.push(this.week[element.indexWeek - 1])
      });
    }
  }

  //Metodos de gestion de eventos
  confirmEvent(titleEvent:String, descriptioEvent: String){
    if(this.hourPicked != undefined && this.sectionSelected != undefined){
      const date: String = this.hourPicked.format("D/H")
      var elemTitle = document.getElementById(this.tit + date)
      var elemDesc = document.getElementById(this.des + date)
      this.modifyPickDom(titleEvent, elemTitle, descriptioEvent, elemDesc)
      this.hourPicked = undefined
    }
  }

  //Metodo de edicion de DOM
  modifyPickDom(titleText:String, titleBox: any, descriptionText: String, descriptionBox: any){
    this.sectionSelected.style.backgroundColor = "rgb(30, 149, 246)"
    this.sectionSelected.disabled = true
    titleBox.innerHTML  = titleText
    descriptionBox.innerText  = descriptionText
    this.sectionSelected = undefined
  }

  

  //Metodos de Click de DOM
  changeDaysRange(flag: any) {
    if (flag < 0) {
      const firstDate = this.daysSelect[0].value
      if(this.doubleMonth == 0){
        if(firstDate <= this.viewedDays){
          this.changeMonth(-1, firstDate)
        }
        else{
          this.getRangeDays(firstDate - this.viewedDays, 0)
        }
      }else if(this.doubleMonth < 0){
        this.changeMonthFromDoubleMonth(flag, firstDate, true)
      }else{
        this.changeMonthFromDoubleMonth(flag, firstDate, false)
      }
    } else {
      const lastDate = this.daysSelect[this.daysSelect.length - 1].value
      if(this.doubleMonth == 0){
        if((this.monthSelect.length - lastDate) < this.viewedDays){
          this.changeMonth(1, lastDate)
        }else{
          this.getRangeDays(lastDate + 1, 0)
        }
      }else if(this.doubleMonth > 0){
        this.changeMonthFromDoubleMonth(flag, lastDate, true)
      }else{
        this.changeMonthFromDoubleMonth(flag, lastDate, false)
      }
    }
    this.getDaysFromWeek()
    this.sendFirstDay()
  }
  sendFirstDay(){
    if(this.doubleMonth >= 0){
      this.pickDate.emit(moment(this.dateSelect.format("MM/") + this.daysSelect[0].value + this.dateSelect.format("/YYYY")))
    }else{
      
      this.pickDate.emit(moment(this.auxDateSelect.format("MM/") + this.daysSelect[0].value + this.auxDateSelect.format("/YYYY")))
    }
  }

  clickHour(hour: any, date: any, elemDom: any){
    if(this.doubleMonth < 0)
      if(date.value < this.viewedDays)
        this.hourPicked = this.convertToMoment(date, hour, false)
      else{
        this.hourPicked = this.convertToMoment(date, hour, true)
      }
    else if(this.doubleMonth > 0)
      if(date.value < this.viewedDays)
        this.hourPicked = this.convertToMoment(date, hour, true)
      else
        this.hourPicked = this.convertToMoment(date, hour, false)
    else 
      this.hourPicked = this.convertToMoment(date, hour, false)
    this.pickHourDay.emit(this.hourPicked)
    this.sectionSelected = elemDom;
  }

  clickDay(day: any){
    var date: moment.Moment;
    if(this.doubleMonth == 0)
      date = this.convertToMoment(day, 0, false)
    else if(this.doubleMonth > 0)
      if(day.value < this.viewedDays)
        date = this.convertToMoment(day, 0, true)
      else
        date = this.convertToMoment(day, 0, false)
    else
      if(day.value < this.viewedDays)
        date = this.convertToMoment(day, 0, false)
      else
        date = this.convertToMoment(day, 0, true)
    this.pickDate.emit(date)
  }

  receiveExternalDate(value: any){
    if(value != undefined){
      this.getDays(value)
    } 
  }

  //Metodos auxiliares
  convertToMoment(date: any, hour: any, dateAux: boolean): moment.Moment{
    if(dateAux)
      return moment(this.auxDateSelect.format("YYYY/M/")+
                      date.value+" "+hour+":00")
    return moment(this.dateSelect.format("YYYY/M/")+
                    date.value+" "+hour+":00")
  }
}
