import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'bluebits-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.css']
})
export class CreateProjectComponent implements OnInit {
  
  clientlist:any=[];
  project_form:FormGroup;
  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
   this.project_form = this.formBuilder.group({
      projectName: [null,Validators.required],
      startDateValue: ['',[Validators.required]],
      endDateValue:['',[Validators.required]],
      clientId:[null,Validators.required],
    });
    this.clientlist = [{
      clientId: 1,
      clientName: 'FTP'
    }, {
      clientId: 2,
      clientName: 'AB'
    }, {
      clientId: 3,
      clientName: 'SPS'
    }]
  }
  onSubmit(form:any)
  {
console.log(form.value);
  }
}
