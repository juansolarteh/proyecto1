import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import { Moment } from 'moment';


@Component({
  selector: 'app-full-calendar',
  templateUrl: './full-calendar.component.html',
  styleUrls: ['./full-calendar.component.css']
})
export class FullCalendarComponent implements OnInit {

  dateWithHour: any
  currentDate: Moment
  titleEvent = "titulo 1"
  descriptionEvent = "descripcion 1"

  constructor() { }

  ngOnInit(): void {
    const currentDate = moment()
    this.currentDate = moment(currentDate.format("YYYY/M/D"))
  }
}
