import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
@Component({
  selector: 'bluebits-create-client',
  templateUrl: './create-client.component.html',
  styleUrls: ['./create-client.component.css']
})

export class CreateClientComponent implements OnInit {
  client_form = this.formBuilder.group({
    clientName: [null,Validators.required],
    emailAdrress: [null,Validators.required],
    primaryContact:[null,Validators.required],
    secondaryContact:[null,Validators.required],
  });
 
  constructor( private formBuilder: FormBuilder) { }

  ngOnInit(): void {
  }
  onSubmit(form:any)
  {
console.log(form.value);
  }
}
