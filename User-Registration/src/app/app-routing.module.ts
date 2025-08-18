import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AccountOpenComponent } from './components/account-open/account-open.component';
import { AccountListComponent } from './components/account-list/account-list.component';
import { UpdateUserComponent } from './components/update-user/update-user.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path:'home', component: HomeComponent},
  { path : 'account-open', component: AccountOpenComponent },
  { path: 'account-list', component: AccountListComponent },
  {path: 'update-user/:id', component: UpdateUserComponent },
  { path: '**', redirectTo: '/home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
