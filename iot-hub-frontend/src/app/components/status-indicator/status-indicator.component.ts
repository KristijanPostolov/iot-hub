import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'status-indicator',
  templateUrl: './status-indicator.component.html',
  styleUrls: ['./status-indicator.component.scss']
})
export class StatusIndicatorComponent implements OnInit {

  @Input() status?: string;
  @Input() showText = false;

  constructor() { }

  ngOnInit() {
  }

  getStyle(): string {
    if ('ACTIVE' === this.status) {
      return 'status active';
    } else if ('PAIRED' === this.status) {
      return 'status paired';
    } else if ('INACTIVE' === this.status) {
      return 'status inactive';
    }
    return '';
  }

  getTextStyle() {
    if ('ACTIVE' === this.status) {
      return 'text active';
    } else if ('PAIRED' === this.status) {
      return 'text paired';
    } else if ('INACTIVE' === this.status) {
      return 'text inactive';
    }
    return '';
  }

}
