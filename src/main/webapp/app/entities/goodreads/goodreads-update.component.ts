import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IGoodreads, Goodreads } from 'app/shared/model/goodreads.model';
import { GoodreadsService } from './goodreads.service';

@Component({
  selector: 'jhi-goodreads-update',
  templateUrl: './goodreads-update.component.html'
})
export class GoodreadsUpdateComponent implements OnInit {
  isSaving: boolean;
  lastUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    bookId: [],
    gRBookId: [],
    ratingCount: [],
    textReviewsCount: [],
    averageRating: [],
    lastUpdated: []
  });

  constructor(protected goodreadsService: GoodreadsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ goodreads }) => {
      this.updateForm(goodreads);
    });
  }

  updateForm(goodreads: IGoodreads) {
    this.editForm.patchValue({
      id: goodreads.id,
      bookId: goodreads.bookId,
      gRBookId: goodreads.gRBookId,
      ratingCount: goodreads.ratingCount,
      textReviewsCount: goodreads.textReviewsCount,
      averageRating: goodreads.averageRating,
      lastUpdated: goodreads.lastUpdated
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const goodreads = this.createFromForm();
    if (goodreads.id !== undefined) {
      this.subscribeToSaveResponse(this.goodreadsService.update(goodreads));
    } else {
      this.subscribeToSaveResponse(this.goodreadsService.create(goodreads));
    }
  }

  private createFromForm(): IGoodreads {
    return {
      ...new Goodreads(),
      id: this.editForm.get(['id']).value,
      bookId: this.editForm.get(['bookId']).value,
      gRBookId: this.editForm.get(['gRBookId']).value,
      ratingCount: this.editForm.get(['ratingCount']).value,
      textReviewsCount: this.editForm.get(['textReviewsCount']).value,
      averageRating: this.editForm.get(['averageRating']).value,
      lastUpdated: this.editForm.get(['lastUpdated']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoodreads>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
