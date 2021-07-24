import { Directive, DoCheck, ElementRef, Input, Renderer2 } from '@angular/core';
import { NgModel } from '@angular/forms';

@Directive({
  selector: '[appInvalidStyle]'
})
export class InvalidStyleDirective implements DoCheck {
  @Input() errors: any = {}

  constructor(
    private elementRef: ElementRef,
    private renderer: Renderer2,
    private model: NgModel
  ) { }

  ngDoCheck(): void {
    if (this.isInValid()) {
      this.renderer.addClass(this.elementRef.nativeElement, 'is-invalid')
    } else {
      this.renderer.removeClass(this.elementRef.nativeElement, 'is-invalid')
    }
  }

  private isInValid(): boolean {
    return (this.model.invalid && (this.model.dirty || this.model.touched)) || (this.errors[this.model.name]?.length && this.model.pristine)
  }
}
