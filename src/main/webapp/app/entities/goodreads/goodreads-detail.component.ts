import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoodreads } from 'app/shared/model/goodreads.model';

@Component({
  selector: 'jhi-goodreads-detail',
  templateUrl: './goodreads-detail.component.html'
})
export class GoodreadsDetailComponent implements OnInit {
  goodreads: IGoodreads;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ goodreads }) => {
      this.goodreads = goodreads;
    });
  }

  previousState() {
    window.history.back();
  }
}
