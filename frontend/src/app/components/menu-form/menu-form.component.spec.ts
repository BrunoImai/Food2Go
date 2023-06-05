/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { MenuFormComponent } from './menu-form.component';

describe('MenuFormComponent', () => {
  let component: MenuFormComponent;
  let fixture: ComponentFixture<MenuFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
