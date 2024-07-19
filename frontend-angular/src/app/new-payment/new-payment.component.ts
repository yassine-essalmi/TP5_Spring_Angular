import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {PaymentType} from "../model/students.model";
import {StudentsService} from "../services/students.service";

@Component({
  selector: 'app-new-payment',
  templateUrl: './new-payment.component.html',
  styleUrl: './new-payment.component.css'
})
export class NewPaymentComponent implements OnInit {
  paymentFormGroup!: FormGroup;
  studentCode!: string
  paymentTypes: string[] = []

  pdfFileUrl!: string
  showProgress : boolean = false

  constructor(private fb: FormBuilder, private activatedRoute: ActivatedRoute , private studentsService : StudentsService) {
  }

  ngOnInit(): void {

    for (let elt in PaymentType) {
      let value = PaymentType[elt]
      if (typeof value === 'string') {
        this.paymentTypes.push(value);
      }
    }
    this.studentCode = this.activatedRoute.snapshot.params['studentCode']

    this.paymentFormGroup = this.fb.group({
      date: this.fb.control(''),
      amount: this.fb.control(''),
      type: this.fb.control(''),
      studentCode: this.fb.control(this.studentCode),
      fileName: this.fb.control(''),
      fileSource: this.fb.control('')
    })

  }

  selectFile(event: any) {
    if (event.target.files.length > 0) {
      let file = event.target.files[0];
      this.paymentFormGroup.patchValue({
        fileSource: file,
        fileName: file.name
      });
      this.pdfFileUrl = window.URL.createObjectURL(file);
    }
  }


  savePayment() {
    this.showProgress = true
    let date = new Date(this.paymentFormGroup.value.date);
    let formattedDate = date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();
    let formData = new FormData();
    formData.set('date', formattedDate);
    formData.set('amount', this.paymentFormGroup.value.amount);
    formData.set('type', this.paymentFormGroup.value.type);
    formData.set('studentCode', this.paymentFormGroup.value.studentCode);
    formData.set('file', this.paymentFormGroup.value.fileSource);

    this.studentsService.savePayment(formData).subscribe(
      {
        next: value => {
          alert('Payment Saved successfully');
          this.showProgress = false
        },
        error: err => {
          console.log(err);
        }
      }
    );
  }


  afterLoadComplete($event:any) {
    console.log("ILovePDF <3")

  }
}
