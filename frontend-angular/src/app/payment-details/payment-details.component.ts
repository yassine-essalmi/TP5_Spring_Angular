import {Component, OnInit} from '@angular/core';
import {StudentsService} from "../services/students.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-payment-details',
  templateUrl: './payment-details.component.html',
  styleUrl: './payment-details.component.css'
})
export class PaymentDetailsComponent implements OnInit{

  paymentId! : number ;
  pdfFileUrl : any ;

  constructor(private studentsService :StudentsService , private route : ActivatedRoute) {
  }
  ngOnInit(): void {
    this.paymentId = this.route.snapshot.params['id'];
    this.studentsService.getPaymentDetails(this.paymentId).subscribe({
      next: value => {
        let mediaType = 'application/pdf';
        let blob: Blob = new Blob([value], { type: mediaType });
        this.pdfFileUrl = window.URL.createObjectURL(blob);
      },
      error: err => {
        console.log(err);
      }
    });
  }


  afterLoadComplete(event:any) {

  }
}
