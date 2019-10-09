import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBook, Book } from 'app/shared/model/book.model';
import { BookService } from './book.service';

@Component({
  selector: 'jhi-book-update',
  templateUrl: './book-update.component.html'
})
export class BookUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    title: [],
    author: [],
    year: [],
    publisher: [],
    pages: [],
    language: [],
    topic: [],
    identifier: [],
    filesize: [],
    extension: [],
    md5: [],
    coverurl: [],
    averageRating: [],
    ratingCount: [],
    originalPublicationYear: [],
    originalPublicationMonth: [],
    originalPublicationDay: []
  });

  constructor(protected bookService: BookService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ book }) => {
      this.updateForm(book);
    });
  }

  updateForm(book: IBook) {
    this.editForm.patchValue({
      id: book.id,
      title: book.title,
      author: book.author,
      year: book.year,
      publisher: book.publisher,
      pages: book.pages,
      language: book.language,
      topic: book.topic,
      identifier: book.identifier,
      filesize: book.filesize,
      extension: book.extension,
      md5: book.md5,
      coverurl: book.coverurl,
      averageRating: book.averageRating,
      ratingCount: book.ratingCount,
      originalPublicationYear: book.originalPublicationYear,
      originalPublicationMonth: book.originalPublicationMonth,
      originalPublicationDay: book.originalPublicationDay
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const book = this.createFromForm();
    if (book.id !== undefined) {
      this.subscribeToSaveResponse(this.bookService.update(book));
    } else {
      this.subscribeToSaveResponse(this.bookService.create(book));
    }
  }

  private createFromForm(): IBook {
    return {
      ...new Book(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      author: this.editForm.get(['author']).value,
      year: this.editForm.get(['year']).value,
      publisher: this.editForm.get(['publisher']).value,
      pages: this.editForm.get(['pages']).value,
      language: this.editForm.get(['language']).value,
      topic: this.editForm.get(['topic']).value,
      identifier: this.editForm.get(['identifier']).value,
      filesize: this.editForm.get(['filesize']).value,
      extension: this.editForm.get(['extension']).value,
      md5: this.editForm.get(['md5']).value,
      coverurl: this.editForm.get(['coverurl']).value,
      averageRating: this.editForm.get(['averageRating']).value,
      ratingCount: this.editForm.get(['ratingCount']).value,
      originalPublicationYear: this.editForm.get(['originalPublicationYear']).value,
      originalPublicationMonth: this.editForm.get(['originalPublicationMonth']).value,
      originalPublicationDay: this.editForm.get(['originalPublicationDay']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBook>>) {
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
