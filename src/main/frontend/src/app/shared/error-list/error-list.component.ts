import { Component, Input, OnChanges } from '@angular/core';
import { NgModel } from '@angular/forms';

@Component({
  selector: 'app-error-list',
  templateUrl: './error-list.component.html',
  styleUrls: ['./error-list.component.css'],
  host: {
    class: 'invalid-feedback'
  }
})
export class ErrorListComponent implements OnChanges {
  @Input() errors: any = {}

  @Input() model: NgModel = {} as NgModel

  list: string[] = []

  constructor() { }

  ngOnChanges(): void {
    this.list = this.errors[this.model.name] ?? []
  }
}
