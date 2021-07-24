import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-user-current',
  templateUrl: './user-current.component.html',
  styleUrls: ['./user-current.component.css']
})
export class UserCurrentComponent implements OnInit {
  user: User = {} as User

  constructor(
    private usersService: UsersService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.getUser()
  }

  goBack(): void {
    this.location.back()
  }

  getUser(): void {
    this.user = this.usersService.getCurrentUser()
  }
}
