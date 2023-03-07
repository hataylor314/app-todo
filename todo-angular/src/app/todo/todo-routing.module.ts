import { NgModule } from '@angular/core';
import { RouterModule, Routes, RoutesRecognized } from '@angular/router';
import { ROUTES } from '../Common/routes';
import { TodoGestionComponent } from './todo-gestion/todo-gestion.component';
import { TodoListComponent } from './todo-list/todo-list.component';

const routes: Routes = [
  { path: '',  component : TodoListComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TodoRoutingModule { }
