import { Component, EventEmitter, OnInit, Output, AfterViewChecked} from '@angular/core';
import * as moment from 'moment'

@Component({
  selector: 'app-calendar-date-picker',
  templateUrl: './calendar-date-picker.component.html',
  styleUrls: ['./calendar-date-picker.component.css']
})
export class CalendarDatePickerComponent implements OnInit, AfterViewChecked{

  @Output() changeDatePicked = new EventEmitter<moment.Moment>();

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

  //Variables de gestion de mes
  dateSelect: any;
  monthSelect: any[];

  //modificador de DOM
  sectionSelected: any;

  //fechaActual
  currentDate: moment.Moment;
  currentDatePicked: boolean = true
  necesaryRepaintCurrentBox: boolean

  //fecha seleccionada
  datePicked: moment.Moment;
  newDatePicked: moment.Moment;

  constructor() {}

  ngOnInit(): void {
    var currentDate = moment();
    const month: number = parseInt(currentDate.format("M"))
    const year: number = parseInt(currentDate.format("YYYY"))
    this.getDaysFromDate( month, year)
    this.currentDate = moment(currentDate.format("YYYY/M/D"))
    this.datePicked = this.currentDate
    this.necesaryRepaintCurrentBox = true
  }

  ngAfterViewChecked(){
    if(this.newDatePicked != undefined && this.newDatePicked.isSame(this.currentDate) == false){
      if(this.newDatePicked.isSame(this.datePicked) == false){
        var elem = document.getElementById(this.newDatePicked.format('D'))
        this.modifyPick(elem)
        this.datePicked = this.newDatePicked
        this.currentDatePicked = false
      }
    }else{
      if(this.currentDatePicked == false){
        this.sectionSelected.style.backgroundColor = "rgb(255, 255, 255)"
        this.datePicked = this.newDatePicked
        this.currentDatePicked = true
      }
    }
    if(this.necesaryRepaintCurrentBox){
      var elem = document.getElementById(this.currentDate.format('D'))
      elem.style.backgroundColor = "rgb(82, 137, 226)"
      this.necesaryRepaintCurrentBox = false
    }
  }

  receiveExternalDate(externalDate: moment.Moment){
    if(externalDate != undefined){

      const month: string = externalDate.format("M")
      const year: string = externalDate.format("YYYY")
      
      if(this.dateSelect.format('M') != month || this.dateSelect.format('YYYY') != year){
        this.getDaysFromDate(month,year)
        this.checkCurrentDate()
      }
      this.newDatePicked = externalDate
    }
  }

  getDaysFromDate(month: any, year: any) {

    const startDate = moment(`${year}/${month}/01`)
    const endDate = startDate.clone().endOf('month')
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

    this.monthSelect = arrayDays;
  }

  checkCurrentDate(){
    if(this.dateSelect.format("M/YYYY") == this.currentDate.format("M/YYYY")){
      if(this.dateSelect.format("D") == this.currentDate.format("D")){
        this.datePicked = this.currentDate
        this.currentDatePicked = true   
      }else
        this.currentDatePicked = false
        this.necesaryRepaintCurrentBox = true
    }
  }

  changeMonth(flag: any) {
    if (flag < 0) {
      const prevDate = this.dateSelect.clone().subtract(1, "month");
      this.getDaysFromDate(prevDate.format("MM"), prevDate.format("YYYY"));
    } else {
      const nextDate = this.dateSelect.clone().add(1, "month");
      this.getDaysFromDate(nextDate.format("MM"), nextDate.format("YYYY"));
    }
    this.checkCurrentDate()
  }

  clickDay(day: any) {
    const monthYear = this.dateSelect.format('YYYY-MM')
    const parse = `${monthYear}-${day.value}`
    const objectDate = moment(parse)
    this.newDatePicked = objectDate
    if(this.newDatePicked.isSame(this.datePicked) == false)
      this.changeDatePicked.emit(this.newDatePicked)
  }

  modifyPick(elem: any){
    elem.style.backgroundColor = "rgb(175, 238, 238)";
    if(this.sectionSelected != undefined)
      this.sectionSelected.style.backgroundColor = "rgb(255, 255, 255)"
    this.sectionSelected = elem;
  }
}
