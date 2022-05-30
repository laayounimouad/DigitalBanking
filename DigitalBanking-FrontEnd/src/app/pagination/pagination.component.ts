import { Component, OnInit } from '@angular/core';
import {CustomerService} from "../services/customer.service";
import {map} from "rxjs";

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit {
  pageNumber!:0
  constructor(private customerService: CustomerService) { }

  ngOnInit(): void {
    this.customerService.getCustomers().pipe(map(d=>{
      this.pageNumber++
    }))
  }

}
