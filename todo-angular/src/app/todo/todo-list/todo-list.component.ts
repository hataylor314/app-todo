import { Component, OnInit } from '@angular/core';
import {TodoService } from 'src/openapi-services';
import { ToastrService} from 'ngx-toastr';
import { MatDialog} from '@angular/material/dialog';
import { TodoGestionComponent } from '../todo-gestion/todo-gestion.component';
import { ROUTES } from 'src/app/Common/routes';
import { TodoDTO } from 'src/openapi-services/model/todoDTO';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.scss']
})
export class TodoListComponent implements OnInit {

  listOfTodo: Array<TodoDTO> = [];
  loading = false;

  routes = ROUTES;

  constructor(private todoService: TodoService,
    private notifier : ToastrService,
    private dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadTodo()
  }

  loadTodo(){
    this.loading = true;
    this.todoService.getAllTodos().subscribe((listOfTodo: Array<TodoDTO>) => {
      this.listOfTodo = listOfTodo;
      this.loading = false;
    }, (error) => {
      this.notifier.error(`Oups, nous n'avons pas pu récupérer les todos. Code erreur : ${error.status}`);
    });
  }

  openModale(mode: string, todo: TodoDTO ){
    this.dialog.open(TodoGestionComponent,
      {
        data : {
          mode : mode,
          todo: mode == 'Add'? undefined : todo
        },
        width: "600px",
        height: "600px"
      }
      ).afterClosed().subscribe(res => {
        this.loadTodo();
      })
  }

  editAuthorized(todo: TodoDTO): boolean{
    return todo.done == false;
  }

}
