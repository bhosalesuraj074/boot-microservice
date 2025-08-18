import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HotToastService } from '@ngneat/hot-toast';
import { first, Observable, of } from 'rxjs';
import { AccoutOpenService } from 'src/app/services/accout-open.service';

@Component({
  selector: 'update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css'],
})
export class UpdateUserComponent implements OnInit {
  userId!: number;
  user!: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: AccoutOpenService,
    private hotToast: HotToastService
  ) {}

  ngOnInit(): void {
    // Get the user ID from the route parameters
    this.route.params.subscribe((params) => {
      this.userId = +params['id'];
      console.log('User ID from route:', this.userId);
    });

    this.createForm();
    this.getUserDataById();
  }

  // 🔹 create form with validations
  createForm() {
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

  // get user data by ID
  getUserDataById() {
    this.service
      .getAccountById(this.userId)
      .pipe(
        this.hotToast.observe({
          success: 'User data loaded successfully',
          error: 'Failed to load user data',
          loading: 'Loading user data...',
        })
      )
      .subscribe((response: any) => {
        if (response.status === 200) {
          this.user.patchValue(response.data);
          console.log('User data:', response.data);
        } else {
          this.hotToast.error('Failed to load user data');
        }
      });
  }

  updateUserData() {
    if (this.user.valid) {
      this.service
        .updateAccount(this.userId, this.user.value)
        .pipe(
          this.hotToast.observe({
            success: 'User data updated successfully',
            error: 'Failed to update user data',
            loading: 'Updating user data...',
          })
        )
        .subscribe((response: any) => {
          if (response.status === 200) {
            this.router.navigate(['/account-list']);
          } else {
            this.hotToast.error('Failed to update user data');
          }
        });
    } else {
      this.user.markAllAsTouched();
    }
  }

  // 🔹 custom async email validator
  mailValidation(control: AbstractControl): Observable<any> {
    const email = control.value;
    if (!email) return of(null);
    const isValid = email.endsWith('@gmail.com');
    return of(isValid ? null : { invalidEmail: true }).pipe(first());
  }

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

  onSubmit() {
    this.service
      .updateAccount(this.userId, this.user.value)
      .pipe(
        this.hotToast.observe({
          success: 'User updated successfully',
          error: 'Failed to update user',
          loading: 'Updating user...',
        })
      )
      .subscribe((response: any) => {
        if (response.status === 200) {
          this.router.navigate(['/account-list']);
        } else {
          this.hotToast.error('Failed to update user');
        }
      });
  }

  onReset() {
    this.user.reset();

  }
}
