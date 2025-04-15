import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../common.service';
import { StrategiesList } from '../models/strategies';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-user-strategy',
  templateUrl: './user-strategy.component.html',
  styleUrls: ['./user-strategy.component.css']
})
export class UserStrategyComponent {
  strategies:StrategiesList[] = []
  token = localStorage.getItem("token") ;
  errorMessage = "";
  constructor(private http: HttpClient, private router:Router, private commonService:CommonService) {
   
  }

  ngOnInit(): void {
    this.commonService.fetchUserStrategies(this.token).subscribe({
      next:data=>this.strategies = data,
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

  deploys(id: number){
    this.http.get<any>(environment.urlForBacktesting+`/${id}`,
   {
    headers: new HttpHeaders()
    .set('Authorization',  `Bearer ${localStorage.getItem('token')}`)
}).subscribe(
    {
      next: data => {
        if(data.status == `deployed`){
          
          this.router.navigate([`/user-orders/${id}`]);
        }
         
        else
        this.errorMessage = "error";
          
        },
      error: error => {
        this.router.navigate([`/logout`]);
      }
  }
   );
  }
  }

