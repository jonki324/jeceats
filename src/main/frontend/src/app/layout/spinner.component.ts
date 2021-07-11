import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { SpinnerService } from '../services/spinner.service';

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html'
})
export class SpinnerComponent implements OnInit {
  isLoading: Subject<boolean> = this.spinnerService.isLoading

  constructor(private spinnerService: SpinnerService) { }

  ngOnInit(): void {
  }

}
