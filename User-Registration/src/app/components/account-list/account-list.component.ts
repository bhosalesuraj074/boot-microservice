import { Component, OnInit } from '@angular/core';
import {  Router } from '@angular/router';
import { HotToastService } from '@ngneat/hot-toast';
import { AccoutOpenService } from 'src/app/services/accout-open.service';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css'],
})
export class AccountListComponent implements OnInit {
  userList: any = [];
  counter: number = 0;
  constructor(
    private service: AccoutOpenService,
    private hotToast: HotToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    console.log('AccountListComponent initialized');
    this.getAllUserList();
  }

  // get the all user list
  getAllUserList() {
    this.service
      .getAccountList()
      .pipe(
        this.hotToast.observe({
          loading: 'Loading user list...',
          error: 'Failed to load user list',
          success: 'User list loaded successfully',
        })
      )
      .subscribe((userList: any) => {
        console.log('User list received:', userList);
        if (userList.status == 200) {
          this.userList = userList.data;
        } else {
          this.hotToast.error('Failed to load user list');
        }
      });
  }

  deleteUser(id: number) {
    this.service
      .deleteAccount(id)
      .pipe(
        this.hotToast.observe({
          loading: 'Deleting user...',
          error: 'Failed to delete user',
          success: 'User deleted successfully',
        })
      )
      .subscribe((response: any) => {
        this.getAllUserList();
      });
  }

  editUser(id: number) {
    this.router.navigate(['/update-user', id]);
  }


}

