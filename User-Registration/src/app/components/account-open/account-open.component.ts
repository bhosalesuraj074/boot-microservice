import { Component, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  Validators,
  AsyncValidatorFn,
  AbstractControl,
} from '@angular/forms';
import { Router } from '@angular/router';
import { HotToastService } from '@ngneat/hot-toast';
import { Observable, of } from 'rxjs';
import { first } from 'rxjs/operators';
import { AccoutOpenService } from 'src/app/services/accout-open.service';

@Component({
  selector: 'account-open',
  templateUrl: './account-open.component.html',
  styleUrls: ['./account-open.component.css'],
})
export class AccountOpenComponent implements OnInit {
  user!: FormGroup;
  constructor(
    private accountService: AccoutOpenService,
    private toast: HotToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  // Create the FormGroup with user registration info
  createForm(): void {
    this.user = new FormGroup({
      firstName: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
      ]),
      lastName: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
      ]),
      email: new FormControl(
        '',
        [Validators.required, Validators.email],
        [this.mailValidation.bind(this)]
      ),
      phoneNumber: new FormControl('', [
        Validators.required,
        Validators.pattern('^[0-9]{10}$'),
      ]),
      dateOfBirth: new FormControl(
        '',
        [Validators.required],
        [this.validateDateOfBirth.bind(this)]
      ),

      account: new FormGroup({
        aadhaarNumber: new FormControl('', [
          Validators.required,
          Validators.pattern('^[0-9]{12}$'),
        ]),

        accountType: new FormControl('', [Validators.required]),
      }),
      address: new FormGroup({
        street: new FormControl('', [Validators.required]),
        city: new FormControl('', [Validators.required]),
        state: new FormControl('', [Validators.required]),
        country: new FormControl('', [Validators.required]),
        postalCode: new FormControl('', [
          Validators.required,
          Validators.pattern('^[0-9]{6}$'),
        ]),
      }),
    });
  }

  // Async email validator to ensure email ends with @gmail.com
  mailValidation(control: AbstractControl): Observable<any> {
    const email = control.value;
    if (control.invalid) {
      return of(null); // No email provided, no validation needed
    }
    const isValid = email.endsWith('@gmail.com');
    return of(isValid ? null : { invalidEmail: true }).pipe(first());
  }

  //birth should be in the past & age must greater 18
  validateDateOfBirth(control: AbstractControl): Observable<any> {
    const selectedDate = new Date(control.value);
    const today = new Date();
    const age = today.getFullYear() - selectedDate.getFullYear();
    const isFutureDate = selectedDate > today;
    if (isFutureDate) {
      return of({ futureDate: true }).pipe(first());
    } else if (age < 18) {
      return of({ ageLimit: true }).pipe(first());
    } else {
      return of(null).pipe(first());
    }
  }

  // Submit handler
  onSubmit(): void {
    if (this.user.valid) {
      // hot toast
      this.accountService
        .createAccount(this.user.value)
        .pipe(
          this.toast.observe({
            success: 'Account created successfully!',
            loading: 'Creating account...',
            error: 'Failed to create account. Please try again.',
          })
        )
        .subscribe({
          next: (response) => {
            console.log('Account created successfully:', response);
            this.router.navigate(['/account-list']);
          },
        });
      // console.log('Form Submitted:', this.user.value);
      this.user.markAllAsTouched();
      this.user.markAsPristine();
      this.user.markAsUntouched();
    } else {
      console.log('Form Invalid');
      console.log('Form Submitted:', this.user.value);
    }
  }

  goBack() {
    this.router.navigate(['/home']);
  }
}
