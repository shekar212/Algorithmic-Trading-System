import { Component } from '@angular/core';
import { UserOrder } from '../models/userOrder';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonService } from '../common.service';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-user-orders',
  templateUrl: './user-orders.component.html',
  styleUrls: ['./user-orders.component.css']
})
export class UserOrdersComponent {
  orders:UserOrder
  token = localStorage.getItem("token") ;
  dataId
  constructor(private http: HttpClient, private router:Router, private route: ActivatedRoute, private commonService:CommonService) {
   this.orders = {meta:{cycles:0, stocks:0, interval:"", targetPercent:0, stopLoss:0},orders:[]}
   this.dataId=Number(this.route.snapshot.paramMap.get('dataId'));
  }

  ngOnInit(): void{
       this.http.get<UserOrder>(environment.urlForUserOrders+`/${this.dataId}`,{
      headers: new HttpHeaders()
      .set('Authorization',  `Bearer ${this.token}`)
  }).subscribe({
    next:data=>this.orders = data,
    error: error => {
      //redirect to login
      setTimeout(() => 
      {
        this.router.navigate([`/logout`]);
      },
      3000);
  }
  });
  }
  
}
