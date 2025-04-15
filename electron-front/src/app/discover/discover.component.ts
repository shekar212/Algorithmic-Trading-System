import { Component, OnInit } from '@angular/core';
import { StrategiesList } from '../models/strategies';
import { header } from '../http-header/auth-header';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonService } from '../common.service';

@Component({
  selector: 'app-discover',
  templateUrl: './discover.component.html',
  styleUrls: ['./discover.component.css']
})
export class DiscoverComponent implements OnInit{
  strategies:StrategiesList[] = []
  token = localStorage.getItem("token") ;
  constructor(private http: HttpClient, private router:Router, private commonService:CommonService) {
   
  }

  ngOnInit(): void {
    this.commonService.fetchStrategies(this.token).subscribe({
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

}
