import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { TodoService } from 'src/openapi-services';
import { DetailedTodoDTO } from 'src/openapi-services/model/detailedTodoDTO';
@Component({
  selector: 'app-todo-gestion',
  templateUrl: './todo-gestion.component.html',
  styleUrls: ['./todo-gestion.component.scss']
})
export class TodoGestionComponent implements OnInit {

  constructor(private fb: FormBuilder,
              private dialogRef: MatDialogRef<TodoGestionComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private toastrService: ToastrService,
              private todoService: TodoService) { }

  public consultMode = false;
  public createMode = false;
  public loading = false;
  public submitting = false;
  public submitted = false;

  private detailedTodo: DetailedTodoDTO;


  public modeleForm = this.fb.group({
    id: [undefined],
    title: [undefined, [Validators.required]],
    done: [false],
    description: [undefined, [Validators.maxLength(255)]]
  });

  ngOnInit(): void {
    if(this.data){
      this.checkDataMode();
    }
  }

  hasError(control: FormControl): boolean {
    return control.invalid && (control.dirty || this.submitted);
  }
  get isPristineForm(): boolean {
    return !this.modeleForm.dirty || this.consultMode;
  }

  close(){
    this.dialogRef.close();
  }

  save(){
    this.submitted = true;
    if (this.modeleForm.invalid) {
      this.toastrService.error(
        "Le formulaire est invalide ! Veuillez vérifier chaque champ marqué d'une erreur",
        undefined,
        { timeOut: 5000 }
      );
      return false;
    }
    this.submitting = true;
    this.loading = true;
    if(this.createMode){ // Création
      this.detailedTodo = this.modeleForm.getRawValue();
      this.todoService.addTodo(this.detailedTodo).subscribe(() => {
        this.loading = false;
        this.submitting = false;
        this.toastrService.success("L'ajout de la To-do a été réalisé avec succès.");
        this.dialogRef.close();
      }, (error) => {
        this.toastrService.error(`Erreur lors de la création de la To-do. Code erreur : ${error.status}`)
      });
    }else{ // Edition
      this.todoService.updateTodoState(this.id.value, this.done.value).subscribe(() => {
        this.loading = false;
        this.submitting = false;
        this.toastrService.success("La modification de la To-do a été réalisée avec succès.");
        this.dialogRef.close();
      }, (error) => {
        this.toastrService.error(`Erreur lors de la modification de la To-do. Code erreur : ${error.status}`)
      });
    }
  }

  get id(): FormControl {
    return this.modeleForm.get('id') as FormControl;
  }
  get title(): FormControl {
    return this.modeleForm.get('title') as FormControl;
  }
  get done(): FormControl {
    return this.modeleForm.get('done') as FormControl;
  }
  get description(): FormControl {
    return this.modeleForm.get('description') as FormControl;
  }

  checkDataMode(){
    if(this.data?.mode){
      switch (this.data.mode){
        case "Edit":{
          this.createMode = false;
          this.consultMode = false;
          this.getTodoAndPatchForm();
          break;
        }
        case "Create":{
          this.createMode = true;
          break;
        }
        case "Consult":{
          this.createMode = false;
          this.consultMode = true;
          this.getTodoAndPatchForm();
          break;
        }
      }
    }
  }

  getTodoAndPatchForm(){
    if(this.data?.todo){
      this.loading = true;
      this.todoService.getTodoById(this.data.todo.id).subscribe((res: DetailedTodoDTO) => {
        if(res){
          this.patchForm(res);
          this.loading = false;
        }
      }, (error) => {
        this.toastrService.error(`Erreur lors de la récupération de la To-do à modifier. Code erreur : ${error.status}`);
      })
    }
  }

  patchForm(detailedTodo: DetailedTodoDTO){
    this.modeleForm.patchValue({
      id: detailedTodo.id,
      title: detailedTodo.title,
      description: detailedTodo.description,
      done: detailedTodo.done
    })
    this.title.disable();
    this.description.disable();
  }
}
