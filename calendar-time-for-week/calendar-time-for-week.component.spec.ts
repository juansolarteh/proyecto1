import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarTimeForWeekComponent } from './calendar-time-for-week.component';

describe('CalendarTimeForWeekComponent', () => {
  let component: CalendarTimeForWeekComponent;
  let fixture: ComponentFixture<CalendarTimeForWeekComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalendarTimeForWeekComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CalendarTimeForWeekComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
