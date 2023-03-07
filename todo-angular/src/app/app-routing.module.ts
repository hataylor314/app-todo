import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ROUTES } from './Common/routes';

const routes: Routes = [
  { path: '', redirectTo: ROUTES.TODOLIST, pathMatch: 'full' },
  {
    path: ROUTES.TODOLIST, loadChildren:
      () => import('src/app/todo/todo.module').then(m => m.TodoModule)
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
