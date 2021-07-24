import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserCurrentComponent } from './user-current.component';

describe('UserCurrentComponent', () => {
  let component: UserCurrentComponent;
  let fixture: ComponentFixture<UserCurrentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserCurrentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserCurrentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
